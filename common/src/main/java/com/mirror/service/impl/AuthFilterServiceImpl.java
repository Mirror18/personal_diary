package com.mirror.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.config.SecurityConfig;
import com.mirror.constant.CommonConstant;
import com.mirror.exception.BizException;
import com.mirror.exception.LoginException;
import com.mirror.result.BaseUserInfoDTO;
import com.mirror.service.AuthFilterService;
import com.mirror.service.TokenService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mirror
 */
@ConditionalOnProperty(prefix = "sys", name = "enable-my-security", havingValue = "true")
@Component
@Slf4j
public class AuthFilterServiceImpl<T> implements AuthFilterService<T> {
    @Resource
    final TokenService<T> tokenService;
    @Resource
    final AntPathMatcher antPathMatcher;
    @Resource
    final SecurityConfig securityConfig;
    @Resource
    final ObjectMapper jsonMapper;
    @Resource
    final HandlerExceptionResolver handlerExceptionResolver;
    @Resource
    final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AuthFilterServiceImpl(TokenService<T> tokenService,
                                 AntPathMatcher antPathMatcher,
                                 SecurityConfig securityConfig,
                                 ObjectMapper jsonMapper,
                                 @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver,
                                 RedisTemplate<String, Object> redisTemplate) {
        this.tokenService = tokenService;
        this.antPathMatcher = antPathMatcher;
        this.securityConfig = securityConfig;
        this.jsonMapper = jsonMapper;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.redisTemplate = redisTemplate;
    }

    //过滤器
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //没开启安全模式的话,就继续处理,过滤器不做处理
            if (securityConfig == null || !securityConfig.getEnable()) {
                filterChain.doFilter(request, response);
                return;
            }

            //开启的话,就检测用户信息
            T userInfo = null;
            if ("token".equals(securityConfig.getGetUserType())) {
                //检测请求头
                String token = request.getHeader("api-access-token");
                userInfo = tokenService.checkToken(token);
            }
            //如果是有网关,那么就串化user信息
            if ("gateway".equals(securityConfig.getGetUserType())) {
                String userInfoJson = request.getHeader("user");
                userInfo = jsonMapper.readValue(userInfoJson, new TypeReference<T>() {
                });
            }
            //处理
            if (userInfo == null) {
                throw new LoginException("无法获取到用户信息");
            }
            //用户信息格式化
            BaseUserInfoDTO baseUserInfoDTO = (BaseUserInfoDTO) userInfo;
            //检查权限
            checkPermissions(baseUserInfoDTO.getSysRoleIds(), request.getServletPath());
            //T userInfo = userService.getRedisUser(tokenResponse.getToken());
            // 用户信息存储在线程中
            tokenService.setThreadLocalUser(userInfo);
            filterChain.doFilter(request, response);
            //然后移除,因为请求和响应已经交给下一步的处理了.
            tokenService.removeThreadLocalUser();
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response,
                    null, ex);
        }
    }

    /**
     * 不经过过滤器筛选
     *
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //什么意思呢,就是设置security,可以跳过某些路径,可以不用经过过滤器
        if (securityConfig == null || !securityConfig.getEnable() || CollectionUtils.isEmpty(securityConfig.getIgnores())) {
            return false;
        }
        //得到URL的路径值
        String path = request.getServletPath();
        //匹配自己设置在application中的ignore的环境
        boolean ignore = securityConfig.getIgnores().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
        //如果处于debug模式下的话.
        if (log.isDebugEnabled()) {
            log.info("path: {} [ignore: {}]", path, ignore);
        }
        return ignore;
    }

    /**
     * 检查权限
     */
    public void checkPermissions(Set<Long> sysRoleIds, String path) {
        //如果是管理员则不检查权限，拥有所有权限
        if (sysRoleIds.contains(CommonConstant.ROLE_ADMIN)) {
            return;
        }
        Set<String> roleBindResourcePaths = listRoleResourcePathByCache(sysRoleIds);
        if (CollectionUtils.isEmpty(roleBindResourcePaths)) {
            throw new BizException("角色没有绑定任何资源");
        }
        for (String url : roleBindResourcePaths) {
            if (antPathMatcher.match(url, path)) {
                return;
            }
        }
        throw new BizException("非法访问");
    }

    /**
     * 从缓存中获取角色对应的菜单id
     *
     * @param roleIds
     * @return
     */
    private Set<String> listRoleResourcePathByCache(Set<Long> roleIds) {
        //从redis中获取
        HashOperations<String, String, Set<String>> hashOps = redisTemplate.opsForHash();
        List<Set<String>> roleMenuIds = hashOps.multiGet(CommonConstant.ROLE_RESOURCE_PERMISSIONS, roleIds.stream().map(String::valueOf).collect(Collectors.toSet()));
        // 对结果进行处理，将 List<Set<String>> 转为 Set<String>
        return roleMenuIds.stream()
                .filter(p -> !CollectionUtils.isEmpty(p))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}

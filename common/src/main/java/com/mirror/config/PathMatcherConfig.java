package com.mirror.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

/**
 * 开启安全检查的配置
 * @author mirror
 */
@ConditionalOnProperty(prefix = "sys",name = "enable-my-security",havingValue = "true")
@Configuration
public class PathMatcherConfig {
    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}

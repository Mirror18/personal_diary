package com.mirror.service.common.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.constant.Enum.SysConfigTypeEnum;
import com.mirror.convertor.ObjectConvertor;
import com.mirror.dto.AdminDTO;
import com.mirror.dto.SmsTemplateDTO;
import com.mirror.entity.SysConfig;
import com.mirror.exception.BizException;
import com.mirror.form.login.SaveSendSmsCodeTemplateConfigForm;
import com.mirror.mapper.wrapper.MyBatisWrapper;
import com.mirror.mapper.wrapper.member.SysConfigMapper;
import com.mirror.service.TokenService;
import com.mirror.service.common.SysConfigService;
import com.mirror.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.mirror.mapper.wrapper.member.Field.SysConfigField.*;

/**
 * @author mirror
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {
    final SysConfigMapper mapper;
    final ObjectConvertor objectConvertor;
    final TokenService<AdminDTO> tokenService;
    final ObjectMapper jsonMapper;
    final RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存短信模板
     *
     * @param form
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public boolean saveSendSmsCodeTemplateConfig(SaveSendSmsCodeTemplateConfigForm form) throws JsonProcessingException {
        SmsTemplateDTO smsTemplateDTO = new SmsTemplateDTO();
        smsTemplateDTO.setSignName(form.getSignName());
        smsTemplateDTO.setTemplateCode(form.getTemplateCode());
        String configValue = jsonMapper.writeValueAsString(smsTemplateDTO);
        MyBatisWrapper<SysConfig> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id)
                .whereBuilder()
                .andEq(Type, SysConfigTypeEnum.SEND_SMS_CODE_TEMPLATE.getCode())
                .andEq(ConfigKey, form.getConfigKey());
        SysConfig sysConfig = mapper.get(wrapper);
        int updateRows = 0;
        if (sysConfig == null) {
            sysConfig = new SysConfig();
            sysConfig.setConfigName(form.getConfigName());
            sysConfig.setType(SysConfigTypeEnum.SEND_SMS_CODE_TEMPLATE.getCode());
            sysConfig.setConfigKey(form.getConfigKey());
            sysConfig.setConfigValue(configValue);
            sysConfig.initDefault();
            sysConfig.setMemberId(tokenService.getThreadLocalUserId());
            sysConfig.setUpdateMemberId(tokenService.getThreadLocalUserId());
            sysConfig.setDelFlag(false);
            sysConfig.setDisable(false);
            updateRows = mapper.insert(sysConfig);
        } else {
            wrapper
                    .update(ConfigName, form.getConfigName())
                    .update(ConfigValue, configValue)
                    .update(UpdateMemberId, tokenService.getThreadLocalUserId())
                    .update(UpdateTime, DateUtil.getSystemTime());

            updateRows = mapper.updateField(wrapper);
        }
        if (updateRows == 0) {
            throw new BizException("保存短信模板失败");
        }
        redisTemplate.opsForValue().set(SysConfigTypeEnum.SEND_SMS_CODE_TEMPLATE + ":" + form.getConfigKey(),
                smsTemplateDTO);
        return true;
    }

    /**
     * 从缓存中获取短信模板
     *
     * @param configKey
     * @return
     */
    @Override
    public SmsTemplateDTO getSmsTemplateByCache(String configKey) {
        return (SmsTemplateDTO) redisTemplate.opsForValue().get(SysConfigTypeEnum.SEND_SMS_CODE_TEMPLATE + ":" + configKey);
    }
}
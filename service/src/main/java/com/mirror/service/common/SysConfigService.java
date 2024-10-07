package com.mirror.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mirror.dto.SmsTemplateDTO;
import com.mirror.form.login.SaveSendSmsCodeTemplateConfigForm;

/**
 * @author mirror
 */
public interface SysConfigService {
    /**
     * 保存短信模板
     * @param form
     * @return
     * @throws JsonProcessingException
     */
    boolean saveSendSmsCodeTemplateConfig(SaveSendSmsCodeTemplateConfigForm form) throws JsonProcessingException;

    /**
     * 从缓存中获取短信模板
     *
     * @param configKey
     * @return
     */
    SmsTemplateDTO getSmsTemplateByCache(String configKey);
}
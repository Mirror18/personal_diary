package com.mirror.dto;

import lombok.Data;

/**
 * @author mirror
 */
@Data
public class SmsTemplateDTO {
    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板编号
     */
    private String templateCode;
}
package com.mirror.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用属性代替访问资源，也就是创造一个bean，
 * @author mirror
 */
//配置注解
@Configuration
//@Component
//要导入的根名称，代替了@value即，从这里导入资源
@ConfigurationProperties(prefix = "ali")
@Data
public class ALiProperties {
    //这里这些字段都是照应，application-local.yml中ali的设置
    private String accessKeyId;

    private String accessKeySecret;
    //因为这一块是用类表明的
    private ALiSmsConfig sms;

    @Data
    public static class ALiSmsConfig {
        /**
         * 是否启用
         */
        private Boolean enable;
        /**
         * 区域
         */
        private String region;
        /**
         * 端点
         */
        private String endpoint;
    }
}
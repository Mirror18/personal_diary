package com.mirror.config;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.mirror.properties.ALiProperties;
import darabonba.core.client.ClientOverrideConfiguration;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mirror
 */
@Configuration
//根据final生成构造器
@RequiredArgsConstructor
//当这个属性生效的时候才能注入这个配置
@ConditionalOnProperty(prefix = "ali.sms", name = "enable", havingValue = "true")
public class ALiSmsConfig {
    @Resource
    final ALiProperties aLiProperties;
    //注入的bean
    @Bean
    public AsyncClient asyncClient() {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                //这里代替的是@value注入的配置，用bean代替
                .accessKeyId(aLiProperties.getAccessKeyId())
                .accessKeySecret(aLiProperties.getAccessKeySecret())
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                // Region ID
                .region(aLiProperties.getSms().getRegion())
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(aLiProperties.getSms().getEndpoint())
                )
                .build();
        return client;
    }
}

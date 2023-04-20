package com.sky.config;

import com.sky.properties.QiniuProperties;
import com.sky.utils.QiniuOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public QiniuOssUtil qiniuOssUtil(QiniuProperties qiniuProperties) {
        log.info("开始创建七牛云文件上传工具类对象{}", qiniuProperties);
        return new QiniuOssUtil(
                qiniuProperties.getAccessKey(),
                qiniuProperties.getSecretKey(),
                qiniuProperties.getBucket(),
                qiniuProperties.getDomain()
        );
    }
}

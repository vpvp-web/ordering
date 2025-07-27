package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS配置类
 * 用于配置对象存储服务（OSS）的相关属性和Bean
 */

@Configuration
@Slf4j
public class OssConfiguration {
    /**
     * 创建AliOssUtil的Bean
     * 该方法将从配置文件中读取OSS相关的属性，并创建一个AliOssUtil实例
     *
     * @param properties 从配置文件中读取的AliOssProperties实例
     * @return 返回一个AliOssUtil实例
     */
    // 注入AliOssProperties配置类
     @Bean
     @ConditionalOnMissingBean // 如果没有其他的AliOssUtil Bean存在，则创建一个新的
     public AliOssUtil aliOssUtil(AliOssProperties properties) {
         return new AliOssUtil(properties.getEndpoint(), properties.getAccessKeyId(),
                 properties.getAccessKeySecret(), properties.getBucketName());
     }
}

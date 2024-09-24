package com.mashiro.utils.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfiguration {

    /**
     * 配置RedisTemplate，用于处理字符串和对象类型的Redis操作
     *
     * @param redisConnectionFactory Redis连接工厂，用于建立Redis连接
     * @return 配置好的RedisTemplate实例，能够处理字符串键和对象值的序列化
     */
    @Bean
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置Redis连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 设置键的序列化方式为字符串序列化
        template.setKeySerializer(RedisSerializer.string());
        // 设置值的序列化方式为Java序列化
        template.setValueSerializer(RedisSerializer.java());
        // 返回配置好的RedisTemplate实例
        return template;
    }

}

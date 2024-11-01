package com.mashiro.utils.generatorId;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * RedisIdWork类用于生成基于Redis的唯一ID
 * 它通过结合时间戳和自增计数器来生成ID，确保在同一天内生成的ID是唯一的
 */
@Component
public class RedisIdWord {

    // 注入StringRedisTemplate用于操作Redis
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // BEGIN_TIMESTAMP定义了起始时间戳，用于计算从特定时间点（2002年12月31日 23:59:59）到现在的秒数
    private static final long BEGIN_TIMESTAMP = 1032307200;

    // COUNT_BITS定义了计数器所占用的位数，这里使用32位
    private static final long COUNT_BITS = 32;

    /**
     * 生成并返回下一个ID
     * 该方法结合了当前时间戳和一个自增计数器，以确保在同一天内生成的ID是唯一的
     *
     * @param keyPrefix 键前缀，用于区分不同业务或客户的ID生成
     * @return 生成的唯一ID
     */
    public long nextId(String keyPrefix) {

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 将当前时间转换为自Unix纪元以来的秒数
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);

        // 计算从BEGIN_TIMESTAMP到现在的秒数
        long timeStamp = nowSecond - BEGIN_TIMESTAMP;

        // 格式化当前日期为"年:月:日"的形式
        String nowDay = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));

        // 在Redis中递增键的值，键的格式为"icr" + keyPrefix + 当前日期
        // 这个值代表了当天的自增计数器
        long conunt = stringRedisTemplate.opsForValue().increment("icr" + keyPrefix + nowDay);

        // 通过位操作结合时间戳和计数器，生成最终的ID
        // 时间戳左移COUNT_BITS位，然后与计数器进行按位或操作
        return timeStamp << COUNT_BITS | conunt;
    }
}
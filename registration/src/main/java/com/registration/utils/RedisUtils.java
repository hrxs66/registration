package com.registration.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component //项目运行时就注入Spring容器
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redis;

    //赋值一个静态的redisTemplate
    public static RedisTemplate redisTemplate;

    @PostConstruct //此注解表示构造时赋值
    public void redisTemplate() {
        redisTemplate = this.redis;
    }
}


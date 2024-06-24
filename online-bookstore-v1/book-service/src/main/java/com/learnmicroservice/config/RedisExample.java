package com.learnmicroservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisExample implements CommandLineRunner {
    private final RedisTemplate template;

    @Override
    public void run(String... args) {
        // Set giá trị của key "hello" là "redis"
        template.opsForValue().set("hello","world");

        // In ra màn hình giá trị của key "hello" trong Redis
        System.out.println("Value of key hello: "+template.opsForValue().get("hello"));
    }
}
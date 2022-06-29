package com.xps.redis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public String redisTest() {
        redisTemplate.opsForValue().set("name", "xiepengsheng");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }
}

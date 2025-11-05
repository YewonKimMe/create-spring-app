package com.github.YewonKimMe.create_spring_app.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(String key, String token, long duration, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, token, duration, timeUnit);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}

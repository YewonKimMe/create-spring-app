package com.github.YewonKimMe.create_spring_app.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@ActiveProfiles("dev")
class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory; // 주입된 팩토리

    @Test
    void redisConnectionTest() {

        // 1. 팩토리에서 직접 호스트/포트 정보 조회
        // (LettuceConnection 객체가 아닌 LettuceConnectionFactory 객체에 정보가 있습니다)
        if (redisConnectionFactory instanceof LettuceConnectionFactory lettuceFactory) {
            String host = lettuceFactory.getHostName();
            int port = lettuceFactory.getPort();
            System.out.println("✅ 현재 Redis 연결 정보: " + host + ":" + port);
        } else {
            System.out.println("✅ 현재 Redis 팩토리: " + redisConnectionFactory.getClass().getSimpleName());
        }

        // --- 기존 테스트 로직 ---
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // given
        String key = "test:key";
        String value = "hello redis";

        // when
        ops.set(key, value);
        String result = ops.get(key);

        // then
        assertThat(result).isEqualTo(value);
        System.out.println("✅ Redis 테스트 성공! 값 = " + result);
    }
}
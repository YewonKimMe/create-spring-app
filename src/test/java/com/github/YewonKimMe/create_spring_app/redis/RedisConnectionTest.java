package com.github.YewonKimMe.create_spring_app.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@ActiveProfiles("dev")
class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void redisConnectionTest() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // given
        String key = "test:key";
        String value = "hello redis";

        // when
        ops.set(key, value);
        String result = ops.get(key);

        // then
        assertThat(result).isEqualTo(value);
        System.out.println("✅ Redis 연결 성공! 값 = " + result);
    }
}

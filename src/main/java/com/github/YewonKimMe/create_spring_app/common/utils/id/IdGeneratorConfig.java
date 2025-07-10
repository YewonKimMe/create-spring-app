package com.github.YewonKimMe.create_spring_app.common.utils.id;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Value("${datacenter.id}")
    private long datacenterId;

    @Value("${server.id}")
    private long serverId;

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(datacenterId, serverId);
    }
}

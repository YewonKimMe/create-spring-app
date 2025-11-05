package com.github.YewonKimMe.create_spring_app.common.utils.id;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Value("${datacenters.id}")
    private long datacenterId;

    @Value("${servers.id}")
    private long serverId;

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(datacenterId, serverId);
    }
}

package com.github.YewonKimMe.create_spring_app.security.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.security.auth")
@Getter
@Setter
public class AuthProperties {
    private Boolean useSession;

    @PostConstruct
    public void logProperties() {
        log.info("[AuthProperties] useSession = {}", useSession);
    }
}

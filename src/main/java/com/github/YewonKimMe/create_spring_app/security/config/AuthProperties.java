package com.github.YewonKimMe.create_spring_app.security.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.security.auth")
@Getter
@Setter
public class AuthProperties {
    private Boolean useSession;
    private List<String> permitUrls;

    private String loginUrl;
    private String logoutUrl;
    private String adminUrlPattern;
    private String userUrlPattern;
    private String baseUrl;

    @PostConstruct
    public void logProperties() {
        log.info("[AuthProperties] useSession = {}", useSession);
        log.info("[AuthProperties] permitUrls = {}", permitUrls);
        log.info("[AuthProperties] baseUrl = {}", baseUrl);
        log.info("[AuthProperties] loginUrl = {}", loginUrl);
        log.info("[AuthProperties] logoutUrl = {}", logoutUrl);
        log.info("[AuthProperties] adminUrlPattern = {}", adminUrlPattern);
        log.info("[AuthProperties] userUrlPattern = {}", userUrlPattern);
    }
}

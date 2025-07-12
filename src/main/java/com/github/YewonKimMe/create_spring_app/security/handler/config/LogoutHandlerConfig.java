package com.github.YewonKimMe.create_spring_app.security.handler.config;

import com.github.YewonKimMe.create_spring_app.security.config.AuthProperties;
import com.github.YewonKimMe.create_spring_app.security.handler.JwtLogoutHandler;
import com.github.YewonKimMe.create_spring_app.security.handler.SessionLogoutHandler;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@RequiredArgsConstructor
@Configuration
public class LogoutHandlerConfig {

    private final AuthProperties authProperties;

    private final TokenProvider tokenProvider;

    @Bean
    public LogoutHandler logoutHandler() {

        if (authProperties.getUseSession()) {
            return new SessionLogoutHandler();
        }

        return new JwtLogoutHandler(tokenProvider);
    }
}

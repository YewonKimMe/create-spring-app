package com.github.YewonKimMe.create_spring_app.security.service.config;

import com.github.YewonKimMe.create_spring_app.security.config.AuthProperties;
import com.github.YewonKimMe.create_spring_app.security.service.auth.LoginSuccessHandler;
import com.github.YewonKimMe.create_spring_app.security.service.auth.jwt.JwtLoginSuccessHandler;
import com.github.YewonKimMe.create_spring_app.security.service.auth.session.SessionLoginSuccessHandler;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenProvider;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoginSuccessHandlerConfig {

    private final AuthProperties authProperties;

    private final TokenProvider tokenProvider;

    private final TokenStore tokenStore;

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        if (authProperties.getUseSession()) {
            return new SessionLoginSuccessHandler();
        }
        return new JwtLoginSuccessHandler(tokenProvider, tokenStore);
    }
}

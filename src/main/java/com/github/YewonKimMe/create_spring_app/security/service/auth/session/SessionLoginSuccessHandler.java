package com.github.YewonKimMe.create_spring_app.security.service.auth.session;

import com.github.YewonKimMe.create_spring_app.security.service.auth.LoginSuccessHandler;
import com.github.YewonKimMe.create_spring_app.security.service.auth.Username;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@Slf4j
public class SessionLoginSuccessHandler implements LoginSuccessHandler {
    @Override
    public void handleLoginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response) {

        Username username = new Username(auth.getName());

        log.info("🔓 User '{}' logged in successfully.", username.getName());

    }
}

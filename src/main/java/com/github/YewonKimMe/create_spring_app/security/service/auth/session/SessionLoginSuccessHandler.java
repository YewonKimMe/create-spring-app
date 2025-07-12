package com.github.YewonKimMe.create_spring_app.security.service.auth.session;

import com.github.YewonKimMe.create_spring_app.security.service.auth.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public class SessionLoginSuccessHandler implements LoginSuccessHandler {
    @Override
    public void handleLoginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response) {

    }
}

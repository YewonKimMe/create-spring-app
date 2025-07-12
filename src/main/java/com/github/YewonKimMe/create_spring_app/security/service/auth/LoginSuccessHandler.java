package com.github.YewonKimMe.create_spring_app.security.service.auth;

import com.github.YewonKimMe.create_spring_app.security.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface LoginSuccessHandler {
    void handleLoginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response);
}

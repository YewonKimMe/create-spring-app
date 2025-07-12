package com.github.YewonKimMe.create_spring_app.security.utils;

import com.github.YewonKimMe.create_spring_app.security.enums.TokenType;
import com.github.YewonKimMe.create_spring_app.security.enums.TokenValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TokenProvider {

    String generateToken(Authentication auth, TokenType tokenType, long duration);

    TokenValidationResult validateToken(String token);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    List<String> getAuthorities(String token);

    String resolveToken(HttpServletRequest req);

    String generateRefreshToken(Authentication auth);

    void saveRefreshToken(String redisKey, String refreshToken);

    void deleteRefreshToken(String redisKey);

    String getRefreshToken(String redisKey);

    TokenValidationResult validateRefreshToken(String refreshToken);

    void addBlackList(Authentication auth, String token);

    void deleteBlackList(Authentication auth, String token);
}

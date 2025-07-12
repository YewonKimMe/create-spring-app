package com.github.YewonKimMe.create_spring_app.security.service.auth.jwt;

import com.github.YewonKimMe.create_spring_app.security.enums.SecurityConst;
import com.github.YewonKimMe.create_spring_app.security.enums.TokenDurationTime;
import com.github.YewonKimMe.create_spring_app.security.enums.TokenType;
import com.github.YewonKimMe.create_spring_app.security.service.auth.LoginSuccessHandler;
import com.github.YewonKimMe.create_spring_app.security.service.auth.Username;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenProvider;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtLoginSuccessHandler implements LoginSuccessHandler {

    private final TokenProvider tokenProvider;

    private final TokenStore tokenStore;

    @Override
    public void handleLoginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
        // 토큰 발급 및 저장, 응답 첨부
        Username username = new Username(auth.getName());

        String accessToken = generateAccessToken(auth, username); // access 생성 및 저장
        String refreshToken = generateAndStoreRefreshToken(auth, username); // refresh 생성 및 저장

        attachTokenToResponse(response, accessToken); //  response token 첨부
        // log 저장

        // 로그인 성공 로그 작성
        String tokenSnippet = accessToken != null && accessToken.length() > 20
                ? accessToken.substring(0, 10) + "..." + accessToken.substring(accessToken.length() - 10)
                : accessToken;

        log.info("🔓 User '{}' logged in successfully. Access token snippet: {}", username.getName(), tokenSnippet);

    }

    private String generateAccessToken(Authentication auth, Username username) {
        String token = tokenProvider.generateToken(auth, TokenType.ACCESS, TokenDurationTime.ACCESS.getHour());
        String key = username.generateKey(TokenType.ACCESS);
        return token;
    }

    private String generateAndStoreRefreshToken(Authentication auth, Username username) {
        String token = tokenProvider.generateRefreshToken(auth);
        String key = username.generateKey(TokenType.REFRESH);
        tokenProvider.saveRefreshToken(key, token);
        return token;
    }

    private void attachTokenToResponse(HttpServletResponse response, String accessToken) {
        String bearer = SecurityConst.BEARER_PREFIX.getValue() + accessToken;
        response.setHeader(SecurityConst.AUTH_HEADER.getValue(), bearer);
    }
}

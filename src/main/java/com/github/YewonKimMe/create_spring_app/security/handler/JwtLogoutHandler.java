package com.github.YewonKimMe.create_spring_app.security.handler;

import com.github.YewonKimMe.create_spring_app.security.enums.TokenType;
import com.github.YewonKimMe.create_spring_app.security.service.auth.Username;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Username username = new Username(authentication.getName());

        // 1. 토큰 추출
        String accessToken = tokenProvider.resolveToken(request);

        // 2. Access blacklist 등록
        tokenProvider.addBlackList(authentication, accessToken);

        // 3. 리프레시 토큰 제거
        tokenProvider.deleteRefreshToken(username.generateKey(TokenType.REFRESH));

        // 4. 로깅
        String tokenSnippet = accessToken != null && accessToken.length() > 20
                ? accessToken.substring(0, 10) + "..." + accessToken.substring(accessToken.length() - 10)
                : accessToken;
        log.info("\uD83D\uDD10 User '{}' logged out successfully. tokenSnippet: {}", username.getName(), tokenSnippet);
    }
}

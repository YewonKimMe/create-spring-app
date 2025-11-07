package com.github.YewonKimMe.create_spring_app.security.filter;

import com.github.YewonKimMe.create_spring_app.security.enums.*;
import com.github.YewonKimMe.create_spring_app.security.service.auth.Username;
import com.github.YewonKimMe.create_spring_app.security.utils.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.YewonKimMe.create_spring_app.security.enums.UrlList.*;

@Component
@RequiredArgsConstructor
public class JwtValidatorFilter extends OncePerRequestFilter {

    /**
     * Token Validator Filter
     * 토큰 유효성 검사 (인증)
     * /login, /api/v1/sign-up 은 제외
     * */

    private final TokenProvider tokenProvider;

    // JWT 검증을 건너뛸 경로 목록
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            LOGIN.getUrl(),
            SIGNUP.getUrl()
            // 여기에 JWT 검증이 필요 없는 다른 공개 엔드포인트를 추가
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = tokenProvider.resolveToken(request);

        if (token == null || token.isBlank()) {
            cleanContextAndContinue(request, response, filterChain);
            return;
        }

        TokenValidationResult tokenValidationResult = tokenProvider.validateToken(token);

        switch (tokenValidationResult) {
            case VALID: {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }
            case EXPIRED: {
                // refresh token check
                String rawUsername = tokenProvider.getUsername(token);
                Username username = new Username(rawUsername);

                String redisKey = username.generateKey(TokenType.REFRESH);

                String refreshToken = tokenProvider.getRefreshToken(redisKey);

                if (refreshToken == null || refreshToken.isBlank()) {
                    cleanContextAndContinue(request, response, filterChain);
                    return;
                }

                TokenValidationResult refTokenValidationResult = tokenProvider.validateRefreshToken(refreshToken);

                if (refTokenValidationResult == TokenValidationResult.VALID) {

                    Authentication authentication = tokenProvider.getAuthentication(refreshToken);

                    String newAccessToken = tokenProvider.generateToken(authentication, TokenType.ACCESS, TokenDurationTime.REFRESH.getHour());

                    String bearer = SecurityConst.BEARER_PREFIX.getValue() + newAccessToken;

                    response.setHeader(SecurityConst.AUTH_HEADER.getValue(), bearer);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    cleanContextAndContinue(request, response, filterChain);
                    return;
                }
                filterChain.doFilter(request, response);
                return;
            }
            default: {
                cleanContextAndContinue(request, response, filterChain);
            }
        }
    }

    private void cleanContextAndContinue(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        return EXCLUDE_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, servletPath))
        || !pathMatcher.match("/api/v1/**", servletPath);

    }
}

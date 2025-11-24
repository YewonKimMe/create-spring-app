package com.github.YewonKimMe.create_spring_app.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.YewonKimMe.create_spring_app.security.config.AuthProperties;
import com.github.YewonKimMe.create_spring_app.security.enums.*;
import com.github.YewonKimMe.create_spring_app.security.exception.dto.SecurityErrorResponse;
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
@Component
@RequiredArgsConstructor
public class JwtValidatorFilter extends OncePerRequestFilter {

    private final AuthProperties authProperties;

    /**
     * Token Validator Filter
     * 토큰 유효성 검사 (인증)
     * /login, /api/v1/sign-up 은 제외
     * */

    private final TokenProvider tokenProvider;

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
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                SecurityErrorResponse errorResponse = new SecurityErrorResponse(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized",
                        "TOKEN_EXPIRED",
                        request.getServletPath()
                );
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().write(mapper.writeValueAsString(errorResponse));
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

        // JWT 검증을 건너뛸 경로 목록
        return authProperties.getPermitUrls().stream().anyMatch(pattern -> pathMatcher.match(pattern, servletPath))
        || !pathMatcher.match(authProperties.getBaseUrl() + "/**", servletPath);

    }
}

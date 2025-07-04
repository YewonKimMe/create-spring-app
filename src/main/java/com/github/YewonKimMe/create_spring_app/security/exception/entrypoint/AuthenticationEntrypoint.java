package com.github.YewonKimMe.create_spring_app.security.exception.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.YewonKimMe.create_spring_app.security.exception.dto.SecurityErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * AuthenticationEntrypoint: 인증 과정에서 예외 발생 시 응답 리턴하는 클래스
 * 필요에 맞게 커스텀 가능
 * */
@RequiredArgsConstructor
@Component
public class AuthenticationEntrypoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 응답 형식 지정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");


        // 커스텀 에러 메세지 생성
        SecurityErrorResponse errorResponse = new SecurityErrorResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized",
                "로그인 또는 인증이 필요합니다.",
                request.getRequestURI()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}

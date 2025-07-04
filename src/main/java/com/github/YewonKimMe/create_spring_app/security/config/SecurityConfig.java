package com.github.YewonKimMe.create_spring_app.security.config;

import com.github.YewonKimMe.create_spring_app.security.enums.Role;
import com.github.YewonKimMe.create_spring_app.security.enums.SecurityConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    /**
     *  JWT 기반 Spring Security Configuration 입니다.
     *  LOGIN: POST /login, JSON 기반 로그인 요청
     *  Session: STATELESS
     *
     *
     * */

    @Value("${spring.security.cors.client.dev-cors-allowed-url}")
    private String DEV_CORS_ALLOWED_URL; // 개발 클라이언트 CORS 허용 URL

    @Value("${spring.security.cors.client.prod-cors-allowed-url}")
    private String PROD_CORS_ALLOWED_URL; // 프로덕션 클라이언트 CORS 허용 URL

    private final AuthenticationConfiguration authenticationConfiguration;
    private final LogoutHandler logoutHandler;


    // Filter Chain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (Header 기반 JWT)
                .formLogin(AbstractHttpConfigurer::disable) // formLogin 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .securityContext(configurer -> configurer
                    .requireExplicitSave(false)) // false: 인증 성공 시 SecurityContext 자동 저장
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler()
                                .deleteCookies("JSESSIONID", "access_token")
                                .permitAll()
                )
                .cors(corsConfigurer -> corsConfigurer.configurationSource(
                        request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();

                            // 클라이언트가 보낼 수 있는 요청 헤더 목록
                            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                            // 클라이언트가 응답에서 접근 가능한 헤더 목록
                            corsConfiguration.setExposedHeaders(List.of(SecurityConst.AUTH_HEADER.getValue()));
                            // 허용할 HTTP Method
                            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                            corsConfiguration.setMaxAge(3600L); // preflight request cache 유효 시간
                            corsConfiguration.setAllowCredentials(true); // cors 요청에서 자격 증명 전송 허용
                            // 허용할 클라이언트 URl 목록
                            corsConfiguration.setAllowedOrigins(List.of(
                                    DEV_CORS_ALLOWED_URL,
                                    PROD_CORS_ALLOWED_URL
                            ));
                            return corsConfiguration;
                        }
                ))
                // Endpoint 인증/인가 여부 설정
                // 엔드포인트에 맞게 변경하면 됩니다.
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers("/api/v1/sign-up").permitAll()
                                // 역할 기반 인가 예시
                                .requestMatchers("/api/v1/admin/**").hasRole(Role.ADMIN.getRole())
                                .requestMatchers("/api/v1/member/**").hasRole(Role.MEMBER.getRole())

                                // ...
                                .anyRequest().authenticated() // 외의 모든 요청은 인증 필요
                )
                .exceptionHandling(
                        configurer -> configurer.authenticationEntryPoint()

                );

        return http.build();
    }

    /**
     * PasswordEncoder: BCryptPasswordEncoder
     * 비밀번호 해시 저장
     * 로그인, 인증 등이 필요한 클래스에서 PasswordEncoder 타입으로 주입
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager: default
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

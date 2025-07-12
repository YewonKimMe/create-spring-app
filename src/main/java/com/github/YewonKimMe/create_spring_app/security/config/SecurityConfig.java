package com.github.YewonKimMe.create_spring_app.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.YewonKimMe.create_spring_app.security.enums.Role;
import com.github.YewonKimMe.create_spring_app.security.enums.SecurityConst;
import com.github.YewonKimMe.create_spring_app.security.exception.entrypoint.AuthenticationEntrypoint;
import com.github.YewonKimMe.create_spring_app.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.github.YewonKimMe.create_spring_app.security.filter.JwtValidatorFilter;
import com.github.YewonKimMe.create_spring_app.security.handler.CustomAuthenticationFailureHandler;
import com.github.YewonKimMe.create_spring_app.security.handler.CustomAuthenticationSuccessHandler;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
     *  JsonUsernamePasswordAuthenticationFilter -> JSON 기반 로그인
     *  HttpRequest body 에 {@link com.github.YewonKimMe.create_spring_app.security.dto.LoginRequest} 형식 JSON 요청 전송 -> 로그인 처리
     * */

    @Value("${spring.security.cors.client.dev-cors-allowed-url}")
    private String DEV_CORS_ALLOWED_URL; // 개발 클라이언트 CORS 허용 URL

    @Value("${spring.security.cors.client.prod-cors-allowed-url}")
    private String PROD_CORS_ALLOWED_URL; // 프로덕션 클라이언트 CORS 허용 URL

    private final AuthProperties authProperties; // 보안 프로퍼티

    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationEntrypoint authenticationEntryPoint;
    private final LogoutHandler logoutHandler;
    private final JwtValidatorFilter jwtValidatorFilter;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;

    private Boolean useSession() {
        return authProperties.getUseSession();
    }

    // Filter Chain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(useSession() ? AbstractHttpConfigurer::disable : config -> {}) // CSRF 비활성화 (Header 기반 JWT)
                .formLogin(AbstractHttpConfigurer::disable) // formLogin 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .securityContext(configurer -> configurer
                    .requireExplicitSave(false)) // false: 인증 성공 시 SecurityContext 자동 저장
                .sessionManagement(
                        session -> {
                            if (useSession()) {
                                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                            } else {
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            }
                        }
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> {

                                }))
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
                // 인증-인가 예외 entry-point
                .exceptionHandling(
                        configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint)
                )
                // UserDetailsService
                .userDetailsService(userDetailsService)
                // 로그인 및 인증 필터 세팅
                // jwtValidatorFilter: 로그인 필터 이전에 실행(요청 검증용), 로그인 성공 시 Context 저장
                // JsonUsernamePasswordAuthenticationFilter: POST /login 에서만 동작, 로그인 필터로 동작
                .addFilterBefore(jwtValidatorFilter, UsernamePasswordAuthenticationFilter.class) // JWT 검증 필터
                .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 로그인 필터

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

    @Bean
    public JsonUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter(new ObjectMapper());
        filter.setAuthenticationManager(authenticationManager()); // AuthenticationManager 주입
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler); // failureHandler 주입
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler); // successHandler 주입
        return filter;
    }
}

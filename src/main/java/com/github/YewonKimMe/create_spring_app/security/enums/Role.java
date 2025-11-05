package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    PREFIX("ROLE_"),
    USER("USER"),   // 일반 회원
    ADMIN("ADMIN"),     // 관리자
    GUEST("GUEST");     // 비회원/게스트

    private final String role;
}

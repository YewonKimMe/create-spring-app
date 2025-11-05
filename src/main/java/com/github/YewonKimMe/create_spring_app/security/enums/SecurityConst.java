package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SecurityConst {

    AUTH_HEADER("Authorization"),

    BEARER_PREFIX("Bearer "),

    ACCESS_TOKEN_COOKIE_NAME("access_token");

    private final String value;
}

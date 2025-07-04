package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SecurityConst {

    AUTH_HEADER("Authorization"),

    BEARER_PREFIX("Bearer ");

    private final String value;
}

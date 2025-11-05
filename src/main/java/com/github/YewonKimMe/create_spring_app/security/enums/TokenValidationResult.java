package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;

@Getter
public enum TokenValidationResult {
    VALID,
    EXPIRED,
    INVALID_SIGNATURE,
    MALFORMED,
    UNSUPPORTED,
    EMPTY,
    UNKNOWN,
    INVALID;
}

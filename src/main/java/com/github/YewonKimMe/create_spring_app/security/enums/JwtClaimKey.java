package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtClaimKey {
    ISSUER("iss"),
    TYPE("type"),
    SUBJECT("sub"),
    PROVIDER("provider"),
    USERNAME("username"),
    AUTHORITIES("authorities");

    private final String claimKey;
}

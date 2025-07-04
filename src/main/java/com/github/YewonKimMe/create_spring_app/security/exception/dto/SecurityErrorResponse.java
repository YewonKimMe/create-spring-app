package com.github.YewonKimMe.create_spring_app.security.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SecurityErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}

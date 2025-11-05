package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public enum TokenDurationTime {

    ACCESS(1), // 1시간

    REFRESH(336); // 2주

    private final long hour;

    public long getHour() {
        return hour;
    }

    public static TimeUnit getTimeUnit() {
        return TimeUnit.HOURS;
    }
}

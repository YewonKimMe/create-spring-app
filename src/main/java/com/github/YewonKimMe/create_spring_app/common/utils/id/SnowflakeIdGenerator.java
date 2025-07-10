package com.github.YewonKimMe.create_spring_app.common.utils.id;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public class SnowflakeIdGenerator implements IdGenerator {

    // 기준 에포크 (2024-01-01 00:00:00 UTC)
    public static final long EPOCH = 1704067200000L;

    // 비트 할당
    private static final int TIMESTAMP_BITS = 41;
    private static final int DATACENTER_BITS = 5;
    private static final int SERVER_BITS = 5;
    private static final int SEQUENCE_BITS = 12;

    // 최대값 계산
    private static final long MAX_DATACENTER = ~(-1L << DATACENTER_BITS);
    private static final long MAX_SERVER = ~(-1L << SERVER_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 시프트 연산
    private static final int SERVER_SHIFT = SEQUENCE_BITS;
    private static final int DATACENTER_SHIFT = SEQUENCE_BITS + SERVER_BITS;
    private static final int TIMESTAMP_SHIFT = SEQUENCE_BITS + SERVER_BITS + DATACENTER_BITS;

    private final long datacenterId;
    private final long serverId;

    private final AtomicLong lastTimestamp = new AtomicLong(-1L);
    private final AtomicLong sequence = new AtomicLong(0L);

    public SnowflakeIdGenerator(long datacenterId, long serverId) {
        if (datacenterId > MAX_DATACENTER || datacenterId < 0) {
            throw new IllegalArgumentException("데이터센터 ID 오류 (0~31)");
        }
        if (serverId > MAX_SERVER || serverId < 0) {
            throw new IllegalArgumentException("서버 ID 오류 (0~31)");
        }
        this.datacenterId = datacenterId;
        this.serverId = serverId;
    }

    /*
     * lock-based Snowflake ID 생성
     * */
    @Override
    public synchronized long generateId() {
        long currentTime = System.currentTimeMillis();

        if (currentTime < lastTimestamp.get()) {
            throw new RuntimeException("Clock reversal occurred: " + System.currentTimeMillis());
        }

        if (currentTime == lastTimestamp.get()) {
            sequence.set((sequence.get() + 1) & MAX_SEQUENCE);
            if (sequence.get() == 0) { // 시퀀스 오버플로
                currentTime = waitNextMillis(currentTime);
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp.set(currentTime);
        return buildId(currentTime, sequence.get());
    }

    private long waitNextMillis(long currentTime) {
        long newTime;
        do {
            newTime = System.currentTimeMillis();
        } while (newTime <= currentTime);
        return newTime;
    }

    /*
     * 64bit Snowflake ID build
     * */
    private long buildId(long timestamp, long sequence) {
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_SHIFT)
                | (serverId << SERVER_SHIFT)
                | sequence;
    }
}
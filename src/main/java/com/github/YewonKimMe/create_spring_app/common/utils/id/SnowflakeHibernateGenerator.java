package com.github.YewonKimMe.create_spring_app.common.utils.id;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

@RequiredArgsConstructor
public class SnowflakeHibernateGenerator implements BeforeExecutionGenerator {

    private final IdGenerator generator;

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        return generator.generateId(); // Snowflake ID 생성;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT); // INSERT 시에만 ID 생성
    }
}

package com.github.YewonKimMe.create_spring_app.common.utils.id.annotation;

import com.github.YewonKimMe.create_spring_app.common.utils.id.SnowflakeHibernateGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@IdGeneratorType(SnowflakeHibernateGenerator.class)
public @interface SnowflakeGenerated {
}
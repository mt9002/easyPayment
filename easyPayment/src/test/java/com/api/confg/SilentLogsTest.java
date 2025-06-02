package com.api.confg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.test.context.TestPropertySource;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestPropertySource(properties = {
    "logging.level.root=OFF",
    "logging.level.org.hibernate.SQL=OFF",
    "logging.level.org.hibernate.engine.jdbc.spi.SqlExceptionHelper=OFF",
    "logging.level.com.api=OFF"
})
public @interface SilentLogsTest {}

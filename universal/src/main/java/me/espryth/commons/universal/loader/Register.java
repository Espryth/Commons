package me.espryth.commons.universal.loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Register {

    Type type();

    String message() default "";

    int priority () default 0;

    enum Type {

        LOAD,
        ENABLE,
        DISABLE

    }

}

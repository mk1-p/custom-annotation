package com.example.customannotation.custom.slack;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SlackMessaging {

    String channel() default "#back_sys_noti";
    String title() default "Running";
    String message() default "";
    ColorType color() default ColorType.GREEN;

}

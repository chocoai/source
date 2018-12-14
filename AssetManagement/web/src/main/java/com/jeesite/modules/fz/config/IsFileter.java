package com.jeesite.modules.fz.config;

import java.lang.annotation.*;

/**
 * 如果为true,则需要使用token来验证
 */
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsFileter {
    String isFile() default "false";
}

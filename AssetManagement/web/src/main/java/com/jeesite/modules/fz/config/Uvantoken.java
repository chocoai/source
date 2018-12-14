package com.jeesite.modules.fz.config;

import java.lang.annotation.*;

/**
 * @author easter
 * @data 2018/12/5 8:54
 */
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Uvantoken {
    String getUvanToken() default "false";
}

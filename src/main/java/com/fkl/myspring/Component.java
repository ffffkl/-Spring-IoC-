package com.fkl.myspring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//作用在类上
@Target(ElementType.TYPE)
//设置运行时机
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    //Component注解默认为空，也可以设置值
    String value() default "";
}

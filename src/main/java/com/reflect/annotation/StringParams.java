package com.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jrsen on 16-4-29.
 * 用于用String array描述方法或者构造器的参数类型
 * (如果参数类型为基本数据类型则用对应的包装类型代替)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface StringParams {
    String[] value();
}

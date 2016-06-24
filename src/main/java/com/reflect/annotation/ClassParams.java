package com.reflect.annotation;

import com.reflect.datatype.Unknown;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jrsen on 16-4-29.
 * 用于用Class array描述方法或者构造器的参数类型
 * (如果参数类型为未知类型则用{@link Unknown.class}代替)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ClassParams {
    Class[] value();
}

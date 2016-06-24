package com.reflect.core;

import com.reflect.datatype.Unknown;

/**
 * Created by jrsen on 16-4-29.
 * StaticMethod object, Please define like this sample:
 *
 * @param ({String.class})//unknown class please use {@link Unknown} instead
 *                                  public static StaticMethod staticMethodName;
 */
public final class StaticMethod<T> {

    private java.lang.reflect.Method method;

    public StaticMethod(java.lang.reflect.Method method) {
        this.method = method;
        method.setAccessible(true);
    }

    public T invokeWithException(Object... args) throws Exception {
        return (T) method.invoke(null, args);
    }

    public T invoke(Object... args) {
        try {
            return invokeWithException(args);
        } catch (Throwable ignore) {
        }
        return null;
    }

}

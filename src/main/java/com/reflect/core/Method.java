package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 * Method object, Please define like this sample:
 *
 * @param ({String.class})//unknown class please use {@link Unknown} instead
 *                                  public static Method methodName;
 */
public final class Method<T> {

    private java.lang.reflect.Method method;

    public Method(java.lang.reflect.Method method) {
        this.method = method;
        method.setAccessible(true);
    }

    public T invokeWithException(Object obj, Object... args) throws Exception {
        return (T) method.invoke(obj, args);
    }

    public T invoke(Object obj, Object... args) {
        try {
            return invokeWithException(obj, args);
        } catch (Throwable ignore) {
        }
        return null;
    }
}

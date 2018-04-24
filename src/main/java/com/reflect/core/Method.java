package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Method<T> {

    private final java.lang.reflect.Method method;

    public Method(java.lang.reflect.Method method) {
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    public T invokeUnsafe(Object obj, Object... args) throws Exception {
        if (!method.isAccessible())
            method.setAccessible(true);
        return (T) method.invoke(obj, args);
    }

    public T invoke(Object obj, Object... args) {
        try {
            return invokeUnsafe(obj, args);
        } catch (Throwable ignore) {
            return null;
        }
    }
}

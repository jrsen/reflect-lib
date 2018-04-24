package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 */
public final class StaticMethod<T> {

    private final java.lang.reflect.Method method;

    public StaticMethod(java.lang.reflect.Method method) {
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    public T invokeUnsafe(Object... args) throws Exception {
        if (!method.isAccessible())
            method.setAccessible(true);
        return (T) method.invoke(null, args);
    }

    public T invoke(Object... args) {
        try {
            return invokeUnsafe(args);
        } catch (Throwable ignore) {
            return null;
        }
    }

}

package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Constructor<T> {

    private final java.lang.reflect.Constructor constructor;

    public Constructor(java.lang.reflect.Constructor constructor) {
        this.constructor = constructor;
    }

    @SuppressWarnings("unchecked")
    public T newInstanceUnSafe(Object... args) throws Exception {
        if (!constructor.isAccessible())
            constructor.setAccessible(true);
        return (T) constructor.newInstance(args);
    }

    public T newInstance(Object... args) {
        try {
            return newInstanceUnSafe(args);
        } catch (Throwable ignore) {
            return null;
        }
    }

}

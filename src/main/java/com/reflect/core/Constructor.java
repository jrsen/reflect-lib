package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 * Constructor object, Please define like this sample:
 *
 * @param ({String.class})//unknown class please use {@link Unknown} instead
 *                                  public static Constructor constructor;
 */
public final class Constructor<T> {

    private java.lang.reflect.Constructor constructor;

    public Constructor(java.lang.reflect.Constructor constructor) {
        this.constructor = constructor;
        constructor.setAccessible(true);
    }

    public T newInstance(Object... args) {
        try {
            return (T) constructor.newInstance(args);
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        }
        return null;
    }

}

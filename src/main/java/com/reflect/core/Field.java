package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Field<T> {

    private final java.lang.reflect.Field field;

    public Field(java.lang.reflect.Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public T getUnsafe(Object obj) throws Exception {
        if (!field.isAccessible())
            field.setAccessible(true);
        return (T) field.get(obj);
    }

    public T get(Object obj) {
        try {
            return getUnsafe(obj);
        } catch (Exception ignore) {
        }
        return null;
    }

    public void setUnsafe(Object obj, T value) throws Exception {
        if (!field.isAccessible())
            field.setAccessible(true);
        field.set(obj, value);
    }

    public void set(Object obj, T value) {
        try {
            setUnsafe(obj, value);
        } catch (Exception ignore) {
        }
    }

}

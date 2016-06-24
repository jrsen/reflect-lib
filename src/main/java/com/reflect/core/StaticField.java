package com.reflect.core;


/**
 * Created by jrsen on 16-4-29.
 * StaticField object, Please define like this sample:
 * <p/>
 * public static StaticField fieldName;
 */
public final class StaticField<T> {

    private java.lang.reflect.Field field;

    public StaticField(java.lang.reflect.Field field) {
        this.field = field;
        field.setAccessible(true);
    }

    public T get() {
        try {
            return (T) field.get(null);
        } catch (Exception ignore) {
        }
        return null;
    }

    public void set(T value) {
        try {
            field.set(null, value);
        } catch (Exception ignore) {
        }
    }

}

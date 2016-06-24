package com.reflect.core;

/**
 * Created by jrsen on 16-4-29.
 * Field object, Please define like this sample:
 * <p>
 * public static Field fieldName;
 */
public final class Field<T> {

    private java.lang.reflect.Field field;

    public Field(java.lang.reflect.Field field) {
        this.field = field;
        field.setAccessible(true);
    }

    public T get(Object obj) {
        try {
            return (T) field.get(obj);
        } catch (Exception ignore) {
        }
        return null;
    }

    public void set(Object obj, T value) {
        try {
            field.set(obj, value);
        } catch (Exception ignore) {
        }
    }

}

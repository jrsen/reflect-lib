package com.reflect.core;


/**
 * Created by jrsen on 16-4-29.
 * StaticField object, Please define like this sample:
 * <p/>
 * public static StaticField fieldName;
 */
public final class StaticField<T> {

    private final java.lang.reflect.Field field;

    public StaticField(java.lang.reflect.Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public T getUnsafe() throws Exception {
        if (!field.isAccessible())
            field.setAccessible(true);
        return (T) field.get(null);
    }

    public T get() {
        try {
            return getUnsafe();
        } catch (Exception ignore) {
        }
        return null;
    }

    public void setUnsafe(T value) throws Exception {
        if (!field.isAccessible())
            field.setAccessible(true);
        field.set(null, value);
    }

    public void set(T value) {
        try {
            setUnsafe(value);
        } catch (Exception ignore) {
        }
    }


}

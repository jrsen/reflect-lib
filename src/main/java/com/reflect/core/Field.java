package com.reflect.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Field<T> extends AccessibleObject {

    private final java.lang.reflect.Field field;

    public Field(java.lang.reflect.Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public T getUnsafe(Object obj) throws Exception {
        fixAccessible(field);
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

    public int getModifiers() {
        return field.getModifiers();
    }

    public boolean isEnumConstant() {
        return field.isEnumConstant();
    }

    public boolean isSynthetic() {
        return field.isSynthetic();
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getDeclaringClass() {
        return field.getDeclaringClass();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public Type getGenericType() {
        return field.getGenericType();
    }

    public Annotation[] getDeclaredAnnotations() {
        return field.getDeclaredAnnotations();
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return field.getAnnotation(annotationType);
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return field.isAnnotationPresent(annotationType);
    }

}

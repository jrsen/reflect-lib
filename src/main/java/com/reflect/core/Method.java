package com.reflect.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Method<T> extends AccessibleObject {

    private final java.lang.reflect.Method method;

    public Method(java.lang.reflect.Method method) {
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    public T invokeUnsafe(Object obj, Object... args) throws Exception {
        fixAccessible(method);
        return (T) method.invoke(obj, args);
    }

    public T invoke(Object obj, Object... args) {
        try {
            return invokeUnsafe(obj, args);
        } catch (Throwable ignore) {
            return null;
        }
    }

    public Annotation[] getAnnotations() {
        return method.getAnnotations();
    }

    public int getModifiers() {
        return method.getModifiers();
    }

    public boolean isVarArgs() {
        return method.isVarArgs();
    }

    public boolean isBridge() {
        return method.isBridge();
    }

    public boolean isSynthetic() {
        return method.isSynthetic();
    }

    public String getName() {
        return method.getName();
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    public Class<?>[] getExceptionTypes() {
        return method.getExceptionTypes();
    }

    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public String toGenericString() {
        return method.toGenericString();
    }

    public TypeVariable<java.lang.reflect.Method>[] getTypeParameters() {
        return method.getTypeParameters();
    }

    public Type[] getGenericParameterTypes() {
        return method.getGenericParameterTypes();
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return method.isAnnotationPresent(annotationType);
    }

    public Type[] getGenericExceptionTypes() {
        return method.getGenericExceptionTypes();
    }

    public Type getGenericReturnType() {
        return method.getGenericReturnType();
    }

    public Annotation[] getDeclaredAnnotations() {
        return method.getDeclaredAnnotations();
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return method.getAnnotation(annotationType);
    }

    public Annotation[][] getParameterAnnotations() {
        return method.getParameterAnnotations();
    }

    public Object getDefaultValue() {
        return method.getDefaultValue();
    }

}

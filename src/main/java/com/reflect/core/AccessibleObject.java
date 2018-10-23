package com.reflect.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class AccessibleObject {

    protected void fixAccessible(Field field) {
        try {
            field.setAccessible(true);
            java.lang.reflect.Field accessFlagsField = field.getClass().getDeclaredField("accessFlags");
            accessFlagsField.setAccessible(true);
            accessFlagsField.set(field, field.getModifiers() | Modifier.PUBLIC & ~Modifier.FINAL);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void fixAccessible(Method method) {
        try {
            method.setAccessible(true);
            Field accessFlagsField = method.getClass().getSuperclass().getDeclaredField("accessFlags");
            accessFlagsField.setAccessible(true);
            accessFlagsField.set(method, method.getModifiers() | Modifier.PUBLIC & ~Modifier.FINAL);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

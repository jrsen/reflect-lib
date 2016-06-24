package com.reflect.core;

import com.reflect.annotation.ClassParams;
import com.reflect.annotation.StringParams;
import com.reflect.datatype.BaseBoolean;
import com.reflect.datatype.BaseByte;
import com.reflect.datatype.BaseChar;
import com.reflect.datatype.BaseDouble;
import com.reflect.datatype.BaseFloat;
import com.reflect.datatype.BaseInt;
import com.reflect.datatype.BaseLong;
import com.reflect.datatype.BaseShort;
import com.reflect.datatype.Unknown;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Reflection {

    public static Class init(String srcClass, Class injectClass) {
        Class srcClazz;
        try {
            srcClazz = Class.forName(srcClass);
        } catch (ClassNotFoundException ignore) {
            return null;
        }

        java.lang.reflect.Field[] injectFields = injectClass.getDeclaredFields();
        for (java.lang.reflect.Field injectField : injectFields) {
            if ((injectField.getModifiers() & Modifier.STATIC) == 0) continue;
            Class<?> classType = injectField.getType();
            if (classType == Constructor.class) {
                linkToConstructor(srcClazz, injectField);
            } else if (classType == Method.class) {
                linkToMethod(srcClazz, injectField);
            } else if (classType == StaticMethod.class) {
                linkToStaticMethod(srcClazz, injectField);
            } else if (classType == Field.class) {
                linkToField(srcClazz, injectField);
            } else if (classType == StaticField.class) {
                linkToStaticField(srcClazz, injectField);
            }
        }
        return srcClazz;
    }

    public static Method getMethod(Class clazz, String name, Class... args) {
        try {
            java.lang.reflect.Method method = clazz.getDeclaredMethod(name, args);
            return new Method(method);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    public static StaticMethod getStaticMethod(Class clazz, String name, Class... args) {
        try {
            java.lang.reflect.Method method = clazz.getDeclaredMethod(name, args);
            return new StaticMethod(method);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    public static Field getField(Class clazz, String name) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(name);
            return new Field(field);
        } catch (NoSuchFieldException e) {
        }
        return null;
    }

    public static StaticField getStaticField(Class clazz, String name) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(name);
            return new StaticField(field);
        } catch (NoSuchFieldException e) {
        }
        return null;
    }

    public static Constructor getConstructor(Class clazz, Class... args) {
        try {
            java.lang.reflect.Constructor constructor = clazz.getDeclaredConstructor(args);
            return new Constructor(constructor);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    private static void linkToConstructor(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] requiredParamTypes = getRequiredParamTypes(injectField);
            requiredParamTypes = fixConsUnknownParamTypes(clazz, requiredParamTypes);
            java.lang.reflect.Constructor constructor = clazz.getDeclaredConstructor(requiredParamTypes);
            injectField.set(null, new Constructor(constructor));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToMethod(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] requiredParamTypes = getRequiredParamTypes(injectField);
            String methodName = injectField.getName();
            requiredParamTypes = fixMethodUnknownParamTypes(clazz, requiredParamTypes, methodName);
            java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, requiredParamTypes);
            injectField.set(null, new Method(method));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToStaticMethod(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] requiredParamTypes = getRequiredParamTypes(injectField);
            String methodName = injectField.getName();
            requiredParamTypes = fixMethodUnknownParamTypes(clazz, requiredParamTypes, methodName);
            java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, requiredParamTypes);
            injectField.set(null, new StaticMethod(method));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToField(Class clazz, java.lang.reflect.Field injectField) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(injectField.getName());
            injectField.set(null, new Field(field));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToStaticField(Class clazz, java.lang.reflect.Field injectField) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(injectField.getName());
            injectField.set(null, new StaticField(field));
        } catch (Throwable ignore) {
        }
    }

    /**
     * get object param types
     *
     * @param object
     * @return
     */
    private static Class[] getRequiredParamTypes(AccessibleObject object) {
        Class[] requiredParamTypes = new Class[]{};
        if (object.isAnnotationPresent(ClassParams.class)) {
            requiredParamTypes = object.getAnnotation(ClassParams.class).value();
        } else if (object.isAnnotationPresent(StringParams.class)) {
            String[] values = object.getAnnotation(StringParams.class).value();
            requiredParamTypes = new Class[values.length];
            for (int i = 0; i < values.length; i++) {
                try {
                    requiredParamTypes[i] = Class.forName(values[i]);
                } catch (ClassNotFoundException ignore) {
                    requiredParamTypes[i] = Unknown.class;
                }
            }
        }
        requiredParamTypes = replaceVirtualParamTypes(requiredParamTypes);
        return requiredParamTypes;
    }

    /**
     * Replace virtual class type
     *
     * @param paramTypes
     * @return
     */
    private static Class[] replaceVirtualParamTypes(Class[] paramTypes) {
        List<Class> classes = Arrays.asList(paramTypes);
        Collections.replaceAll(classes, BaseInt.class, int.class);
        Collections.replaceAll(classes, BaseShort.class, short.class);
        Collections.replaceAll(classes, BaseLong.class, long.class);
        Collections.replaceAll(classes, BaseDouble.class, long.class);
        Collections.replaceAll(classes, BaseFloat.class, long.class);
        Collections.replaceAll(classes, BaseChar.class, long.class);
        Collections.replaceAll(classes, BaseByte.class, long.class);
        Collections.replaceAll(classes, BaseBoolean.class, long.class);
        return classes.toArray(paramTypes);
    }

    /**
     * fix Constructor required param types
     *
     * @param clazz
     * @param requiredParamTypes
     * @returnn
     */
    private static Class[] fixConsUnknownParamTypes(Class clazz, Class[] requiredParamTypes) {
        List<Class> classes = Arrays.asList(requiredParamTypes);
        if (classes.contains(Unknown.class)) {
            java.lang.reflect.Constructor[] constructors = clazz.getDeclaredConstructors();
            for (java.lang.reflect.Constructor constructor : constructors) {
                Class[] paramTypes = constructor.getParameterTypes();
                if (equalsClassesIgnoreUnknown(requiredParamTypes, paramTypes)) {
                    return paramTypes;
                }
            }
        }
        return requiredParamTypes;
    }

    /**
     * fix Method required param types
     *
     * @param clazz
     * @param requiredParamTypes
     * @param methodName
     * @return
     */
    private static Class[] fixMethodUnknownParamTypes(Class clazz, Class[] requiredParamTypes, String methodName) {
        List<Class> classes = Arrays.asList(requiredParamTypes);
        if (classes.contains(Unknown.class)) {
            java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
            for (java.lang.reflect.Method method : methods) {
                if (!method.getName().equals(methodName)) continue;
                Class<?>[] paramTypes = method.getParameterTypes();
                if (equalsClassesIgnoreUnknown(requiredParamTypes, paramTypes)) {
                    return paramTypes;
                }
            }
        }
        return requiredParamTypes;
    }

    /**
     * 比较两个class数组忽略Unknown类型
     *
     * @param a1
     * @param a2
     * @return
     */
    private static boolean equalsClassesIgnoreUnknown(Class[] a1, Class[] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null || a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            Object e1 = a1[i], e2 = a2[i];

            if (e1 == null || e2 == null) {
                return false;
            }

            if (e1.getClass() != Unknown.class && e2.getClass() != Unknown.class && e1.getClass() != e2.getClass()) {
                return false;
            }
        }
        return true;
    }

}

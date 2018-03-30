package com.reflect.core;

import com.reflect.annotation.Parameter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;

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
            if (!Modifier.isStatic(injectField.getModifiers())) continue;
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

    private static void linkToConstructor(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] typeParameters = getTypeParameters(injectField);
            java.lang.reflect.Constructor constructor = clazz.getDeclaredConstructor(typeParameters);
            injectField.set(null, new Constructor(constructor));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToMethod(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] typeParameters = getTypeParameters(injectField);
            String methodName = injectField.getName();
            java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, typeParameters);
            injectField.set(null, new Method(method));
        } catch (Throwable ignore) {
        }
    }

    private static void linkToStaticMethod(Class clazz, java.lang.reflect.Field injectField) {
        try {
            Class[] typeParameters = getTypeParameters(injectField);
            String methodName = injectField.getName();
            java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, typeParameters);
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

    private static Class[] getTypeParameters(AccessibleObject object) {
        if (object.isAnnotationPresent(Parameter.class)) {
            Parameter param = object.getAnnotation(Parameter.class);
            Class[] typeParameters = param.value();
            if (typeParameters.length > 0) {
                return typeParameters;
            }
            Class[] clsParams = {
                    param.c_arg0(), param.c_arg1(),
                    param.c_arg2(), param.c_arg3(),
                    param.c_arg4(), param.c_arg5(),
                    param.c_arg6(), param.c_arg7(),
                    param.c_arg8(), param.c_arg9(),
                    param.c_arg10(), param.c_arg11(),
                    param.c_arg12(), param.c_arg13(),
                    param.c_arg14(), param.c_arg15(),
                    param.c_arg16(), param.c_arg17(),
                    param.c_arg18(), param.c_arg19()
            };
            String[] strParams = {
                    param.s_arg0(), param.s_arg1(),
                    param.s_arg2(), param.s_arg3(),
                    param.s_arg4(), param.s_arg5(),
                    param.s_arg6(), param.s_arg7(),
                    param.s_arg8(), param.s_arg9(),
                    param.s_arg10(), param.s_arg11(),
                    param.s_arg12(), param.s_arg13(),
                    param.s_arg14(), param.s_arg15(),
                    param.s_arg16(), param.s_arg17(),
                    param.s_arg18(), param.s_arg19()
            };
            int params_length = 0;
            final int N = clsParams.length;
            for (int i = 0; i < N; i++) {
                if (clsParams[i] == Void.class) {
                    if (strParams[i].length() > 0) {
                        try {
                            clsParams[i] = Class.forName(strParams[i]);
                        } catch (ClassNotFoundException ignore) {
                        }
                    } else {
                        params_length = i;
                        break;
                    }
                }
            }
            typeParameters = new Class[params_length];
            System.arraycopy(clsParams, 0, typeParameters, 0, params_length);
            return typeParameters;
        }
        return new Class[0];
    }


}

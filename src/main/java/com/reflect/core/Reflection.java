package com.reflect.core;

import com.reflect.annotation.Parameter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;

/**
 * Created by jrsen on 16-4-29.
 */
public final class Reflection {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> init(ClassLoader loader, String srcClass, Class<?> injectClass) {
        try {
            return (Class<T>) init(loader.loadClass(srcClass), injectClass);
        } catch (ClassNotFoundException ignore) {
//            ignore.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> init(String srcClass, Class<?> injectClass) {
        try {
            return (Class<T>) init(Class.forName(srcClass), injectClass);
        } catch (ClassNotFoundException ignore) {
//            ignore.printStackTrace();
            return null;
        }
    }

    public static <T> Class<T> init(Class<T> srcClass, Class<?> injectClass) {
        java.lang.reflect.Field[] injectFields = injectClass.getDeclaredFields();
        for (java.lang.reflect.Field injectField : injectFields) {
            if (!injectField.isAnnotationPresent(Parameter.class)
                    && !Modifier.isStatic(injectField.getModifiers()))
                continue;
            Class<?> classType = injectField.getType();
            if (classType == Constructor.class) {
                linkToConstructor(srcClass, injectField);
            } else if (classType == Method.class) {
                linkToMethod(srcClass, injectField);
            } else if (classType == StaticMethod.class) {
                linkToStaticMethod(srcClass, injectField);
            } else if (classType == Field.class) {
                linkToField(srcClass, injectField);
            } else if (classType == StaticField.class) {
                linkToStaticField(srcClass, injectField);
            }
        }
        return srcClass;
    }

    private static void linkToConstructor(Class<?> clazz, java.lang.reflect.Field injectField) {
        try {
            Constructor<?> constructor = getConstructor(clazz, getParameterTypes(injectField));
            if (constructor != null) {
                injectField.setAccessible(true);
                injectField.set(null, constructor);
            }
        } catch (Throwable ignore) {
        }
    }

    private static void linkToMethod(Class<?> clazz, java.lang.reflect.Field injectField) {
        try {
            String name = injectField.getAnnotation(Parameter.class).name();
            Method<?> method = getMethod(clazz, name.isEmpty() ? injectField.getName() : name, getParameterTypes(injectField));
            if (method != null) {
                injectField.setAccessible(true);
                injectField.set(null, method);
            }
        } catch (Throwable ignore) {
        }
    }

    private static void linkToStaticMethod(Class<?> clazz, java.lang.reflect.Field injectField) {
        try {
            String name = injectField.getAnnotation(Parameter.class).name();
            StaticMethod<?> staticMethod = getStaticMethod(clazz, name.isEmpty() ? injectField.getName() : name, getParameterTypes(injectField));
            if (staticMethod != null) {
                injectField.setAccessible(true);
                injectField.set(null, staticMethod);
            }
        } catch (Throwable ignore) {
        }
    }

    private static void linkToField(Class<?> clazz, java.lang.reflect.Field injectField) {
        try {
            String name = injectField.getAnnotation(Parameter.class).name();
            Field<?> field = getField(clazz, name.isEmpty() ? injectField.getName() : name);
            if (field != null) {
                injectField.setAccessible(true);
                injectField.set(null, field);
            }
        } catch (Throwable ignore) {
        }
    }

    private static void linkToStaticField(Class<?> clazz, java.lang.reflect.Field injectField) {
        try {
            String name = injectField.getAnnotation(Parameter.class).name();
            StaticField<?> staticField = getStaticField(clazz, name.isEmpty() ? injectField.getName() : name);
            if (staticField != null) {
                injectField.setAccessible(true);
                injectField.set(null, staticField);
            }
        } catch (Throwable ignore) {
        }
    }

    private static Class<?>[] getParameterTypes(AccessibleObject object) {
        if (object.isAnnotationPresent(Parameter.class)) {
            Parameter param = object.getAnnotation(Parameter.class);
            if (param.value().length > 0) {
                return param.value().clone();
            }

            Class[] clsParams =
                    {
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
            String[] strParams =
                    {
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
            final int N = strParams.length;
            for (int i = 0; i < N; i++) {
                if (!strParams[i].isEmpty()) {
                    try {
                        clsParams[i] = Class.forName(strParams[i]);
                    } catch (ClassNotFoundException ignore) {
                    }
                } else if (clsParams[i] == Void.class) {
                    params_length = i;
                    break;
                }
            }
            Class[] parameterTypes = new Class[params_length];
            System.arraycopy(clsParams, 0, parameterTypes, 0, params_length);
            return parameterTypes;
        }
        return new Class[0];
    }

    public static <T> Constructor<T> getConstructor(String srcClass, Class<?>... parameterTypes) {
        try {
            return getConstructor(Class.forName(srcClass), parameterTypes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Constructor<T> getConstructor(Class srcClass, Class<?>... parameterTypes) {
        try {
            java.lang.reflect.Constructor constructor = srcClass.getDeclaredConstructor(parameterTypes);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return new Constructor<>(constructor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Field<T> getField(String srcClass, String fieldName) {
        try {
            return getField(Class.forName(srcClass), fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Field<T> getField(Class<?> srcClass, String fieldName) {
        try {
            java.lang.reflect.Field field = srcClass.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return new Field<>(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> StaticField<T> getStaticField(String srcClass, String fieldName) {
        try {
            return getStaticField(Class.forName(srcClass), fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> StaticField<T> getStaticField(Class<?> srcClass, String fieldName) {
        try {
            java.lang.reflect.Field field = srcClass.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return new StaticField<>(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Method<T> getMethod(String srcClass, String methodName, Class<?>... parameterTypes) {
        try {
            return getMethod(Class.forName(srcClass), methodName, parameterTypes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Method<T> getMethod(Class<?> srcClass, String methodName, Class<?>... parameterTypes) {
        try {
            java.lang.reflect.Method method = srcClass.getDeclaredMethod(methodName, parameterTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return new Method<>(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> StaticMethod<T> getStaticMethod(String srcClass, String methodName, Class<?>... parameterTypes) {
        try {
            return getStaticMethod(Class.forName(srcClass), methodName, parameterTypes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> StaticMethod<T> getStaticMethod(Class<?> srcClass, String methodName, Class<?>... parameterTypes) {
        try {
            java.lang.reflect.Method method = srcClass.getDeclaredMethod(methodName, parameterTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return new StaticMethod<>(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FieldWrapper streamGetObject(Object obj) {
        return new FieldWrapper(obj);
    }

}

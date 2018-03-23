package com.reflect.core;

import com.reflect.annotation.ClassParam0;
import com.reflect.annotation.ClassParam1;
import com.reflect.annotation.ClassParam2;
import com.reflect.annotation.ClassParam3;
import com.reflect.annotation.ClassParam4;
import com.reflect.annotation.ClassParam5;
import com.reflect.annotation.ClassParam6;
import com.reflect.annotation.ClassParam7;
import com.reflect.annotation.ClassParam8;
import com.reflect.annotation.ClassParam9;
import com.reflect.annotation.ClassParams;
import com.reflect.annotation.StringParam0;
import com.reflect.annotation.StringParam1;
import com.reflect.annotation.StringParam2;
import com.reflect.annotation.StringParam3;
import com.reflect.annotation.StringParam4;
import com.reflect.annotation.StringParam5;
import com.reflect.annotation.StringParam6;
import com.reflect.annotation.StringParam7;
import com.reflect.annotation.StringParam8;
import com.reflect.annotation.StringParam9;
import com.reflect.annotation.StringParams;

import java.lang.annotation.Annotation;
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

    /**
     * get object param types
     *
     * @param object
     * @return
     */
    private static Class[] getTypeParameters(AccessibleObject object) {
        Class[] typeparams;
        if (object.isAnnotationPresent(ClassParams.class)) {
            typeparams = object.getAnnotation(ClassParams.class).value();
        } else if (object.isAnnotationPresent(StringParams.class)) {
            String[] classtypes = object.getAnnotation(StringParams.class).value();
            final int N = classtypes.length;
            typeparams = new Class[N];
            for (int i = 0; i < N; i++) {
                try {
                    typeparams[i] = Class.forName(classtypes[i]);
                } catch (ClassNotFoundException ignore) {
                }
            }
        } else if (object.isAnnotationPresent(ClassParam0.class)
                || object.isAnnotationPresent(ClassParam1.class)
                || object.isAnnotationPresent(ClassParam2.class)
                || object.isAnnotationPresent(ClassParam3.class)
                || object.isAnnotationPresent(ClassParam4.class)
                || object.isAnnotationPresent(ClassParam5.class)
                || object.isAnnotationPresent(ClassParam6.class)
                || object.isAnnotationPresent(ClassParam7.class)
                || object.isAnnotationPresent(ClassParam8.class)
                || object.isAnnotationPresent(ClassParam9.class)
                || object.isAnnotationPresent(StringParam0.class)
                || object.isAnnotationPresent(StringParam1.class)
                || object.isAnnotationPresent(StringParam2.class)
                || object.isAnnotationPresent(StringParam3.class)
                || object.isAnnotationPresent(StringParam4.class)
                || object.isAnnotationPresent(StringParam5.class)
                || object.isAnnotationPresent(StringParam6.class)
                || object.isAnnotationPresent(StringParam7.class)
                || object.isAnnotationPresent(StringParam8.class)
                || object.isAnnotationPresent(StringParam9.class)) {
            Annotation[] annotations = object.getAnnotations();
            final int N = annotations.length;
            typeparams = new Class[N];
            for (int i = 0; i < N; i++) {
                Annotation annotation = annotations[i];
                try {
                    if (annotation instanceof ClassParam0) {
                        typeparams[0] = ((ClassParam0) annotation).value();
                    } else if (annotation instanceof ClassParam1) {
                        typeparams[1] = ((ClassParam1) annotation).value();
                    } else if (annotation instanceof ClassParam2) {
                        typeparams[2] = ((ClassParam2) annotation).value();
                    } else if (annotation instanceof ClassParam3) {
                        typeparams[3] = ((ClassParam3) annotation).value();
                    } else if (annotation instanceof ClassParam4) {
                        typeparams[4] = ((ClassParam4) annotation).value();
                    } else if (annotation instanceof ClassParam5) {
                        typeparams[5] = ((ClassParam5) annotation).value();
                    } else if (annotation instanceof ClassParam6) {
                        typeparams[6] = ((ClassParam6) annotation).value();
                    } else if (annotation instanceof ClassParam7) {
                        typeparams[7] = ((ClassParam7) annotation).value();
                    } else if (annotation instanceof ClassParam8) {
                        typeparams[8] = ((ClassParam8) annotation).value();
                    } else if (annotation instanceof ClassParam9) {
                        typeparams[9] = ((ClassParam9) annotation).value();
                    } else if (annotation instanceof StringParam0) {
                        typeparams[0] = Class.forName(((StringParam0) annotation).value());
                    } else if (annotation instanceof StringParam1) {
                        typeparams[1] = Class.forName(((StringParam1) annotation).value());
                    } else if (annotation instanceof StringParam2) {
                        typeparams[2] = Class.forName(((StringParam2) annotation).value());
                    } else if (annotation instanceof StringParam3) {
                        typeparams[3] = Class.forName(((StringParam3) annotation).value());
                    } else if (annotation instanceof StringParam4) {
                        typeparams[4] = Class.forName(((StringParam4) annotation).value());
                    } else if (annotation instanceof StringParam5) {
                        typeparams[5] = Class.forName(((StringParam5) annotation).value());
                    } else if (annotation instanceof StringParam6) {
                        typeparams[6] = Class.forName(((StringParam6) annotation).value());
                    } else if (annotation instanceof StringParam7) {
                        typeparams[7] = Class.forName(((StringParam7) annotation).value());
                    } else if (annotation instanceof StringParam8) {
                        typeparams[8] = Class.forName(((StringParam8) annotation).value());
                    } else if (annotation instanceof StringParam9) {
                        typeparams[9] = Class.forName(((StringParam9) annotation).value());
                    }
                } catch (ClassNotFoundException ignore) {
                }
            }
        } else {
            typeparams = new Class[]{};
        }
        return typeparams;
    }


}

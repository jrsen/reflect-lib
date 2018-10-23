package com.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Parameter {
    String name() default "";
    Class[] value() default {};
    Class c_arg0() default Void.class;
    Class c_arg1() default Void.class;
    Class c_arg2() default Void.class;
    Class c_arg3() default Void.class;
    Class c_arg4() default Void.class;
    Class c_arg5() default Void.class;
    Class c_arg6() default Void.class;
    Class c_arg7() default Void.class;
    Class c_arg8() default Void.class;
    Class c_arg9() default Void.class;
    Class c_arg10() default Void.class;
    Class c_arg11() default Void.class;
    Class c_arg12() default Void.class;
    Class c_arg13() default Void.class;
    Class c_arg14() default Void.class;
    Class c_arg15() default Void.class;
    Class c_arg16() default Void.class;
    Class c_arg17() default Void.class;
    Class c_arg18() default Void.class;
    Class c_arg19() default Void.class;
    String s_arg0() default "";
    String s_arg1() default "";
    String s_arg2() default "";
    String s_arg3() default "";
    String s_arg4() default "";
    String s_arg5() default "";
    String s_arg6() default "";
    String s_arg7() default "";
    String s_arg8() default "";
    String s_arg9() default "";
    String s_arg10() default "";
    String s_arg11() default "";
    String s_arg12() default "";
    String s_arg13() default "";
    String s_arg14() default "";
    String s_arg15() default "";
    String s_arg16() default "";
    String s_arg17() default "";
    String s_arg18() default "";
    String s_arg19() default "";

}

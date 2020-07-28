# 一款易用的反射库可以将需要反射调用的类映射到自定义类方便调用

<!-- vim-markdown-toc -->

## 如何映射?

* ```com.reflect.core.Reflection.init(String srcClass, Class injectClass)``` 参数srcClass为映射源类名 参数injectClass为自定义映射类
* ```com.reflect.core.Reflection.init(Class srcClass, Class injectClass)``` 参数srcClass为映射源类 参数injectClass为自定义映射类

**Tip:建议在自定义映射类中的```static```代码块中进行初始化这样调用映射字段可以做到懒加载并且自动映射**

## 如何在自定义类中定义映射对象?

- 直接在自定义类中定义如下**静态**字段:
* 构造器:```com.reflect.core.Constructor```
* 变量:```com.reflect.core.Field```
* 静态变量:```com.reflect.core.StaticField```
* 方法:```com.reflect.core.Method```
* 静态方法:```com.reflect.core.StaticMethod```
  
<!-- vim-markdown-toc -->

## 如何映射带参数的构造器或方法?

使用```com.reflect.annotation.Parameter```注解标记映射对象,当参数的类型都可以直接访问的话可以直接使用```@Parameter({xxx.class,xxx.class})```来直接定义,当有部分参数类型无法直接访问
则可以使用第二种方式指定类名参数如:```@Parameter({c_arg0=String.class, s_arg1="java.lang.Object", s_arg2="libcore.io.Memory"})第二种方式的话需要通过```c_argx```或者```s_argx```指定所有参数索引且第二种参数最多支持20个

<!-- vim-markdown-toc -->

## 举个例子
```java
public final class ApplicationPackageManagerMapping
{
    public static final Class<?> CLASS;
    //反射构造器
    @com.reflect.annotation.Parameter(
        s_arg0 = "android.app.ContextImpl",
        s_arg1 = "android.content.pm.IPackageManager"
    )
    public static com.reflect.core.Constructor<PackageManager> ctor;
    //反射字段
    public static com.reflect.core.Field<IInterface> mPM;
    //反射方法
    @com.reflect.annotation.Parameter(
        c_arg0 = String.class,
        s_arg1 = "android.content.pm.IPackageDeleteObserver",
        c_arg2 = int.class
    )
    public static com.reflect.core.Method<Void> deletePackage;


    static
    {
        CLASS = com.reflect.core.Reflection.init("android.app.ApplicationPackageManager", ApplicationPackageManagerMapping.class);
    }
}
```

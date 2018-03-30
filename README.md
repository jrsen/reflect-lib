# 一款易用的反射库
```
public final class IPackageManager {

    @Parameter({int.class, int.class})
    public static Method<Object> getInstalledPackages;

    @Parameter(
            c_arg0 = String.class,
            s_arg1 = "android.content.pm.IPackageDeleteObserver",
            c_arg2 = int.class,
            c_arg3 = int.class
    )
    public static Method deletePackageAsUser;

    static {
        Reflection.init("android.content.pm.IPackageManager", IPackageManager.class);
    }
    
}
```

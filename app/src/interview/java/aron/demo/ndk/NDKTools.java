package aron.demo.ndk;

/**
 * 1. build后在build/intermediates/classes/interview/debug/
 * 2. 在该目录下生成 .h 命令 javah -jni aron.demo.ndk.NDKTools
 * 3. 如果没问题 则生成 NDKTools.h文件
 * 4. 新建jni目录,新建c文件 拷贝.h文件
 * 5. 同样在jni目录下，添加一个Android.mk文件
 * 6. build.gradle  defaultConfig 添加 ndk
 * 7. buildType 添加 externalNativeBuild  sourceSets
 * Created by liuwenrong on 2019/3/21
 */
public class NDKTools {

    static {
        System.loadLibrary("ndk_demo-jni");
    }

    public static native String getStringFromNDK();

    public static String getJavaString() {
        return "I'm in Java!";
    }

}

#include "aron_demo_ndk_NDKTools.h"
#include <android/log.h>
#include <string.h>

#define TAG    "jni-test-aron-demo-ndk" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

char *getString(jboolean isNdkTools);

JNIEXPORT char *getString(jboolean isNdkTools) {
    char *isNdkToolsChar;
    if (isNdkTools) {
        isNdkToolsChar = "true";
    } else {
        isNdkToolsChar = "false";
    }
    return isNdkToolsChar;
}

jstring JNICALL Java_aron_demo_ndk_NDKTools_getStringFromNDK
        (JNIEnv *env, jobject obj) {
    jclass cls = (*env)->FindClass(env, "aron/demo/ndk/NDKTools");
    jmethodID mid = (*env)->GetMethodID(env, cls, "<init>", "()V");
    jobject objNdkTools = (*env)->NewObject(env, cls, mid);

    jclass jclassObj = (*env)->GetObjectClass(env, obj);
    jboolean isNdkTools = (*env)->IsInstanceOf(env, obj, cls);
//    jboolean isNdkTools = 1;  // 可赋值0 1
    jstring isNdkToolsString; // 不能直接赋值 = "t";
    char *isNdkToolsChar;
    isNdkToolsChar = getString(isNdkTools);
//    const char *isNdkToolsChar = (*env)->GetStringUTFChars(env, isNdkToolsString, 0);

    LOGD("打印log 方法的参数obj是否是NDKTools的实例对象: %s%", isNdkToolsChar);

    isNdkTools = (*env)->IsInstanceOf(env, obj, jclassObj);
    isNdkToolsChar = getString(isNdkTools);
    LOGD("打印log 方法的参数obj是否是通过obj获取的class的实例对象: %s%", isNdkToolsChar);

    jmethodID getJavaString = (*env)->GetStaticMethodID(env, cls, "getJavaString",
                                                        "()Ljava/lang/String;");
    jstring string = (jstring) (*env)->CallStaticObjectMethod(env, /*objNdkTools*/cls,
                                                              getJavaString);
    //转换成char型
    const char *print = (char *) (*env)->GetStringUTFChars(env, string, 0);
    LOGD("jChar = %s%", print);

    char string1[120] = "Hello World，这是Aron的NDK的第一行代码,I'm in C . ";
    strcat(string1, print);

    return (*env)->NewStringUTF(env, string1);
}
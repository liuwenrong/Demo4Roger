# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#防止混淆后和主工程出现包名类名相同的类
-repackageclasses aron.interview

#插件化开发需要前后两次编译相同元素混淆后的名字一致,避免每次发版都要重新编译更新插件apk
-applymapping libs_mapping/mapping.txt
# 打印混淆后的映射 mapping目录在 \app\build\outputs\mapping\release
-printmapping map.txt

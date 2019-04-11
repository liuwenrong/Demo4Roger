LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := ndk_demo-jni

LOCAL_SRC_FILES := ndk_demo.c

LOCAL_LDLIBS :=-llog # Android Log

include $(BUILD_SHARED_LIBRARY)
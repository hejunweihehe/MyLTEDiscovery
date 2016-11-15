LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

#LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SRC_FILES += src/com/mediatek/FMRadio/IFMRadioService.aidl
LOCAL_SRC_FILES += src/com/android/factoryservice/IFmNative.aidl

LOCAL_PACKAGE_NAME := LTE
LOCAL_CERTIFICATE := platform
LOCAL_JNI_SHARED_LIBRARIES := libfmjni
LOCAL_JNI_SHARED_LIBRARIES := libps-jni

LOCAL_STATIC_JAVA_LIBRARIES := com.android.phone.shared \

LOCAL_JAVA_LIBRARIES := telephony-common
LOCAL_JAVA_LIBRARIES += mediatek-framework
LOCAL_JAVA_LIBRARIES += com.ireadygo.cloudsim
include $(BUILD_PACKAGE)


# ============================================================

include $(call all-makefiles-under,$(LOCAL_PATH))

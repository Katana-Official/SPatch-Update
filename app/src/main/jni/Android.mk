#
# Copyright (C) 2014 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# ==========================================================
# Setup some common variables for the different build
# targets here.
# ==========================================================
LOCAL_PATH:= $(call my-dir)

aaptMain := Main.cpp
aaptSources := \
    AaptAssets.cpp \
    AaptConfig.cpp \
    AaptUtil.cpp \
    AaptXml.cpp \
    ApkBuilder.cpp \
    Command.cpp \
    CrunchCache.cpp \
    FileFinder.cpp \
    Images.cpp \
    Package.cpp \
    pseudolocalize.cpp \
    Resource.cpp \
    ResourceFilter.cpp \
    ResourceIdCache.cpp \
    ResourceTable.cpp \
    SourcePos.cpp \
    StringPool.cpp \
    WorkQueue.cpp \
    XMLNode.cpp \
    ZipEntry.cpp \
    ZipFile.cpp \
    system_properties.cpp
# add system_properties from bionic to satisfy linkage to liblog

aaptCFlags := -DAAPT_VERSION=\"93fdc3faa2\"
aaptCFlags += -std=gnu++11

# ==========================================================
# Build the target executable: aapt
# ==========================================================
include $(CLEAR_VARS)

LOCAL_MODULE := aapt
LOCAL_C_INCLUDES += \
    $(LOCAL_PATH)/include \
    $(LOCAL_PATH)/libbase/include \
    $(LOCAL_PATH)/libexpat/lib \
    $(LOCAL_PATH)/libpng
LOCAL_CFLAGS := $(aaptCFlags) -w
LOCAL_CPPFLAGS := $(aaptCppFlags)
LOCAL_SRC_FILES := $(aaptSources) $(aaptMain)
LOCAL_LDFLAGS += \
    $(LOCAL_PATH)/obj/local/armeabi/libandroidfw.a \
    $(LOCAL_PATH)/obj/local/armeabi/libutils.a \
    $(LOCAL_PATH)/obj/local/armeabi/libcutils.a \
    $(LOCAL_PATH)/obj/local/armeabi/libexpat.a \
    $(LOCAL_PATH)/obj/local/armeabi/liblog.a \
    $(LOCAL_PATH)/obj/local/armeabi/libpng.a \
    $(LOCAL_PATH)/obj/local/armeabi/libz.a \
    $(LOCAL_PATH)/obj/local/armeabi/libziparchive.a \
    $(LOCAL_PATH)/obj/local/armeabi/libbase.a \

LOCAL_LDFLAGS += -static
LOCAL_LDLIBS := -latomic

include $(BUILD_EXECUTABLE)

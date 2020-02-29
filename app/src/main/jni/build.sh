#!/bin/sh

case "$(uname -s)" in
  CYGWIN*|MINGW32*|MSYS*) ext=.cmd;
esac;

case $1 in
  clean)
    rm -rf libs obj;
    exit 0;
  ;;
esac;

if [ ! "$NDK_ROOT" ]; then
  NDK_ROOT=~/android/android-ndk-r*;
fi;
if [ ! "$NDK_TOOLCHAIN_VERSION" ]; then
  NDK_TOOLCHAIN_VERSION=clang;
fi;
if [ ! "$APP_ABI" ]; then
  APP_ABI=armeabi;
fi;
if [ ! "$APP_PLATFORM" ]; then
  APP_PLATFORM=android-21;
fi;

for i in libandroidfw libbase libcutils libexpat liblog libpng libutils libziparchive zlib; do
  cd $i;
  case $i in
    libandroidfw)
      # CursorWindow appears to require a different STL; compile it first so its object can be used later
      $NDK_ROOT/ndk-build$ext NDK_PROJECT_PATH=. NDK_OUT=../obj NDK_LIBS_OUT=../libs APP_BUILD_SCRIPT=./CursorWindowHack.mk NDK_TOOLCHAIN_VERSION=$NDK_TOOLCHAIN_VERSION APP_ABI=$APP_ABI APP_PLATFORM=$APP_PLATFORM APP_STL=c++_static;
    ;;
    libcutils)
      # sockets requires a LOCAL_CFLAGS += -std=gnu++11 with clang; compile it first so its object can be used later
      $NDK_ROOT/ndk-build$ext NDK_PROJECT_PATH=. NDK_OUT=../obj NDK_LIBS_OUT=../libs APP_BUILD_SCRIPT=./socketsHack.mk NDK_TOOLCHAIN_VERSION=$NDK_TOOLCHAIN_VERSION APP_ABI=$APP_ABI APP_PLATFORM=$APP_PLATFORM APP_STL=c++_static;
    ;;
  esac;
  $NDK_ROOT/ndk-build$ext NDK_PROJECT_PATH=. NDK_OUT=../obj NDK_LIBS_OUT=../libs APP_BUILD_SCRIPT=./Android.mk NDK_TOOLCHAIN_VERSION=$NDK_TOOLCHAIN_VERSION APP_ABI=$APP_ABI APP_PLATFORM=$APP_PLATFORM APP_STL=gnustl_static;
  cd ..;
done;

$NDK_ROOT/ndk-build$ext NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk NDK_TOOLCHAIN_VERSION=$NDK_TOOLCHAIN_VERSION APP_ABI=$APP_ABI APP_PLATFORM=$APP_PLATFORM APP_STL=gnustl_static;
exit 0;


package com.swift.sandhook.utils;

import android.util.Log;
import com.swift.sandhook.HookLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Unsafe {
  private static final String TAG = "Unsafe";
  
  private static Method arrayBaseOffsetMethod;
  
  private static Method arrayIndexScaleMethod;
  
  private static Method getIntMethod;
  
  private static Method getLongMethod;
  
  private static Class objectArrayClass = Object[].class;
  
  private static volatile boolean supported = false;
  
  private static Object unsafe;
  
  private static Class unsafeClass;
  
  static  {
    try {
      Class<?> clazz = Class.forName("sun.misc.Unsafe");
      unsafeClass = clazz;
      Field field = clazz.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      unsafe = field.get(null);
    } catch (Exception exception) {
      try {
        Field field = unsafeClass.getDeclaredField("THE_ONE");
        field.setAccessible(true);
        unsafe = field.get(null);
      } catch (Exception exception1) {
        Log.w("Unsafe", "Unsafe not found o.O");
      } 
    } 
    if (unsafe != null)
      try {
        arrayBaseOffsetMethod = unsafeClass.getDeclaredMethod("arrayBaseOffset", new Class[] { Class.class });
        arrayIndexScaleMethod = unsafeClass.getDeclaredMethod("arrayIndexScale", new Class[] { Class.class });
        getIntMethod = unsafeClass.getDeclaredMethod("getInt", new Class[] { Object.class, long.class });
        getLongMethod = unsafeClass.getDeclaredMethod("getLong", new Class[] { Object.class, long.class });
        supported = true;
        return;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public static int arrayBaseOffset(Class paramClass) {
    try {
      return ((Integer)arrayBaseOffsetMethod.invoke(unsafe, new Object[] { paramClass })).intValue();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static int arrayIndexScale(Class paramClass) {
    try {
      return ((Integer)arrayIndexScaleMethod.invoke(unsafe, new Object[] { paramClass })).intValue();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static int getInt(Object paramObject, long paramLong) {
    try {
      return ((Integer)getIntMethod.invoke(unsafe, new Object[] { paramObject, Long.valueOf(paramLong) })).intValue();
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static long getLong(Object paramObject, long paramLong) {
    try {
      return ((Long)getLongMethod.invoke(unsafe, new Object[] { paramObject, Long.valueOf(paramLong) })).longValue();
    } catch (Exception exception) {
      return 0L;
    } 
  }
  
  public static long getObjectAddress(Object paramObject) {
    try {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramObject;
      if (arrayIndexScale(objectArrayClass) == 8)
        return getLong(arrayOfObject, arrayBaseOffset(objectArrayClass)); 
      int i = getInt(arrayOfObject, arrayBaseOffset(objectArrayClass));
      return i & 0xFFFFFFFFL;
    } catch (Exception exception) {
      HookLog.e("get object address error", exception);
      return -1L;
    } 
  }
  
  public static boolean support() { return supported; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhoo\\utils\Unsafe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
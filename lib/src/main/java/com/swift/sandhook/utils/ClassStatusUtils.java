package com.swift.sandhook.utils;

import com.swift.sandhook.SandHookConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class ClassStatusUtils {
  static Field fieldStatusOfClass;
  
  static  {
    try {
      Field field = Class.class.getDeclaredField("status");
      fieldStatusOfClass = field;
      field.setAccessible(true);
      return;
    } catch (NoSuchFieldException noSuchFieldException) {
      return;
    } 
  }
  
  public static int getClassStatus(Class paramClass, boolean paramBoolean) {
    int i = 0;
    if (paramClass == null)
      return 0; 
    try {
      int k = fieldStatusOfClass.getInt(paramClass);
      i = k;
    } finally {}
    int j = i;
    if (paramBoolean)
      j = (int)(toUnsignedLong(i) >> 28); 
    return j;
  }
  
  public static boolean isInitialized(Class paramClass) { return (fieldStatusOfClass == null) ? true : ((SandHookConfig.SDK_INT >= 28) ? ((getClassStatus(paramClass, true) == 14)) : ((SandHookConfig.SDK_INT == 27) ? ((getClassStatus(paramClass, false) == 11)) : ((getClassStatus(paramClass, false) == 10)))); }
  
  public static boolean isStaticAndNoInited(Member paramMember) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramMember != null) {
      if (paramMember instanceof java.lang.reflect.Constructor)
        return false; 
      Class<?> clazz = paramMember.getDeclaringClass();
      bool1 = bool2;
      if (Modifier.isStatic(paramMember.getModifiers())) {
        bool1 = bool2;
        if (!isInitialized(clazz))
          bool1 = true; 
      } 
    } 
    return bool1;
  }
  
  public static long toUnsignedLong(int paramInt) { return paramInt & 0xFFFFFFFFL; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhoo\\utils\ClassStatusUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
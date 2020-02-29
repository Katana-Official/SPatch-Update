package com.swift.sandhook.utils;

import com.swift.sandhook.SandHook;

public class ParamWrapper {
  private static boolean is64Bit = SandHook.is64Bit();
  
  public static Object addressToObject(Class paramClass, long paramLong) { return is64Bit ? addressToObject64(paramClass, paramLong) : addressToObject32(paramClass, (int)paramLong); }
  
  public static Object addressToObject32(Class<int> paramClass, int paramInt) {
    if (paramClass == null)
      return null; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return Integer.valueOf(paramInt); 
      if (paramClass == short.class)
        return Short.valueOf((short)paramInt); 
      if (paramClass == byte.class)
        return Byte.valueOf((byte)paramInt); 
      if (paramClass == char.class)
        return Character.valueOf((char)paramInt); 
      if (paramClass == boolean.class) {
        boolean bool;
        if (paramInt != 0) {
          bool = true;
        } else {
          bool = false;
        } 
        return Boolean.valueOf(bool);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unknown type: ");
      stringBuilder.append(paramClass.toString());
      throw new RuntimeException(stringBuilder.toString());
    } 
    return SandHook.getObject(paramInt);
  }
  
  public static Object addressToObject64(Class<int> paramClass, long paramLong) {
    if (paramClass == null)
      return null; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return Integer.valueOf((int)paramLong); 
      if (paramClass == long.class)
        return Long.valueOf(paramLong); 
      if (paramClass == short.class)
        return Short.valueOf((short)(int)paramLong); 
      if (paramClass == byte.class)
        return Byte.valueOf((byte)(int)paramLong); 
      if (paramClass == char.class)
        return Character.valueOf((char)(int)paramLong); 
      if (paramClass == boolean.class) {
        boolean bool;
        if (paramLong != 0L) {
          bool = true;
        } else {
          bool = false;
        } 
        return Boolean.valueOf(bool);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unknown type: ");
      stringBuilder.append(paramClass.toString());
      throw new RuntimeException(stringBuilder.toString());
    } 
    return SandHook.getObject(paramLong);
  }
  
  public static long objectToAddress(Class paramClass, Object paramObject) { return is64Bit ? objectToAddress64(paramClass, paramObject) : objectToAddress32(paramClass, paramObject); }
  
  public static int objectToAddress32(Class paramClass, Object paramObject) { throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n"); }
  
  public static long objectToAddress64(Class<int> paramClass, Object paramObject) {
    long l = 0L;
    if (paramObject == null)
      return 0L; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return ((Integer)paramObject).intValue(); 
      if (paramClass == long.class)
        return ((Long)paramObject).longValue(); 
      if (paramClass == short.class)
        return ((Short)paramObject).shortValue(); 
      if (paramClass == byte.class)
        return ((Byte)paramObject).byteValue(); 
      if (paramClass == char.class)
        return ((Character)paramObject).charValue(); 
      if (paramClass == boolean.class) {
        if (Boolean.TRUE.equals(paramObject))
          l = 1L; 
        return l;
      } 
      paramObject = new StringBuilder();
      paramObject.append("unknown type: ");
      paramObject.append(paramClass.toString());
      throw new RuntimeException(paramObject.toString());
    } 
    return SandHook.getObjectAddress(paramObject);
  }
  
  public static boolean support(Class<float> paramClass) { return is64Bit ? ((paramClass != float.class && paramClass != double.class)) : ((paramClass != float.class && paramClass != double.class && paramClass != long.class)); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhoo\\utils\ParamWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
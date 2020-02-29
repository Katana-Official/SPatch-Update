package com.swift.sandhook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SandHookMethodResolver {
  public static Field artMethodField;
  
  public static boolean canResolvedInJava = false;
  
  public static Field dexCacheField;
  
  public static int dexMethodIndex = 0;
  
  public static Field dexMethodIndexField;
  
  public static long entryPointFromCompiledCode = 0L;
  
  public static long entryPointFromInterpreter = 0L;
  
  public static Field fieldEntryPointFromCompiledCode;
  
  public static Field fieldEntryPointFromInterpreter;
  
  public static boolean isArtMethod = false;
  
  public static long resolvedMethodsAddress;
  
  public static Field resolvedMethodsField;
  
  public static Object testArtMethod;
  
  public static Method testMethod;
  
  static  {
  
  }
  
  private static void checkSupport() {
    try {
      Field field = SandHook.getField(Method.class, "artMethod");
      artMethodField = field;
      testArtMethod = field.get(testMethod);
      if (SandHook.hasJavaArtMethod() && testArtMethod.getClass() == SandHook.artMethodClass) {
        checkSupportForArtMethod();
        isArtMethod = true;
        return;
      } 
      if (testArtMethod instanceof Long) {
        checkSupportForArtMethodId();
        isArtMethod = false;
        return;
      } 
      canResolvedInJava = false;
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  private static void checkSupportForArtMethod() throws Exception { // Byte code:
    //   0: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   3: ldc 'dexMethodIndex'
    //   5: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   8: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   11: goto -> 25
    //   14: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   17: ldc 'methodDexIndex'
    //   19: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   22: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   25: ldc java/lang/Class
    //   27: ldc 'dexCache'
    //   29: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   32: astore_0
    //   33: aload_0
    //   34: putstatic com/swift/sandhook/SandHookMethodResolver.dexCacheField : Ljava/lang/reflect/Field;
    //   37: aload_0
    //   38: getstatic com/swift/sandhook/SandHookMethodResolver.testMethod : Ljava/lang/reflect/Method;
    //   41: invokevirtual getDeclaringClass : ()Ljava/lang/Class;
    //   44: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   47: astore_0
    //   48: aload_0
    //   49: invokevirtual getClass : ()Ljava/lang/Class;
    //   52: ldc 'resolvedMethods'
    //   54: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   57: astore_1
    //   58: aload_1
    //   59: putstatic com/swift/sandhook/SandHookMethodResolver.resolvedMethodsField : Ljava/lang/reflect/Field;
    //   62: aload_1
    //   63: aload_0
    //   64: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   67: instanceof [Ljava/lang/Object;
    //   70: ifeq -> 77
    //   73: iconst_1
    //   74: putstatic com/swift/sandhook/SandHookMethodResolver.canResolvedInJava : Z
    //   77: getstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   80: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   83: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   86: checkcast java/lang/Integer
    //   89: invokevirtual intValue : ()I
    //   92: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndex : I
    //   95: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   98: ldc 'entryPointFromQuickCompiledCode'
    //   100: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   103: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   106: goto -> 120
    //   109: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   112: ldc 'entryPointFromCompiledCode'
    //   114: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   117: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   120: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   123: invokevirtual getType : ()Ljava/lang/Class;
    //   126: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   129: if_acmpne -> 148
    //   132: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   135: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   138: invokevirtual getInt : (Ljava/lang/Object;)I
    //   141: i2l
    //   142: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromCompiledCode : J
    //   145: goto -> 172
    //   148: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   151: invokevirtual getType : ()Ljava/lang/Class;
    //   154: getstatic java/lang/Long.TYPE : Ljava/lang/Class;
    //   157: if_acmpne -> 172
    //   160: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   163: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   166: invokevirtual getLong : (Ljava/lang/Object;)J
    //   169: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromCompiledCode : J
    //   172: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   175: ldc 'entryPointFromInterpreter'
    //   177: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   180: astore_0
    //   181: aload_0
    //   182: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   185: aload_0
    //   186: invokevirtual getType : ()Ljava/lang/Class;
    //   189: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   192: if_acmpne -> 209
    //   195: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   198: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   201: invokevirtual getInt : (Ljava/lang/Object;)I
    //   204: i2l
    //   205: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromInterpreter : J
    //   208: return
    //   209: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   212: invokevirtual getType : ()Ljava/lang/Class;
    //   215: getstatic java/lang/Long.TYPE : Ljava/lang/Class;
    //   218: if_acmpne -> 233
    //   221: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   224: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   227: invokevirtual getLong : (Ljava/lang/Object;)J
    //   230: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromInterpreter : J
    //   233: return
    //   234: astore_0
    //   235: goto -> 14
    //   238: astore_0
    //   239: goto -> 95
    //   242: astore_0
    //   243: goto -> 109
    //   246: astore_0
    //   247: return
    // Exception table:
    //   from	to	target	type
    //   0	11	234	java/lang/NoSuchFieldException
    //   77	95	238	finally
    //   95	106	242	finally
    //   109	120	246	finally
    //   120	145	246	finally
    //   148	172	246	finally
    //   172	208	246	finally
    //   209	233	246	finally }
  
  private static void checkSupportForArtMethodId() throws Exception {
    Field field1 = SandHook.getField(Method.class, "dexMethodIndex");
    dexMethodIndexField = field1;
    dexMethodIndex = ((Integer)field1.get(testMethod)).intValue();
    field1 = SandHook.getField(Class.class, "dexCache");
    dexCacheField = field1;
    Object object = field1.get(testMethod.getDeclaringClass());
    Field field2 = SandHook.getField(object.getClass(), "resolvedMethods");
    resolvedMethodsField = field2;
    object = field2.get(object);
    if (object instanceof Long) {
      canResolvedInJava = false;
      resolvedMethodsAddress = ((Long)object).longValue();
      return;
    } 
    if (object instanceof long[]) {
      canResolvedInJava = true;
      return;
    } 
    if (object instanceof int[])
      canResolvedInJava = true; 
  }
  
  public static void init() {
    testMethod = SandHook.testOffsetMethod1;
    checkSupport();
  }
  
  private static void resolveInJava(Method paramMethod1, Method paramMethod2) throws Exception {
    Object object2;
    Object object1 = dexCacheField.get(paramMethod1.getDeclaringClass());
    if (isArtMethod) {
      object2 = artMethodField.get(paramMethod2);
      int j = ((Integer)dexMethodIndexField.get(object2)).intValue();
      ((Object[])resolvedMethodsField.get(object1))[j] = object2;
      return;
    } 
    int i = ((Integer)dexMethodIndexField.get(object2)).intValue();
    object1 = resolvedMethodsField.get(object1);
    if (object1 instanceof long[]) {
      long l = ((Long)artMethodField.get(object2)).longValue();
      ((long[])object1)[i] = l;
      return;
    } 
    if (object1 instanceof int[]) {
      int j = Long.valueOf(((Long)artMethodField.get(object2)).longValue()).intValue();
      ((int[])object1)[i] = j;
      return;
    } 
    throw new UnsupportedOperationException("un support");
  }
  
  private static void resolveInNative(Method paramMethod1, Method paramMethod2) { SandHook.ensureMethodCached(paramMethod1, paramMethod2); }
  
  public static void resolveMethod(Method paramMethod1, Method paramMethod2) {
    if (canResolvedInJava && artMethodField != null)
      try {
        resolveInJava(paramMethod1, paramMethod2);
        return;
      } catch (Exception exception) {
        resolveInNative(paramMethod1, paramMethod2);
        return;
      }  
    resolveInNative(paramMethod1, paramMethod2);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\SandHookMethodResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
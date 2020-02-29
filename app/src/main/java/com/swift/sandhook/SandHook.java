package com.swift.sandhook;

import com.swift.sandhook.utils.ReflectionUtils;
import com.swift.sandhook.utils.Unsafe;
import com.swift.sandhook.wrapper.HookErrorException;
import com.swift.sandhook.wrapper.HookWrapper;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SandHook {
  public static Class artMethodClass;
  
  static Map<Method, HookWrapper.HookEntity> globalBackupMap;
  
  static Map<Member, HookWrapper.HookEntity> globalHookEntityMap = new ConcurrentHashMap<Member, HookWrapper.HookEntity>();
  
  private static HookModeCallBack hookModeCallBack;
  
  private static HookResultCallBack hookResultCallBack;
  
  public static Field nativePeerField;
  
  public static int testAccessFlag;
  
  public static Object testOffsetArtMethod1;
  
  public static Object testOffsetArtMethod2;
  
  public static Method testOffsetMethod1;
  
  public static Method testOffsetMethod2;
  
  static  {
    globalBackupMap = new ConcurrentHashMap<Method, HookWrapper.HookEntity>();
    SandHookConfig.libLoader.loadLib();
    init();
  }
  
  public static void addHookClass(ClassLoader paramClassLoader, Class... paramVarArgs) throws HookErrorException { HookWrapper.addHookClass(paramClassLoader, paramVarArgs); }
  
  public static void addHookClass(Class... paramVarArgs) throws HookErrorException { HookWrapper.addHookClass(paramVarArgs); }
  
  public static final Object callOriginByBackup(Method paramMethod, Object paramObject, Object... paramVarArgs) throws Throwable {
    HookWrapper.HookEntity hookEntity = globalBackupMap.get(paramMethod);
    return (hookEntity == null) ? null : callOriginMethod(hookEntity.backupIsStub, hookEntity.target, paramMethod, paramObject, paramVarArgs);
  }
  
  public static final Object callOriginMethod(Member paramMember, Object paramObject, Object... paramVarArgs) throws Throwable {
    HookWrapper.HookEntity hookEntity = globalHookEntityMap.get(paramMember);
    return (hookEntity == null || hookEntity.backup == null) ? null : callOriginMethod(hookEntity.backupIsStub, paramMember, hookEntity.backup, paramObject, paramVarArgs);
  }
  
  public static final Object callOriginMethod(Member paramMember, Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws Throwable { return callOriginMethod(true, paramMember, paramMethod, paramObject, paramArrayOfObject); }
  
  public static final Object callOriginMethod(boolean paramBoolean, Member paramMember, Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws Throwable {
    if (!paramBoolean && SandHookConfig.SDK_INT >= 24) {
      paramMember.getDeclaringClass();
      ensureDeclareClass(paramMember, paramMethod);
    } 
    if (Modifier.isStatic(paramMember.getModifiers()))
      try {
        return paramMethod.invoke(null, paramArrayOfObject);
      } catch (InvocationTargetException invocationTargetException) {
        if (invocationTargetException.getCause() != null)
          throw invocationTargetException.getCause(); 
        throw invocationTargetException;
      }  
    try {
      return paramMethod.invoke(paramObject, paramArrayOfObject);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() != null)
        throw invocationTargetException.getCause(); 
      throw invocationTargetException;
    } 
  }
  
  public static native boolean canGetObject();
  
  public static boolean canGetObjectAddress() { return Unsafe.support(); }
  
  public static native boolean compileMethod(Member paramMember);
  
  public static native boolean deCompileMethod(Member paramMember, boolean paramBoolean);
  
  public static native boolean disableDex2oatInline(boolean paramBoolean);
  
  public static native boolean disableVMInline();
  
  public static final void ensureBackupMethod(Method paramMethod) {
    if (SandHookConfig.SDK_INT < 24)
      return; 
    HookWrapper.HookEntity hookEntity = globalBackupMap.get(paramMethod);
    if (hookEntity != null)
      ensureDeclareClass(hookEntity.target, paramMethod); 
  }
  
  public static native void ensureDeclareClass(Member paramMember, Method paramMethod);
  
  public static native void ensureMethodCached(Method paramMethod1, Method paramMethod2);
  
  private static Object[] getFakeArgs(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    return (arrayOfClass == null || arrayOfClass.length == 0) ? new Object[] { new Object() } : null;
  }
  
  public static Field getField(Class<Object> paramClass, String paramString) throws NoSuchFieldException {
    while (true) {
      if (paramClass != null && paramClass != Object.class)
        try {
          Field field = paramClass.getDeclaredField(paramString);
          field.setAccessible(true);
          return field;
        } catch (Exception exception) {
          paramClass = (Class)paramClass.getSuperclass();
          continue;
        }  
      throw new NoSuchFieldException(paramString);
    } 
  }
  
  public static Object getObject(long paramLong) { return getObjectNative(getThreadId(), paramLong); }
  
  public static long getObjectAddress(Object paramObject) { return Unsafe.getObjectAddress(paramObject); }
  
  public static native Object getObjectNative(long paramLong1, long paramLong2);
  
  public static long getThreadId() {
    Field field = nativePeerField;
    if (field == null)
      return 0L; 
    try {
      return (field.getType() == int.class) ? nativePeerField.getInt(Thread.currentThread()) : nativePeerField.getLong(Thread.currentThread());
    } catch (IllegalAccessException illegalAccessException) {
      return 0L;
    } 
  }
  
  public static boolean hasJavaArtMethod() {
    if (SandHookConfig.SDK_INT >= 26)
      return false; 
    if (artMethodClass != null)
      return true; 
    try {
      ClassLoader classLoader = SandHookConfig.initClassLoader;
      if (classLoader == null) {
        artMethodClass = Class.forName("java.lang.reflect.ArtMethod");
        return true;
      } 
      artMethodClass = Class.forName("java.lang.reflect.ArtMethod", true, SandHookConfig.initClassLoader);
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    } 
  }
  
  public static void hook(HookWrapper.HookEntity paramHookEntity) throws HookErrorException { // Byte code:
    //   0: ldc com/swift/sandhook/SandHook
    //   2: monitorenter
    //   3: aload_0
    //   4: ifnull -> 545
    //   7: aload_0
    //   8: getfield target : Ljava/lang/reflect/Member;
    //   11: astore_3
    //   12: aload_0
    //   13: getfield hook : Ljava/lang/reflect/Method;
    //   16: astore #4
    //   18: aload_0
    //   19: getfield backup : Ljava/lang/reflect/Method;
    //   22: astore #5
    //   24: aload_3
    //   25: ifnull -> 534
    //   28: aload #4
    //   30: ifnull -> 534
    //   33: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   36: aload_0
    //   37: getfield target : Ljava/lang/reflect/Member;
    //   40: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   45: ifne -> 486
    //   48: aload_3
    //   49: invokestatic canNotHook : (Ljava/lang/reflect/Member;)Z
    //   52: ifne -> 438
    //   55: getstatic com/swift/sandhook/SandHookConfig.delayHook : Z
    //   58: ifeq -> 85
    //   61: invokestatic canWork : ()Z
    //   64: ifeq -> 85
    //   67: aload_0
    //   68: getfield target : Ljava/lang/reflect/Member;
    //   71: invokestatic isStaticAndNoInited : (Ljava/lang/reflect/Member;)Z
    //   74: ifeq -> 85
    //   77: aload_0
    //   78: invokestatic addPendingHook : (Lcom/swift/sandhook/wrapper/HookWrapper$HookEntity;)V
    //   81: ldc com/swift/sandhook/SandHook
    //   83: monitorexit
    //   84: return
    //   85: aload_0
    //   86: getfield initClass : Z
    //   89: ifeq -> 97
    //   92: aload_3
    //   93: invokestatic resolveStaticMethod : (Ljava/lang/reflect/Member;)Z
    //   96: pop
    //   97: aload #5
    //   99: invokestatic resolveStaticMethod : (Ljava/lang/reflect/Member;)Z
    //   102: pop
    //   103: aload #5
    //   105: ifnull -> 122
    //   108: aload_0
    //   109: getfield resolveDexCache : Z
    //   112: ifeq -> 122
    //   115: aload #4
    //   117: aload #5
    //   119: invokestatic resolveMethod : (Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
    //   122: aload_3
    //   123: instanceof java/lang/reflect/Method
    //   126: ifeq -> 137
    //   129: aload_3
    //   130: checkcast java/lang/reflect/Method
    //   133: iconst_1
    //   134: invokevirtual setAccessible : (Z)V
    //   137: getstatic com/swift/sandhook/SandHook.hookModeCallBack : Lcom/swift/sandhook/SandHook$HookModeCallBack;
    //   140: astore #6
    //   142: iconst_0
    //   143: istore_2
    //   144: aload #6
    //   146: ifnull -> 561
    //   149: getstatic com/swift/sandhook/SandHook.hookModeCallBack : Lcom/swift/sandhook/SandHook$HookModeCallBack;
    //   152: aload_3
    //   153: invokeinterface hookMode : (Ljava/lang/reflect/Member;)I
    //   158: istore_1
    //   159: goto -> 162
    //   162: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   165: aload_0
    //   166: getfield target : Ljava/lang/reflect/Member;
    //   169: aload_0
    //   170: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   175: pop
    //   176: iload_1
    //   177: ifeq -> 193
    //   180: aload_3
    //   181: aload #4
    //   183: aload #5
    //   185: iload_1
    //   186: invokestatic hookMethod : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;I)I
    //   189: istore_1
    //   190: goto -> 234
    //   193: aload #4
    //   195: ldc_w com/swift/sandhook/annotation/HookMode
    //   198: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
    //   201: checkcast com/swift/sandhook/annotation/HookMode
    //   204: astore #6
    //   206: aload #6
    //   208: ifnonnull -> 216
    //   211: iconst_0
    //   212: istore_1
    //   213: goto -> 224
    //   216: aload #6
    //   218: invokeinterface value : ()I
    //   223: istore_1
    //   224: aload_3
    //   225: aload #4
    //   227: aload #5
    //   229: iload_1
    //   230: invokestatic hookMethod : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;I)I
    //   233: istore_1
    //   234: iload_1
    //   235: ifle -> 249
    //   238: aload #5
    //   240: ifnull -> 249
    //   243: aload #5
    //   245: iconst_1
    //   246: invokevirtual setAccessible : (Z)V
    //   249: aload_0
    //   250: iload_1
    //   251: putfield hookMode : I
    //   254: getstatic com/swift/sandhook/SandHook.hookResultCallBack : Lcom/swift/sandhook/SandHook$HookResultCallBack;
    //   257: ifnull -> 278
    //   260: getstatic com/swift/sandhook/SandHook.hookResultCallBack : Lcom/swift/sandhook/SandHook$HookResultCallBack;
    //   263: astore_3
    //   264: iload_1
    //   265: ifle -> 270
    //   268: iconst_1
    //   269: istore_2
    //   270: aload_3
    //   271: iload_2
    //   272: aload_0
    //   273: invokeinterface hookResult : (ZLcom/swift/sandhook/wrapper/HookWrapper$HookEntity;)V
    //   278: iload_1
    //   279: iflt -> 377
    //   282: aload_0
    //   283: getfield backup : Ljava/lang/reflect/Method;
    //   286: ifnull -> 303
    //   289: getstatic com/swift/sandhook/SandHook.globalBackupMap : Ljava/util/Map;
    //   292: aload_0
    //   293: getfield backup : Ljava/lang/reflect/Method;
    //   296: aload_0
    //   297: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   302: pop
    //   303: new java/lang/StringBuilder
    //   306: dup
    //   307: invokespecial <init> : ()V
    //   310: astore_3
    //   311: aload_3
    //   312: ldc_w 'method <'
    //   315: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload_3
    //   320: aload_0
    //   321: getfield target : Ljava/lang/reflect/Member;
    //   324: invokevirtual toString : ()Ljava/lang/String;
    //   327: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: pop
    //   331: aload_3
    //   332: ldc_w '> hook <'
    //   335: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: pop
    //   339: iload_1
    //   340: iconst_1
    //   341: if_icmpne -> 566
    //   344: ldc_w 'inline'
    //   347: astore_0
    //   348: goto -> 351
    //   351: aload_3
    //   352: aload_0
    //   353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: pop
    //   357: aload_3
    //   358: ldc_w '> success!'
    //   361: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   364: pop
    //   365: aload_3
    //   366: invokevirtual toString : ()Ljava/lang/String;
    //   369: invokestatic d : (Ljava/lang/String;)I
    //   372: pop
    //   373: ldc com/swift/sandhook/SandHook
    //   375: monitorexit
    //   376: return
    //   377: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   380: aload_0
    //   381: getfield target : Ljava/lang/reflect/Member;
    //   384: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   389: pop
    //   390: new java/lang/StringBuilder
    //   393: dup
    //   394: invokespecial <init> : ()V
    //   397: astore_3
    //   398: aload_3
    //   399: ldc_w 'hook method <'
    //   402: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   405: pop
    //   406: aload_3
    //   407: aload_0
    //   408: getfield target : Ljava/lang/reflect/Member;
    //   411: invokevirtual toString : ()Ljava/lang/String;
    //   414: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   417: pop
    //   418: aload_3
    //   419: ldc_w '> error in native!'
    //   422: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   425: pop
    //   426: new com/swift/sandhook/wrapper/HookErrorException
    //   429: dup
    //   430: aload_3
    //   431: invokevirtual toString : ()Ljava/lang/String;
    //   434: invokespecial <init> : (Ljava/lang/String;)V
    //   437: athrow
    //   438: new java/lang/StringBuilder
    //   441: dup
    //   442: invokespecial <init> : ()V
    //   445: astore_3
    //   446: aload_3
    //   447: ldc_w 'method <'
    //   450: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   453: pop
    //   454: aload_3
    //   455: aload_0
    //   456: getfield target : Ljava/lang/reflect/Member;
    //   459: invokevirtual toString : ()Ljava/lang/String;
    //   462: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: pop
    //   466: aload_3
    //   467: ldc_w '> can not hook, because of in blacklist!'
    //   470: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   473: pop
    //   474: new com/swift/sandhook/wrapper/HookErrorException
    //   477: dup
    //   478: aload_3
    //   479: invokevirtual toString : ()Ljava/lang/String;
    //   482: invokespecial <init> : (Ljava/lang/String;)V
    //   485: athrow
    //   486: new java/lang/StringBuilder
    //   489: dup
    //   490: invokespecial <init> : ()V
    //   493: astore_3
    //   494: aload_3
    //   495: ldc_w 'method <'
    //   498: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload_3
    //   503: aload_0
    //   504: getfield target : Ljava/lang/reflect/Member;
    //   507: invokevirtual toString : ()Ljava/lang/String;
    //   510: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   513: pop
    //   514: aload_3
    //   515: ldc_w '> has been hooked!'
    //   518: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   521: pop
    //   522: new com/swift/sandhook/wrapper/HookErrorException
    //   525: dup
    //   526: aload_3
    //   527: invokevirtual toString : ()Ljava/lang/String;
    //   530: invokespecial <init> : (Ljava/lang/String;)V
    //   533: athrow
    //   534: new com/swift/sandhook/wrapper/HookErrorException
    //   537: dup
    //   538: ldc_w 'null input'
    //   541: invokespecial <init> : (Ljava/lang/String;)V
    //   544: athrow
    //   545: new com/swift/sandhook/wrapper/HookErrorException
    //   548: dup
    //   549: ldc_w 'null hook entity'
    //   552: invokespecial <init> : (Ljava/lang/String;)V
    //   555: athrow
    //   556: ldc com/swift/sandhook/SandHook
    //   558: monitorexit
    //   559: aload_0
    //   560: athrow
    //   561: iconst_0
    //   562: istore_1
    //   563: goto -> 162
    //   566: ldc_w 'replacement'
    //   569: astore_0
    //   570: goto -> 351
    //   573: astore_0
    //   574: goto -> 556
    // Exception table:
    //   from	to	target	type
    //   7	24	573	finally
    //   33	81	573	finally
    //   85	97	573	finally
    //   97	103	573	finally
    //   108	122	573	finally
    //   122	137	573	finally
    //   137	142	573	finally
    //   149	159	573	finally
    //   162	176	573	finally
    //   180	190	573	finally
    //   193	206	573	finally
    //   216	224	573	finally
    //   224	234	573	finally
    //   243	249	573	finally
    //   249	264	573	finally
    //   270	278	573	finally
    //   282	303	573	finally
    //   303	339	573	finally
    //   351	373	573	finally
    //   377	438	573	finally
    //   438	486	573	finally
    //   486	534	573	finally
    //   534	545	573	finally
    //   545	556	573	finally }
  
  private static native int hookMethod(Member paramMember, Method paramMethod1, Method paramMethod2, int paramInt);
  
  private static boolean init() {
    initTestOffset();
    initThreadPeer();
    SandHookMethodResolver.init();
    return initNative(SandHookConfig.SDK_INT, SandHookConfig.DEBUG);
  }
  
  public static native boolean initForPendingHook();
  
  private static native boolean initNative(int paramInt, boolean paramBoolean);
  
  private static void initTestAccessFlag() {
    if (hasJavaArtMethod())
      try {
        loadArtMethod();
        testAccessFlag = ((Integer)getField(artMethodClass, "accessFlags").get(testOffsetArtMethod1)).intValue();
        return;
      } catch (Exception exception) {
        return;
      }  
    testAccessFlag = ((Integer)getField(Method.class, "accessFlags").get(testOffsetMethod1)).intValue();
  }
  
  private static void initTestOffset() {
    ArtMethodSizeTest.method1();
    ArtMethodSizeTest.method2();
    try {
      testOffsetMethod1 = ArtMethodSizeTest.class.getDeclaredMethod("method1", new Class[0]);
      testOffsetMethod2 = ArtMethodSizeTest.class.getDeclaredMethod("method2", new Class[0]);
      initTestAccessFlag();
      return;
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new RuntimeException("SandHook init error", noSuchMethodException);
    } 
  }
  
  private static void initThreadPeer() {
    try {
      nativePeerField = getField(Thread.class, "nativePeer");
      return;
    } catch (NoSuchFieldException noSuchFieldException) {
      return;
    } 
  }
  
  public static native boolean is64Bit();
  
  private static void loadArtMethod() {
    try {
      Field field = getField(Method.class, "artMethod");
      testOffsetArtMethod1 = field.get(testOffsetMethod1);
      testOffsetArtMethod2 = field.get(testOffsetMethod2);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
      return;
    } catch (NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.printStackTrace();
      return;
    } 
  }
  
  public static boolean passApiCheck() { return ReflectionUtils.passApiCheck(); }
  
  public static boolean resolveStaticMethod(Member paramMember) {
    if (paramMember == null)
      return true; 
    try {
      return true;
    } catch (ExceptionInInitializerError exceptionInInitializerError) {
      return false;
    } finally {
      paramMember = null;
    } 
  }
  
  public static native void setHookMode(int paramInt);
  
  public static void setHookModeCallBack(HookModeCallBack paramHookModeCallBack) { hookModeCallBack = paramHookModeCallBack; }
  
  public static void setHookResultCallBack(HookResultCallBack paramHookResultCallBack) { hookResultCallBack = paramHookResultCallBack; }
  
  public static native void setInlineSafeCheck(boolean paramBoolean);
  
  public static native boolean setNativeEntry(Member paramMember1, Member paramMember2, long paramLong);
  
  public static native void skipAllSafeCheck(boolean paramBoolean);
  
  public static boolean tryDisableProfile(String paramString) {
    if (SandHookConfig.SDK_INT < 24)
      return false; 
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("/data/misc/profiles/cur/");
      stringBuilder.append(SandHookConfig.curUser);
      stringBuilder.append("/");
      stringBuilder.append(paramString);
      stringBuilder.append("/primary.prof");
      File file = new File(stringBuilder.toString());
      boolean bool = file.getParentFile().exists();
      if (!bool)
        return false; 
    } finally {
      paramString = null;
    } 
  }
  
  @FunctionalInterface
  public static interface HookModeCallBack {
    int hookMode(Member param1Member);
  }
  
  @FunctionalInterface
  public static interface HookResultCallBack {
    void hookResult(boolean param1Boolean, HookWrapper.HookEntity param1HookEntity);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\SandHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
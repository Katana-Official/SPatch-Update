package com.swift.sandhook;

import com.swift.sandhook.wrapper.HookErrorException;
import com.swift.sandhook.wrapper.HookWrapper;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class PendingHookHandler {
  private static boolean canUsePendingHook;
  
  private static Map<Class, Vector<HookWrapper.HookEntity>> pendingHooks = (Map)new ConcurrentHashMap<Class<?>, Vector<HookWrapper.HookEntity>>();
  
  static  {
    canUsePendingHook = SandHook.initForPendingHook();
  }
  
  public static void addPendingHook(HookWrapper.HookEntity paramHookEntity) { // Byte code:
    //   0: ldc com/swift/sandhook/PendingHookHandler
    //   2: monitorenter
    //   3: getstatic com/swift/sandhook/PendingHookHandler.pendingHooks : Ljava/util/Map;
    //   6: aload_0
    //   7: getfield target : Ljava/lang/reflect/Member;
    //   10: invokeinterface getDeclaringClass : ()Ljava/lang/Class;
    //   15: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   20: checkcast java/util/Vector
    //   23: astore_2
    //   24: aload_2
    //   25: astore_1
    //   26: aload_2
    //   27: ifnonnull -> 57
    //   30: new java/util/Vector
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore_1
    //   38: getstatic com/swift/sandhook/PendingHookHandler.pendingHooks : Ljava/util/Map;
    //   41: aload_0
    //   42: getfield target : Ljava/lang/reflect/Member;
    //   45: invokeinterface getDeclaringClass : ()Ljava/lang/Class;
    //   50: aload_1
    //   51: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   56: pop
    //   57: aload_1
    //   58: aload_0
    //   59: invokevirtual add : (Ljava/lang/Object;)Z
    //   62: pop
    //   63: ldc com/swift/sandhook/PendingHookHandler
    //   65: monitorexit
    //   66: return
    //   67: astore_0
    //   68: ldc com/swift/sandhook/PendingHookHandler
    //   70: monitorexit
    //   71: aload_0
    //   72: athrow
    // Exception table:
    //   from	to	target	type
    //   3	24	67	finally
    //   30	57	67	finally
    //   57	63	67	finally }
  
  public static boolean canWork() { return (canUsePendingHook && SandHook.canGetObject() && !SandHookConfig.DEBUG); }
  
  public static void onClassInit(long paramLong) {
    if (paramLong == 0L)
      return; 
    Class clazz = (Class)SandHook.getObject(paramLong);
    if (clazz == null)
      return; 
    Vector vector = pendingHooks.get(clazz);
    if (vector == null)
      return; 
    for (HookWrapper.HookEntity hookEntity : vector) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("do pending hook for method: ");
      stringBuilder.append(hookEntity.target.toString());
      HookLog.w(stringBuilder.toString());
      try {
        hookEntity.initClass = false;
        SandHook.hook(hookEntity);
      } catch (HookErrorException hookErrorException) {
        HookLog.e("Pending Hook Error!", (Throwable)hookErrorException);
      } 
    } 
    pendingHooks.remove(clazz);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\PendingHookHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
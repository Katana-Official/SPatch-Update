package com.swift.sandhook.blacklist;

import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Set;

public class HookBlackList {
  public static Set<Class> classBlackList;
  
  public static Set<String> methodBlackList = new HashSet<String>();
  
  public static Set<String> methodUseInHookBridge;
  
  public static Set<String> methodUseInHookStub;
  
  static  {
    classBlackList = (Set)new HashSet<Class<?>>();
    methodUseInHookBridge = new HashSet<String>();
    methodUseInHookStub = new HashSet<String>();
    methodBlackList.add("java.lang.reflect.Method.invoke");
    methodBlackList.add("java.lang.reflect.AccessibleObject.setAccessible");
    methodUseInHookBridge.add("java.lang.Class.getDeclaredField");
    methodUseInHookBridge.add("java.lang.reflect.InvocationTargetException.getCause");
    methodUseInHookStub.add("java.lang.Object.equals");
    methodUseInHookStub.add("java.lang.Class.isPrimitive");
  }
  
  public static final boolean canNotHook(Member paramMember) {
    if (classBlackList.contains(paramMember.getDeclaringClass()))
      return true; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramMember.getDeclaringClass().getName());
    stringBuilder.append(".");
    stringBuilder.append(paramMember.getName());
    String str = stringBuilder.toString();
    return methodBlackList.contains(str);
  }
  
  public static final boolean canNotHookByBridge(Member paramMember) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramMember.getDeclaringClass().getName());
    stringBuilder.append(".");
    stringBuilder.append(paramMember.getName());
    String str = stringBuilder.toString();
    return methodUseInHookBridge.contains(str);
  }
  
  public static final boolean canNotHookByStub(Member paramMember) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramMember.getDeclaringClass().getName());
    stringBuilder.append(".");
    stringBuilder.append(paramMember.getName());
    String str = stringBuilder.toString();
    return methodUseInHookStub.contains(str);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\blacklist\HookBlackList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
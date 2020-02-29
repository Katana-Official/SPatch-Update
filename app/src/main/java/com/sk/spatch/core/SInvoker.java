package com.sk.spatch.core;

import android.content.Context;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.LinkedList;

public class SInvoker {
  private static ProxyInvocation defaultProxy;
  
  static  {
    try {
      System.loadLibrary("spatch");
      putToMap(Byte.class.getName(), byte.class);
      putToMap(Short.class.getName(), short.class);
      putToMap(Integer.class.getName(), int.class);
      putToMap(Long.class.getName(), long.class);
      putToMap(Float.class.getName(), float.class);
      putToMap(Double.class.getName(), double.class);
      putToMap(Boolean.class.getName(), boolean.class);
      putToMap(Character.class.getName(), char.class);
      putToMap(PathClassLoader.class.getName(), ClassLoader.class);
      putToMap(DexClassLoader.class.getName(), ClassLoader.class);
    } finally {
      Exception exception = null;
    } 
  }
  
  private static native void clearMap();
  
  public static Object getFieldObject(Class<?> paramClass, Object paramObject, String paramString) throws NoSuchFieldException, IllegalAccessException {
    paramClass.getDeclaredField(paramString).setAccessible(true);
    return paramClass.getDeclaredField(paramString).get(paramObject);
  }
  
  private static native Object getFromMap(String paramString);
  
  public static Class<?> getStaticClass(String paramString) throws ClassNotFoundException { return Class.forName(paramString); }
  
  public static Class<?> getStaticClass(String paramString, Context paramContext) throws ClassNotFoundException { return paramContext.getClassLoader().loadClass(paramString); }
  
  public static Class<?> getStaticClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException { return paramClassLoader.loadClass(paramString); }
  
  public static Object getStaticFieldObject(Class<?> paramClass, String paramString) throws NoSuchFieldException, IllegalAccessException { return getFieldObject(paramClass, null, paramString); }
  
  public static Object getStaticFieldObject(String paramString1, Context paramContext, String paramString2) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException { return getFieldObject(paramContext.getClassLoader().loadClass(paramString1), null, paramString2); }
  
  public static Object getStaticFieldObject(String paramString1, ClassLoader paramClassLoader, String paramString2) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException { return getFieldObject(paramClassLoader.loadClass(paramString1), null, paramString2); }
  
  public static Object getStaticFieldObject(String paramString1, String paramString2) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException { return getFieldObject(Class.forName(paramString1), null, paramString2); }
  
  public static Object interfaceToObject(Class<?> paramClass, Context paramContext, ProxyInvocation paramProxyInvocation) { return interfaceToObject(paramClass, paramContext.getClassLoader(), paramProxyInvocation); }
  
  public static Object interfaceToObject(Class<?> paramClass, ProxyInvocation paramProxyInvocation) throws ClassNotFoundException { return interfaceToObject(paramClass, ClassLoader.getSystemClassLoader(), paramProxyInvocation); }
  
  public static Object interfaceToObject(Class<?> paramClass, ClassLoader paramClassLoader, ProxyInvocation paramProxyInvocation) {
    InvHandler invHandler = new InvHandler();
    if (paramProxyInvocation == null)
      paramProxyInvocation = defaultProxy; 
    invHandler.px = paramProxyInvocation;
    return Proxy.newProxyInstance(paramClassLoader, new Class[] { paramClass }, invHandler);
  }
  
  public static Object interfaceToObject(String paramString, Context paramContext, ProxyInvocation paramProxyInvocation) throws ClassNotFoundException { return interfaceToObject(paramContext.getClassLoader().loadClass(paramString), paramContext.getClassLoader(), paramProxyInvocation); }
  
  public static Object interfaceToObject(String paramString, ProxyInvocation paramProxyInvocation) throws ClassNotFoundException { return interfaceToObject(Class.forName(paramString), ClassLoader.getSystemClassLoader(), paramProxyInvocation); }
  
  public static Object invokeMethod(Class<?> paramClass, String paramString, Object paramObject, Object... paramVarArgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LinkedList<Class<?>> linkedList = new LinkedList();
    int j = paramVarArgs.length;
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      Object object = paramVarArgs[i];
      Class<?> clazz = baseType.get(object.getClass().getName());
      if (clazz != null) {
        linkedList.add(clazz);
      } else {
        linkedList.add(object.getClass());
      } 
    } 
    Class[] arrayOfClass = new Class[linkedList.size()];
    Iterator<Class<?>> iterator = linkedList.iterator();
    for (i = bool; iterator.hasNext(); i++)
      arrayOfClass[i] = iterator.next(); 
    Method method = paramClass.getDeclaredMethod(paramString, arrayOfClass);
    method.setAccessible(true);
    return invokeMethod(method, paramObject, paramVarArgs);
  }
  
  public static Object invokeMethod(String paramString1, String paramString2, Context paramContext, Object paramObject, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeStaticMethod(paramContext.getClassLoader().loadClass(paramString1), paramString2, new Object[] { paramObject, paramVarArgs }); }
  
  public static Object invokeMethod(String paramString1, String paramString2, ClassLoader paramClassLoader, Object paramObject, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeMethod(paramClassLoader.loadClass(paramString1), paramString2, paramObject, paramVarArgs); }
  
  public static Object invokeMethod(String paramString1, String paramString2, Object paramObject, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeMethod(Class.forName(paramString1), paramString2, paramObject, paramVarArgs); }
  
  public static Object invokeMethod(Method paramMethod, Object paramObject, Object... paramVarArgs) throws InvocationTargetException, IllegalAccessException { return paramMethod.invoke(paramObject, paramVarArgs); }
  
  public static Object invokeMethodInterface(Class<?> paramClass, String paramString, Object paramObject, Object... paramVarArgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LinkedList<Class<Object>> linkedList = new LinkedList();
    int j = paramVarArgs.length;
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      Object object = paramVarArgs[i];
      if (object == null) {
        linkedList.add(Object.class);
      } else {
        Class<?> clazz = baseType.get(object.getClass().getName());
        Class[] arrayOfClass1 = object.getClass().getInterfaces();
        if (clazz != null) {
          linkedList.add(clazz);
        } else if (arrayOfClass1.length == 0) {
          linkedList.add(object.getClass());
        } else if (object.getClass().getName().startsWith("java")) {
          linkedList.add(object.getClass());
        } else {
          int m = arrayOfClass1.length;
          int k;
          for (k = 0; k < m; k++) {
            object = arrayOfClass1[k];
            if (!object.getName().startsWith("java"))
              linkedList.add(object); 
          } 
        } 
      } 
    } 
    Class[] arrayOfClass = new Class[linkedList.size()];
    Iterator<Class<Object>> iterator = linkedList.iterator();
    for (i = bool; iterator.hasNext(); i++)
      arrayOfClass[i] = iterator.next(); 
    Method method = paramClass.getDeclaredMethod(paramString, arrayOfClass);
    method.setAccessible(true);
    return invokeMethod(method, paramObject, paramVarArgs);
  }
  
  public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LinkedList<Class<Object>> linkedList = new LinkedList();
    int j = paramVarArgs.length;
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      Object object = paramVarArgs[i];
      if (object == null) {
        linkedList.add(Object.class);
      } else {
        Class<?> clazz = baseType.get(object.getClass().getName());
        if (clazz != null) {
          linkedList.add(clazz);
        } else {
          linkedList.add(object.getClass());
        } 
      } 
    } 
    Class[] arrayOfClass = new Class[linkedList.size()];
    Iterator<Class<Object>> iterator = linkedList.iterator();
    for (i = bool; iterator.hasNext(); i++)
      arrayOfClass[i] = iterator.next(); 
    Method method = paramClass.getDeclaredMethod(paramString, arrayOfClass);
    method.setAccessible(true);
    return invokeMethod(method, null, paramVarArgs);
  }
  
  public static Object invokeStaticMethod(String paramString1, String paramString2, Context paramContext, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeStaticMethod(paramContext.getClassLoader().loadClass(paramString1), paramString2, paramVarArgs); }
  
  public static Object invokeStaticMethod(String paramString1, String paramString2, ClassLoader paramClassLoader, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeStaticMethod(paramClassLoader.loadClass(paramString1), paramString2, paramVarArgs); }
  
  public static Object invokeStaticMethod(String paramString1, String paramString2, Object... paramVarArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException { return invokeStaticMethod(Class.forName(paramString1), paramString2, paramVarArgs); }
  
  private static native void putToMap(String paramString, Object paramObject);
  
  private static native void remFromMap(String paramString);
  
  public static void setFieldObject(Class<?> paramClass, String paramString, Object paramObject1, Object paramObject2) throws NoSuchFieldException, IllegalAccessException {
    paramClass.getDeclaredField(paramString).setAccessible(true);
    paramClass.getDeclaredField(paramString).set(paramObject1, paramObject2);
  }
  
  public static void setStaticFieldObject(String paramString1, Context paramContext, String paramString2, Object paramObject) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException { setFieldObject(paramContext.getClassLoader().loadClass(paramString1), paramString2, null, paramObject); }
  
  public static void setStaticFieldObject(String paramString1, ClassLoader paramClassLoader, String paramString2, Object paramObject) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException { setFieldObject(paramClassLoader.loadClass(paramString1), paramString2, null, paramObject); }
  
  public static void setStaticFieldObject(String paramString1, String paramString2, Object paramObject) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException { setFieldObject(Class.forName(paramString1), paramString2, null, paramObject); }
  
  private static class InvHandler implements InvocationHandler {
    public SInvoker.ProxyInvocation px = null;
    
    private InvHandler() {}
    
    public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable { return this.px.doEnv(param1Object, param1Method, param1ArrayOfObject); }
  }
  
  public static interface ProxyInvocation {
    Object doEnv(Object param1Object, Method param1Method, Object... param1VarArgs);
  }
  
  private static class baseType {
    public static Class<?> get(String param1String) { return (Class)SInvoker.getFromMap(param1String); }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\core\SInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.swift.sandhook.wrapper;

import android.text.TextUtils;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookReflectClass;
import com.swift.sandhook.annotation.MethodParams;
import com.swift.sandhook.annotation.MethodReflectParams;
import com.swift.sandhook.annotation.Param;
import com.swift.sandhook.annotation.SkipParamCheck;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HookWrapper {
  public static void addHookClass(ClassLoader paramClassLoader, Class<?> paramClass) throws HookErrorException {
    Class clazz = getTargetHookClass(paramClassLoader, paramClass);
    if (clazz != null) {
      Map<Member, HookEntity> map = getHookMethods(paramClassLoader, clazz, paramClass);
      try {
        fillBackupMethod(paramClassLoader, paramClass, map);
        Iterator<HookEntity> iterator = map.values().iterator();
        return;
      } finally {
        paramClassLoader = null;
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("error hook wrapper class :");
    stringBuilder.append(paramClass.getName());
    throw new HookErrorException(stringBuilder.toString());
  }
  
  public static void addHookClass(ClassLoader paramClassLoader, Class<?>... paramVarArgs) throws HookErrorException {
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++)
      addHookClass(paramClassLoader, paramVarArgs[i]); 
  }
  
  public static void addHookClass(Class<?>... paramVarArgs) throws HookErrorException { addHookClass(null, paramVarArgs); }
  
  public static void checkSignature(Member paramMember, Method paramMethod, Class[] paramArrayOfClass) throws HookErrorException {
    if (Modifier.isStatic(paramMethod.getModifiers())) {
      StringBuilder stringBuilder1;
      if (paramMember instanceof java.lang.reflect.Constructor) {
        if (!paramMethod.getReturnType().equals(void.class)) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("error return type! - ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } else if (stringBuilder1 instanceof Method) {
        Class<?> clazz = ((Method)stringBuilder1).getReturnType();
        if (clazz != paramMethod.getReturnType() && !clazz.isAssignableFrom(clazz)) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("error return type! - ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } 
      Class[] arrayOfClass2 = paramMethod.getParameterTypes();
      int i = 0;
      Class[] arrayOfClass1 = arrayOfClass2;
      if (arrayOfClass2 == null)
        arrayOfClass1 = new Class[0]; 
      arrayOfClass2 = paramArrayOfClass;
      if (paramArrayOfClass == null)
        arrayOfClass2 = new Class[0]; 
      if (arrayOfClass2.length == 0 && arrayOfClass1.length == 0)
        return; 
      boolean bool = Modifier.isStatic(stringBuilder1.getModifiers());
      byte b = 1;
      if (!bool) {
        if (arrayOfClass1.length != 0) {
          if (arrayOfClass1[0] == stringBuilder1.getDeclaringClass() || arrayOfClass1[0].isAssignableFrom(stringBuilder1.getDeclaringClass())) {
            if (arrayOfClass1.length != arrayOfClass2.length + 1) {
              stringBuilder1 = new StringBuilder();
              stringBuilder1.append("hook method pars must match the origin method! ");
              stringBuilder1.append(paramMethod.getName());
              throw new HookErrorException(stringBuilder1.toString());
            } 
          } else {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append("first par must be this! ");
            stringBuilder1.append(paramMethod.getName());
            throw new HookErrorException(stringBuilder1.toString());
          } 
        } else {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("first par must be this! ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } else if (arrayOfClass1.length == arrayOfClass2.length) {
        b = 0;
      } else {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("hook method pars must match the origin method! ");
        stringBuilder1.append(paramMethod.getName());
        throw new HookErrorException(stringBuilder1.toString());
      } 
      while (i < arrayOfClass2.length) {
        int j = i + b;
        if (arrayOfClass1[j] == arrayOfClass2[i] || arrayOfClass1[j].isAssignableFrom(arrayOfClass2[i])) {
          i++;
          continue;
        } 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("hook method pars must match the origin method! ");
        stringBuilder1.append(paramMethod.getName());
        throw new HookErrorException(stringBuilder1.toString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("hook method must static! - ");
    stringBuilder.append(paramMethod.getName());
    throw new HookErrorException(stringBuilder.toString());
  }
  
  private static Class classNameToClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 109413500:
        if (paramString.equals("short")) {
          b = 7;
          break;
        } 
      case 97526364:
        if (paramString.equals("float")) {
          b = 4;
          break;
        } 
      case 64711720:
        if (paramString.equals("boolean")) {
          b = 0;
          break;
        } 
      case 3327612:
        if (paramString.equals("long")) {
          b = 6;
          break;
        } 
      case 3052374:
        if (paramString.equals("char")) {
          b = 2;
          break;
        } 
      case 3039496:
        if (paramString.equals("byte")) {
          b = 1;
          break;
        } 
      case 104431:
        if (paramString.equals("int")) {
          b = 5;
          break;
        } 
      case -1325958191:
        if (paramString.equals("double")) {
          b = 3;
          break;
        } 
    } 
    switch (b) {
      default:
        return (paramClassLoader == null) ? Class.forName(paramString) : Class.forName(paramString, true, paramClassLoader);
      case 7:
        return short.class;
      case 6:
        return long.class;
      case 5:
        return int.class;
      case 4:
        return float.class;
      case 3:
        return double.class;
      case 2:
        return char.class;
      case 1:
        return byte.class;
      case 0:
        break;
    } 
    return boolean.class;
  }
  
  private static void fillBackupMethod(ClassLoader paramClassLoader, Class<?> paramClass, Map<Member, HookEntity> paramMap) {
    try {
      Field[] arrayOfField = paramClass.getDeclaredFields();
    } finally {
      paramClass = null;
    } 
  }
  
  private static Map<Member, HookEntity> getHookMethods(ClassLoader paramClassLoader, Class paramClass, Class<?> paramClass1) throws HookErrorException {
    StringBuilder stringBuilder2;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      Method[] arrayOfMethod = paramClass1.getDeclaredMethods();
    } finally {
      paramClass1 = null;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("error hook wrapper class :");
    stringBuilder1.append(stringBuilder2.getName());
    throw new HookErrorException(stringBuilder1.toString());
  }
  
  private static int getParsCount(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    return (arrayOfClass == null) ? 0 : arrayOfClass.length;
  }
  
  private static Class getRealParType(ClassLoader paramClassLoader, Class paramClass, Annotation[] paramArrayOfAnnotation, boolean paramBoolean) throws Exception {
    if (paramArrayOfAnnotation != null) {
      if (paramArrayOfAnnotation.length == 0)
        return paramClass; 
      int j = paramArrayOfAnnotation.length;
      int i;
      for (i = 0; i < j; i++) {
        Annotation annotation = paramArrayOfAnnotation[i];
        if (annotation instanceof Param) {
          Param param = (Param)annotation;
          if (TextUtils.isEmpty(param.value()))
            return paramClass; 
          Class<?> clazz = classNameToClass(param.value(), paramClassLoader);
          if (!paramBoolean && !clazz.equals(paramClass)) {
            if (paramClass.isAssignableFrom(clazz))
              return clazz; 
            throw new ClassCastException("hook method par cast error!");
          } 
          return clazz;
        } 
      } 
    } 
    return paramClass;
  }
  
  private static Class getTargetHookClass(ClassLoader paramClassLoader, Class<?> paramClass) {
    HookClass hookClass = paramClass.getAnnotation(HookClass.class);
    HookReflectClass hookReflectClass = paramClass.getAnnotation(HookReflectClass.class);
    if (hookClass != null)
      return hookClass.value(); 
    if (hookReflectClass != null) {
      if (paramClassLoader == null)
        try {
          return Class.forName(hookReflectClass.value());
        } catch (ClassNotFoundException classNotFoundException) {
          return null;
        }  
      return Class.forName(hookReflectClass.value(), true, (ClassLoader)classNotFoundException);
    } 
    return null;
  }
  
  private static boolean hasThisObject(Method paramMethod) {
    Annotation[][] arrayOfAnnotation = paramMethod.getParameterAnnotations();
    return (arrayOfAnnotation != null) ? ((arrayOfAnnotation.length == 0) ? false : isThisObject(arrayOfAnnotation[0])) : false;
  }
  
  private static boolean isThisObject(Annotation[] paramArrayOfAnnotation) {
    if (paramArrayOfAnnotation != null) {
      if (paramArrayOfAnnotation.length == 0)
        return false; 
      int j = paramArrayOfAnnotation.length;
      for (int i = 0; i < j; i++) {
        if (paramArrayOfAnnotation[i] instanceof com.swift.sandhook.annotation.ThisObject)
          return true; 
      } 
    } 
    return false;
  }
  
  private static Class[] parseMethodPars(ClassLoader paramClassLoader, Field paramField) throws HookErrorException {
    Object object = paramField.getAnnotation(MethodParams.class);
    MethodReflectParams methodReflectParams = paramField.getAnnotation(MethodReflectParams.class);
    if (object != null)
      return object.value(); 
    object = null;
    if (methodReflectParams != null) {
      if ((methodReflectParams.value()).length == 0)
        return null; 
      Class[] arrayOfClass = new Class[(methodReflectParams.value()).length];
      int i = 0;
      while (true) {
        Class[] arrayOfClass1 = arrayOfClass;
        if (i < (methodReflectParams.value()).length)
          try {
            arrayOfClass[i] = classNameToClass(methodReflectParams.value()[i], paramClassLoader);
            i++;
            continue;
          } catch (ClassNotFoundException classNotFoundException) {
            object = new StringBuilder();
            object.append("hook method pars error: ");
            object.append(paramField.getName());
            throw new HookErrorException(object.toString(), classNotFoundException);
          }  
        break;
      } 
    } 
    return (Class[])object;
  }
  
  private static Class[] parseMethodPars(ClassLoader paramClassLoader, Method paramMethod) throws HookErrorException {
    MethodParams methodParams = paramMethod.getAnnotation(MethodParams.class);
    MethodReflectParams methodReflectParams = paramMethod.getAnnotation(MethodReflectParams.class);
    if (methodParams != null)
      return methodParams.value(); 
    if (methodReflectParams != null) {
      if ((methodReflectParams.value()).length == 0)
        return null; 
      Class[] arrayOfClass = new Class[(methodReflectParams.value()).length];
      int i = 0;
      while (i < (methodReflectParams.value()).length) {
        try {
          arrayOfClass[i] = classNameToClass(methodReflectParams.value()[i], paramClassLoader);
          i++;
        } catch (ClassNotFoundException classNotFoundException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("hook method pars error: ");
          stringBuilder.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder.toString(), classNotFoundException);
        } 
      } 
      return arrayOfClass;
    } 
    return (getParsCount(paramMethod) > 0) ? ((getParsCount(paramMethod) == 1) ? (hasThisObject(paramMethod) ? parseMethodParsNew((ClassLoader)classNotFoundException, paramMethod) : null) : parseMethodParsNew((ClassLoader)classNotFoundException, paramMethod)) : null;
  }
  
  private static Class[] parseMethodParsNew(ClassLoader paramClassLoader, Method paramMethod) throws HookErrorException {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    Class clazz2 = null;
    Class clazz1 = null;
    if (arrayOfClass != null) {
      if (arrayOfClass.length == 0)
        return null; 
      Annotation[][] arrayOfAnnotation = paramMethod.getParameterAnnotations();
      int i = 0;
      int j = 0;
      while (true) {
        Annotation[] arrayOfAnnotation1;
        Object[] arrayOfObject;
        clazz2 = clazz1;
        if (i < arrayOfAnnotation.length) {
          clazz2 = arrayOfClass[i];
          arrayOfAnnotation1 = arrayOfAnnotation[i];
          if (i == 0) {
            if (isThisObject(arrayOfAnnotation1)) {
              arrayOfObject = (Object[])new Class[arrayOfAnnotation.length - 1];
            } else {
              arrayOfObject = (Object[])new Class[arrayOfAnnotation.length];
              try {
                arrayOfObject[j] = getRealParType(paramClassLoader, clazz2, arrayOfAnnotation1, paramMethod.isAnnotationPresent((Class)SkipParamCheck.class));
                j++;
                continue;
              } catch (Exception exception) {
                arrayOfObject = (Object[])new StringBuilder();
                arrayOfObject.append("hook method <");
                arrayOfObject.append(paramMethod.getName());
                arrayOfObject.append("> parser pars error");
                throw new HookErrorException(arrayOfObject.toString(), exception);
              } 
            } 
            continue;
          } 
        } else {
          break;
        } 
        try {
          arrayOfObject[j] = getRealParType((ClassLoader)exception, clazz2, arrayOfAnnotation1, paramMethod.isAnnotationPresent((Class)SkipParamCheck.class));
          j++;
          continue;
        } catch (Exception exception1) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("hook method <");
          stringBuilder.append(paramMethod.getName());
          stringBuilder.append("> parser pars error");
          throw new HookErrorException(stringBuilder.toString(), exception1);
        } 
        i++;
      } 
    } 
    return (Class[])clazz2;
  }
  
  private static boolean samePars(ClassLoader paramClassLoader, Field paramField, Class[] paramArrayOfClass) {
    Class[] arrayOfClass;
    try {
      Class[] arrayOfClass1 = parseMethodPars(paramClassLoader, paramField);
      if (arrayOfClass1 == null && paramField.isAnnotationPresent((Class)SkipParamCheck.class))
        return true; 
      arrayOfObject = (Object[])paramArrayOfClass;
      if (paramArrayOfClass == null)
        arrayOfObject = (Object[])new Class[0]; 
      arrayOfClass = arrayOfClass1;
      if (arrayOfClass1 == null)
        arrayOfClass = new Class[0]; 
      if (arrayOfObject.length != arrayOfClass.length)
        return false; 
    } catch (HookErrorException arrayOfObject) {
      return false;
    } 
    int i;
    for (i = 0; i < arrayOfObject.length; i++) {
      Object object = arrayOfObject[i];
      Class clazz = arrayOfClass[i];
      if (object != clazz)
        return false; 
    } 
    return true;
  }
  
  public static class HookEntity {
    public Method backup;
    
    public boolean backupIsStub = true;
    
    public Method hook;
    
    public boolean hookIsStub = false;
    
    public int hookMode;
    
    public boolean initClass = true;
    
    public Class[] pars;
    
    public boolean resolveDexCache = true;
    
    public Member target;
    
    public HookEntity(Member param1Member) { this.target = param1Member; }
    
    public HookEntity(Member param1Member, Method param1Method1, Method param1Method2) {
      this.target = param1Member;
      this.hook = param1Method1;
      this.backup = param1Method2;
    }
    
    public HookEntity(Member param1Member, Method param1Method1, Method param1Method2, boolean param1Boolean) {
      this.target = param1Member;
      this.hook = param1Method1;
      this.backup = param1Method2;
      this.resolveDexCache = param1Boolean;
    }
    
    public Object callOrigin(Object param1Object, Object... param1VarArgs) throws Throwable { return SandHook.callOriginMethod(this.backupIsStub, this.target, this.backup, param1Object, param1VarArgs); }
    
    public boolean isCtor() { return this.target instanceof java.lang.reflect.Constructor; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\wrapper\HookWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
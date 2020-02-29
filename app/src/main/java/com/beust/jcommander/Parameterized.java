package com.beust.jcommander;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Parameterized {
  private Field field;
  
  private Method getter;
  
  private Method method;
  
  private ParametersDelegate parametersDelegate;
  
  private WrappedParameter wrappedParameter;
  
  public Parameterized(WrappedParameter paramWrappedParameter, ParametersDelegate paramParametersDelegate, Field paramField, Method paramMethod) {
    this.wrappedParameter = paramWrappedParameter;
    this.method = paramMethod;
    this.field = paramField;
    if (paramField != null)
      setFieldAccessible(paramField); 
    this.parametersDelegate = paramParametersDelegate;
  }
  
  private static Set<Class<?>> describeClassTree(Class<?> paramClass) {
    if (paramClass == null)
      return Collections.emptySet(); 
    Set<Class<?>> set = Sets.newLinkedHashSet();
    describeClassTree(paramClass, set);
    return set;
  }
  
  private static void describeClassTree(Class<?> paramClass, Set<Class<?>> paramSet) {
    if (paramClass == null)
      return; 
    if (!Object.class.equals(paramClass)) {
      if (paramSet.contains(paramClass))
        return; 
      paramSet.add(paramClass);
      describeClassTree(paramClass.getSuperclass(), paramSet);
      Class[] arrayOfClass = paramClass.getInterfaces();
      int j = arrayOfClass.length;
      for (int i = 0; i < j; i++)
        describeClassTree(arrayOfClass[i], paramSet); 
    } 
  }
  
  private static String errorMessage(Method paramMethod, Exception paramException) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Could not invoke ");
    stringBuilder.append(paramMethod);
    stringBuilder.append("\n    Reason: ");
    stringBuilder.append(paramException.getMessage());
    return stringBuilder.toString();
  }
  
  public static List<Parameterized> parseArg(Object<Class<?>> paramObject) {
    List<Parameterized> list = Lists.newArrayList();
    paramObject = (Object<Class<?>>)describeClassTree(paramObject.getClass()).iterator();
    while (paramObject.hasNext()) {
      Class clazz = paramObject.next();
      Field[] arrayOfField = clazz.getDeclaredFields();
      int j = arrayOfField.length;
      boolean bool = false;
      int i;
      for (i = 0; i < j; i++) {
        Field field1 = arrayOfField[i];
        Parameter parameter = (Parameter)field1.getAnnotation((Class)Parameter.class);
        ParametersDelegate parametersDelegate1 = (ParametersDelegate)field1.getAnnotation((Class)ParametersDelegate.class);
        DynamicParameter dynamicParameter = (DynamicParameter)field1.getAnnotation((Class)DynamicParameter.class);
        if (parameter != null) {
          list.add(new Parameterized(new WrappedParameter(parameter), null, field1, null));
        } else if (dynamicParameter != null) {
          list.add(new Parameterized(new WrappedParameter(dynamicParameter), null, field1, null));
        } else if (parametersDelegate1 != null) {
          list.add(new Parameterized(null, parametersDelegate1, field1, null));
        } 
      } 
      Method[] arrayOfMethod = clazz.getDeclaredMethods();
      j = arrayOfMethod.length;
      for (i = bool; i < j; i++) {
        Method method1 = arrayOfMethod[i];
        method1.setAccessible(true);
        Parameter parameter = (Parameter)method1.getAnnotation((Class)Parameter.class);
        ParametersDelegate parametersDelegate1 = (ParametersDelegate)method1.getAnnotation((Class)ParametersDelegate.class);
        DynamicParameter dynamicParameter = (DynamicParameter)method1.getAnnotation((Class)DynamicParameter.class);
        if (parameter != null) {
          list.add(new Parameterized(new WrappedParameter(parameter), null, null, method1));
        } else if (dynamicParameter != null) {
          list.add(new Parameterized(new WrappedParameter(dynamicParameter), null, null, method1));
        } else if (parametersDelegate1 != null) {
          list.add(new Parameterized(null, parametersDelegate1, null, method1));
        } 
      } 
    } 
    return list;
  }
  
  private static void setFieldAccessible(Field paramField) {
    if (!Modifier.isFinal(paramField.getModifiers())) {
      paramField.setAccessible(true);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot use final field ");
    stringBuilder.append(paramField.getDeclaringClass().getName());
    stringBuilder.append("#");
    stringBuilder.append(paramField.getName());
    stringBuilder.append(" as a parameter; compile-time constant inlining may hide new values written to it.");
    throw new ParameterException(stringBuilder.toString());
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    Field field1 = this.field;
    if (field1 == null) {
      if (paramObject.field != null)
        return false; 
    } else if (!field1.equals(paramObject.field)) {
      return false;
    } 
    Method method1 = this.method;
    if (method1 == null) {
      if (paramObject.method != null)
        return false; 
    } else if (!method1.equals(paramObject.method)) {
      return false;
    } 
    return true;
  }
  
  public Type findFieldGenericType() {
    if (this.method != null)
      return null; 
    if (this.field.getGenericType() instanceof ParameterizedType) {
      Type type = ((ParameterizedType)this.field.getGenericType()).getActualTypeArguments()[0];
      if (type instanceof Class)
        return type; 
    } 
    return null;
  }
  
  public Object get(Object paramObject) {
    try {
      if (this.method != null) {
        if (this.getter == null) {
          Class<?> clazz = this.method.getDeclaringClass();
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("g");
          stringBuilder.append(this.method.getName().substring(1));
          this.getter = clazz.getMethod(stringBuilder.toString(), new Class[0]);
        } 
        return this.getter.invoke(paramObject, new Object[0]);
      } 
      return this.field.get(paramObject);
    } catch (SecurityException null) {
      throw new ParameterException((Throwable)object);
    } catch (IllegalArgumentException null) {
      throw new ParameterException((Throwable)object);
    } catch (InvocationTargetException null) {
      throw new ParameterException((Throwable)object);
    } catch (IllegalAccessException object) {
      throw new ParameterException((Throwable)object);
    } catch (NoSuchMethodException noSuchMethodException) {
      String str1 = this.method.getName();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Character.toLowerCase(str1.charAt(3)));
      stringBuilder.append(str1.substring(4));
      String str2 = stringBuilder.toString();
      str1 = null;
      try {
        Object object1;
        Field field1 = this.method.getDeclaringClass().getDeclaredField(str2);
        if (field1 != null) {
          setFieldAccessible(field1);
          object1 = field1.get(object);
        } 
        return object1;
      } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
        return null;
      } 
    } 
  }
  
  public ParametersDelegate getDelegateAnnotation() { return this.parametersDelegate; }
  
  public Type getGenericType() {
    Method method1 = this.method;
    return (method1 != null) ? method1.getGenericParameterTypes()[0] : this.field.getGenericType();
  }
  
  public String getName() {
    Method method1 = this.method;
    return (method1 != null) ? method1.getName() : this.field.getName();
  }
  
  public Parameter getParameter() { return this.wrappedParameter.getParameter(); }
  
  public Class<?> getType() {
    Method method1 = this.method;
    return (method1 != null) ? method1.getParameterTypes()[0] : this.field.getType();
  }
  
  public WrappedParameter getWrappedParameter() { return this.wrappedParameter; }
  
  public int hashCode() {
    int i;
    Field field1 = this.field;
    int j = 0;
    if (field1 == null) {
      i = 0;
    } else {
      i = field1.hashCode();
    } 
    Method method1 = this.method;
    if (method1 != null)
      j = method1.hashCode(); 
    return (i + 31) * 31 + j;
  }
  
  public boolean isDynamicParameter() { return (this.wrappedParameter.getDynamicParameter() != null); }
  
  public boolean isDynamicParameter(Field paramField) {
    Method method1 = this.method;
    return (method1 != null) ? ((method1.getAnnotation((Class)DynamicParameter.class) != null)) : ((this.field.getAnnotation((Class)DynamicParameter.class) != null));
  }
  
  public void set(Object paramObject1, Object paramObject2) {
    try {
      if (this.method != null) {
        this.method.invoke(paramObject1, new Object[] { paramObject2 });
        return;
      } 
      this.field.set(paramObject1, paramObject2);
      return;
    } catch (IllegalAccessException null) {
    
    } catch (IllegalArgumentException null) {
    
    } catch (InvocationTargetException object) {
      if (object.getTargetException() instanceof ParameterException)
        throw (ParameterException)object.getTargetException(); 
      throw new ParameterException(errorMessage(this.method, (Exception)object));
    } 
    throw new ParameterException(errorMessage(this.method, (Exception)object));
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\Parameterized.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
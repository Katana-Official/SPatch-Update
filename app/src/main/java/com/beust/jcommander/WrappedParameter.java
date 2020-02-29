package com.beust.jcommander;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WrappedParameter {
  private DynamicParameter dynamicParameter;
  
  private Parameter parameter;
  
  public WrappedParameter(DynamicParameter paramDynamicParameter) { this.dynamicParameter = paramDynamicParameter; }
  
  public WrappedParameter(Parameter paramParameter) { this.parameter = paramParameter; }
  
  private void callPut(Object paramObject, Parameterized paramParameterized, String paramString1, String paramString2) {
    try {
      findPut(paramParameterized.getType()).invoke(paramParameterized.get(paramObject), new Object[] { paramString1, paramString2 });
      return;
    } catch (SecurityException null) {
    
    } catch (IllegalAccessException null) {
    
    } catch (NoSuchMethodException null) {
    
    } catch (InvocationTargetException object) {}
    object.printStackTrace();
  }
  
  private Method findPut(Class<?> paramClass) throws SecurityException, NoSuchMethodException { return paramClass.getMethod("put", new Class[] { Object.class, Object.class }); }
  
  public void addValue(Parameterized paramParameterized, Object paramObject1, Object paramObject2) {
    try {
      addValue(paramParameterized, paramObject1, paramObject2, null);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Couldn't set ");
      stringBuilder.append(paramObject1);
      stringBuilder.append(" to ");
      stringBuilder.append(paramObject2);
      throw new ParameterException(stringBuilder.toString(), illegalAccessException);
    } 
  }
  
  public void addValue(Parameterized paramParameterized, Object paramObject1, Object paramObject2, Field paramField) throws IllegalAccessException {
    if (this.parameter != null) {
      if (paramField != null) {
        paramField.set(paramObject1, paramObject2);
        return;
      } 
      paramParameterized.set(paramObject1, paramObject2);
      return;
    } 
    String str = this.dynamicParameter.assignment();
    paramObject2 = paramObject2.toString();
    int i = paramObject2.indexOf(str);
    if (i != -1) {
      callPut(paramObject1, paramParameterized, paramObject2.substring(0, i), paramObject2.substring(i + 1));
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Dynamic parameter expected a value of the form a");
    stringBuilder.append(str);
    stringBuilder.append("b but got:");
    stringBuilder.append((String)paramObject2);
    throw new ParameterException(stringBuilder.toString());
  }
  
  public int arity() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.arity() : 1;
  }
  
  public boolean echoInput() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.echoInput() : false;
  }
  
  public String getAssignment() {
    DynamicParameter dynamicParameter1 = this.dynamicParameter;
    return (dynamicParameter1 != null) ? dynamicParameter1.assignment() : "";
  }
  
  public DynamicParameter getDynamicParameter() { return this.dynamicParameter; }
  
  public Parameter getParameter() { return this.parameter; }
  
  public boolean hidden() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.hidden() : this.dynamicParameter.hidden();
  }
  
  public boolean isHelp() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null && parameter1.help());
  }
  
  public boolean isNonOverwritableForced() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null && parameter1.forceNonOverwritable());
  }
  
  public String[] names() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.names() : this.dynamicParameter.names();
  }
  
  public boolean password() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.password() : false;
  }
  
  public boolean required() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.required() : this.dynamicParameter.required();
  }
  
  public Class<? extends IValueValidator> validateValueWith() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.validateValueWith() : this.dynamicParameter.validateValueWith();
  }
  
  public Class<? extends IParameterValidator> validateWith() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.validateWith() : this.dynamicParameter.validateWith();
  }
  
  public boolean variableArity() {
    Parameter parameter1 = this.parameter;
    return (parameter1 != null) ? parameter1.variableArity() : false;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\WrappedParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.beust.jcommander;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DynamicParameter {
  String assignment() default "=";
  
  String description() default "";
  
  String descriptionKey() default "";
  
  boolean hidden() default false;
  
  String[] names() default {};
  
  boolean required() default false;
  
  Class<? extends IValueValidator> validateValueWith() default com.beust.jcommander.validators.NoValueValidator.class;
  
  Class<? extends IParameterValidator> validateWith() default com.beust.jcommander.validators.NoValidator.class;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\DynamicParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
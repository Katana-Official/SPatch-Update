package com.beust.jcommander;

import com.beust.jcommander.converters.IParameterSplitter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Parameter {
  int arity() default -1;
  
  Class<? extends IStringConverter<?>> converter() default com.beust.jcommander.converters.NoConverter.class;
  
  String description() default "";
  
  String descriptionKey() default "";
  
  boolean echoInput() default false;
  
  boolean forceNonOverwritable() default false;
  
  boolean help() default false;
  
  boolean hidden() default false;
  
  Class<? extends IStringConverter<?>> listConverter() default com.beust.jcommander.converters.NoConverter.class;
  
  String[] names() default {};
  
  int order() default -1;
  
  boolean password() default false;
  
  boolean required() default false;
  
  Class<? extends IParameterSplitter> splitter() default com.beust.jcommander.converters.CommaParameterSplitter.class;
  
  Class<? extends IValueValidator> validateValueWith() default com.beust.jcommander.validators.NoValueValidator.class;
  
  Class<? extends IParameterValidator> validateWith() default com.beust.jcommander.validators.NoValidator.class;
  
  boolean variableArity() default false;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
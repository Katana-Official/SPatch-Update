package com.beust.jcommander;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Parameters {
  String commandDescription() default "";
  
  String commandDescriptionKey() default "";
  
  String[] commandNames() default {};
  
  boolean hidden() default false;
  
  String resourceBundle() default "";
  
  String separators() default " ";
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\Parameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.swift.sandhook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HookMode {
  public static final int AUTO = 0;
  
  public static final int INLINE = 1;
  
  public static final int REPLACE = 2;
  
  int value() default 0;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\annotation\HookMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
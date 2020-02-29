package com.beust.jcommander;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ParametersDelegate {}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\ParametersDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
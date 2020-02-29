package com.beust.jcommander.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Nullable {}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\Nullable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
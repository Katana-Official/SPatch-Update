package com.swift.sandhook;

import android.os.Build;

public class SandHookConfig {
  public static volatile boolean DEBUG;
  
  public static volatile int SDK_INT = Build.VERSION.SDK_INT;
  
  public static volatile boolean compiler;
  
  public static volatile int curUser;
  
  public static volatile boolean delayHook;
  
  public static volatile ClassLoader initClassLoader;
  
  public static volatile LibLoader libLoader;
  
  public static volatile String libSandHookPath;
  
  static  {
    DEBUG = true;
    compiler = true;
    curUser = 0;
    delayHook = true;
    libLoader = new LibLoader() {
        public void loadLib() {
          if (SandHookConfig.libSandHookPath == null) {
            System.loadLibrary("sandhook");
            return;
          } 
          System.load(SandHookConfig.libSandHookPath);
        }
      };
  }
  
  public static interface LibLoader {
    void loadLib();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\SandHookConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.swift.sandhook;

import android.util.Log;

public class HookLog {
  public static boolean DEBUG = SandHookConfig.DEBUG;
  
  public static final String TAG = "SandHook";
  
  public static int d(String paramString) { return Log.d("SandHook", paramString); }
  
  public static int e(String paramString) { return Log.e("SandHook", paramString); }
  
  public static int e(String paramString, Throwable paramThrowable) { return Log.e("SandHook", paramString, paramThrowable); }
  
  public static int i(String paramString) { return Log.i("SandHook", paramString); }
  
  public static int v(String paramString) { return Log.v("SandHook", paramString); }
  
  public static int w(String paramString) { return Log.w("SandHook", paramString); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\HookLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
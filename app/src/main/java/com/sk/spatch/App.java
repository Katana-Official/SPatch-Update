package com.sk.spatch;

import android.app.Application;
import com.tencent.tinker.loader.app.TinkerApplication;

public class App extends TinkerApplication {
  public App() { super(15, VAPPLike.class.getName()); }
  
  public static Application getApp() { return VAPPLike.getApp().getApplication(); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\App.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
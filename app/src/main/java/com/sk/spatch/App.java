package com.sk.spatch;

import android.app.Application;

public class App extends Application {
	static Object gApp;
	public()
	{
		gApp = this;
	}
  public static Application getApp() { return (Application)gApp; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\App.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.sk.spatch.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.topbottomsnackbar.TBSnackbar;
import com.keijumt.passwordview.ActionListener;
import com.keijumt.passwordview.PasswordView;
import com.sk.spatch.MainActivity;
import com.sk.spatch.ui.appListAdapter;
import com.sk.spatch.utils.SettingsControl;
import java.util.UUID;

public class XHome extends AppCompatActivity {
  private static boolean isInitialized;
  
  private String sLastInput = "";
  
  private String sPwdFinal = "";
  
  static  {
    try {
      System.loadLibrary("spatch");
    } finally {
      Exception exception = null;
    } 
  }
  
  private native String getDeviceNavId();
  
  public static String getUniqueId(Context paramContext) { return UUID.randomUUID().toString(); }
  
  private void setupBtn() {
    PasswordView passwordView = (PasswordView)findViewById(2131231025);
    runOnUiThread(new -$$Lambda$XHome$A5UDH6kgEOG4QtX_oY-sJtGXj6s(this, (TextView)findViewById(2131231112), passwordView, (TextView)findViewById(2131231113), (TextView)findViewById(2131231114), (TextView)findViewById(2131231115), (TextView)findViewById(2131231116), (TextView)findViewById(2131231117), (TextView)findViewById(2131231118), (TextView)findViewById(2131231119), (TextView)findViewById(2131231120), (TextView)findViewById(2131231111), (TextView)findViewById(2131231121)));
  }
  
  private void setupPlatformRealView() { (new Thread(new -$$Lambda$XHome$QGJu8dLZSj6SLqdTUr7ajG8sGfg(this))).start(); }
  
  private void setupView() {
    String str2 = SettingsControl.getUniqueIds((Context)this, "");
    String str1 = str2;
    if (str2.equals("")) {
      str1 = getDeviceNavId();
      SettingsControl.setUniqueIds((Context)this, str1);
    } 
    PasswordView passwordView = (PasswordView)findViewById(2131231025);
    StringBuilder stringBuilder = new StringBuilder();
    for (char c : str1.toCharArray()) {
      if (c > '0' && c < '9')
        stringBuilder.append(c); 
      if (stringBuilder.length() >= 4)
        break; 
    } 
    while (stringBuilder.length() < 4)
      stringBuilder.append('0'); 
    this.sPwdFinal = stringBuilder.toString();
    runOnUiThread(new -$$Lambda$XHome$T2pBraOEihLi3Cajg4dPsE7NsBU(this, passwordView));
    setupBtn();
  }
  
  public void finalize() throws Throwable {
    if (this.sPwdFinal.equals(this.sLastInput)) {
      super.finalize();
      return;
    } 
    throw new SecurityException("Env setup failed!");
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427363);
    if (!isInitialized) {
      (new Thread(new -$$Lambda$XHome$7puz6zgKxKxwUht7c5bxKX1xBxg(this))).start();
      return;
    } 
    (new Thread(new -$$Lambda$XHome$C07risNvLEhe9r5E58FeQe8eomQ(this))).start();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\act\XHome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
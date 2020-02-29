package com.sk.spatch.act;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.github.topbottomsnackbar.TBSnackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitomi.refresh.view.FunGameRefreshView;
import com.sk.spatch.ui.appListAdapter;
import com.sk.spatch.ui.appv;
import com.sk.spatch.utils.modInfo;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jonathanfinerty.once.Once;

public class SelectAppAct extends AppCompatActivity {
  public static final int retCode = 601;
  
  public static final String szFlagPathSelected = "path";
  
  private List<appv> appvList = null;
  
  private long sTime = 0L;
  
  private void setupAppList() { (new Thread(new -$$Lambda$SelectAppAct$TChh1pMtyf25rgrJeYR3C92xnEA(this))).start(); }
  
  private void setupSearch() { (new Thread(new -$$Lambda$SelectAppAct$GLPBWE0MChhCkoEqJsqlPx1AUY4(this))).start(); }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427359);
    try {
      setSupportActionBar((Toolbar)findViewById(2131231131));
      ((FloatingActionButton)findViewById(2131230911)).setOnClickListener(new -$$Lambda$SelectAppAct$w_6dS2pzbwEjK50gnXsY5DEJ5pM(this));
      ((FloatingActionButton)findViewById(2131230912)).setOnClickListener(new -$$Lambda$SelectAppAct$Y377qLzuiF-JdB2NBfWaRKj8AO4(this));
      setupAppList();
      setupSearch();
      FunGameRefreshView funGameRefreshView = (FunGameRefreshView)findViewById(2131230968);
      funGameRefreshView.setLoadingText(getResources().getString(2131689620));
      funGameRefreshView.setTopMaskText(getResources().getString(2131689608));
      funGameRefreshView.setLoadingFinishedText(getResources().getString(2131689607));
      return;
    } finally {
      paramBundle = null;
      paramBundle.printStackTrace();
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\act\SelectAppAct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.sk.spatch.act;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.github.topbottomsnackbar.TBSnackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sk.spatch.utils.BackupRestoreUtils;
import com.sk.spatch.utils.SettingsControl;
import jonathanfinerty.once.Once;

public class SettingsAct extends AppCompatActivity {
  public static final String ExperimentModeEnabled = "enable_experiment";
  
  public static final String NonModifyEnabled = "non_modify";
  
  public static final String UnpackModeEnabled = "use_unpack";
  
  private static final int easterEggNeedClick = 5;
  
  private int easterEgg = 0;
  
  private long reTimeEgg = 5000L;
  
  private long timeTick = 0L;
  
  private ColorStateList createColorStateList(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = { 16842919, 16842910 };
    int[] arrayOfInt2 = { 16842909 };
    return new ColorStateList(new int[][] { arrayOfInt1, { 16842910, 16842908 }, , { 16842910 }, , { 16842908 }, , arrayOfInt2, {} }, new int[] { paramInt2, paramInt3, paramInt1, paramInt3, paramInt4, paramInt1 });
  }
  
  private void setupBtn() {
    Button button = (Button)findViewById(2131230823);
    button.setVisibility(0);
    button.setOnClickListener(new -$$Lambda$SettingsAct$YPUGFSuwX7q8rKEg7zrGzO216oM(this));
    ((Button)findViewById(2131230821)).setOnClickListener(new -$$Lambda$SettingsAct$pySoFarux6zmq9JBYTia_5JDizY(this));
  }
  
  private void setupSwitch() {
    Switch switch_1 = (Switch)findViewById(2131231085);
    Switch switch_2 = (Switch)findViewById(2131231086);
    Switch switch_3 = (Switch)findViewById(2131231087);
    Switch switch_4 = (Switch)findViewById(2131231088);
    Switch switch_5 = (Switch)findViewById(2131231089);
    Switch switch_6 = (Switch)findViewById(2131231090);
    Switch switch_7 = (Switch)findViewById(2131231091);
    switch_1.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$N4eI696KwRauf9xvnPnp-JsbsdM(this));
    switch_2.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$mMrrndPUYmF5OoB76GKb-9ELKCw(this));
    switch_3.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$h4Lzk4NFeN5ffuaKKbijNNsu4mI(this));
    switch_4.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$xE5hVnUd5RzWsabbcXa-W77L9MU(this));
    switch_5.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$hLN2nXjaeOu8nefBLBSdZ8m6Npk(this));
    switch_6.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$6YLOMEiTkMr0_jZ7uexHOOWtVfo(this));
    switch_7.setOnCheckedChangeListener(new -$$Lambda$SettingsAct$HiBkvgnEcKGfu48sMktROAYhHB8(this));
    switch_1.setChecked(SettingsControl.getIsNonApkModify((Context)this));
    switch_2.setChecked(SettingsControl.getAlertWhenDone((Context)this));
    switch_3.setChecked(SettingsControl.getAutoUnpack((Context)this));
    switch_4.setChecked(SettingsControl.getDisableMod((Context)this));
    switch_5.setChecked(SettingsControl.getDisableSafeMode((Context)this));
    switch_6.setChecked(SettingsControl.getDebug((Context)this));
    switch_7.setChecked(SettingsControl.getExperiment((Context)this));
    if (!Once.beenDone("use_unpack"))
      switch_3.setEnabled(false); 
    if (!Once.beenDone("non_modify"))
      switch_1.setEnabled(false); 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427360);
    setSupportActionBar((Toolbar)findViewById(2131231131));
    this.timeTick = System.currentTimeMillis();
    ((FloatingActionButton)findViewById(2131230911)).setOnClickListener(new -$$Lambda$SettingsAct$UUm-8Cew-cucLnOzSpSe6f6FIAo(this));
    ((FloatingActionButton)findViewById(2131230912)).setOnClickListener(new -$$Lambda$SettingsAct$zwoVpNv_6oiSTaa3-v5F8NuEA5k(this));
    try {
      return;
    } finally {
      paramBundle = null;
      paramBundle.printStackTrace();
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\act\SettingsAct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
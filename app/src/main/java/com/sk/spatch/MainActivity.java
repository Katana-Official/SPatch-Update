package com.sk.spatch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import cn.refactor.lib.colordialog.PromptDialog;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.github.topbottomsnackbar.TBSnackbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitomi.refresh.view.FunGameRefreshView;
import com.sk.spatch.act.SelectAppAct;
import com.sk.spatch.act.SettingsAct;
import com.sk.spatch.act.SuperSUDlg;
import com.sk.spatch.act.XHome;
import com.sk.spatch.core.SInvoker;
import com.sk.spatch.ui.DoLaunchApp;
import com.sk.spatch.ui.appListAdapter;
import com.sk.spatch.ui.appv;
import com.sk.spatch.ui.dlgBuilder;
import com.sk.spatch.ui.misc.XMenuItem;
import com.sk.spatch.utils.PcsApkData;
import com.sk.spatch.utils.SettingsControl;
import com.sk.spatch.utils.bksm;
import com.sk.spatch.utils.modInfo;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  public static final int ResultGen = 128;
  
  private static int nFlagSelecgtApk = 810;
  
  private List<appv> appvList = null;
  
  private Uri hFileUriBuffer = null;
  
  int lastIdItem = 2131231007;
  
  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new -$$Lambda$MainActivity$odE97VjFXP5-qCkY31Yj489BwD8(this);
  
  private List<appv> modToProcess = new LinkedList<appv>();
  
  ZLoadingDialog xDialog = null;
  
  private boolean xbool = true;
  
  static  {
  
  }
  
  private void bindMTA() {
    try {
      return;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
  
  public static void goHome(Context paramContext) {
    Intent intent = new Intent(paramContext, MainActivity.class);
    intent.addFlags(131072);
    intent.addFlags(268435456);
    intent.putExtra(XHome.class.getName(), true);
    paramContext.startActivity(intent);
  }
  
  private void hideLoadingDialog() { runOnUiThread(new -$$Lambda$MainActivity$4xYhHs6Wgt_l1_V73xx9k0pmVWU(this)); }
  
  private void initMTA() {
    if (ContextCompat.checkSelfPermission((Context)this, "android.permission.READ_PHONE_STATE") != 0 || ContextCompat.checkSelfPermission((Context)this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
      if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity)this, "android.permission.READ_PHONE_STATE") || !ActivityCompat.shouldShowRequestPermissionRationale((Activity)this, "android.permission.WRITE_EXTERNAL_STORAGE"))
        ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.READ_PHONE_STATE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" }, 128); 
      (new Thread(new -$$Lambda$MainActivity$DRTm9gWwz0kPFHokEN-DvHRo3uU(this))).start();
      return;
    } 
    bindMTA();
  }
  
  private void install(String paramString) {
    File file = new File(paramString);
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setFlags(268435456);
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setFlags(1);
      intent.setDataAndType(FileProvider.getUriForFile((Context)this, "com.sk.spatch.provider", file), "application/vnd.android.package-archive");
    } else {
      intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    } 
    startActivity(intent);
  }
  
  private void setupAppView() {
    if (SettingsControl.getIsNonApkModify((Context)this)) {
      (new Thread(new -$$Lambda$MainActivity$Oi0lYXHzhGVjjx3tf2zsS055Fj8(this))).start();
      return;
    } 
    (new Thread(new -$$Lambda$MainActivity$2SZ9eMsI6lKGlwr4MpmCEWJLiN4(this))).start();
  }
  
  private void setupBtn() { ((FloatingActionButton)findViewById(2131230912)).setOnClickListener(new -$$Lambda$MainActivity$z-YBMtDP3Y3CS7hTsjSZGUx51uo(this)); }
  
  private void setupFloatMenu() {
    FloatingActionButton floatingActionButton1 = (FloatingActionButton)findViewById(2131230911);
    FloatingActionButton floatingActionButton2 = (FloatingActionButton)findViewById(2131230913);
    FloatingActionButton floatingActionButton3 = (FloatingActionButton)findViewById(2131230914);
    FloatingActionButton floatingActionButton4 = (FloatingActionButton)findViewById(2131230915);
    floatingActionButton2.setOnClickListener(new -$$Lambda$MainActivity$hQnCeMYcIKYqyJebL9lA_8TxrHc(this));
    floatingActionButton1.setOnClickListener(new -$$Lambda$MainActivity$-PF5rSwGAzEripCLKzTnmtlmec8(this, floatingActionButton2, floatingActionButton3, floatingActionButton4));
    floatingActionButton3.setOnClickListener(new -$$Lambda$MainActivity$5YHpxOGv4Lka0sSyDRVHzZK0i3Y(this));
    floatingActionButton4.setOnClickListener(new -$$Lambda$MainActivity$QYQ8NWGgYiconKzDLlB-pNTs2SI(this));
  }
  
  private void setupModView() { (new Thread(new -$$Lambda$MainActivity$uLZgncfmY0ql8IACH58kE544RtY(this))).start(); }
  
  private void setupSearch() throws Throwable { (new Thread(new -$$Lambda$MainActivity$6oe8CfVvTIWt0KXiZPzz2vtId3w(this))).start(); }
  
  private void showFailDlg() { showFailDlg(""); }
  
  private void showFailDlg(String paramString) { runOnUiThread(new -$$Lambda$MainActivity$W8Lok7mVdSI_9RaXzGYcougCmwo(this, paramString)); }
  
  private void showLoadingDialog() { runOnUiThread(new -$$Lambda$MainActivity$WJoUkDe5eb35BtjTuE2yR4odl04(this)); }
  
  private void showSuccseeDlg(String paramString) {
    (new Thread(new -$$Lambda$MainActivity$AtGI8CqCkFVTgS-ZE55rWoFgyZc(this))).start();
    runOnUiThread(new -$$Lambda$MainActivity$5DKPvCjvrqr1Glke-wPQ_Ys25qg(this, paramString));
  }
  
  public String getDataColumn(Context paramContext, Uri paramUri, String paramString, String[] paramArrayOfString) {
    Cursor cursor = null;
    try {
      Cursor cursor1 = paramContext.getContentResolver().query(paramUri, new String[] { "_data" }, paramString, paramArrayOfString, null);
      return null;
    } finally {
      Cursor cursor1 = cursor;
      if (cursor1 != null)
        cursor1.close(); 
    } 
  }
  
  AlertDialog.Builder getDlgItemFromIfo(appv paramappv) {
    ApplicationInfo applicationInfo = paramappv.getAppInfo();
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle(paramappv.getName());
    builder.setNegativeButton(2131689584, -$$Lambda$MainActivity$PGgoQquZkomfP-w6LiD2RvwIMVM.INSTANCE);
    if (applicationInfo != null)
      try {
        String str6;
        String str5;
        String str4;
        String str3;
        String str2;
        String str8 = paramappv.getName();
        String str1 = applicationInfo.processName;
        String str7 = "unknown";
        if (str1 != null) {
          str1 = applicationInfo.processName;
        } else {
          str1 = "unknown";
        } 
        if (applicationInfo.dataDir != null) {
          str2 = applicationInfo.dataDir;
        } else {
          str2 = "unknown";
        } 
        if (applicationInfo.nativeLibraryDir != null) {
          str3 = applicationInfo.nativeLibraryDir;
        } else {
          str3 = "unknown";
        } 
        if (applicationInfo.sourceDir != null) {
          str4 = applicationInfo.sourceDir;
        } else {
          str4 = "unknown";
        } 
        int i = applicationInfo.targetSdkVersion;
        if (applicationInfo.packageName != null) {
          str5 = applicationInfo.packageName;
        } else {
          str5 = "unknown";
        } 
        if (applicationInfo.permission != null) {
          str6 = applicationInfo.permission;
        } else {
          str6 = "unknown";
        } 
        if (applicationInfo.className != null)
          str7 = applicationInfo.className; 
        return builder;
      } finally {
        paramappv = null;
        paramappv.printStackTrace();
      }  
    builder.setIcon(2131165306);
    return builder;
  }
  
  public String getPath(Context paramContext, Uri paramUri) {
    try {
    
    } finally {
      paramContext = null;
    } 
    return null;
  }
  
  public boolean isDownloadsDocument(Uri paramUri) { return "com.android.providers.downloads.documents".equals(paramUri.getAuthority()); }
  
  public boolean isExternalStorageDocument(Uri paramUri) { return "com.android.externalstorage.documents".equals(paramUri.getAuthority()); }
  
  public boolean isMediaDocument(Uri paramUri) { return "com.android.providers.media.documents".equals(paramUri.getAuthority()); }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == nFlagSelecgtApk) {
      if (SettingsControl.getIsNonApkModify((Context)this)) {
        if (paramInt2 == -1 && paramIntent != null)
          try {
            Uri uri = paramIntent.getData();
            if (uri == null)
              return; 
            return;
          } finally {
            paramIntent = null;
            paramIntent.printStackTrace();
          }  
      } else if (paramInt2 == -1 && paramIntent != null) {
        try {
          Uri uri = paramIntent.getData();
          if (uri == null)
            return; 
          String str = getPath((Context)this, uri);
          return;
        } finally {
          paramIntent = null;
          paramIntent.printStackTrace();
        } 
      } 
    } else if (paramInt1 == 601 && paramInt2 == -1 && paramIntent != null) {
      try {
        String str = paramIntent.getStringExtra("path");
        if (str == null)
          return; 
        return;
      } finally {
        paramIntent = null;
      } 
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (!getIntent().getBooleanExtra(XHome.class.getName(), false)) {
      finish();
      return;
    } 
    setContentView(2131427358);
    ((BubbleNavigationConstraintView)findViewById(2131231004)).setNavigationChangeListener(new -$$Lambda$MainActivity$98nStW_TUpPqZavi1FTPPIZoBPk(this));
    try {
      setupFloatMenu();
      setupBtn();
      setupSearch();
      FunGameRefreshView funGameRefreshView = (FunGameRefreshView)findViewById(2131230968);
      funGameRefreshView.setLoadingText(getResources().getString(2131689620));
      funGameRefreshView.setTopMaskText(getResources().getString(2131689608));
      funGameRefreshView.setLoadingFinishedText(getResources().getString(2131689607));
      funGameRefreshView.setBottomMaskText(getResources().getString(2131689597));
      funGameRefreshView.setOnRefreshListener(new FunGameRefreshView.FunGameRefreshListener() {
            public void onPullRefreshing() { MainActivity.this.runOnUiThread(new -$$Lambda$MainActivity$4$6jF8unxcWsiZLaYIKQ8sUpU96fo(this)); }
            
            public void onRefreshComplete() {}
          });
    } finally {
      paramBundle = null;
      paramBundle.printStackTrace();
    } 
  }
  
  protected void onNewIntent(Intent paramIntent) { super.onNewIntent(paramIntent); }
  
  protected void onPostCreate(Bundle paramBundle) { super.onPostCreate(paramBundle); }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    if (paramInt == 128) {
      if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
        bindMTA();
        return;
      } 
      finish();
    } 
  }
  
  protected void onRestart() { super.onRestart(); }
  
  protected void onRestoreInstanceState(Bundle paramBundle) { super.onRestoreInstanceState(paramBundle); }
  
  protected void onSaveInstanceState(Bundle paramBundle) { super.onSaveInstanceState(paramBundle); }
  
  protected void onStart() { super.onStart(); }
  
  public void processApk(String paramString) {
    if (this.modToProcess.isEmpty()) {
      processApkMethod(paramString);
      return;
    } 
    runOnUiThread(new -$$Lambda$MainActivity$q9NzhNE1f2WabMdOywP5iABTLu0(this, paramString));
  }
  
  public void processApkMain(String paramString) {
    (new Thread(new -$$Lambda$MainActivity$LASs8X8BeuyZW5C2MTloGn2WmgE(this))).start();
    (new Thread(new -$$Lambda$MainActivity$qH4mZ92fCVLJ80Bza2Lp2upcrUI(this, paramString))).start();
  }
  
  public void processApkMethod(String paramString) { // Byte code:
    //   0: aload_0
    //   1: invokestatic getAutoUnpack : (Landroid/content/Context;)Z
    //   4: istore_3
    //   5: ldc_w ''
    //   8: astore #7
    //   10: iconst_1
    //   11: istore_2
    //   12: iconst_0
    //   13: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   16: astore #9
    //   18: iload_3
    //   19: ifeq -> 289
    //   22: aload_0
    //   23: invokestatic getIsNonApkModify : (Landroid/content/Context;)Z
    //   26: ifne -> 289
    //   29: aload_0
    //   30: invokespecial showLoadingDialog : ()V
    //   33: ldc_w com/sk/spatch/kt/srcs/task/ApkModifyTask
    //   36: invokevirtual getName : ()Ljava/lang/String;
    //   39: invokestatic getEnvNonModTask : (Ljava/lang/String;)Ljava/lang/String;
    //   42: astore #4
    //   44: aload #4
    //   46: ldc_w ''
    //   49: invokevirtual equals : (Ljava/lang/Object;)Z
    //   52: ifeq -> 64
    //   55: aload_0
    //   56: invokespecial hideLoadingDialog : ()V
    //   59: aload_0
    //   60: invokestatic buildCoreError : (Landroid/content/Context;)V
    //   63: return
    //   64: aload #4
    //   66: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
    //   69: astore #5
    //   71: aload #5
    //   73: invokevirtual newInstance : ()Ljava/lang/Object;
    //   76: astore #6
    //   78: aload #5
    //   80: ldc_w 'HotPatchApkTask'
    //   83: iconst_3
    //   84: anewarray java/lang/Class
    //   87: dup
    //   88: iconst_0
    //   89: ldc java/lang/String
    //   91: aastore
    //   92: dup
    //   93: iconst_1
    //   94: getstatic java/lang/Boolean.TYPE : Ljava/lang/Class;
    //   97: aastore
    //   98: dup
    //   99: iconst_2
    //   100: ldc java/lang/String
    //   102: aastore
    //   103: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   106: astore #4
    //   108: invokestatic addCallMethodV : ()Z
    //   111: pop
    //   112: invokestatic addMainMethod : ()Z
    //   115: pop
    //   116: aload #4
    //   118: aload #6
    //   120: iconst_3
    //   121: anewarray java/lang/Object
    //   124: dup
    //   125: iconst_0
    //   126: aload_1
    //   127: aastore
    //   128: dup
    //   129: iconst_1
    //   130: aload #9
    //   132: aastore
    //   133: dup
    //   134: iconst_2
    //   135: ldc_w 'hPatch'
    //   138: aastore
    //   139: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   142: checkcast [B
    //   145: checkcast [B
    //   148: astore #7
    //   150: aload #5
    //   152: ldc_w 'getBackgroundTask'
    //   155: iconst_1
    //   156: anewarray java/lang/Class
    //   159: dup
    //   160: iconst_0
    //   161: ldc java/lang/String
    //   163: aastore
    //   164: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   167: aload #6
    //   169: iconst_1
    //   170: anewarray java/lang/Object
    //   173: dup
    //   174: iconst_0
    //   175: aload_1
    //   176: aastore
    //   177: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   180: checkcast java/lang/String
    //   183: astore #4
    //   185: new java/lang/Thread
    //   188: dup
    //   189: aload #5
    //   191: aload #4
    //   193: iconst_1
    //   194: anewarray java/lang/Class
    //   197: dup
    //   198: iconst_0
    //   199: ldc java/lang/String
    //   201: aastore
    //   202: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   205: aload #6
    //   207: iconst_1
    //   208: anewarray java/lang/Object
    //   211: dup
    //   212: iconst_0
    //   213: aload_1
    //   214: aastore
    //   215: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   218: checkcast java/lang/Runnable
    //   221: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   224: invokevirtual start : ()V
    //   227: aload #7
    //   229: invokestatic doVMX : ([B)Z
    //   232: pop
    //   233: iconst_0
    //   234: istore_2
    //   235: aload #4
    //   237: astore_1
    //   238: goto -> 262
    //   241: astore #5
    //   243: aload #4
    //   245: astore_1
    //   246: aload #5
    //   248: astore #4
    //   250: goto -> 257
    //   253: astore #4
    //   255: aconst_null
    //   256: astore_1
    //   257: aload #4
    //   259: invokevirtual printStackTrace : ()V
    //   262: aload_0
    //   263: invokespecial hideLoadingDialog : ()V
    //   266: iload_2
    //   267: ifeq -> 274
    //   270: aload_0
    //   271: invokestatic buildCoreError : (Landroid/content/Context;)V
    //   274: aload_1
    //   275: ifnull -> 284
    //   278: aload_0
    //   279: aload_1
    //   280: invokespecial showSuccseeDlg : (Ljava/lang/String;)V
    //   283: return
    //   284: aload_0
    //   285: invokespecial showFailDlg : ()V
    //   288: return
    //   289: aload_0
    //   290: invokestatic getIsNonApkModify : (Landroid/content/Context;)Z
    //   293: ifeq -> 1106
    //   296: new java/lang/Thread
    //   299: dup
    //   300: new com/sk/spatch/-$$Lambda$MainActivity$LASs8X8BeuyZW5C2MTloGn2WmgE
    //   303: dup
    //   304: aload_0
    //   305: invokespecial <init> : (Lcom/sk/spatch/MainActivity;)V
    //   308: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   311: invokevirtual start : ()V
    //   314: aload_0
    //   315: getfield hFileUriBuffer : Landroid/net/Uri;
    //   318: ifnull -> 712
    //   321: aload_0
    //   322: invokevirtual getCacheDir : ()Ljava/io/File;
    //   325: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   328: astore_1
    //   329: new java/lang/StringBuilder
    //   332: dup
    //   333: invokespecial <init> : ()V
    //   336: astore #4
    //   338: aload #4
    //   340: aload_1
    //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: pop
    //   345: aload #4
    //   347: getstatic java/io/File.separatorChar : C
    //   350: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   353: pop
    //   354: aload #4
    //   356: ldc_w 'installBase'
    //   359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload #4
    //   365: invokevirtual toString : ()Ljava/lang/String;
    //   368: astore_1
    //   369: new java/lang/StringBuilder
    //   372: dup
    //   373: invokespecial <init> : ()V
    //   376: astore #4
    //   378: aload #4
    //   380: aload_1
    //   381: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: pop
    //   385: aload #4
    //   387: ldc_w '.apk'
    //   390: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload #4
    //   396: invokevirtual toString : ()Ljava/lang/String;
    //   399: astore #8
    //   401: aload_0
    //   402: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   405: aload_0
    //   406: getfield hFileUriBuffer : Landroid/net/Uri;
    //   409: invokevirtual openInputStream : (Landroid/net/Uri;)Ljava/io/InputStream;
    //   412: astore #4
    //   414: new java/io/FileOutputStream
    //   417: dup
    //   418: new java/io/File
    //   421: dup
    //   422: aload #8
    //   424: invokespecial <init> : (Ljava/lang/String;)V
    //   427: invokespecial <init> : (Ljava/io/File;)V
    //   430: astore #5
    //   432: aload #4
    //   434: ifnull -> 508
    //   437: sipush #1024
    //   440: newarray byte
    //   442: astore_1
    //   443: aload #4
    //   445: aload_1
    //   446: invokevirtual read : ([B)I
    //   449: istore_2
    //   450: iload_2
    //   451: iconst_m1
    //   452: if_icmpeq -> 466
    //   455: aload #5
    //   457: aload_1
    //   458: iconst_0
    //   459: iload_2
    //   460: invokevirtual write : ([BII)V
    //   463: goto -> 443
    //   466: aload #4
    //   468: ifnull -> 484
    //   471: aload #4
    //   473: invokevirtual close : ()V
    //   476: goto -> 484
    //   479: astore_1
    //   480: aload_1
    //   481: invokevirtual printStackTrace : ()V
    //   484: aload #5
    //   486: invokevirtual close : ()V
    //   489: goto -> 497
    //   492: astore_1
    //   493: aload_1
    //   494: invokevirtual printStackTrace : ()V
    //   497: ldc_w ''
    //   500: astore_1
    //   501: goto -> 658
    //   504: astore_1
    //   505: goto -> 530
    //   508: new java/io/IOException
    //   511: dup
    //   512: ldc_w 'Fuck! inputStream is null!'
    //   515: invokespecial <init> : (Ljava/lang/String;)V
    //   518: athrow
    //   519: astore_1
    //   520: goto -> 527
    //   523: astore_1
    //   524: aconst_null
    //   525: astore #4
    //   527: aconst_null
    //   528: astore #5
    //   530: aload_1
    //   531: invokevirtual printStackTrace : ()V
    //   534: aload_1
    //   535: invokevirtual getMessage : ()Ljava/lang/String;
    //   538: ifnull -> 549
    //   541: aload_1
    //   542: invokevirtual getMessage : ()Ljava/lang/String;
    //   545: astore_1
    //   546: goto -> 1126
    //   549: new java/lang/StringBuilder
    //   552: dup
    //   553: invokespecial <init> : ()V
    //   556: astore_1
    //   557: aload_1
    //   558: ldc_w 'Fuck! Unknown Error Occurred! On '
    //   561: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   564: pop
    //   565: aload_1
    //   566: invokestatic getClassName : ()Ljava/lang/String;
    //   569: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: aload_1
    //   574: ldc_w '->'
    //   577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: pop
    //   581: aload_1
    //   582: invokestatic getMethodName : ()Ljava/lang/String;
    //   585: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   588: pop
    //   589: aload_1
    //   590: ldc_w '->'
    //   593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: pop
    //   597: aload_1
    //   598: invokestatic getLineNumber : ()I
    //   601: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   604: pop
    //   605: aload_1
    //   606: invokevirtual toString : ()Ljava/lang/String;
    //   609: astore_1
    //   610: goto -> 1126
    //   613: aload #4
    //   615: ifnull -> 631
    //   618: aload #4
    //   620: invokevirtual close : ()V
    //   623: goto -> 631
    //   626: astore_1
    //   627: aload_1
    //   628: invokevirtual printStackTrace : ()V
    //   631: aload #6
    //   633: astore_1
    //   634: aload #5
    //   636: ifnull -> 658
    //   639: aload #5
    //   641: invokevirtual close : ()V
    //   644: aload #6
    //   646: astore_1
    //   647: goto -> 658
    //   650: astore_1
    //   651: aload_1
    //   652: invokevirtual printStackTrace : ()V
    //   655: aload #6
    //   657: astore_1
    //   658: iconst_0
    //   659: istore_3
    //   660: aload_1
    //   661: astore #4
    //   663: aload #8
    //   665: astore_1
    //   666: goto -> 719
    //   669: astore_1
    //   670: aload #4
    //   672: ifnull -> 690
    //   675: aload #4
    //   677: invokevirtual close : ()V
    //   680: goto -> 690
    //   683: astore #4
    //   685: aload #4
    //   687: invokevirtual printStackTrace : ()V
    //   690: aload #5
    //   692: ifnull -> 710
    //   695: aload #5
    //   697: invokevirtual close : ()V
    //   700: goto -> 710
    //   703: astore #4
    //   705: aload #4
    //   707: invokevirtual printStackTrace : ()V
    //   710: aload_1
    //   711: athrow
    //   712: ldc_w ''
    //   715: astore #4
    //   717: iconst_1
    //   718: istore_3
    //   719: aload #4
    //   721: ldc_w ''
    //   724: invokevirtual equals : (Ljava/lang/Object;)Z
    //   727: ifeq -> 847
    //   730: ldc_w 'com.sk.svm.conn.remote.InstallOptions'
    //   733: invokestatic getStaticClass : (Ljava/lang/String;)Ljava/lang/Class;
    //   736: ldc_w 'makeOptions'
    //   739: iconst_3
    //   740: anewarray java/lang/Object
    //   743: dup
    //   744: iconst_0
    //   745: iload_3
    //   746: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   749: aastore
    //   750: dup
    //   751: iconst_1
    //   752: aload #9
    //   754: aastore
    //   755: dup
    //   756: iconst_2
    //   757: ldc_w 'com.sk.svm.conn.remote.InstallOptions$UpdateStrategy'
    //   760: invokestatic getStaticClass : (Ljava/lang/String;)Ljava/lang/Class;
    //   763: ldc_w 'FORCE_UPDATE'
    //   766: invokestatic getStaticFieldObject : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
    //   769: aastore
    //   770: invokestatic invokeStaticMethod : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    //   773: astore #4
    //   775: ldc 'com.sk.svm.local.client.core.VirtualCore'
    //   777: invokestatic getStaticClass : (Ljava/lang/String;)Ljava/lang/Class;
    //   780: astore #5
    //   782: aload #5
    //   784: ldc_w 'installPackageSync'
    //   787: aload #5
    //   789: ldc_w 'get'
    //   792: iconst_0
    //   793: anewarray java/lang/Object
    //   796: invokestatic invokeStaticMethod : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    //   799: iconst_2
    //   800: anewarray java/lang/Object
    //   803: dup
    //   804: iconst_0
    //   805: aload_1
    //   806: aastore
    //   807: dup
    //   808: iconst_1
    //   809: aload #4
    //   811: aastore
    //   812: invokestatic invokeMethod : (Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   815: astore #4
    //   817: aload_0
    //   818: getfield hFileUriBuffer : Landroid/net/Uri;
    //   821: ifnull -> 841
    //   824: new java/io/File
    //   827: dup
    //   828: aload_1
    //   829: invokespecial <init> : (Ljava/lang/String;)V
    //   832: invokevirtual delete : ()Z
    //   835: pop
    //   836: aload_0
    //   837: aconst_null
    //   838: putfield hFileUriBuffer : Landroid/net/Uri;
    //   841: aload #4
    //   843: astore_1
    //   844: goto -> 890
    //   847: new java/io/IOException
    //   850: dup
    //   851: aload #4
    //   853: invokespecial <init> : (Ljava/lang/String;)V
    //   856: athrow
    //   857: astore #4
    //   859: aload #4
    //   861: invokevirtual printStackTrace : ()V
    //   864: aload_0
    //   865: getfield hFileUriBuffer : Landroid/net/Uri;
    //   868: ifnull -> 888
    //   871: new java/io/File
    //   874: dup
    //   875: aload_1
    //   876: invokespecial <init> : (Ljava/lang/String;)V
    //   879: invokevirtual delete : ()Z
    //   882: pop
    //   883: aload_0
    //   884: aconst_null
    //   885: putfield hFileUriBuffer : Landroid/net/Uri;
    //   888: aconst_null
    //   889: astore_1
    //   890: aload_0
    //   891: invokespecial hideLoadingDialog : ()V
    //   894: aload_1
    //   895: ifnull -> 926
    //   898: aload_1
    //   899: invokevirtual getClass : ()Ljava/lang/Class;
    //   902: aload_1
    //   903: ldc_w 'isSuccess'
    //   906: invokestatic getFieldObject : (Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   909: checkcast java/lang/Boolean
    //   912: invokevirtual booleanValue : ()Z
    //   915: istore_3
    //   916: goto -> 928
    //   919: astore #4
    //   921: aload #4
    //   923: invokevirtual printStackTrace : ()V
    //   926: iconst_0
    //   927: istore_3
    //   928: iload_3
    //   929: ifeq -> 1043
    //   932: ldc 'com.sk.svm.local.client.core.VirtualCore'
    //   934: invokestatic getStaticClass : (Ljava/lang/String;)Ljava/lang/Class;
    //   937: astore #4
    //   939: aload #4
    //   941: ldc_w 'getInstalledAppInfo'
    //   944: aload #4
    //   946: ldc_w 'get'
    //   949: iconst_0
    //   950: anewarray java/lang/Object
    //   953: invokestatic invokeStaticMethod : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    //   956: iconst_2
    //   957: anewarray java/lang/Object
    //   960: dup
    //   961: iconst_0
    //   962: aload_1
    //   963: invokevirtual getClass : ()Ljava/lang/Class;
    //   966: aload_1
    //   967: ldc_w 'packageName'
    //   970: invokestatic getFieldObject : (Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   973: aastore
    //   974: dup
    //   975: iconst_1
    //   976: iconst_0
    //   977: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   980: aastore
    //   981: invokestatic invokeMethod : (Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   984: astore_1
    //   985: aload_1
    //   986: invokevirtual getClass : ()Ljava/lang/Class;
    //   989: aload_1
    //   990: ldc_w 'packageName'
    //   993: invokestatic getFieldObject : (Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   996: checkcast java/lang/String
    //   999: astore_1
    //   1000: aload_1
    //   1001: astore #4
    //   1003: aload_1
    //   1004: ifnull -> 1036
    //   1007: aload_0
    //   1008: aload_1
    //   1009: invokestatic setupVSPath : (Landroid/content/Context;Ljava/lang/String;)V
    //   1012: aload_1
    //   1013: astore #4
    //   1015: goto -> 1036
    //   1018: astore #4
    //   1020: goto -> 1028
    //   1023: astore #4
    //   1025: aload #7
    //   1027: astore_1
    //   1028: aload #4
    //   1030: invokevirtual printStackTrace : ()V
    //   1033: aload_1
    //   1034: astore #4
    //   1036: aload_0
    //   1037: aload #4
    //   1039: invokespecial showSuccseeDlg : (Ljava/lang/String;)V
    //   1042: return
    //   1043: aload_1
    //   1044: ifnull -> 1066
    //   1047: aload_0
    //   1048: aload_1
    //   1049: invokevirtual getClass : ()Ljava/lang/Class;
    //   1052: aload_1
    //   1053: ldc_w 'error'
    //   1056: invokestatic getFieldObject : (Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   1059: checkcast java/lang/String
    //   1062: invokespecial showFailDlg : (Ljava/lang/String;)V
    //   1065: return
    //   1066: aload_0
    //   1067: invokespecial showFailDlg : ()V
    //   1070: return
    //   1071: astore_1
    //   1072: aload_1
    //   1073: invokevirtual printStackTrace : ()V
    //   1076: return
    //   1077: astore #4
    //   1079: aload_0
    //   1080: getfield hFileUriBuffer : Landroid/net/Uri;
    //   1083: ifnull -> 1103
    //   1086: new java/io/File
    //   1089: dup
    //   1090: aload_1
    //   1091: invokespecial <init> : (Ljava/lang/String;)V
    //   1094: invokevirtual delete : ()Z
    //   1097: pop
    //   1098: aload_0
    //   1099: aconst_null
    //   1100: putfield hFileUriBuffer : Landroid/net/Uri;
    //   1103: aload #4
    //   1105: athrow
    //   1106: new java/lang/Thread
    //   1109: dup
    //   1110: new com/sk/spatch/-$$Lambda$MainActivity$0SzfxoJ2EJTrXiwn91qU8Tv7iKU
    //   1113: dup
    //   1114: aload_0
    //   1115: aload_1
    //   1116: invokespecial <init> : (Lcom/sk/spatch/MainActivity;Ljava/lang/String;)V
    //   1119: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   1122: invokevirtual start : ()V
    //   1125: return
    //   1126: aload_1
    //   1127: astore #6
    //   1129: goto -> 613
    // Exception table:
    //   from	to	target	type
    //   64	185	253	finally
    //   185	233	241	finally
    //   401	414	523	finally
    //   414	432	519	finally
    //   437	443	504	finally
    //   443	450	504	finally
    //   455	463	504	finally
    //   471	476	479	finally
    //   484	489	492	finally
    //   508	519	504	finally
    //   530	546	669	finally
    //   549	610	669	finally
    //   618	623	626	finally
    //   639	644	650	finally
    //   675	680	683	finally
    //   695	700	703	finally
    //   719	817	857	finally
    //   847	857	857	finally
    //   859	864	1077	finally
    //   898	916	919	finally
    //   932	1000	1023	finally
    //   1007	1012	1018	finally
    //   1047	1065	1071	finally
    //   1066	1070	1071	finally }
  
  public void selectApk() {
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.setType("*/*");
    intent.addCategory("android.intent.category.OPENABLE");
    startActivityForResult(intent, nFlagSelecgtApk);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.sk.spatch.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import com.sk.spatch.VAPPLike;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import java.io.File;

public class BackupRestoreUtils {
  private static final String sVDataDir = "qihoo";
  
  private Context ctx = (Context)VAPPLike.getApp().getApplication();
  
  private ZLoadingDialog dialog = null;
  
  private boolean isSetup = false;
  
  private String sDataPath = "";
  
  private String sFilePath = "";
  
  public BackupRestoreUtils() {}
  
  public BackupRestoreUtils(Context paramContext) {}
  
  private void dismissAnim() {
    if (isShowAnim() && this.dialog != null)
      ((Activity)this.ctx).runOnUiThread(new -$$Lambda$BackupRestoreUtils$e3xxitiMQY28Yf2iGe4jgUyIYTk(this)); 
  }
  
  private static File ensureCreated(File paramFile) {
    if (!paramFile.exists() && !paramFile.mkdirs())
      Log.d("BackupRestore", "Fuck! Data dir cannot read or write!"); 
    return paramFile;
  }
  
  private boolean isShowAnim() { return this.ctx instanceof Activity; }
  
  private void setupPath() {
    ZipUtils.isDebug = false;
    Context context = this.ctx;
    String str = "";
    File file2 = context.getExternalFilesDir("");
    if (Build.VERSION.SDK_INT >= 24)
      str = this.ctx.getApplicationContext().getDataDir().getAbsolutePath(); 
    if (file2 != null)
      str = file2.getAbsolutePath(); 
    this.sFilePath = str;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(this.sFilePath);
    stringBuilder1.append(File.separatorChar);
    stringBuilder1.append("backup.zip");
    this.sFilePath = stringBuilder1.toString();
    File file1 = ensureCreated(new File(new File((VAPPLike.getApp().getApplication().getApplicationInfo()).dataDir), "qihoo"));
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(file1.getAbsolutePath());
    stringBuilder2.append(File.separatorChar);
    this.sDataPath = stringBuilder2.toString();
    this.isSetup = true;
  }
  
  private void showAnim() {
    if (isShowAnim()) {
      ZLoadingDialog zLoadingDialog = new ZLoadingDialog(this.ctx);
      this.dialog = zLoadingDialog;
      zLoadingDialog.setLoadingBuilder(Z_TYPE.PAC_MAN).setLoadingColor(-16777216).setHintText(this.ctx.getString(2131689608));
      ((Activity)this.ctx).runOnUiThread(new -$$Lambda$BackupRestoreUtils$pLDYBGEr0r_vvMkAuOM34rQ4dg4(this));
    } 
  }
  
  public void buildDlg() { (new Thread(new -$$Lambda$BackupRestoreUtils$gW_DJOQTw_mTz8nZWcaS6HI_PqI(this))).start(); }
  
  public void startBackup() { // Byte code:
    //   0: aload_0
    //   1: getfield isSetup : Z
    //   4: ifne -> 19
    //   7: aload_0
    //   8: invokespecial setupPath : ()V
    //   11: goto -> 19
    //   14: astore_1
    //   15: aload_1
    //   16: invokevirtual printStackTrace : ()V
    //   19: aload_0
    //   20: invokespecial showAnim : ()V
    //   23: new java/io/File
    //   26: dup
    //   27: aload_0
    //   28: getfield sFilePath : Ljava/lang/String;
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: astore_1
    //   35: aload_1
    //   36: invokevirtual exists : ()Z
    //   39: ifeq -> 47
    //   42: aload_1
    //   43: invokevirtual delete : ()Z
    //   46: pop
    //   47: new java/io/FileOutputStream
    //   50: dup
    //   51: aload_1
    //   52: invokespecial <init> : (Ljava/io/File;)V
    //   55: astore_1
    //   56: aload_0
    //   57: getfield sDataPath : Ljava/lang/String;
    //   60: aload_1
    //   61: iconst_1
    //   62: invokestatic toZipEx : (Ljava/lang/String;Ljava/io/OutputStream;Z)V
    //   65: aload_1
    //   66: invokevirtual close : ()V
    //   69: aload_0
    //   70: getfield ctx : Landroid/content/Context;
    //   73: instanceof android/app/Activity
    //   76: ifeq -> 105
    //   79: aload_0
    //   80: getfield ctx : Landroid/content/Context;
    //   83: checkcast android/app/Activity
    //   86: new com/sk/spatch/utils/-$$Lambda$BackupRestoreUtils$UoVOG27_dunV67tTpnqyFQrJQeA
    //   89: dup
    //   90: aload_0
    //   91: invokespecial <init> : (Lcom/sk/spatch/utils/BackupRestoreUtils;)V
    //   94: invokevirtual runOnUiThread : (Ljava/lang/Runnable;)V
    //   97: goto -> 105
    //   100: astore_1
    //   101: aload_1
    //   102: invokevirtual printStackTrace : ()V
    //   105: aload_0
    //   106: invokespecial dismissAnim : ()V
    //   109: return
    //   110: astore_1
    //   111: aload_1
    //   112: invokevirtual printStackTrace : ()V
    //   115: return
    // Exception table:
    //   from	to	target	type
    //   0	11	14	finally
    //   15	19	110	finally
    //   19	23	110	finally
    //   23	47	100	finally
    //   47	97	100	finally
    //   101	105	110	finally
    //   105	109	110	finally }
  
  public void startRestore() { // Byte code:
    //   0: aload_0
    //   1: getfield isSetup : Z
    //   4: ifne -> 19
    //   7: aload_0
    //   8: invokespecial setupPath : ()V
    //   11: goto -> 19
    //   14: astore_1
    //   15: aload_1
    //   16: invokevirtual printStackTrace : ()V
    //   19: aload_0
    //   20: invokespecial showAnim : ()V
    //   23: new java/io/File
    //   26: dup
    //   27: aload_0
    //   28: getfield sFilePath : Ljava/lang/String;
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: astore_1
    //   35: aload_1
    //   36: invokevirtual exists : ()Z
    //   39: ifeq -> 104
    //   42: new java/io/File
    //   45: dup
    //   46: aload_0
    //   47: getfield sDataPath : Ljava/lang/String;
    //   50: invokespecial <init> : (Ljava/lang/String;)V
    //   53: astore_2
    //   54: aload_2
    //   55: invokevirtual exists : ()Z
    //   58: ifeq -> 65
    //   61: aload_2
    //   62: invokestatic deleteDir : (Ljava/io/File;)V
    //   65: aload_1
    //   66: aload_0
    //   67: getfield sDataPath : Ljava/lang/String;
    //   70: invokestatic unZip : (Ljava/io/File;Ljava/lang/String;)V
    //   73: aload_0
    //   74: getfield ctx : Landroid/content/Context;
    //   77: instanceof android/app/Activity
    //   80: ifeq -> 120
    //   83: aload_0
    //   84: getfield ctx : Landroid/content/Context;
    //   87: checkcast android/app/Activity
    //   90: new com/sk/spatch/utils/-$$Lambda$BackupRestoreUtils$clbb76b6QHW0X0cIUG7tf4KMaoM
    //   93: dup
    //   94: aload_0
    //   95: invokespecial <init> : (Lcom/sk/spatch/utils/BackupRestoreUtils;)V
    //   98: invokevirtual runOnUiThread : (Ljava/lang/Runnable;)V
    //   101: goto -> 120
    //   104: new java/lang/Exception
    //   107: dup
    //   108: ldc_w 'Could not found backup file!'
    //   111: invokespecial <init> : (Ljava/lang/String;)V
    //   114: athrow
    //   115: astore_1
    //   116: aload_1
    //   117: invokevirtual printStackTrace : ()V
    //   120: aload_0
    //   121: invokespecial dismissAnim : ()V
    //   124: return
    //   125: astore_1
    //   126: aload_1
    //   127: invokevirtual printStackTrace : ()V
    //   130: return
    // Exception table:
    //   from	to	target	type
    //   0	11	14	finally
    //   15	19	125	finally
    //   19	23	125	finally
    //   23	65	115	finally
    //   65	101	115	finally
    //   104	115	115	finally
    //   116	120	125	finally
    //   120	124	125	finally }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\BackupRestoreUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
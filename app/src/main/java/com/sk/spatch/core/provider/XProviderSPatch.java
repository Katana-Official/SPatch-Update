package com.sk.spatch.core.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;
import java.util.Iterator;

public class XProviderSPatch extends ContentProvider {
  public static final Uri AUTHORITY_URI;
  
  public static final Uri CONTENT_URI;
  
  public static final String PARAM_KEY = "skBannedNotificationKey";
  
  public static final String PARAM_VALUE = "skBannedNotificationValue";
  
  private final String DB_NAME = "SK_Banned_Notification.db";
  
  private SharedPreferences mStore = null;
  
  static  {
    Uri uri = Uri.parse("content://sk.vpkg.provider.XProviderSPatch");
    AUTHORITY_URI = uri;
    CONTENT_URI = uri;
  }
  
  public static int getInt(Context paramContext, String paramString, int paramInt) {
    Cursor cursor = query(paramContext, new String[] { paramString });
    int i = paramInt;
    if (cursor.moveToNext())
      try {
        i = cursor.getInt(0);
      } catch (Exception exception) {
        exception.printStackTrace();
        i = paramInt;
      }  
    cursor.close();
    return i;
  }
  
  public static String getString(Context paramContext, String paramString) {
    try {
      return getString(paramContext, paramString, null);
    } finally {
      paramContext = null;
      paramContext.printStackTrace();
    } 
  }
  
  public static String getString(Context paramContext, String paramString1, String paramString2) {
    Cursor cursor = query(paramContext, new String[] { paramString1 });
    String str = paramString2;
    if (cursor.moveToNext()) {
      str = cursor.getString(0);
      if (TextUtils.isEmpty(str))
        str = paramString2; 
    } 
    cursor.close();
    return str;
  }
  
  public static Cursor query(Context paramContext, String... paramVarArgs) { return paramContext.getContentResolver().query(CONTENT_URI, paramVarArgs, null, null, null); }
  
  public static Uri remove(Context paramContext, String paramString) {
    try {
      return save(paramContext, paramString, null);
    } finally {
      paramContext = null;
      paramContext.printStackTrace();
    } 
  }
  
  public static Uri save(Context paramContext, ContentValues paramContentValues) { return paramContext.getContentResolver().insert(CONTENT_URI, paramContentValues); }
  
  public static Uri save(Context paramContext, String paramString1, String paramString2) {
    try {
      return save(paramContext, contentValues);
    } finally {
      paramContext = null;
      paramContext.printStackTrace();
    } 
  }
  
  protected Cursor createCursor(String[] paramArrayOfString1, String[] paramArrayOfString2) {
    MatrixCursor matrixCursor = new MatrixCursor(paramArrayOfString1, 1);
    matrixCursor.addRow((Object[])paramArrayOfString2);
    return (Cursor)matrixCursor;
  }
  
  protected Cursor createSingleCursor(String paramString1, String paramString2) {
    MatrixCursor matrixCursor = new MatrixCursor(new String[] { paramString1 }, 1);
    if (!TextUtils.isEmpty(paramString2))
      matrixCursor.addRow(new Object[] { paramString2 }); 
    return (Cursor)matrixCursor;
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    String str;
    if (paramString == null) {
      paramUri = null;
    } else {
      str = paramUri.getQueryParameter("skBannedNotificationKey");
    } 
    if (!TextUtils.isEmpty(str)) {
      remove(str);
      return 1;
    } 
    return 0;
  }
  
  public String getType(Uri paramUri) { return ""; }
  
  protected String getValue(String paramString1, String paramString2) {
    SharedPreferences sharedPreferences = this.mStore;
    return (sharedPreferences == null) ? "" : sharedPreferences.getString(paramString1, paramString2);
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    if (paramContentValues != null && paramContentValues.size() > 0) {
      save(paramContentValues);
    } else {
      String str2 = paramUri.getQueryParameter("skBannedNotificationKey");
      String str1 = paramUri.getQueryParameter("skBannedNotificationValue");
      if (!TextUtils.isEmpty(str2))
        save(str2, str1); 
    } 
    return null;
  }
  
  public boolean onCreate() {
    try {
      if (getContext() != null)
        this.mStore = getContext().getSharedPreferences("SK_Banned_Notification.db", 0); 
    } finally {
      Exception exception = null;
    } 
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    int i;
    String[] arrayOfString;
    int j = 0;
    if (paramArrayOfString1 == null) {
      i = 0;
    } else {
      i = paramArrayOfString1.length;
    } 
    paramString1 = null;
    if (i > 0) {
      arrayOfString = new String[i];
      while (j < i) {
        arrayOfString[j] = getValue(paramArrayOfString1[j], null);
        j++;
      } 
      return createCursor(paramArrayOfString1, arrayOfString);
    } 
    String str2 = arrayOfString.getQueryParameter("skBannedNotificationKey");
    String str1 = paramString1;
    if (!TextUtils.isEmpty(str2))
      str1 = getValue(str2, null); 
    return createSingleCursor(str2, str1);
  }
  
  protected void remove(String paramString) {
    SharedPreferences sharedPreferences = this.mStore;
    if (sharedPreferences == null)
      return; 
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.remove(paramString);
    editor.apply();
  }
  
  protected void save(ContentValues paramContentValues) {
    if (this.mStore == null)
      return; 
    Iterator<String> iterator = paramContentValues.keySet().iterator();
    SharedPreferences.Editor editor = this.mStore.edit();
    while (iterator.hasNext()) {
      String str1 = iterator.next();
      String str2 = paramContentValues.getAsString(str1);
      if (!TextUtils.isEmpty(str1)) {
        if (str2 != null) {
          editor.putString(str1, str2);
          continue;
        } 
        editor.remove(str1);
      } 
    } 
    editor.apply();
  }
  
  protected void save(String paramString1, String paramString2) {
    SharedPreferences sharedPreferences = this.mStore;
    if (sharedPreferences == null)
      return; 
    SharedPreferences.Editor editor = sharedPreferences.edit();
    if (paramString2 == null) {
      editor.remove(paramString1);
    } else if (!paramString2.equals("removeAll")) {
      editor.putString(paramString1, paramString2);
    } 
    editor.apply();
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    if (paramContentValues != null && paramContentValues.size() > 0) {
      save(paramContentValues);
      return paramContentValues.size();
    } 
    String str2 = paramUri.getQueryParameter("skBannedNotificationKey");
    String str1 = paramUri.getQueryParameter("skBannedNotificationValue");
    if (!TextUtils.isEmpty(str2)) {
      save(str2, str1);
      return 1;
    } 
    return 0;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\core\provider\XProviderSPatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
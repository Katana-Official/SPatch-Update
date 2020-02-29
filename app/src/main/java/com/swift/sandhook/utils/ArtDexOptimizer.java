package com.swift.sandhook.utils;

import android.os.Build;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ArtDexOptimizer {
  public static void dexoatAndDisableInline(String paramString1, String paramString2) throws IOException {
    File file = new File(paramString2);
    if (!file.exists())
      file.getParentFile().mkdirs(); 
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("dex2oat");
    if (SandHookConfig.SDK_INT >= 24) {
      arrayList.add("--runtime-arg");
      arrayList.add("-classpath");
      arrayList.add("--runtime-arg");
      arrayList.add("&");
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("--dex-file=");
    stringBuilder2.append(paramString1);
    arrayList.add(stringBuilder2.toString());
    Object object = new StringBuilder();
    object.append("--oat-file=");
    object.append(paramString2);
    arrayList.add(object.toString());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("--instruction-set=");
    if (SandHook.is64Bit()) {
      String str = "arm64";
    } else {
      object = "arm";
    } 
    stringBuilder1.append((String)object);
    arrayList.add(stringBuilder1.toString());
    arrayList.add("--compiler-filter=everything");
    if (SandHookConfig.SDK_INT >= 22 && SandHookConfig.SDK_INT < 29)
      arrayList.add("--compile-pic"); 
    if (SandHookConfig.SDK_INT > 25) {
      arrayList.add("--inline-max-code-units=0");
    } else if (Build.VERSION.SDK_INT >= 23) {
      arrayList.add("--inline-depth-limit=0");
    } 
    object = new ProcessBuilder(arrayList);
    object.redirectErrorStream(true);
    object = object.start();
    StreamConsumer.consumeInputStream(object.getInputStream());
    StreamConsumer.consumeInputStream(object.getErrorStream());
    try {
      int i = object.waitFor();
      if (i == 0)
        return; 
      object = new StringBuilder();
      object.append("dex2oat works unsuccessfully, exit code: ");
      object.append(i);
      throw new IOException(object.toString());
    } catch (InterruptedException interruptedException) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("dex2oat is interrupted, msg: ");
      stringBuilder1.append(interruptedException.getMessage());
      throw new IOException(stringBuilder1.toString(), interruptedException);
    } 
  }
  
  private static class StreamConsumer {
    static final Executor STREAM_CONSUMER = Executors.newSingleThreadExecutor();
    
    static void consumeInputStream(final InputStream is) { STREAM_CONSUMER.execute(new Runnable() {
            public void run() {
              if (is == null)
                return; 
              null = new byte[256];
              try {
                while (true) {
                  int i = is.read(null);
                  if (i > 0)
                    continue; 
                  break;
                } 
              } catch (IOException iOException) {
                try {
                  return;
                } catch (Exception exception) {}
              } finally {
                try {
                  is.close();
                } catch (Exception exception) {}
              } 
            }
          }); }
  }
  
  static final class null implements Runnable {
    public void run() {
      if (is == null)
        return; 
      null = new byte[256];
      try {
        while (true) {
          int i = is.read(null);
          if (i > 0)
            continue; 
          break;
        } 
      } catch (IOException iOException) {
        try {
          return;
        } catch (Exception exception) {}
      } finally {
        try {
          is.close();
        } catch (Exception exception) {}
      } 
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhoo\\utils\ArtDexOptimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
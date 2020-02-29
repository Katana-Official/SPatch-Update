package com.sk.spatch.xmltool.decode;

import java.io.File;

public interface ExtractCallback {
  void done(ZipEntry paramZipEntry, File paramFile);
  
  File filter(ZipEntry paramZipEntry, int paramInt1, int paramInt2);
  
  void onProgress(long paramLong1, long paramLong2);
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ExtractCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
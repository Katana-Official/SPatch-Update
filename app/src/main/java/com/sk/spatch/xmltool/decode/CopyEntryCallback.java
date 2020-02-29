package com.sk.spatch.xmltool.decode;

public interface CopyEntryCallback {
  void done(ZipEntry paramZipEntry);
  
  ZipEntry filter(ZipEntry paramZipEntry, int paramInt1, int paramInt2);
  
  void onProgress(long paramLong1, long paramLong2);
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\CopyEntryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
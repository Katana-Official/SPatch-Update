package com.sk.spatch.xmltool.decode;

public interface UnixStat {
  public static final int DEFAULT_DIR_PERM = 493;
  
  public static final int DEFAULT_FILE_PERM = 420;
  
  public static final int DEFAULT_LINK_PERM = 511;
  
  public static final int DIR_FLAG = 16384;
  
  public static final int FILE_FLAG = 32768;
  
  public static final int LINK_FLAG = 40960;
  
  public static final int PERM_MASK = 4095;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\UnixStat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
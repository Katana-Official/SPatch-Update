package com.sk.spatch.xmltool.android.content.res;

import java.io.IOException;

class ChunkUtil {
  public static final void readCheckType(IntReader paramIntReader, int paramInt) throws IOException {
    int i = paramIntReader.readInt();
    if (i == paramInt)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected chunk of type 0x");
    stringBuilder.append(Integer.toHexString(paramInt));
    stringBuilder.append(", read 0x");
    stringBuilder.append(Integer.toHexString(i));
    stringBuilder.append(".");
    throw new IOException(stringBuilder.toString());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\android\content\res\ChunkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
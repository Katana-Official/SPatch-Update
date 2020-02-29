package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;
import java.util.ArrayList;

public class ResourceChunk {
  public byte[] ids;
  
  public ArrayList<Integer> resourcIdList;
  
  public byte[] size;
  
  public byte[] type;
  
  public static ResourceChunk createChunk(byte[] paramArrayOfbyte, int paramInt) {
    ResourceChunk resourceChunk = new ResourceChunk();
    resourceChunk.type = Utils.copyByte(paramArrayOfbyte, paramInt + 0, 4);
    byte[] arrayOfByte = Utils.copyByte(paramArrayOfbyte, paramInt + 4, 4);
    resourceChunk.size = arrayOfByte;
    int i = Utils.byte2int(arrayOfByte);
    paramInt += 8;
    i -= 8;
    resourceChunk.ids = Utils.copyByte(paramArrayOfbyte, paramInt, i);
    paramArrayOfbyte = Utils.copyByte(paramArrayOfbyte, paramInt, i);
    ArrayList<Integer> arrayList = new ArrayList(paramArrayOfbyte.length / 4);
    for (paramInt = 0; paramInt < paramArrayOfbyte.length; paramInt += 4)
      arrayList.add(Integer.valueOf(Utils.byte2int(Utils.copyByte(paramArrayOfbyte, paramInt, 4)))); 
    return resourceChunk;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\ResourceChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
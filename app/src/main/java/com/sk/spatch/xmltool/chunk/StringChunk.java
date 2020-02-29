package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;
import java.util.ArrayList;
import java.util.Iterator;

public class StringChunk {
  public byte[] size;
  
  public byte[] strCount;
  
  public byte[] strOffsets;
  
  public byte[] strPool;
  
  public byte[] strPoolOffset;
  
  public ArrayList<String> stringContentList;
  
  public byte[] styleCount;
  
  public byte[] styleOffsets;
  
  public byte[] stylePool;
  
  public byte[] stylePoolOffset;
  
  public byte[] type;
  
  public byte[] unknown;
  
  private ArrayList<String> convertStrList(ArrayList<String> paramArrayList) {
    ArrayList<String> arrayList = new ArrayList(paramArrayList.size());
    Iterator<String> iterator = paramArrayList.iterator();
    while (iterator.hasNext()) {
      byte[] arrayOfByte1 = ((String)iterator.next()).getBytes();
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length * 2];
      for (int i = 0; i < arrayOfByte1.length; i++) {
        int j = i * 2;
        arrayOfByte2[j] = arrayOfByte1[i];
        arrayOfByte2[j + 1] = 0;
      } 
      arrayList.add(new String(arrayOfByte2));
    } 
    return arrayList;
  }
  
  public static StringChunk createChunk(byte[] paramArrayOfbyte, int paramInt) {
    StringChunk stringChunk = new StringChunk();
    stringChunk.type = Utils.copyByte(paramArrayOfbyte, paramInt + 0, 4);
    byte[] arrayOfByte = Utils.copyByte(paramArrayOfbyte, paramInt + 4, 4);
    stringChunk.size = arrayOfByte;
    int j = Utils.byte2int(arrayOfByte);
    arrayOfByte = Utils.copyByte(paramArrayOfbyte, paramInt + 8, 4);
    stringChunk.strCount = arrayOfByte;
    int k = Utils.byte2int(arrayOfByte);
    stringChunk.stringContentList = new ArrayList<String>(k);
    arrayOfByte = Utils.copyByte(paramArrayOfbyte, paramInt + 12, 4);
    stringChunk.styleCount = arrayOfByte;
    int i = Utils.byte2int(arrayOfByte);
    stringChunk.unknown = Utils.copyByte(paramArrayOfbyte, paramInt + 16, 4);
    stringChunk.strPoolOffset = Utils.copyByte(paramArrayOfbyte, paramInt + 20, 4);
    stringChunk.stylePoolOffset = Utils.copyByte(paramArrayOfbyte, paramInt + 24, 4);
    paramInt += 28;
    int m = k * 4;
    stringChunk.strOffsets = Utils.copyByte(paramArrayOfbyte, paramInt, m);
    stringChunk.styleOffsets = Utils.copyByte(paramArrayOfbyte, paramInt + m, i * 4);
    m = Utils.byte2int(stringChunk.strPoolOffset) + 8;
    arrayOfByte = Utils.copyByte(paramArrayOfbyte, m, j);
    i = 0;
    paramInt = Utils.byte2Short(Utils.copyByte(arrayOfByte, 0, 2)) * 2 + 2;
    String str = new String(Utils.copyByte(arrayOfByte, 2, paramInt));
    stringChunk.stringContentList.add(Utils.filterStringNull(str));
    for (paramInt += 2; stringChunk.stringContentList.size() < k; paramInt += n + 2) {
      int n = Utils.byte2Short(Utils.copyByte(arrayOfByte, paramInt, 2)) * 2 + 2;
      str = new String(Utils.copyByte(arrayOfByte, paramInt + 2, n));
      stringChunk.stringContentList.add(Utils.filterStringNull(str));
    } 
    Iterator<String> iterator = stringChunk.stringContentList.iterator();
    for (paramInt = i; iterator.hasNext(); paramInt = paramInt + 2 + ((String)iterator.next()).length() * 2 + 2);
    stringChunk.strPool = Utils.copyByte(paramArrayOfbyte, m, paramInt);
    paramInt = m + paramInt;
    stringChunk.stylePool = Utils.copyByte(paramArrayOfbyte, paramInt, j - paramInt);
    return stringChunk;
  }
  
  private byte[] getStrListByte(ArrayList<String> paramArrayList) {
    byte[] arrayOfByte2 = new byte[0];
    ArrayList<String> arrayList = convertStrList(paramArrayList);
    int i = 0;
    byte[] arrayOfByte1 = arrayOfByte2;
    while (i < arrayList.size()) {
      arrayOfByte2 = Utils.shortToByte((short)(((String)arrayList.get(i)).length() / 2));
      arrayOfByte1 = Utils.addByte(arrayOfByte1, Utils.addByte(Utils.addByte(Utils.addByte(new byte[0], arrayOfByte2), ((String)arrayList.get(i)).getBytes()), new byte[] { 0, 0 }));
      i++;
    } 
    return arrayOfByte1;
  }
  
  public byte[] getByte(ArrayList<String> paramArrayList) {
    byte[] arrayOfByte3 = getStrListByte(paramArrayList);
    int i = 0;
    byte[] arrayOfByte2 = this.type;
    byte[] arrayOfByte4 = Utils.addByte(Utils.addByte(Utils.addByte(Utils.addByte(Utils.addByte(Utils.addByte(Utils.addByte(new byte[0], arrayOfByte2), this.size), Utils.int2Byte(paramArrayList.size())), this.styleCount), this.unknown), this.strPoolOffset), this.stylePoolOffset);
    ArrayList<String> arrayList = convertStrList(paramArrayList);
    arrayOfByte2 = new byte[0];
    int j = 0;
    while (i < arrayList.size()) {
      arrayOfByte2 = Utils.addByte(arrayOfByte2, Utils.int2Byte(j));
      j += ((String)arrayList.get(i)).length() + 4;
      i++;
    } 
    arrayOfByte2 = Utils.addByte(arrayOfByte4, arrayOfByte2);
    i = arrayOfByte2.length;
    arrayOfByte2 = Utils.addByte(arrayOfByte2, this.styleOffsets);
    j = arrayOfByte2.length;
    arrayOfByte3 = Utils.addByte(Utils.addByte(arrayOfByte2, arrayOfByte3), this.stylePool);
    arrayOfByte4 = this.styleOffsets;
    arrayOfByte2 = arrayOfByte3;
    if (arrayOfByte4 != null) {
      arrayOfByte2 = arrayOfByte3;
      if (arrayOfByte4.length > 0)
        arrayOfByte2 = Utils.replaceBytes(arrayOfByte3, Utils.int2Byte(i), paramArrayList.size() * 4 + 28); 
    } 
    arrayOfByte2 = Utils.replaceBytes(arrayOfByte2, Utils.int2Byte(j), 20);
    byte[] arrayOfByte1 = arrayOfByte2;
    if (arrayOfByte2.length % 4 != 0)
      arrayOfByte1 = Utils.addByte(arrayOfByte2, new byte[] { 0, 0 }); 
    return Utils.replaceBytes(arrayOfByte1, Utils.int2Byte(arrayOfByte1.length), 4);
  }
  
  public int getLen() { return this.type.length + this.size.length + this.strCount.length + this.styleCount.length + this.unknown.length + this.strPoolOffset.length + this.stylePoolOffset.length + this.strOffsets.length + this.styleOffsets.length + this.strPool.length + this.stylePool.length; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\StringChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.sk.spatch.xmltool.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class AXmlDecoder {
  private static final int AXML_CHUNK_TYPE = 524291;
  
  byte[] data;
  
  private final ZInput mIn;
  
  public StringDecoder mTableStrings;
  
  private AXmlDecoder(ZInput paramZInput) { this.mIn = paramZInput; }
  
  private void checkChunk(int paramInt1, int paramInt2) throws IOException {
    if (paramInt1 == paramInt2)
      return; 
    throw new IOException(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramInt1) }));
  }
  
  public static AXmlDecoder decode(InputStream paramInputStream) throws IOException {
    AXmlDecoder aXmlDecoder = new AXmlDecoder(new ZInput(paramInputStream));
    aXmlDecoder.readStrings();
    return aXmlDecoder;
  }
  
  private void readStrings() throws IOException {
    checkChunk(this.mIn.readInt(), 524291);
    this.mIn.readInt();
    this.mTableStrings = StringDecoder.read(this.mIn);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[2048];
    while (true) {
      int i = this.mIn.read(arrayOfByte, 0, 2048);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      break;
    } 
    this.data = byteArrayOutputStream.toByteArray();
    this.mIn.close();
    byteArrayOutputStream.close();
  }
  
  public byte[] encode() throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ZOutput zOutput = new ZOutput(byteArrayOutputStream);
    this.mTableStrings.write(zOutput);
    zOutput.writeFully(this.data);
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    byteArrayOutputStream.reset();
    zOutput.writeInt(524291);
    zOutput.writeInt(arrayOfByte.length + 8);
    zOutput.writeFully(arrayOfByte);
    return byteArrayOutputStream.toByteArray();
  }
  
  public byte[] getData() { return this.data; }
  
  public void setData(byte[] paramArrayOfbyte) { this.data = paramArrayOfbyte; }
  
  public void write(ZOutput paramZOutput) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ZOutput zOutput = new ZOutput(byteArrayOutputStream);
    this.mTableStrings.write(zOutput);
    zOutput.writeFully(this.data);
    paramZOutput.writeInt(524291);
    paramZOutput.writeInt(byteArrayOutputStream.size() + 8);
    paramZOutput.writeFully(byteArrayOutputStream.toByteArray());
    byteArrayOutputStream.reset();
    zOutput.close();
  }
  
  public void write(List<String> paramList, ZOutput paramZOutput) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ZOutput zOutput = new ZOutput(byteArrayOutputStream);
    String[] arrayOfString = new String[paramList.size()];
    paramList.toArray(arrayOfString);
    this.mTableStrings.write(arrayOfString, zOutput);
    zOutput.writeFully(this.data);
    paramZOutput.writeInt(524291);
    paramZOutput.writeInt(byteArrayOutputStream.size() + 8);
    paramZOutput.writeFully(byteArrayOutputStream.toByteArray());
    zOutput.close();
  }
  
  public void write(List<String> paramList, OutputStream paramOutputStream) throws IOException { write(paramList, new ZOutput(paramOutputStream)); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\AXmlDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
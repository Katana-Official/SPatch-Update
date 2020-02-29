package com.sk.spatch.xmltool.chunk;

import java.util.ArrayList;

public class XmlStruct {
  public byte[] byteSrc;
  
  public EndNameSpaceChunk endNamespaceChunk;
  
  public ArrayList<EndTagChunk> endTagChunkList = new ArrayList<EndTagChunk>();
  
  public byte[] fileSize;
  
  public byte[] magicNumber;
  
  public ResourceChunk resChunk;
  
  public StartNameSpaceChunk startNamespaceChunk;
  
  public ArrayList<StartTagChunk> startTagChunkList = new ArrayList<StartTagChunk>();
  
  public StringChunk stringChunk;
  
  public ArrayList<TagChunk> tagChunkList = new ArrayList<TagChunk>();
  
  public ArrayList<TextChunk> textChunkList = new ArrayList<TextChunk>();
  
  public void clear() {
    this.magicNumber = null;
    this.fileSize = null;
    this.stringChunk = null;
    this.resChunk = null;
    this.startNamespaceChunk = null;
    this.endNamespaceChunk = null;
    this.startTagChunkList.clear();
    this.endTagChunkList.clear();
    this.textChunkList.clear();
    this.tagChunkList.clear();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\XmlStruct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
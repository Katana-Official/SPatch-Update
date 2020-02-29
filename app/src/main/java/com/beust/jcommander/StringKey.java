package com.beust.jcommander;

public class StringKey implements FuzzyMap.IKey {
  private String name;
  
  public StringKey(String paramString) { this.name = paramString; }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    String str = this.name;
    if (str == null) {
      if (paramObject.name != null)
        return false; 
    } else if (!str.equals(paramObject.name)) {
      return false;
    } 
    return true;
  }
  
  public String getName() { return this.name; }
  
  public int hashCode() {
    int i;
    String str = this.name;
    if (str == null) {
      i = 0;
    } else {
      i = str.hashCode();
    } 
    return 31 + i;
  }
  
  public String toString() { return this.name; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\StringKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
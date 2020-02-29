package com.simple.spiderman;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class CrashModel implements Parcelable {
  public static final Parcelable.Creator<CrashModel> CREATOR = new Parcelable.Creator<CrashModel>() {
      public CrashModel createFromParcel(Parcel param1Parcel) { return new CrashModel(param1Parcel); }
      
      public CrashModel[] newArray(int param1Int) { return new CrashModel[param1Int]; }
    };
  
  private String className;
  
  private Device device = new Device();
  
  private Throwable ex;
  
  private String exceptionMsg;
  
  private String exceptionType;
  
  private String fileName;
  
  private String fullException;
  
  private int lineNumber;
  
  private String methodName;
  
  private String packageName;
  
  private long time;
  
  public CrashModel() {}
  
  protected CrashModel(Parcel paramParcel) {
    this.ex = (Throwable)paramParcel.readSerializable();
    this.exceptionMsg = paramParcel.readString();
    this.className = paramParcel.readString();
    this.fileName = paramParcel.readString();
    this.methodName = paramParcel.readString();
    this.lineNumber = paramParcel.readInt();
    this.exceptionType = paramParcel.readString();
    this.fullException = paramParcel.readString();
    this.time = paramParcel.readLong();
  }
  
  public int describeContents() { return 0; }
  
  public String getClassName() { return this.className; }
  
  public Device getDevice() { return this.device; }
  
  public Throwable getEx() { return this.ex; }
  
  public String getExceptionMsg() { return this.exceptionMsg; }
  
  public String getExceptionType() { return this.exceptionType; }
  
  public String getFileName() { return this.fileName; }
  
  public String getFullException() { return this.fullException; }
  
  public int getLineNumber() { return this.lineNumber; }
  
  public String getMethodName() { return this.methodName; }
  
  public String getPackageName() { return getClassName().replace(getFileName(), ""); }
  
  public long getTime() { return this.time; }
  
  public void setClassName(String paramString) { this.className = paramString; }
  
  public void setEx(Throwable paramThrowable) { this.ex = paramThrowable; }
  
  public void setExceptionMsg(String paramString) { this.exceptionMsg = paramString; }
  
  public void setExceptionType(String paramString) { this.exceptionType = paramString; }
  
  public void setFileName(String paramString) { this.fileName = paramString; }
  
  public void setFullException(String paramString) { this.fullException = paramString; }
  
  public void setLineNumber(int paramInt) { this.lineNumber = paramInt; }
  
  public void setMethodName(String paramString) { this.methodName = paramString; }
  
  public void setTime(long paramLong) { this.time = paramLong; }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CrashModel{ex=");
    stringBuilder.append(this.ex);
    stringBuilder.append(", packageName='");
    stringBuilder.append(this.packageName);
    stringBuilder.append('\'');
    stringBuilder.append(", exceptionMsg='");
    stringBuilder.append(this.exceptionMsg);
    stringBuilder.append('\'');
    stringBuilder.append(", className='");
    stringBuilder.append(this.className);
    stringBuilder.append('\'');
    stringBuilder.append(", fileName='");
    stringBuilder.append(this.fileName);
    stringBuilder.append('\'');
    stringBuilder.append(", methodName='");
    stringBuilder.append(this.methodName);
    stringBuilder.append('\'');
    stringBuilder.append(", lineNumber=");
    stringBuilder.append(this.lineNumber);
    stringBuilder.append(", exceptionType='");
    stringBuilder.append(this.exceptionType);
    stringBuilder.append('\'');
    stringBuilder.append(", fullException='");
    stringBuilder.append(this.fullException);
    stringBuilder.append('\'');
    stringBuilder.append(", time=");
    stringBuilder.append(this.time);
    stringBuilder.append(", device=");
    stringBuilder.append(this.device);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeSerializable(this.ex);
    paramParcel.writeString(this.exceptionMsg);
    paramParcel.writeString(this.className);
    paramParcel.writeString(this.fileName);
    paramParcel.writeString(this.methodName);
    paramParcel.writeInt(this.lineNumber);
    paramParcel.writeString(this.exceptionType);
    paramParcel.writeString(this.fullException);
    paramParcel.writeLong(this.time);
  }
  
  public static class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public CrashModel.Device createFromParcel(Parcel param2Parcel) { return new CrashModel.Device(param2Parcel); }
        
        public CrashModel.Device[] newArray(int param2Int) { return new CrashModel.Device[param2Int]; }
      };
    
    private String brand = Build.BRAND;
    
    private String model = Build.MODEL;
    
    private String version = String.valueOf(Build.VERSION.SDK_INT);
    
    public Device() {}
    
    protected Device(Parcel param1Parcel) {
      this.model = param1Parcel.readString();
      this.brand = param1Parcel.readString();
      this.version = param1Parcel.readString();
    }
    
    public int describeContents() { return 0; }
    
    public String getBrand() { return this.brand; }
    
    public String getModel() { return this.model; }
    
    public String getVersion() { return this.version; }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeString(this.model);
      param1Parcel.writeString(this.brand);
      param1Parcel.writeString(this.version);
    }
  }
  
  static final class null implements Parcelable.Creator<Device> {
    public CrashModel.Device createFromParcel(Parcel param1Parcel) { return new CrashModel.Device(param1Parcel); }
    
    public CrashModel.Device[] newArray(int param1Int) { return new CrashModel.Device[param1Int]; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\simple\spiderman\CrashModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
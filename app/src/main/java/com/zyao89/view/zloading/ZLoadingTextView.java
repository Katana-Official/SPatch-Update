package com.zyao89.view.zloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.zyao89.view.zloading.text.TextBuilder;

public class ZLoadingTextView extends ZLoadingView {
  private String mText = "Zyao89";
  
  public ZLoadingTextView(Context paramContext) { this(paramContext, null); }
  
  public ZLoadingTextView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, -1); }
  
  public ZLoadingTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    super.setLoadingBuilder(Z_TYPE.TEXT);
    try {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ZLoadingTextView);
      String str = typedArray.getString(R.styleable.ZLoadingTextView_z_text);
      typedArray.recycle();
      if (!TextUtils.isEmpty(str)) {
        this.mText = str;
        return;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  protected void onAttachedToWindow() {
    setText(this.mText);
    super.onAttachedToWindow();
  }
  
  @Deprecated
  public void setLoadingBuilder(Z_TYPE paramZ_TYPE) { super.setLoadingBuilder(Z_TYPE.TEXT); }
  
  public void setText(String paramString) {
    this.mText = paramString;
    if (this.mZLoadingBuilder instanceof TextBuilder)
      ((TextBuilder)this.mZLoadingBuilder).setText(this.mText); 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ZLoadingTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.zyao89.view.zloading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;

public class ZLoadingDialog {
  private boolean mCancelable = true;
  
  private boolean mCanceledOnTouchOutside = true;
  
  private final WeakReference<Context> mContext;
  
  private int mDialogBackgroundColor = -1;
  
  private double mDurationTimePercent = 1.0D;
  
  private String mHintText;
  
  private int mHintTextColor = -1;
  
  private float mHintTextSize = -1.0F;
  
  private int mLoadingBuilderColor;
  
  private Z_TYPE mLoadingBuilderType;
  
  private final int mThemeResId;
  
  private Dialog mZLoadingDialog;
  
  public ZLoadingDialog(Context paramContext) { this(paramContext, R.style.alert_dialog); }
  
  public ZLoadingDialog(Context paramContext, int paramInt) {
    this.mContext = new WeakReference<Context>(paramContext);
    this.mThemeResId = paramInt;
  }
  
  private View createContentView() {
    if (!isContextNotExist())
      return View.inflate(this.mContext.get(), R.layout.z_loading_dialog, null); 
    throw new RuntimeException("Context is null...");
  }
  
  private boolean isContextNotExist() { return ((Context)this.mContext.get() == null); }
  
  public void cancel() {
    Dialog dialog = this.mZLoadingDialog;
    if (dialog != null)
      dialog.cancel(); 
    this.mZLoadingDialog = null;
  }
  
  public Dialog create() {
    if (!isContextNotExist()) {
      if (this.mZLoadingDialog != null)
        cancel(); 
      this.mZLoadingDialog = new Dialog(this.mContext.get(), this.mThemeResId);
      View view = createContentView();
      LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.z_loading);
      if (this.mDialogBackgroundColor != -1) {
        Drawable drawable = linearLayout.getBackground();
        if (drawable != null) {
          drawable.setAlpha(Color.alpha(this.mDialogBackgroundColor));
          drawable.setColorFilter(this.mDialogBackgroundColor, PorterDuff.Mode.SRC_ATOP);
        } 
      } 
      ZLoadingView zLoadingView = (ZLoadingView)view.findViewById(R.id.z_loading_view);
      ZLoadingTextView zLoadingTextView = (ZLoadingTextView)view.findViewById(R.id.z_text_view);
      TextView textView = (TextView)view.findViewById(R.id.z_custom_text_view);
      if (this.mHintTextSize > 0.0F && !TextUtils.isEmpty(this.mHintText)) {
        textView.setVisibility(0);
        textView.setText(this.mHintText);
        textView.setTextSize(this.mHintTextSize);
        int j = this.mHintTextColor;
        int i = j;
        if (j == -1)
          i = this.mLoadingBuilderColor; 
        textView.setTextColor(i);
      } else if (!TextUtils.isEmpty(this.mHintText)) {
        zLoadingTextView.setVisibility(0);
        zLoadingTextView.setText(this.mHintText);
        int j = this.mHintTextColor;
        int i = j;
        if (j == -1)
          i = this.mLoadingBuilderColor; 
        zLoadingTextView.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
      } 
      zLoadingView.setLoadingBuilder(this.mLoadingBuilderType);
      if (zLoadingView.mZLoadingBuilder != null)
        zLoadingView.mZLoadingBuilder.setDurationTimePercent(this.mDurationTimePercent); 
      zLoadingView.setColorFilter(this.mLoadingBuilderColor, PorterDuff.Mode.SRC_ATOP);
      this.mZLoadingDialog.setContentView(view);
      this.mZLoadingDialog.setCancelable(this.mCancelable);
      this.mZLoadingDialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
      return this.mZLoadingDialog;
    } 
    throw new RuntimeException("Context is null...");
  }
  
  public void dismiss() {
    Dialog dialog = this.mZLoadingDialog;
    if (dialog != null)
      dialog.dismiss(); 
    this.mZLoadingDialog = null;
  }
  
  public ZLoadingDialog setCancelable(boolean paramBoolean) {
    this.mCancelable = paramBoolean;
    return this;
  }
  
  public ZLoadingDialog setCanceledOnTouchOutside(boolean paramBoolean) {
    this.mCanceledOnTouchOutside = paramBoolean;
    return this;
  }
  
  public ZLoadingDialog setDialogBackgroundColor(int paramInt) {
    this.mDialogBackgroundColor = paramInt;
    return this;
  }
  
  public ZLoadingDialog setDurationTime(double paramDouble) {
    this.mDurationTimePercent = paramDouble;
    return this;
  }
  
  public ZLoadingDialog setHintText(String paramString) {
    this.mHintText = paramString;
    return this;
  }
  
  public ZLoadingDialog setHintTextColor(int paramInt) {
    this.mHintTextColor = paramInt;
    return this;
  }
  
  public ZLoadingDialog setHintTextSize(float paramFloat) {
    this.mHintTextSize = paramFloat;
    return this;
  }
  
  public ZLoadingDialog setLoadingBuilder(Z_TYPE paramZ_TYPE) {
    this.mLoadingBuilderType = paramZ_TYPE;
    return this;
  }
  
  public ZLoadingDialog setLoadingColor(int paramInt) {
    this.mLoadingBuilderColor = paramInt;
    return this;
  }
  
  public void show() {
    Dialog dialog = this.mZLoadingDialog;
    if (dialog != null) {
      dialog.show();
      return;
    } 
    create().show();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ZLoadingDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
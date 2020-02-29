package cn.refactor.lib.colordialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.refactor.lib.colordialog.util.DisplayUtil;

public class ColorDialog extends Dialog implements View.OnClickListener {
  private AnimationSet mAnimIn;
  
  private AnimationSet mAnimOut;
  
  private int mBackgroundColor;
  
  private View mBkgView;
  
  private View mBtnGroupView;
  
  private Bitmap mContentBitmap;
  
  private ImageView mContentIv;
  
  private CharSequence mContentText;
  
  private int mContentTextColor;
  
  private TextView mContentTv;
  
  private View mDialogView;
  
  private View mDividerView;
  
  private Drawable mDrawable;
  
  private boolean mIsShowAnim;
  
  private TextView mNegativeBtn;
  
  private OnNegativeListener mNegativeListener;
  
  private CharSequence mNegativeText;
  
  private TextView mPositiveBtn;
  
  private OnPositiveListener mPositiveListener;
  
  private CharSequence mPositiveText;
  
  private int mResId;
  
  private CharSequence mTitleText;
  
  private int mTitleTextColor;
  
  private TextView mTitleTv;
  
  public ColorDialog(Context paramContext) { this(paramContext, 0); }
  
  public ColorDialog(Context paramContext, int paramInt) {
    super(paramContext, R.style.color_dialog);
    init();
  }
  
  private void callDismiss() { super.dismiss(); }
  
  private void dismissWithAnimation(boolean paramBoolean) {
    if (paramBoolean) {
      this.mDialogView.startAnimation((Animation)this.mAnimOut);
      return;
    } 
    super.dismiss();
  }
  
  private void init() {
    this.mAnimIn = AnimationLoader.getInAnimation(getContext());
    this.mAnimOut = AnimationLoader.getOutAnimation(getContext());
    initAnimListener();
  }
  
  private void initAnimListener() { this.mAnimOut.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) { ColorDialog.this.mDialogView.post(new Runnable() {
                  public void run() { ColorDialog.this.callDismiss(); }
                }); }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {}
        }); }
  
  private void setBackgroundColor() {
    if (this.mBackgroundColor == 0)
      return; 
    float f = DisplayUtil.dp2px(getContext(), 6.0F);
    ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f, f, f, f, 0.0F, 0.0F, 0.0F, 0.0F }, null, null));
    shapeDrawable.getPaint().setColor(this.mBackgroundColor);
    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    this.mBkgView.setBackgroundDrawable((Drawable)shapeDrawable);
  }
  
  private void setContentMode() {
    boolean bool;
    if (this.mDrawable != null) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mContentBitmap != null) {
      j = 1;
    } else {
      j = 0;
    } 
    if (this.mResId != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    int i = i | j | bool;
    int j = true ^ TextUtils.isEmpty(this.mContentText);
    if (i != 0 && j != 0) {
      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mContentTv.getLayoutParams();
      layoutParams.gravity = 80;
      this.mContentTv.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mContentTv.setBackgroundColor(-16777216);
      this.mContentTv.getBackground().setAlpha(40);
      this.mContentTv.setVisibility(0);
      this.mContentIv.setVisibility(0);
      return;
    } 
    if (j != 0) {
      FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mContentTv.getLayoutParams();
      layoutParams.gravity = 0;
      this.mContentTv.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.mContentIv.setVisibility(8);
      this.mContentTv.setVisibility(0);
      return;
    } 
    if (i != 0) {
      this.mContentTv.setVisibility(8);
      this.mContentIv.setVisibility(0);
    } 
  }
  
  private void setTextColor() {
    int i = this.mTitleTextColor;
    if (i != 0)
      this.mTitleTv.setTextColor(i); 
    i = this.mContentTextColor;
    if (i != 0)
      this.mContentTv.setTextColor(i); 
  }
  
  private void startWithAnimation(boolean paramBoolean) {
    if (paramBoolean)
      this.mDialogView.startAnimation((Animation)this.mAnimIn); 
  }
  
  public void dismiss() { dismissWithAnimation(this.mIsShowAnim); }
  
  public CharSequence getContentText() { return this.mContentText; }
  
  public CharSequence getNegativeText() { return this.mNegativeText; }
  
  public CharSequence getPositiveText() { return this.mPositiveText; }
  
  public CharSequence getTitleText() { return this.mTitleText; }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (R.id.btnPositive == i) {
      this.mPositiveListener.onClick(this);
      return;
    } 
    if (R.id.btnNegative == i)
      this.mNegativeListener.onClick(this); 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    View view = View.inflate(getContext(), R.layout.layout_colordialog, null);
    setContentView(view);
    this.mDialogView = getWindow().getDecorView().findViewById(16908290);
    this.mBkgView = view.findViewById(R.id.llBkg);
    this.mTitleTv = (TextView)view.findViewById(R.id.tvTitle);
    this.mContentTv = (TextView)view.findViewById(R.id.tvContent);
    this.mContentIv = (ImageView)view.findViewById(R.id.ivContent);
    this.mPositiveBtn = (TextView)view.findViewById(R.id.btnPositive);
    this.mNegativeBtn = (TextView)view.findViewById(R.id.btnNegative);
    this.mDividerView = view.findViewById(R.id.divider);
    this.mBtnGroupView = view.findViewById(R.id.llBtnGroup);
    this.mPositiveBtn.setOnClickListener(this);
    this.mNegativeBtn.setOnClickListener(this);
    this.mTitleTv.setText(this.mTitleText);
    this.mContentTv.setText(this.mContentText);
    this.mPositiveBtn.setText(this.mPositiveText);
    this.mNegativeBtn.setText(this.mNegativeText);
    if (this.mPositiveListener == null && this.mNegativeListener == null) {
      this.mBtnGroupView.setVisibility(8);
    } else if (this.mPositiveListener == null && this.mNegativeListener != null) {
      this.mPositiveBtn.setVisibility(8);
      this.mDividerView.setVisibility(8);
      this.mNegativeBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
    } else if (this.mPositiveListener != null && this.mNegativeListener == null) {
      this.mNegativeBtn.setVisibility(8);
      this.mDividerView.setVisibility(8);
      this.mPositiveBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
    } 
    Drawable drawable = this.mDrawable;
    if (drawable != null)
      this.mContentIv.setBackgroundDrawable(drawable); 
    Bitmap bitmap = this.mContentBitmap;
    if (bitmap != null)
      this.mContentIv.setImageBitmap(bitmap); 
    int i = this.mResId;
    if (i != 0)
      this.mContentIv.setBackgroundResource(i); 
    setTextColor();
    setBackgroundColor();
    setContentMode();
  }
  
  protected void onStart() {
    super.onStart();
    startWithAnimation(this.mIsShowAnim);
  }
  
  public ColorDialog setAnimationEnable(boolean paramBoolean) {
    this.mIsShowAnim = paramBoolean;
    return this;
  }
  
  public ColorDialog setAnimationIn(AnimationSet paramAnimationSet) {
    this.mAnimIn = paramAnimationSet;
    return this;
  }
  
  public ColorDialog setAnimationOut(AnimationSet paramAnimationSet) {
    this.mAnimOut = paramAnimationSet;
    initAnimListener();
    return this;
  }
  
  public ColorDialog setColor(int paramInt) {
    this.mBackgroundColor = paramInt;
    return this;
  }
  
  public ColorDialog setColor(String paramString) {
    try {
      setColor(Color.parseColor(paramString));
      return this;
    } catch (IllegalArgumentException illegalArgumentException) {
      illegalArgumentException.printStackTrace();
      return this;
    } 
  }
  
  public ColorDialog setContentImage(int paramInt) {
    this.mResId = paramInt;
    return this;
  }
  
  public ColorDialog setContentImage(Bitmap paramBitmap) {
    this.mContentBitmap = paramBitmap;
    return this;
  }
  
  public ColorDialog setContentImage(Drawable paramDrawable) {
    this.mDrawable = paramDrawable;
    return this;
  }
  
  public ColorDialog setContentText(int paramInt) { return setContentText(getContext().getText(paramInt)); }
  
  public ColorDialog setContentText(CharSequence paramCharSequence) {
    this.mContentText = paramCharSequence;
    return this;
  }
  
  public ColorDialog setContentTextColor(int paramInt) {
    this.mContentTextColor = paramInt;
    return this;
  }
  
  public ColorDialog setContentTextColor(String paramString) {
    try {
      setContentTextColor(Color.parseColor(paramString));
      return this;
    } catch (IllegalArgumentException illegalArgumentException) {
      illegalArgumentException.printStackTrace();
      return this;
    } 
  }
  
  public ColorDialog setNegativeListener(int paramInt, OnNegativeListener paramOnNegativeListener) { return setNegativeListener(getContext().getText(paramInt), paramOnNegativeListener); }
  
  public ColorDialog setNegativeListener(CharSequence paramCharSequence, OnNegativeListener paramOnNegativeListener) {
    this.mNegativeText = paramCharSequence;
    this.mNegativeListener = paramOnNegativeListener;
    return this;
  }
  
  public ColorDialog setPositiveListener(int paramInt, OnPositiveListener paramOnPositiveListener) { return setPositiveListener(getContext().getText(paramInt), paramOnPositiveListener); }
  
  public ColorDialog setPositiveListener(CharSequence paramCharSequence, OnPositiveListener paramOnPositiveListener) {
    this.mPositiveText = paramCharSequence;
    this.mPositiveListener = paramOnPositiveListener;
    return this;
  }
  
  public void setTitle(int paramInt) { setTitle(getContext().getText(paramInt)); }
  
  public void setTitle(CharSequence paramCharSequence) { this.mTitleText = paramCharSequence; }
  
  public ColorDialog setTitleTextColor(int paramInt) {
    this.mTitleTextColor = paramInt;
    return this;
  }
  
  public ColorDialog setTitleTextColor(String paramString) {
    try {
      setTitleTextColor(Color.parseColor(paramString));
      return this;
    } catch (IllegalArgumentException illegalArgumentException) {
      illegalArgumentException.printStackTrace();
      return this;
    } 
  }
  
  public static interface OnNegativeListener {
    void onClick(ColorDialog param1ColorDialog);
  }
  
  public static interface OnPositiveListener {
    void onClick(ColorDialog param1ColorDialog);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\cn\refactor\lib\colordialog\ColorDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
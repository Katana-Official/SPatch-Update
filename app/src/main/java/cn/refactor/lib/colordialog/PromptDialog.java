package cn.refactor.lib.colordialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.refactor.lib.colordialog.util.DisplayUtil;

public class PromptDialog extends Dialog {
  private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
  
  private static final int DEFAULT_RADIUS = 6;
  
  public static final int DIALOG_TYPE_DEFAULT = 0;
  
  public static final int DIALOG_TYPE_HELP = 1;
  
  public static final int DIALOG_TYPE_INFO = 0;
  
  public static final int DIALOG_TYPE_SUCCESS = 3;
  
  public static final int DIALOG_TYPE_WARNING = 4;
  
  public static final int DIALOG_TYPE_WRONG = 2;
  
  private AnimationSet mAnimIn;
  
  private AnimationSet mAnimOut;
  
  private CharSequence mBtnText;
  
  private CharSequence mContent;
  
  private TextView mContentTv;
  
  private int mDialogType;
  
  private View mDialogView;
  
  private boolean mIsShowAnim;
  
  private OnPositiveListener mOnPositiveListener;
  
  private TextView mPositiveBtn;
  
  private CharSequence mTitle;
  
  private TextView mTitleTv;
  
  public PromptDialog(Context paramContext) { this(paramContext, 0); }
  
  public PromptDialog(Context paramContext, int paramInt) {
    super(paramContext, R.style.color_dialog);
    init();
  }
  
  private void callDismiss() { super.dismiss(); }
  
  private ColorStateList createColorStateList(int paramInt1, int paramInt2) { return createColorStateList(paramInt1, paramInt2, -16777216, -16777216); }
  
  private ColorStateList createColorStateList(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = { 16842919, 16842910 };
    int[] arrayOfInt2 = { 16842910, 16842908 };
    int[] arrayOfInt3 = { 16842910 };
    int[] arrayOfInt4 = { 16842909 };
    int[] arrayOfInt5 = new int[0];
    return new ColorStateList(new int[][] { arrayOfInt1, arrayOfInt2, arrayOfInt3, { 16842908 }, , arrayOfInt4, arrayOfInt5 }, new int[] { paramInt2, paramInt3, paramInt1, paramInt3, paramInt4, paramInt1 });
  }
  
  private Bitmap createTriangel(int paramInt1, int paramInt2) { return (paramInt1 <= 0 || paramInt2 <= 0) ? null : getBitmap(paramInt1, paramInt2, getContext().getResources().getColor(getColorResId(this.mDialogType))); }
  
  private void dismissWithAnimation(boolean paramBoolean) {
    if (paramBoolean) {
      this.mDialogView.startAnimation((Animation)this.mAnimOut);
      return;
    } 
    super.dismiss();
  }
  
  private Bitmap getBitmap(int paramInt1, int paramInt2, int paramInt3) {
    Bitmap bitmap = Bitmap.createBitmap(paramInt1, paramInt2, BITMAP_CONFIG);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(1);
    paint.setColor(paramInt3);
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.lineTo(paramInt1, 0.0F);
    path.lineTo((paramInt1 / 2), paramInt2);
    path.close();
    canvas.drawPath(path, paint);
    return bitmap;
  }
  
  private int getColorResId(int paramInt) { return (paramInt == 0) ? R.color.color_type_info : ((paramInt == 0) ? R.color.color_type_info : ((1 == paramInt) ? R.color.color_type_help : ((2 == paramInt) ? R.color.color_type_wrong : ((3 == paramInt) ? R.color.color_type_success : ((4 == paramInt) ? R.color.color_type_warning : R.color.color_type_info))))); }
  
  private int getLogoResId(int paramInt) { return (paramInt == 0) ? R.mipmap.ic_info : ((paramInt == 0) ? R.mipmap.ic_info : ((1 == paramInt) ? R.mipmap.ic_help : ((2 == paramInt) ? R.mipmap.ic_wrong : ((3 == paramInt) ? R.mipmap.ic_success : ((4 == paramInt) ? R.mipmap.icon_warning : R.mipmap.ic_info))))); }
  
  private int getSelBtn(int paramInt) { return (paramInt == 0) ? R.drawable.sel_btn : ((paramInt == 0) ? R.drawable.sel_btn_info : ((1 == paramInt) ? R.drawable.sel_btn_help : ((2 == paramInt) ? R.drawable.sel_btn_wrong : ((3 == paramInt) ? R.drawable.sel_btn_success : ((4 == paramInt) ? R.drawable.sel_btn_warning : R.drawable.sel_btn))))); }
  
  private void init() {
    this.mAnimIn = AnimationLoader.getInAnimation(getContext());
    this.mAnimOut = AnimationLoader.getOutAnimation(getContext());
  }
  
  private void initAnimListener() { this.mAnimOut.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) { PromptDialog.this.mDialogView.post(new Runnable() {
                  public void run() { PromptDialog.this.callDismiss(); }
                }); }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {}
        }); }
  
  private void initListener() {
    this.mPositiveBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (PromptDialog.this.mOnPositiveListener != null)
              PromptDialog.this.mOnPositiveListener.onClick(PromptDialog.this); 
          }
        });
    initAnimListener();
  }
  
  private void initView() {
    View view2 = View.inflate(getContext(), R.layout.layout_promptdialog, null);
    setContentView(view2);
    resizeDialog();
    this.mDialogView = getWindow().getDecorView().findViewById(16908290);
    this.mTitleTv = (TextView)view2.findViewById(R.id.tvTitle);
    this.mContentTv = (TextView)view2.findViewById(R.id.tvContent);
    this.mPositiveBtn = (TextView)view2.findViewById(R.id.btnPositive);
    View view1 = findViewById(R.id.llBtnGroup);
    ((ImageView)view2.findViewById(R.id.logoIv)).setBackgroundResource(getLogoResId(this.mDialogType));
    LinearLayout linearLayout = (LinearLayout)view2.findViewById(R.id.topLayout);
    ImageView imageView = new ImageView(getContext());
    imageView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, DisplayUtil.dp2px(getContext(), 10.0F)));
    imageView.setImageBitmap(createTriangel((int)((DisplayUtil.getScreenSize(getContext())).x * 0.7D), DisplayUtil.dp2px(getContext(), 10.0F)));
    linearLayout.addView((View)imageView);
    setBtnBackground(this.mPositiveBtn);
    setBottomCorners(view1);
    float f = DisplayUtil.dp2px(getContext(), 6.0F);
    ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f, f, f, f, 0.0F, 0.0F, 0.0F, 0.0F }, null, null));
    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    shapeDrawable.getPaint().setColor(getContext().getResources().getColor(getColorResId(this.mDialogType)));
    ((LinearLayout)findViewById(R.id.llTop)).setBackgroundDrawable((Drawable)shapeDrawable);
    this.mTitleTv.setText(this.mTitle);
    this.mContentTv.setText(this.mContent);
    this.mPositiveBtn.setText(this.mBtnText);
  }
  
  private void resizeDialog() {
    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
    layoutParams.width = (int)((DisplayUtil.getScreenSize(getContext())).x * 0.7D);
    getWindow().setAttributes(layoutParams);
  }
  
  private void setBottomCorners(View paramView) {
    float f = DisplayUtil.dp2px(getContext(), 6.0F);
    ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, f, f, f, f }, null, null));
    shapeDrawable.getPaint().setColor(-1);
    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    paramView.setBackgroundDrawable((Drawable)shapeDrawable);
  }
  
  private void setBtnBackground(TextView paramTextView) {
    paramTextView.setTextColor(createColorStateList(getContext().getResources().getColor(getColorResId(this.mDialogType)), getContext().getResources().getColor(R.color.color_dialog_gray)));
    paramTextView.setBackgroundDrawable(getContext().getResources().getDrawable(getSelBtn(this.mDialogType)));
  }
  
  private void startWithAnimation(boolean paramBoolean) {
    if (paramBoolean)
      this.mDialogView.startAnimation((Animation)this.mAnimIn); 
  }
  
  public void dismiss() { dismissWithAnimation(this.mIsShowAnim); }
  
  public TextView getContentTextView() { return this.mContentTv; }
  
  public int getDialogType() { return this.mDialogType; }
  
  public TextView getTitleTextView() { return this.mTitleTv; }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    initView();
    initListener();
  }
  
  protected void onStart() {
    super.onStart();
    startWithAnimation(this.mIsShowAnim);
  }
  
  public PromptDialog setAnimationEnable(boolean paramBoolean) {
    this.mIsShowAnim = paramBoolean;
    return this;
  }
  
  public PromptDialog setAnimationIn(AnimationSet paramAnimationSet) {
    this.mAnimIn = paramAnimationSet;
    return this;
  }
  
  public PromptDialog setAnimationOut(AnimationSet paramAnimationSet) {
    this.mAnimOut = paramAnimationSet;
    initAnimListener();
    return this;
  }
  
  public PromptDialog setContentText(int paramInt) { return setContentText(getContext().getString(paramInt)); }
  
  public PromptDialog setContentText(CharSequence paramCharSequence) {
    this.mContent = paramCharSequence;
    return this;
  }
  
  public PromptDialog setDialogType(int paramInt) {
    this.mDialogType = paramInt;
    return this;
  }
  
  public PromptDialog setPositiveListener(int paramInt, OnPositiveListener paramOnPositiveListener) { return setPositiveListener(getContext().getString(paramInt), paramOnPositiveListener); }
  
  public PromptDialog setPositiveListener(OnPositiveListener paramOnPositiveListener) {
    this.mOnPositiveListener = paramOnPositiveListener;
    return this;
  }
  
  public PromptDialog setPositiveListener(CharSequence paramCharSequence, OnPositiveListener paramOnPositiveListener) {
    this.mBtnText = paramCharSequence;
    return setPositiveListener(paramOnPositiveListener);
  }
  
  public PromptDialog setTitleText(int paramInt) { return setTitleText(getContext().getString(paramInt)); }
  
  public PromptDialog setTitleText(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    return this;
  }
  
  public static interface OnPositiveListener {
    void onClick(PromptDialog param1PromptDialog);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\cn\refactor\lib\colordialog\PromptDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
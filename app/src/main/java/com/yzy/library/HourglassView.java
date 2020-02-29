package com.yzy.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class HourglassView extends View {
  private final int DURATION_DEFAULT = 5000;
  
  private final float FALT_DEFAULT = 7.5F;
  
  private float bottomWave;
  
  private float centerX;
  
  private float centerY;
  
  private float defaultBottomHeight;
  
  private float height;
  
  private float heightHalf;
  
  private AnimatorSet mAnimatorSet = new AnimatorSet();
  
  private boolean mAuto;
  
  ObjectAnimator mBottomAnimator;
  
  private Context mContext;
  
  ObjectAnimator mDropAnimator = ObjectAnimator.ofFloat(this, "progressDrop", new float[] { 0.0F, 1.0F });
  
  private int mDropDuration = 1000;
  
  private Paint mDropPaint = new Paint(1);
  
  private int mDuration = 5200;
  
  private float mFalt;
  
  private Path mFillBottom = new Path();
  
  private Paint mFillPaint = new Paint(1);
  
  private Path mFillTop = new Path();
  
  private Paint mFramePaint = new Paint(1);
  
  private int mLeftAndRightColor = Color.parseColor("#844F01");
  
  private Path mLeftPath = new Path();
  
  private Path mMoveBottom = new Path();
  
  private Path mMoveTop = new Path();
  
  private Path mRightPath = new Path();
  
  private int mSandColor = Color.parseColor("#00B7EE");
  
  private Paint mSandPaint = new Paint(1);
  
  private OnStateListener mStateListener;
  
  private int mTopAndBottomColor = Color.parseColor("#EC6941");
  
  private Paint mTopAndBottomPaint = new Paint(1);
  
  ObjectAnimator mTopAnimator = ObjectAnimator.ofFloat(this, "progressTop", new float[] { 0.0F, 1.0F });
  
  ObjectAnimator mWaveBottomAnimator;
  
  ObjectAnimator mWaveTopAnimator;
  
  private float maxBottomHeight;
  
  private float progressBottom;
  
  private float progressDrop;
  
  private float progressTop;
  
  private float temp;
  
  private float topWave;
  
  private float width;
  
  public HourglassView(Context paramContext) { this(paramContext, null); }
  
  public HourglassView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public HourglassView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    init(paramAttributeSet);
  }
  
  private void calculationBottomMovePath() {
    this.mMoveBottom.reset();
    float f1 = this.centerY;
    float f2 = this.heightHalf;
    float f3 = this.defaultBottomHeight;
    f3 = f1 + f2 - f3 - (this.maxBottomHeight - f3) * this.progressBottom;
    this.mMoveBottom.moveTo(this.centerX - this.width / 2.0F + this.height / 15.0F, f1 + f2);
    this.mMoveBottom.lineTo(this.centerX - this.width / 2.0F + this.height / 15.0F, f3);
    Path path = this.mMoveBottom;
    f1 = this.centerX;
    path.quadTo(f1, this.bottomWave + f3, this.width / 2.0F + f1 - this.height / 15.0F, f3);
    this.mMoveBottom.lineTo(this.centerX + this.width / 2.0F - this.height / 15.0F, this.centerY + this.heightHalf);
  }
  
  private void calculationTopMovePath() { // Byte code:
    //   0: aload_0
    //   1: getfield mMoveTop : Landroid/graphics/Path;
    //   4: invokevirtual reset : ()V
    //   7: aload_0
    //   8: getfield temp : F
    //   11: fstore_1
    //   12: aload_0
    //   13: getfield progressBottom : F
    //   16: fstore_3
    //   17: fconst_1
    //   18: fstore_2
    //   19: fload_1
    //   20: fload_3
    //   21: fsub
    //   22: fconst_0
    //   23: fcmpl
    //   24: ifle -> 52
    //   27: aload_0
    //   28: getfield mDuration : I
    //   31: istore #5
    //   33: iload #5
    //   35: sipush #5000
    //   38: if_icmple -> 52
    //   41: iload #5
    //   43: sipush #5000
    //   46: idiv
    //   47: i2f
    //   48: fstore_1
    //   49: goto -> 54
    //   52: fconst_1
    //   53: fstore_1
    //   54: aload_0
    //   55: getfield progressTop : F
    //   58: fstore_3
    //   59: fload_3
    //   60: fload_1
    //   61: fmul
    //   62: fconst_1
    //   63: fcmpl
    //   64: ifle -> 72
    //   67: fload_2
    //   68: fstore_1
    //   69: goto -> 76
    //   72: fload_3
    //   73: fload_1
    //   74: fmul
    //   75: fstore_1
    //   76: aload_0
    //   77: getfield centerY : F
    //   80: fstore_3
    //   81: aload_0
    //   82: getfield heightHalf : F
    //   85: fstore #4
    //   87: aload_0
    //   88: getfield height : F
    //   91: fstore_2
    //   92: fload_3
    //   93: fload #4
    //   95: fsub
    //   96: fload_2
    //   97: ldc 20.0
    //   99: fdiv
    //   100: fadd
    //   101: fload #4
    //   103: fload_2
    //   104: ldc 20.0
    //   106: fdiv
    //   107: fsub
    //   108: fload_1
    //   109: fmul
    //   110: fadd
    //   111: fstore_1
    //   112: aload_0
    //   113: getfield mMoveTop : Landroid/graphics/Path;
    //   116: aload_0
    //   117: getfield centerX : F
    //   120: aload_0
    //   121: getfield width : F
    //   124: fconst_2
    //   125: fdiv
    //   126: fsub
    //   127: fload_2
    //   128: ldc 30.0
    //   130: fdiv
    //   131: fadd
    //   132: fload_1
    //   133: invokevirtual moveTo : (FF)V
    //   136: aload_0
    //   137: getfield mMoveTop : Landroid/graphics/Path;
    //   140: aload_0
    //   141: getfield centerX : F
    //   144: aload_0
    //   145: getfield width : F
    //   148: fconst_2
    //   149: fdiv
    //   150: fsub
    //   151: aload_0
    //   152: getfield height : F
    //   155: ldc 30.0
    //   157: fdiv
    //   158: fadd
    //   159: aload_0
    //   160: getfield centerY : F
    //   163: invokevirtual lineTo : (FF)V
    //   166: aload_0
    //   167: getfield mMoveTop : Landroid/graphics/Path;
    //   170: aload_0
    //   171: getfield centerX : F
    //   174: aload_0
    //   175: getfield width : F
    //   178: fconst_2
    //   179: fdiv
    //   180: fadd
    //   181: aload_0
    //   182: getfield height : F
    //   185: ldc 30.0
    //   187: fdiv
    //   188: fsub
    //   189: aload_0
    //   190: getfield centerY : F
    //   193: invokevirtual lineTo : (FF)V
    //   196: aload_0
    //   197: getfield mMoveTop : Landroid/graphics/Path;
    //   200: aload_0
    //   201: getfield centerX : F
    //   204: aload_0
    //   205: getfield width : F
    //   208: fconst_2
    //   209: fdiv
    //   210: fadd
    //   211: aload_0
    //   212: getfield height : F
    //   215: ldc 30.0
    //   217: fdiv
    //   218: fsub
    //   219: fload_1
    //   220: invokevirtual lineTo : (FF)V
    //   223: aload_0
    //   224: getfield mMoveTop : Landroid/graphics/Path;
    //   227: astore #6
    //   229: aload_0
    //   230: getfield centerX : F
    //   233: fstore_2
    //   234: aload #6
    //   236: fload_2
    //   237: aload_0
    //   238: getfield topWave : F
    //   241: fload_1
    //   242: fadd
    //   243: fload_2
    //   244: aload_0
    //   245: getfield width : F
    //   248: fconst_2
    //   249: fdiv
    //   250: fsub
    //   251: aload_0
    //   252: getfield height : F
    //   255: ldc 30.0
    //   257: fdiv
    //   258: fadd
    //   259: fload_1
    //   260: invokevirtual quadTo : (FFFF)V
    //   263: return }
  
  private float dp2px(float paramFloat) { return (int)(TypedValue.applyDimension(1, paramFloat, this.mContext.getResources().getDisplayMetrics()) + 0.5D); }
  
  private void drawDownSand(Canvas paramCanvas) {
    int i = paramCanvas.saveLayer(null, null, 31);
    paramCanvas.drawPath(this.mFillBottom, this.mFillPaint);
    this.mSandPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    calculationBottomMovePath();
    paramCanvas.drawPath(this.mMoveBottom, this.mSandPaint);
    this.mSandPaint.setXfermode(null);
    paramCanvas.restoreToCount(i);
  }
  
  private void drawDropLine(Canvas paramCanvas) {
    float f1;
    if (this.temp - this.progressBottom <= 0.0F) {
      f1 = this.centerY;
      float f10 = this.height;
      float f11 = f10 / 25.0F;
      float f12 = this.heightHalf;
      float f13 = this.defaultBottomHeight;
      float f14 = f10 / 25.0F;
      float f15 = this.progressDrop;
      float f16 = this.centerX;
      paramCanvas.drawLine(f16, f1 - f10 / 25.0F, f16, f1 - f11 + (f12 - f13 + f14) * f15, this.mDropPaint);
      this.temp = this.progressBottom;
      return;
    } 
    int i = this.mDuration;
    float f2 = 1.0F;
    if (i > 5000) {
      f1 = (i / 5000);
    } else {
      f1 = 1.0F;
    } 
    float f3 = this.progressBottom;
    if ((1.1F - f3) / 0.2F * f1 >= 1.0F) {
      f1 = f2;
    } else {
      f1 = (1.1F - f3) / 0.2F * f1;
    } 
    f2 = this.centerY;
    f3 = this.heightHalf;
    float f4 = this.maxBottomHeight;
    float f8 = this.height;
    float f5 = f8 / 5.0F;
    float f6 = f8 / 25.0F;
    float f7 = f8 / 25.0F;
    f8 /= 5.0F;
    float f9 = this.centerX;
    paramCanvas.drawLine(f9, f2 - f6 + (f3 - f4 + f7 + f8) * f1, f9, f2 + f3 - f4 + f5, this.mDropPaint);
    this.temp = this.progressBottom;
  }
  
  private void drawFrame(Canvas paramCanvas) {
    paramCanvas.drawPath(this.mLeftPath, this.mFramePaint);
    paramCanvas.drawPath(this.mRightPath, this.mFramePaint);
  }
  
  private void drawTopSand(Canvas paramCanvas) {
    int i = paramCanvas.saveLayer(null, null, 31);
    paramCanvas.drawPath(this.mFillTop, this.mFillPaint);
    this.mSandPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    calculationTopMovePath();
    paramCanvas.drawPath(this.mMoveTop, this.mSandPaint);
    this.mSandPaint.setXfermode(null);
    paramCanvas.restoreToCount(i);
  }
  
  private void drwaTopAndBottom(Canvas paramCanvas) {
    float f1 = this.centerX;
    float f2 = this.width;
    float f3 = f2 / 2.0F;
    float f4 = this.centerY;
    float f5 = this.heightHalf;
    float f6 = this.height;
    paramCanvas.drawLine(f1 - f3, f4 - f5 - f6 / 30.0F, f1 + f2 / 2.0F, f4 - f5 - f6 / 30.0F, this.mTopAndBottomPaint);
    f1 = this.centerX;
    f2 = this.width;
    f3 = f2 / 2.0F;
    f4 = this.centerY;
    f5 = this.heightHalf;
    f6 = this.height;
    paramCanvas.drawLine(f1 - f3, f4 + f5 + f6 / 30.0F, f1 + f2 / 2.0F, f4 + f5 + f6 / 30.0F, this.mTopAndBottomPaint);
  }
  
  private void init(AttributeSet paramAttributeSet) {
    TypedArray typedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HourglassView);
    this.mTopAndBottomColor = typedArray.getColor(R.styleable.HourglassView_hv_topAndBottom_color, this.mTopAndBottomColor);
    this.mLeftAndRightColor = typedArray.getColor(R.styleable.HourglassView_hv_leftAndRight_color, this.mLeftAndRightColor);
    this.mSandColor = typedArray.getColor(R.styleable.HourglassView_hv_sand_color, this.mSandColor);
    this.mDuration = typedArray.getInt(R.styleable.HourglassView_hv_duration, 5000);
    this.mFalt = typedArray.getFloat(R.styleable.HourglassView_hv_flat, 7.5F);
    this.mAuto = typedArray.getBoolean(R.styleable.HourglassView_hv_auto, false);
    if (this.mFalt <= 0.0F)
      this.mFalt = 7.5F; 
    if (this.mDuration <= 0)
      this.mDuration = 5000; 
    typedArray.recycle();
    this.mTopAndBottomPaint.setColor(this.mTopAndBottomColor);
    this.mTopAndBottomPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mTopAndBottomPaint.setStyle(Paint.Style.STROKE);
    this.mFillPaint.setColor(Color.parseColor("#ffffff"));
    this.mFillPaint.setStyle(Paint.Style.FILL);
    this.mFramePaint.setStyle(Paint.Style.STROKE);
    this.mFramePaint.setColor(this.mLeftAndRightColor);
    this.mSandPaint.setColor(this.mSandColor);
    this.mSandPaint.setStyle(Paint.Style.FILL);
    this.mDropPaint.setColor(this.mSandColor);
    this.mDropPaint.setAlpha(178);
    this.mDropPaint.setStyle(Paint.Style.STROKE);
  }
  
  private void initAnimator() {
    float f = this.height;
    this.mWaveBottomAnimator = ObjectAnimator.ofFloat(this, "bottomWave", new float[] { 0.0F, -f / 7.5F, f / 10.0F, -f / 20.0F });
    this.mWaveTopAnimator = ObjectAnimator.ofFloat(this, "topWave", new float[] { 0.0F, this.height / 20.0F, 0.0F });
    ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[] { PropertyValuesHolder.ofKeyframe("progressBottom", new Keyframe[] { Keyframe.ofFloat(0.0F, 0.0F), Keyframe.ofFloat(0.85F, 1.1F), Keyframe.ofFloat(1.0F, 0.9F) }) });
    this.mBottomAnimator = objectAnimator;
    objectAnimator.setDuration((this.mDuration - this.mDropDuration));
    this.mTopAnimator.setDuration((this.mDuration - this.mDropDuration));
    this.mWaveBottomAnimator.setDuration((this.mDuration - this.mDropDuration));
    this.mWaveTopAnimator.setDuration((this.mDuration - this.mDropDuration));
    this.mDropAnimator.setDuration(this.mDropDuration);
    this.mDropAnimator.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
    this.mAnimatorSet.play((Animator)this.mTopAnimator).with((Animator)this.mWaveBottomAnimator).with((Animator)this.mBottomAnimator).with((Animator)this.mWaveTopAnimator).after((Animator)this.mDropAnimator);
    this.mAnimatorSet.addListener(new Animator.AnimatorListener() {
          public void onAnimationCancel(Animator param1Animator) {}
          
          public void onAnimationEnd(Animator param1Animator) {
            HourglassView.this.mDropPaint.setColor(0);
            if (HourglassView.this.mStateListener != null)
              HourglassView.this.mStateListener.onEnd(); 
          }
          
          public void onAnimationRepeat(Animator param1Animator) {}
          
          public void onAnimationStart(Animator param1Animator) {
            HourglassView.this.mDropPaint.setColor(HourglassView.this.mSandColor);
            if (HourglassView.this.mStateListener != null)
              HourglassView.this.mStateListener.onStart(); 
          }
        });
    if (this.mAuto)
      start(); 
  }
  
  private void initSize() {
    float f = this.height;
    this.heightHalf = f / 2.0F;
    this.defaultBottomHeight = 0.0F;
    this.mTopAndBottomPaint.setStrokeWidth(f / 15.0F);
    this.mFramePaint.setStrokeWidth(this.height / 60.0F);
    this.mDropPaint.setStrokeWidth(this.height / 75.0F);
    this.mDropDuration = (int)(this.height * 800.0F / dp2px(100.0F));
    initAnimator();
  }
  
  private void setBottomWave(float paramFloat) { this.bottomWave = paramFloat; }
  
  private void setProgressBottom(float paramFloat) { this.progressBottom = paramFloat; }
  
  private void setProgressDrop(float paramFloat) {
    this.progressDrop = paramFloat;
    invalidate();
  }
  
  private void setProgressTop(float paramFloat) {
    this.progressTop = paramFloat;
    invalidate();
  }
  
  private void setTopWave(float paramFloat) { this.topWave = paramFloat; }
  
  public void end() {
    AnimatorSet animatorSet = this.mAnimatorSet;
    if (animatorSet != null)
      animatorSet.end(); 
  }
  
  public boolean isStart() {
    AnimatorSet animatorSet = this.mAnimatorSet;
    return (animatorSet != null) ? animatorSet.isStarted() : false;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mAnimatorSet.end();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    drawFrame(paramCanvas);
    drawTopSand(paramCanvas);
    drawDownSand(paramCanvas);
    drawDropLine(paramCanvas);
    drwaTopAndBottom(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    paramInt1 = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    if (paramInt1 != Integer.MIN_VALUE && paramInt1 != 0) {
      if (paramInt1 != 1073741824)
        return; 
      float f = paramInt2;
      f -= f / 7.5F;
      this.height = f;
      f = f / 2.0F + f / this.mFalt;
      this.width = f;
      setMeasuredDimension((int)(f + (paramInt2 / 15)) + getPaddingLeft() + getPaddingRight(), paramInt2 + getPaddingTop() + getPaddingBottom());
      return;
    } 
    float f1 = dp2px(100.0F) - dp2px(100.0F) / 7.5F;
    this.height = f1;
    float f2 = f1 / 2.0F + f1 / this.mFalt;
    this.width = f2;
    setMeasuredDimension((int)(f2 + f1 / 15.0F) + getPaddingLeft() + getPaddingRight(), (int)(this.height + dp2px(100.0F) / 7.5F) + getPaddingTop() + getPaddingBottom());
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    initSize();
    float f1 = (paramInt1 / 2);
    this.centerX = f1;
    float f2 = (paramInt2 / 2);
    this.centerY = f2;
    float f3 = this.heightHalf;
    float f4 = this.height;
    this.maxBottomHeight = f3 - f4 / 7.5F;
    this.mLeftPath.moveTo(f1 - this.width / 2.0F + f4 / 30.0F, f2 - f3);
    Path path = this.mLeftPath;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.centerY;
    f4 = this.heightHalf / 3.0F;
    float f5 = this.height;
    path.quadTo(f1 - f2, f3 - f4, f1 - f5 / 20.0F, f3 - f5 / 20.0F);
    path = this.mLeftPath;
    f1 = this.centerX;
    f2 = this.centerY;
    f3 = this.height;
    path.quadTo(f1, f2, f1 - f3 / 20.0F, f3 / 20.0F + f2);
    path = this.mLeftPath;
    f1 = this.centerX;
    f2 = this.width;
    f3 = f2 / 2.0F;
    f4 = this.centerY;
    f5 = this.heightHalf;
    path.quadTo(f1 - f3, f5 / 3.0F + f4, f1 - f2 / 2.0F + this.height / 30.0F, f4 + f5);
    this.mRightPath.moveTo(this.centerX + this.width / 2.0F - this.height / 30.0F, this.centerY - this.heightHalf);
    path = this.mRightPath;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.centerY;
    f4 = this.heightHalf / 3.0F;
    f5 = this.height;
    path.quadTo(f2 + f1, f3 - f4, f1 + f5 / 20.0F, f3 - f5 / 20.0F);
    path = this.mRightPath;
    f1 = this.centerX;
    f2 = this.centerY;
    f3 = this.height;
    path.quadTo(f1, f2, f3 / 20.0F + f1, f3 / 20.0F + f2);
    path = this.mRightPath;
    f1 = this.centerX;
    f2 = this.width;
    f3 = f2 / 2.0F;
    f4 = this.centerY;
    f5 = this.heightHalf;
    path.quadTo(f3 + f1, f5 / 3.0F + f4, f1 + f2 / 2.0F - this.height / 30.0F, f4 + f5);
    path = this.mFillTop;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.height;
    path.moveTo(f1 - f2 + f3 / 15.0F, this.centerY - this.heightHalf - f3 / 20.0F);
    path = this.mFillTop;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.height;
    f4 = f3 / 30.0F;
    f5 = this.centerY;
    path.quadTo(f1 - f2 + f4, f5 - this.heightHalf / 3.0F - f3 / 30.0F, f1 - f3 / 20.0F, f5 - f3 / 12.0F);
    this.mFillTop.lineTo(this.centerX, this.centerY - this.height / 30.0F);
    path = this.mFillTop;
    f1 = this.centerX;
    f2 = this.height;
    path.lineTo(f1 + f2 / 20.0F, this.centerY - f2 / 12.0F);
    path = this.mFillTop;
    f1 = this.centerX;
    f2 = this.width;
    f3 = f2 / 2.0F;
    f4 = this.height;
    f5 = f4 / 30.0F;
    float f6 = this.centerY;
    float f7 = this.heightHalf;
    path.quadTo(f3 + f1 - f5, f6 - f7 / 3.0F - f4 / 30.0F, f1 + f2 / 2.0F - f4 / 15.0F, f6 - f7 - f4 / 20.0F);
    path = this.mFillBottom;
    f1 = this.centerX;
    f2 = this.height;
    path.moveTo(f1 - f2 / 20.0F, this.centerY + f2 / 12.0F);
    path = this.mFillBottom;
    f1 = this.centerX;
    f2 = this.width;
    f3 = f2 / 2.0F;
    f4 = this.height;
    f5 = f4 / 30.0F;
    f6 = this.centerY;
    f7 = this.heightHalf;
    path.quadTo(f1 - f3 + f5, f7 / 3.0F + f6 + f4 / 30.0F, f1 - f2 / 2.0F + f4 / 15.0F, f6 + f7 + f4 / 30.0F);
    path = this.mFillBottom;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.height;
    path.lineTo(f1 + f2 - f3 / 15.0F, this.centerY + this.heightHalf + f3 / 30.0F);
    path = this.mFillBottom;
    f1 = this.centerX;
    f2 = this.width / 2.0F;
    f3 = this.height;
    f4 = f3 / 30.0F;
    f5 = this.centerY;
    path.quadTo(f2 + f1 - f4, this.heightHalf / 3.0F + f5 + f3 / 30.0F, f1 + f3 / 20.0F, f5 + f3 / 12.0F);
    this.mMoveBottom.moveTo(this.centerX - this.width / 2.0F + this.height / 15.0F, this.centerY + this.heightHalf);
    this.mMoveBottom.lineTo(this.centerX - this.width / 2.0F + this.height / 15.0F, this.centerY + this.heightHalf - this.defaultBottomHeight);
    this.mMoveBottom.lineTo(this.centerX + this.width / 2.0F - this.height / 15.0F, this.centerY + this.heightHalf - this.defaultBottomHeight);
    this.mMoveBottom.lineTo(this.centerX + this.width / 2.0F - this.height / 15.0F, this.centerY + this.heightHalf);
  }
  
  public void pause() {
    if (Build.VERSION.SDK_INT >= 19)
      this.mAnimatorSet.pause(); 
  }
  
  public void reset() {
    AnimatorSet animatorSet = this.mAnimatorSet;
    if (animatorSet != null && animatorSet.isStarted())
      this.mAnimatorSet.cancel(); 
    this.progressTop = 0.0F;
    this.progressBottom = 0.0F;
    this.progressDrop = 0.0F;
    this.bottomWave = 0.0F;
    this.topWave = 0.0F;
    this.temp = 0.0F;
    invalidate();
  }
  
  public void resume() {
    if (Build.VERSION.SDK_INT >= 19)
      this.mAnimatorSet.resume(); 
  }
  
  public void setDuration(int paramInt) {
    this.mDuration = paramInt;
    if (paramInt <= 0)
      this.mDuration = 5000; 
  }
  
  public void setFalt(float paramFloat) {
    this.mFalt = paramFloat;
    if (paramFloat <= 0.0F)
      this.mFalt = 7.5F; 
    invalidate();
  }
  
  public void setLeftAndRightColor(int paramInt) {
    this.mLeftAndRightColor = paramInt;
    this.mFramePaint.setColor(paramInt);
    invalidate();
  }
  
  public void setSandColor(int paramInt) {
    this.mSandColor = paramInt;
    this.mSandPaint.setColor(paramInt);
    this.mDropPaint.setColor(this.mSandColor);
    invalidate();
  }
  
  public void setStateListener(OnStateListener paramOnStateListener) { this.mStateListener = paramOnStateListener; }
  
  public void setTopAndBottomColor(int paramInt) {
    this.mTopAndBottomColor = paramInt;
    this.mTopAndBottomPaint.setColor(paramInt);
    invalidate();
  }
  
  public void start() {
    if (this.mAnimatorSet != null)
      post(new Runnable() {
            public void run() {
              if (!HourglassView.this.mAnimatorSet.isStarted()) {
                if (0.0F < HourglassView.this.progressBottom)
                  HourglassView.this.reset(); 
                HourglassView.this.mAnimatorSet.start();
              } 
            }
          }); 
  }
  
  public static interface OnStateListener {
    void onEnd();
    
    void onStart();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\yzy\library\HourglassView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
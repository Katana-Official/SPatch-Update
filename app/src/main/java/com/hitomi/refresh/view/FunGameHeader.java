package com.hitomi.refresh.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sk.spatch.R;

public class FunGameHeader extends FrameLayout {
  private int bottomMaskTextSize = 16;
  
  private TextView bottomMaskView;
  
  private String bottomMaskViewText = "Scrooll to move handle";
  
  private RelativeLayout curtainReLayout;
  
  private FunGameView funGameView;
  
  private String gameOverText = "Game Over";
  
  private int halfHitBlockHeight;
  
  private int headerType;
  
  private boolean isStart = false;
  
  private String loadingFinishedText = "Loading Finished";
  
  private String loadingText = "Loading...";
  
  private Context mContext;
  
  private RelativeLayout maskReLayout;
  
  private int topMaskTextSize = 16;
  
  private TextView topMaskView;
  
  private String topMaskViewText = "Pull To Break Out!";
  
  public FunGameHeader(Context paramContext) { this(paramContext, null); }
  
  public FunGameHeader(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public FunGameHeader(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FunGameHeader);
    this.headerType = typedArray.getInt(1, 0);
    String str2 = typedArray.getString(3);
    String str1 = str2;
    if (str2 == null)
      str1 = this.topMaskViewText; 
    this.topMaskViewText = str1;
    str2 = typedArray.getString(2);
    str1 = str2;
    if (str2 == null)
      str1 = this.bottomMaskViewText; 
    this.bottomMaskViewText = str1;
    str2 = typedArray.getString(5);
    str1 = str2;
    if (str2 == null)
      str1 = this.loadingText; 
    this.loadingText = str1;
    str2 = typedArray.getString(6);
    str1 = str2;
    if (str2 == null)
      str1 = this.loadingFinishedText; 
    this.loadingFinishedText = str1;
    str2 = typedArray.getString(4);
    str1 = str2;
    if (str2 == null)
      str1 = this.gameOverText; 
    this.gameOverText = str1;
    this.topMaskTextSize = typedArray.getInt(7, this.topMaskTextSize);
    this.bottomMaskTextSize = typedArray.getInt(0, this.bottomMaskTextSize);
    typedArray.recycle();
    initView(paramAttributeSet);
  }
  
  private void coverMaskView() {
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    layoutParams.topMargin = 1;
    layoutParams.bottomMargin = 1;
    addView((View)this.maskReLayout, (ViewGroup.LayoutParams)layoutParams);
    addView((View)this.curtainReLayout, (ViewGroup.LayoutParams)layoutParams);
  }
  
  private TextView createMaskTextView(String paramString, int paramInt1, int paramInt2) {
    TextView textView = new TextView(this.mContext);
    textView.setTextColor(-16777216);
    textView.setBackgroundColor(-1);
    textView.setGravity(paramInt2 | true);
    textView.setTextSize(paramInt1);
    textView.setText(paramString);
    return textView;
  }
  
  private void doStart(long paramLong) {
    TextView textView1 = this.topMaskView;
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textView1, "translationY", new float[] { textView1.getTranslationY(), -this.halfHitBlockHeight });
    TextView textView2 = this.bottomMaskView;
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textView2, "translationY", new float[] { textView2.getTranslationY(), this.halfHitBlockHeight });
    RelativeLayout relativeLayout = this.maskReLayout;
    ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(relativeLayout, "alpha", new float[] { relativeLayout.getAlpha(), 0.0F });
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.play((Animator)objectAnimator1).with((Animator)objectAnimator2).with((Animator)objectAnimator3);
    animatorSet.setDuration(800L);
    animatorSet.setStartDelay(paramLong);
    animatorSet.start();
    animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            FunGameHeader.this.topMaskView.setVisibility(8);
            FunGameHeader.this.bottomMaskView.setVisibility(8);
            FunGameHeader.this.maskReLayout.setVisibility(8);
            FunGameHeader.this.funGameView.postStatus(1);
          }
        });
  }
  
  private void initView(AttributeSet paramAttributeSet) {
    this.funGameView = FunGameFactory.createFunGameView(this.mContext, paramAttributeSet, this.headerType);
    setHeaderLodingStr(this.loadingText);
    setHeaderLoadingFinishedStr(this.loadingFinishedText);
    setHeaderGameOverStr(this.gameOverText);
    this.funGameView.postStatus(0);
    addView(this.funGameView);
    this.curtainReLayout = new RelativeLayout(this.mContext);
    RelativeLayout relativeLayout = new RelativeLayout(this.mContext);
    this.maskReLayout = relativeLayout;
    relativeLayout.setBackgroundColor(Color.parseColor("#3A3A3A"));
    this.topMaskView = createMaskTextView(this.topMaskViewText, this.topMaskTextSize, 80);
    this.bottomMaskView = createMaskTextView(this.bottomMaskViewText, this.bottomMaskTextSize, 48);
    coverMaskView();
    this.funGameView.getViewTreeObserver().addOnGlobalLayoutListener(new MeasureListener());
  }
  
  public void back2StartPoint(long paramLong) { this.funGameView.moveController2StartPoint(paramLong); }
  
  public int getGameStatus() { return this.funGameView.getCurrStatus(); }
  
  public void moveRacket(float paramFloat) {
    if (this.isStart)
      this.funGameView.moveController(paramFloat); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int m = View.MeasureSpec.getMode(paramInt2);
    int n = getChildCount();
    int j = 0;
    int k = 0;
    int i = k;
    while (j < n) {
      View view = getChildAt(j);
      measureChild(view, paramInt1, paramInt2);
      if (view instanceof FunGameView) {
        k = view.getMeasuredWidth();
        i = view.getMeasuredHeight();
      } 
      j++;
    } 
    if (m == 0 || m == 1073741824) {
      paramInt1 = View.MeasureSpec.makeMeasureSpec(k, 1073741824);
      paramInt2 = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    } 
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void postComplete() { this.funGameView.postStatus(3); }
  
  public void postEnd() {
    this.isStart = false;
    this.funGameView.postStatus(0);
    TextView textView = this.topMaskView;
    textView.setTranslationY(textView.getTranslationY() + this.halfHitBlockHeight);
    textView = this.bottomMaskView;
    textView.setTranslationY(textView.getTranslationY() - this.halfHitBlockHeight);
    this.maskReLayout.setAlpha(1.0F);
    this.topMaskView.setVisibility(0);
    this.bottomMaskView.setVisibility(0);
    this.maskReLayout.setVisibility(0);
  }
  
  public void postStart() {
    if (!this.isStart) {
      doStart(200L);
      this.isStart = true;
    } 
  }
  
  public void setBottomMaskViewText(String paramString) {
    this.bottomMaskViewText = paramString;
    this.bottomMaskView.setText(paramString);
  }
  
  public void setHeaderGameOverStr(String paramString) { this.funGameView.setTextGameOver(paramString); }
  
  public void setHeaderLoadingFinishedStr(String paramString) { this.funGameView.setTextLoadingFinished(paramString); }
  
  public void setHeaderLodingStr(String paramString) { this.funGameView.setTextLoading(paramString); }
  
  public void setTopMaskViewText(String paramString) {
    this.topMaskViewText = paramString;
    this.topMaskView.setText(paramString);
  }
  
  private class MeasureListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private MeasureListener() {}
    
    public void onGlobalLayout() {
      FunGameHeader funGameHeader = FunGameHeader.this;
      FunGameHeader.access$102(funGameHeader, (int)((funGameHeader.funGameView.getHeight() - 2.0F) * 0.5F));
      RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-1, FunGameHeader.this.halfHitBlockHeight);
      RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, FunGameHeader.this.halfHitBlockHeight);
      layoutParams2.topMargin = FunGameHeader.this.halfHitBlockHeight;
      FunGameHeader.this.curtainReLayout.removeAllViews();
      FunGameHeader.this.curtainReLayout.addView((View)FunGameHeader.this.topMaskView, 0, (ViewGroup.LayoutParams)layoutParams1);
      FunGameHeader.this.curtainReLayout.addView((View)FunGameHeader.this.bottomMaskView, 1, (ViewGroup.LayoutParams)layoutParams2);
      FunGameHeader.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\FunGameHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
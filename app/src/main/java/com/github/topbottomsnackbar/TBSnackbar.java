package com.github.topbottomsnackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class TBSnackbar {
  static final int ANIMATION_DURATION = 250;
  
  static final int ANIMATION_FADE_DURATION = 180;
  
  public static final int LENGTH_INDEFINITE = -2;
  
  public static final int LENGTH_LONG = 0;
  
  public static final int LENGTH_SHORT = -1;
  
  static final int MSG_DISMISS = 1;
  
  static final int MSG_SHOW = 0;
  
  public static final int STYLE_SHOW_BOTTOM = 2;
  
  public static final int STYLE_SHOW_TOP = 1;
  
  public static final int STYLE_SHOW_TOP_FITSYSTEMWINDOW = 3;
  
  static final Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          int i = param1Message.what;
          if (i != 0) {
            if (i != 1)
              return false; 
            ((TBSnackbar)param1Message.obj).hideView(param1Message.arg1);
            return true;
          } 
          ((TBSnackbar)param1Message.obj).showView();
          return true;
        }
      });
  
  private final AccessibilityManager mAccessibilityManager;
  
  private Callback mCallback;
  
  private final Context mContext;
  
  private int mDuration;
  
  final TBSnackbarManager.Callback mManagerCallback = new TBSnackbarManager.Callback() {
      public void dismiss(int param1Int) { TBSnackbar.sHandler.sendMessage(TBSnackbar.sHandler.obtainMessage(1, param1Int, 0, TBSnackbar.this)); }
      
      public void show() { TBSnackbar.sHandler.sendMessage(TBSnackbar.sHandler.obtainMessage(0, TBSnackbar.this)); }
    };
  
  private int mStyle = 2;
  
  private final ViewGroup mTargetParent;
  
  final SnackbarLayout mView;
  
  private TBSnackbar(ViewGroup paramViewGroup) {
    this.mTargetParent = paramViewGroup;
    Context context = paramViewGroup.getContext();
    this.mContext = context;
    this.mView = (SnackbarLayout)LayoutInflater.from(context).inflate(R.layout.design_layout_snackbar, this.mTargetParent, false);
    this.mAccessibilityManager = (AccessibilityManager)this.mContext.getSystemService("accessibility");
  }
  
  @Deprecated
  private TBSnackbar addIcon(int paramInt1, int paramInt2) {
    this.mView.getMessageView().setCompoundDrawablesWithIntrinsicBounds((Drawable)new BitmapDrawable(Bitmap.createScaledBitmap(((BitmapDrawable)this.mContext.getResources().getDrawable(paramInt1)).getBitmap(), paramInt2, paramInt2, true)), null, null, null);
    return this;
  }
  
  private void animateViewOut(int paramInt) {
    if (Build.VERSION.SDK_INT >= 14) {
      if (this.mStyle == 2) {
        out(paramInt, this.mView.getHeight());
        return;
      } 
      out(paramInt, -this.mView.getHeight());
      return;
    } 
    if (this.mStyle == 2) {
      out2(paramInt, R.anim.design_snackbar_out);
      return;
    } 
    out2(paramInt, R.anim.top_out);
  }
  
  private void changeLayoutParams(int paramInt) {
    Object object = this.mTargetParent;
    if (object instanceof CoordinatorLayout) {
      object = this.mView.getLayoutParams();
      object.gravity = paramInt;
      this.mView.setLayoutParams((ViewGroup.LayoutParams)object);
      return;
    } 
    if (object instanceof android.widget.FrameLayout) {
      object = this.mView.getLayoutParams();
      object.gravity = paramInt;
      this.mView.setLayoutParams((ViewGroup.LayoutParams)object);
    } 
  }
  
  private static float convertDpToPixel(float paramFloat, Context paramContext) { return paramFloat * (paramContext.getResources().getDisplayMetrics()).densityDpi / 160.0F; }
  
  private static ViewGroup findSuitableParent(View paramView) {
    ViewGroup viewGroup1;
    ViewParent viewParent;
    ViewGroup viewGroup2 = null;
    View view = paramView;
    do {
      if (view instanceof CoordinatorLayout)
        return (ViewGroup)view; 
      viewGroup1 = viewGroup2;
      if (view instanceof android.widget.FrameLayout) {
        if (view.getId() == 16908290)
          return (ViewGroup)view; 
        viewGroup1 = (ViewGroup)view;
      } 
      paramView = view;
      if (view != null) {
        viewParent = view.getParent();
        if (viewParent instanceof View) {
          View view1 = (View)viewParent;
        } else {
          viewParent = null;
        } 
      } 
      viewGroup2 = viewGroup1;
      ViewParent viewParent1 = viewParent;
    } while (viewParent != null);
    return viewGroup1;
  }
  
  private Drawable fitDrawable(Drawable paramDrawable, int paramInt) { // Byte code:
    //   0: aload_1
    //   1: invokevirtual getIntrinsicWidth : ()I
    //   4: iload_2
    //   5: if_icmpne -> 18
    //   8: aload_1
    //   9: astore_3
    //   10: aload_1
    //   11: invokevirtual getIntrinsicHeight : ()I
    //   14: iload_2
    //   15: if_icmpeq -> 52
    //   18: aload_1
    //   19: astore_3
    //   20: aload_1
    //   21: instanceof android/graphics/drawable/BitmapDrawable
    //   24: ifeq -> 52
    //   27: new android/graphics/drawable/BitmapDrawable
    //   30: dup
    //   31: aload_0
    //   32: getfield mContext : Landroid/content/Context;
    //   35: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   38: aload_1
    //   39: invokestatic getBitmap : (Landroid/graphics/drawable/Drawable;)Landroid/graphics/Bitmap;
    //   42: iload_2
    //   43: iload_2
    //   44: iconst_1
    //   45: invokestatic createScaledBitmap : (Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
    //   48: invokespecial <init> : (Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V
    //   51: astore_3
    //   52: aload_3
    //   53: iconst_0
    //   54: iconst_0
    //   55: iload_2
    //   56: iload_2
    //   57: invokevirtual setBounds : (IIII)V
    //   60: aload_3
    //   61: areturn }
  
  private static Bitmap getBitmap(Drawable paramDrawable) {
    if (paramDrawable instanceof BitmapDrawable)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    if (paramDrawable instanceof VectorDrawable)
      return getBitmap((VectorDrawable)paramDrawable); 
    throw new IllegalArgumentException("unsupported drawable type");
  }
  
  private static Bitmap getBitmap(VectorDrawable paramVectorDrawable) {
    Bitmap bitmap = Bitmap.createBitmap(paramVectorDrawable.getIntrinsicWidth(), paramVectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    paramVectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    paramVectorDrawable.draw(canvas);
    return bitmap;
  }
  
  private static int getStatusBarHeight(Context paramContext) {
    int i = paramContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return (i > 0) ? paramContext.getResources().getDimensionPixelOffset(i) : 0;
  }
  
  private void in(int paramInt) {
    ViewCompat.setTranslationY((View)this.mView, paramInt);
    ViewCompat.animate((View)this.mView).translationY(0.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener((ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
          public void onAnimationEnd(View param1View) { TBSnackbar.this.onViewShown(); }
          
          public void onAnimationStart(View param1View) { TBSnackbar.this.mView.animateChildrenIn(70, 180); }
        }).start();
  }
  
  private void in2(int paramInt) {
    Animation animation = AnimationUtils.loadAnimation(this.mView.getContext(), paramInt);
    animation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    animation.setDuration(250L);
    animation.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) { TBSnackbar.this.onViewShown(); }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {}
        });
    this.mView.startAnimation(animation);
  }
  
  public static TBSnackbar make(View paramView, int paramInt1, int paramInt2, int paramInt3) { return make(paramView, paramView.getResources().getText(paramInt1), paramInt2, paramInt3); }
  
  public static TBSnackbar make(View paramView, CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    TBSnackbar tBSnackbar = new TBSnackbar(findSuitableParent(paramView));
    tBSnackbar.mStyle = paramInt2;
    tBSnackbar.setText(paramCharSequence);
    tBSnackbar.setDuration(paramInt1);
    return tBSnackbar;
  }
  
  private void out(final int event, int paramInt2) { ViewCompat.animate((View)this.mView).translationY(paramInt2).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener((ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
          public void onAnimationEnd(View param1View) { TBSnackbar.this.onViewHidden(event); }
          
          public void onAnimationStart(View param1View) { TBSnackbar.this.mView.animateChildrenOut(0, 180); }
        }).start(); }
  
  private void out2(final int event, int paramInt2) {
    Animation animation = AnimationUtils.loadAnimation(this.mView.getContext(), paramInt2);
    animation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    animation.setDuration(250L);
    animation.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) { TBSnackbar.this.onViewHidden(event); }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {}
        });
    this.mView.startAnimation(animation);
  }
  
  void animateViewIn() {
    if (Build.VERSION.SDK_INT >= 14) {
      if (this.mStyle == 2) {
        in(this.mView.getHeight());
        return;
      } 
      in(-this.mView.getHeight());
      return;
    } 
    if (this.mStyle == 2) {
      in2(R.anim.design_snackbar_in);
      return;
    } 
    in2(R.anim.top_in);
  }
  
  public void dismiss() { dispatchDismiss(3); }
  
  void dispatchDismiss(int paramInt) { TBSnackbarManager.getInstance().dismiss(this.mManagerCallback, paramInt); }
  
  public int getDuration() { return this.mDuration; }
  
  public View getView() { return (View)this.mView; }
  
  final void hideView(int paramInt) {
    if (shouldAnimate() && this.mView.getVisibility() == 0) {
      animateViewOut(paramInt);
      return;
    } 
    onViewHidden(paramInt);
  }
  
  public boolean isShown() { return TBSnackbarManager.getInstance().isCurrent(this.mManagerCallback); }
  
  public boolean isShownOrQueued() { return TBSnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback); }
  
  void onViewHidden(int paramInt) {
    TBSnackbarManager.getInstance().onDismissed(this.mManagerCallback);
    Callback callback = this.mCallback;
    if (callback != null)
      callback.onDismissed(this, paramInt); 
    if (Build.VERSION.SDK_INT < 11)
      this.mView.setVisibility(8); 
    ViewParent viewParent = this.mView.getParent();
    if (viewParent instanceof ViewGroup)
      ((ViewGroup)viewParent).removeView((View)this.mView); 
  }
  
  void onViewShown() {
    TBSnackbarManager.getInstance().onShown(this.mManagerCallback);
    Callback callback = this.mCallback;
    if (callback != null)
      callback.onShown(this); 
  }
  
  public TBSnackbar setAction(int paramInt, View.OnClickListener paramOnClickListener) { return setAction(this.mContext.getText(paramInt), paramOnClickListener); }
  
  public TBSnackbar setAction(CharSequence paramCharSequence, final View.OnClickListener listener) {
    Button button = this.mView.getActionView();
    if (TextUtils.isEmpty(paramCharSequence) || listener == null) {
      button.setVisibility(8);
      button.setOnClickListener(null);
      return this;
    } 
    button.setVisibility(0);
    button.setText(paramCharSequence);
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            listener.onClick(param1View);
            TBSnackbar.this.dispatchDismiss(1);
          }
        });
    return this;
  }
  
  public TBSnackbar setActionTextColor(int paramInt) {
    this.mView.getActionView().setTextColor(paramInt);
    return this;
  }
  
  public TBSnackbar setActionTextColor(ColorStateList paramColorStateList) {
    this.mView.getActionView().setTextColor(paramColorStateList);
    return this;
  }
  
  public TBSnackbar setCallback(Callback paramCallback) {
    this.mCallback = paramCallback;
    return this;
  }
  
  public TBSnackbar setDuration(int paramInt) {
    this.mDuration = paramInt;
    return this;
  }
  
  public TBSnackbar setIconLeft(int paramInt, float paramFloat) {
    TextView textView = this.mView.getMessageView();
    Drawable drawable = ContextCompat.getDrawable(this.mContext, paramInt);
    if (drawable != null) {
      drawable = fitDrawable(drawable, (int)convertDpToPixel(paramFloat, this.mContext));
      Drawable[] arrayOfDrawable = textView.getCompoundDrawables();
      textView.setCompoundDrawables(drawable, arrayOfDrawable[1], arrayOfDrawable[2], arrayOfDrawable[3]);
      return this;
    } 
    throw new IllegalArgumentException("resource_id is not a valid drawable!");
  }
  
  public TBSnackbar setIconPadding(int paramInt) {
    this.mView.getMessageView().setCompoundDrawablePadding(paramInt);
    return this;
  }
  
  public TBSnackbar setIconRight(int paramInt, float paramFloat) {
    TextView textView = this.mView.getMessageView();
    Drawable drawable = ContextCompat.getDrawable(this.mContext, paramInt);
    if (drawable != null) {
      drawable = fitDrawable(drawable, (int)convertDpToPixel(paramFloat, this.mContext));
      Drawable[] arrayOfDrawable = textView.getCompoundDrawables();
      textView.setCompoundDrawables(arrayOfDrawable[0], arrayOfDrawable[1], drawable, arrayOfDrawable[3]);
      return this;
    } 
    throw new IllegalArgumentException("resource_id is not a valid drawable!");
  }
  
  public TBSnackbar setMaxWidth(int paramInt) {
    SnackbarLayout.access$002(this.mView, paramInt);
    return this;
  }
  
  public TBSnackbar setText(int paramInt) { return setText(this.mContext.getText(paramInt)); }
  
  public TBSnackbar setText(CharSequence paramCharSequence) {
    this.mView.getMessageView().setText(paramCharSequence);
    return this;
  }
  
  boolean shouldAnimate() { return this.mAccessibilityManager.isEnabled() ^ true; }
  
  public void show() {
    if (this.mStyle == 2) {
      changeLayoutParams(80);
    } else {
      changeLayoutParams(48);
      if (this.mStyle == 3 && Build.VERSION.SDK_INT >= 19)
        this.mView.setPadding(0, getStatusBarHeight(this.mContext), 0, 0); 
    } 
    TBSnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
  }
  
  final void showView() {
    if (this.mView.getParent() == null) {
      ViewGroup.LayoutParams layoutParams = this.mView.getLayoutParams();
      if (layoutParams instanceof CoordinatorLayout.LayoutParams && this.mStyle == 2) {
        CoordinatorLayout.LayoutParams layoutParams1 = (CoordinatorLayout.LayoutParams)layoutParams;
        Behavior behavior = new Behavior();
        behavior.setStartAlphaSwipeDistance(0.1F);
        behavior.setEndAlphaSwipeDistance(0.6F);
        behavior.setSwipeDirection(0);
        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
              public void onDismiss(View param1View) {
                param1View.setVisibility(8);
                TBSnackbar.this.dispatchDismiss(0);
              }
              
              public void onDragStateChanged(int param1Int) {
                if (param1Int != 0) {
                  if (param1Int != 1 && param1Int != 2)
                    return; 
                  TBSnackbarManager.getInstance().cancelTimeout(TBSnackbar.this.mManagerCallback);
                  return;
                } 
                TBSnackbarManager.getInstance().restoreTimeout(TBSnackbar.this.mManagerCallback);
              }
            });
        layoutParams1.setBehavior((CoordinatorLayout.Behavior)behavior);
        layoutParams1.insetEdge = 80;
      } 
      this.mTargetParent.addView((View)this.mView);
    } 
    this.mView.setOnAttachStateChangeListener(new SnackbarLayout.OnAttachStateChangeListener() {
          public void onViewAttachedToWindow(View param1View) {}
          
          public void onViewDetachedFromWindow(View param1View) {
            if (TBSnackbar.this.isShownOrQueued())
              TBSnackbar.sHandler.post(new Runnable() {
                    public void run() { TBSnackbar.this.onViewHidden(3); }
                  }); 
          }
        });
    if (ViewCompat.isLaidOut((View)this.mView)) {
      if (shouldAnimate()) {
        animateViewIn();
        return;
      } 
      onViewShown();
      return;
    } 
    this.mView.setOnLayoutChangeListener(new SnackbarLayout.OnLayoutChangeListener() {
          public void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
            TBSnackbar.this.mView.setOnLayoutChangeListener(null);
            if (TBSnackbar.this.shouldAnimate()) {
              TBSnackbar.this.animateViewIn();
              return;
            } 
            TBSnackbar.this.onViewShown();
          }
        });
  }
  
  final class Behavior extends SwipeDismissBehavior<SnackbarLayout> {
    public boolean canSwipeDismissView(View param1View) { return param1View instanceof TBSnackbar.SnackbarLayout; }
    
    public boolean onInterceptTouchEvent(CoordinatorLayout param1CoordinatorLayout, TBSnackbar.SnackbarLayout param1SnackbarLayout, MotionEvent param1MotionEvent) {
      if (param1CoordinatorLayout.isPointInChildBounds((View)param1SnackbarLayout, (int)param1MotionEvent.getX(), (int)param1MotionEvent.getY())) {
        int i = param1MotionEvent.getActionMasked();
        if (i != 0) {
          if (i == 1 || i == 3)
            TBSnackbarManager.getInstance().restoreTimeout(TBSnackbar.this.mManagerCallback); 
        } else {
          TBSnackbarManager.getInstance().cancelTimeout(TBSnackbar.this.mManagerCallback);
        } 
      } 
      return super.onInterceptTouchEvent(param1CoordinatorLayout, (View)param1SnackbarLayout, param1MotionEvent);
    }
  }
  
  public static abstract class Callback {
    public static final int DISMISS_EVENT_ACTION = 1;
    
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    
    public static final int DISMISS_EVENT_MANUAL = 3;
    
    public static final int DISMISS_EVENT_SWIPE = 0;
    
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    
    public void onDismissed(TBSnackbar param1TBSnackbar, int param1Int) {}
    
    public void onShown(TBSnackbar param1TBSnackbar) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface DismissEvent {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DismissEvent {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Duration {}
  
  public static class SnackbarLayout extends LinearLayout {
    private Button mActionView;
    
    private int mMaxInlineActionWidth;
    
    private int mMaxWidth;
    
    private TextView mMessageView;
    
    private OnAttachStateChangeListener mOnAttachStateChangeListener;
    
    private OnLayoutChangeListener mOnLayoutChangeListener;
    
    public SnackbarLayout(Context param1Context) { this(param1Context, null); }
    
    public SnackbarLayout(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.SnackbarLayout);
      this.mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
      this.mMaxInlineActionWidth = typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
      if (typedArray.hasValue(R.styleable.SnackbarLayout_elevation))
        ViewCompat.setElevation((View)this, typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0)); 
      typedArray.recycle();
      setClickable(true);
      LayoutInflater.from(param1Context).inflate(R.layout.design_layout_snackbar_include, (ViewGroup)this);
      ViewCompat.setAccessibilityLiveRegion((View)this, 1);
      ViewCompat.setImportantForAccessibility((View)this, 1);
      ViewCompat.setFitsSystemWindows((View)this, true);
      ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View param2View, WindowInsetsCompat param2WindowInsetsCompat) {
              param2View.setPadding(param2View.getPaddingLeft(), param2View.getPaddingTop(), param2View.getPaddingRight(), param2WindowInsetsCompat.getSystemWindowInsetBottom());
              return param2WindowInsetsCompat;
            }
          });
    }
    
    private static void updateTopBottomPadding(View param1View, int param1Int1, int param1Int2) {
      if (ViewCompat.isPaddingRelative(param1View)) {
        ViewCompat.setPaddingRelative(param1View, ViewCompat.getPaddingStart(param1View), param1Int1, ViewCompat.getPaddingEnd(param1View), param1Int2);
        return;
      } 
      param1View.setPadding(param1View.getPaddingLeft(), param1Int1, param1View.getPaddingRight(), param1Int2);
    }
    
    private boolean updateViewsWithinLayout(int param1Int1, int param1Int2, int param1Int3) {
      boolean bool;
      if (param1Int1 != getOrientation()) {
        setOrientation(param1Int1);
        bool = true;
      } else {
        bool = false;
      } 
      if (this.mMessageView.getPaddingTop() != param1Int2 || this.mMessageView.getPaddingBottom() != param1Int3) {
        updateTopBottomPadding((View)this.mMessageView, param1Int2, param1Int3);
        return true;
      } 
      return bool;
    }
    
    void animateChildrenIn(int param1Int1, int param1Int2) {
      ViewCompat.setAlpha((View)this.mMessageView, 0.0F);
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mMessageView).alpha(1.0F);
      long l1 = param1Int2;
      viewPropertyAnimatorCompat = viewPropertyAnimatorCompat.setDuration(l1);
      long l2 = param1Int1;
      viewPropertyAnimatorCompat.setStartDelay(l2).start();
      if (this.mActionView.getVisibility() == 0) {
        ViewCompat.setAlpha((View)this.mActionView, 0.0F);
        ViewCompat.animate((View)this.mActionView).alpha(1.0F).setDuration(l1).setStartDelay(l2).start();
      } 
    }
    
    void animateChildrenOut(int param1Int1, int param1Int2) {
      ViewCompat.setAlpha((View)this.mMessageView, 1.0F);
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mMessageView).alpha(0.0F);
      long l1 = param1Int2;
      viewPropertyAnimatorCompat = viewPropertyAnimatorCompat.setDuration(l1);
      long l2 = param1Int1;
      viewPropertyAnimatorCompat.setStartDelay(l2).start();
      if (this.mActionView.getVisibility() == 0) {
        ViewCompat.setAlpha((View)this.mActionView, 1.0F);
        ViewCompat.animate((View)this.mActionView).alpha(0.0F).setDuration(l1).setStartDelay(l2).start();
      } 
    }
    
    Button getActionView() { return this.mActionView; }
    
    TextView getMessageView() { return this.mMessageView; }
    
    protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      OnAttachStateChangeListener onAttachStateChangeListener = this.mOnAttachStateChangeListener;
      if (onAttachStateChangeListener != null)
        onAttachStateChangeListener.onViewAttachedToWindow((View)this); 
      ViewCompat.requestApplyInsets((View)this);
    }
    
    protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      OnAttachStateChangeListener onAttachStateChangeListener = this.mOnAttachStateChangeListener;
      if (onAttachStateChangeListener != null)
        onAttachStateChangeListener.onViewDetachedFromWindow((View)this); 
    }
    
    protected void onFinishInflate() {
      super.onFinishInflate();
      this.mMessageView = (TextView)findViewById(R.id.snackbar_text);
      this.mActionView = (Button)findViewById(R.id.snackbar_action);
    }
    
    protected void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      super.onLayout(param1Boolean, param1Int1, param1Int2, param1Int3, param1Int4);
      OnLayoutChangeListener onLayoutChangeListener = this.mOnLayoutChangeListener;
      if (onLayoutChangeListener != null)
        onLayoutChangeListener.onLayoutChange((View)this, param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    protected void onMeasure(int param1Int1, int param1Int2) { // Byte code:
      //   0: aload_0
      //   1: iload_1
      //   2: iload_2
      //   3: invokespecial onMeasure : (II)V
      //   6: iload_1
      //   7: istore_3
      //   8: aload_0
      //   9: getfield mMaxWidth : I
      //   12: ifle -> 50
      //   15: aload_0
      //   16: invokevirtual getMeasuredWidth : ()I
      //   19: istore #4
      //   21: aload_0
      //   22: getfield mMaxWidth : I
      //   25: istore #5
      //   27: iload_1
      //   28: istore_3
      //   29: iload #4
      //   31: iload #5
      //   33: if_icmple -> 50
      //   36: iload #5
      //   38: ldc 1073741824
      //   40: invokestatic makeMeasureSpec : (II)I
      //   43: istore_3
      //   44: aload_0
      //   45: iload_3
      //   46: iload_2
      //   47: invokespecial onMeasure : (II)V
      //   50: aload_0
      //   51: invokevirtual getResources : ()Landroid/content/res/Resources;
      //   54: getstatic com/google/android/material/R$dimen.design_snackbar_padding_vertical_2lines : I
      //   57: invokevirtual getDimensionPixelSize : (I)I
      //   60: istore #4
      //   62: aload_0
      //   63: invokevirtual getResources : ()Landroid/content/res/Resources;
      //   66: getstatic com/google/android/material/R$dimen.design_snackbar_padding_vertical : I
      //   69: invokevirtual getDimensionPixelSize : (I)I
      //   72: istore #6
      //   74: aload_0
      //   75: getfield mMessageView : Landroid/widget/TextView;
      //   78: invokevirtual getLayout : ()Landroid/text/Layout;
      //   81: invokevirtual getLineCount : ()I
      //   84: istore_1
      //   85: iconst_0
      //   86: istore #5
      //   88: iload_1
      //   89: iconst_1
      //   90: if_icmple -> 98
      //   93: iconst_1
      //   94: istore_1
      //   95: goto -> 100
      //   98: iconst_0
      //   99: istore_1
      //   100: iload_1
      //   101: ifeq -> 146
      //   104: aload_0
      //   105: getfield mMaxInlineActionWidth : I
      //   108: ifle -> 146
      //   111: aload_0
      //   112: getfield mActionView : Landroid/widget/Button;
      //   115: invokevirtual getMeasuredWidth : ()I
      //   118: aload_0
      //   119: getfield mMaxInlineActionWidth : I
      //   122: if_icmple -> 146
      //   125: iload #5
      //   127: istore_1
      //   128: aload_0
      //   129: iconst_1
      //   130: iload #4
      //   132: iload #4
      //   134: iload #6
      //   136: isub
      //   137: invokespecial updateViewsWithinLayout : (III)Z
      //   140: ifeq -> 174
      //   143: goto -> 172
      //   146: iload_1
      //   147: ifeq -> 153
      //   150: goto -> 157
      //   153: iload #6
      //   155: istore #4
      //   157: iload #5
      //   159: istore_1
      //   160: aload_0
      //   161: iconst_0
      //   162: iload #4
      //   164: iload #4
      //   166: invokespecial updateViewsWithinLayout : (III)Z
      //   169: ifeq -> 174
      //   172: iconst_1
      //   173: istore_1
      //   174: iload_1
      //   175: ifeq -> 184
      //   178: aload_0
      //   179: iload_3
      //   180: iload_2
      //   181: invokespecial onMeasure : (II)V
      //   184: return }
    
    void setOnAttachStateChangeListener(OnAttachStateChangeListener param1OnAttachStateChangeListener) { this.mOnAttachStateChangeListener = param1OnAttachStateChangeListener; }
    
    void setOnLayoutChangeListener(OnLayoutChangeListener param1OnLayoutChangeListener) { this.mOnLayoutChangeListener = param1OnLayoutChangeListener; }
    
    static interface OnAttachStateChangeListener {
      void onViewAttachedToWindow(View param2View);
      
      void onViewDetachedFromWindow(View param2View);
    }
    
    static interface OnLayoutChangeListener {
      void onLayoutChange(View param2View, int param2Int1, int param2Int2, int param2Int3, int param2Int4);
    }
  }
  
  class null implements OnApplyWindowInsetsListener {
    public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
      param1View.setPadding(param1View.getPaddingLeft(), param1View.getPaddingTop(), param1View.getPaddingRight(), param1WindowInsetsCompat.getSystemWindowInsetBottom());
      return param1WindowInsetsCompat;
    }
  }
  
  static interface OnAttachStateChangeListener {
    void onViewAttachedToWindow(View param1View);
    
    void onViewDetachedFromWindow(View param1View);
  }
  
  static interface OnLayoutChangeListener {
    void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Style {}
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\github\topbottomsnackbar\TBSnackbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
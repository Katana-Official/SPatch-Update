package com.gauravk.bubblenavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.gauravk.bubblenavigation.util.ViewUtils;

public class BubbleToggleView extends RelativeLayout {
  private static final int DEFAULT_ANIM_DURATION = 300;
  
  private static final String TAG = "BNI_View";
  
  private int animationDuration;
  
  private TextView badgeView;
  
  private BubbleToggleItem bubbleToggleItem;
  
  private ImageView iconView;
  
  private boolean isActive = false;
  
  private float maxTitleWidth;
  
  private float measuredTitleWidth;
  
  private boolean showShapeAlways;
  
  private TextView titleView;
  
  public BubbleToggleView(Context paramContext) {
    super(paramContext);
    init(paramContext, null);
  }
  
  public BubbleToggleView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public BubbleToggleView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  public BubbleToggleView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet);
  }
  
  private void createBubbleItemView(Context paramContext) {
    ImageView imageView = new ImageView(paramContext);
    this.iconView = imageView;
    imageView.setId(ViewCompat.generateViewId());
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)this.bubbleToggleItem.getIconWidth(), (int)this.bubbleToggleItem.getIconHeight());
    layoutParams.addRule(15, -1);
    this.iconView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.iconView.setImageDrawable(this.bubbleToggleItem.getIcon());
    this.titleView = new TextView(paramContext);
    layoutParams = new RelativeLayout.LayoutParams(-2, -2);
    layoutParams.addRule(15, -1);
    if (Build.VERSION.SDK_INT >= 17) {
      layoutParams.addRule(17, this.iconView.getId());
    } else {
      layoutParams.addRule(1, this.iconView.getId());
    } 
    this.titleView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.titleView.setSingleLine(true);
    this.titleView.setTextColor(this.bubbleToggleItem.getColorActive());
    this.titleView.setText(this.bubbleToggleItem.getTitle());
    this.titleView.setTextSize(0, this.bubbleToggleItem.getTitleSize());
    this.titleView.setVisibility(0);
    this.titleView.setPadding(this.bubbleToggleItem.getTitlePadding(), 0, this.bubbleToggleItem.getTitlePadding(), 0);
    this.titleView.measure(0, 0);
    float f1 = this.titleView.getMeasuredWidth();
    this.measuredTitleWidth = f1;
    float f2 = this.maxTitleWidth;
    if (f1 > f2)
      this.measuredTitleWidth = f2; 
    this.titleView.setVisibility(8);
    addView((View)this.iconView);
    addView((View)this.titleView);
    updateBadge(paramContext);
    setInitialState(this.isActive);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    Object object;
    String str;
    int i1;
    Drawable drawable1;
    int j = ViewUtils.getThemeAccentColor(paramContext);
    int k = ContextCompat.getColor(paramContext, R.color.default_inactive_color);
    float f2 = paramContext.getResources().getDimension(R.dimen.default_nav_item_text_size);
    this.maxTitleWidth = paramContext.getResources().getDimension(R.dimen.default_nav_item_title_max_width);
    float f1 = paramContext.getResources().getDimension(R.dimen.default_icon_size);
    float f3 = paramContext.getResources().getDimension(R.dimen.default_icon_size);
    int m = (int)paramContext.getResources().getDimension(R.dimen.default_nav_item_padding);
    int n = (int)paramContext.getResources().getDimension(R.dimen.default_nav_item_text_padding);
    int i = (int)paramContext.getResources().getDimension(R.dimen.default_nav_item_badge_text_size);
    int i2 = ContextCompat.getColor(paramContext, R.color.default_badge_background_color);
    int i3 = ContextCompat.getColor(paramContext, R.color.default_badge_text_color);
    Drawable drawable3 = null;
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BubbleToggleView, 0, 0);
      try {
        if (Build.VERSION.SDK_INT >= 21) {
          drawable3 = typedArray.getDrawable(R.styleable.BubbleToggleView_bt_icon);
        } else {
          drawable3 = AppCompatResources.getDrawable(getContext(), typedArray.getResourceId(R.styleable.BubbleToggleView_bt_icon, R.drawable.default_icon));
        } 
        f1 = typedArray.getDimension(R.styleable.BubbleToggleView_bt_iconWidth, f1);
        f3 = typedArray.getDimension(R.styleable.BubbleToggleView_bt_iconHeight, f3);
        drawable1 = typedArray.getDrawable(R.styleable.BubbleToggleView_bt_shape);
        i1 = typedArray.getColor(R.styleable.BubbleToggleView_bt_shapeColor, -2147483648);
        this.showShapeAlways = typedArray.getBoolean(R.styleable.BubbleToggleView_bt_showShapeAlways, false);
        str = typedArray.getString(R.styleable.BubbleToggleView_bt_title);
        f2 = typedArray.getDimension(R.styleable.BubbleToggleView_bt_titleSize, f2);
        j = typedArray.getColor(R.styleable.BubbleToggleView_bt_colorActive, j);
        k = typedArray.getColor(R.styleable.BubbleToggleView_bt_colorInactive, k);
        this.isActive = typedArray.getBoolean(R.styleable.BubbleToggleView_bt_active, false);
        this.animationDuration = typedArray.getInteger(R.styleable.BubbleToggleView_bt_duration, 300);
        m = (int)typedArray.getDimension(R.styleable.BubbleToggleView_bt_padding, m);
        n = (int)typedArray.getDimension(R.styleable.BubbleToggleView_bt_titlePadding, n);
        i = (int)typedArray.getDimension(R.styleable.BubbleToggleView_bt_badgeTextSize, i);
        i2 = typedArray.getColor(R.styleable.BubbleToggleView_bt_badgeBackgroundColor, i2);
        i3 = typedArray.getColor(R.styleable.BubbleToggleView_bt_badgeTextColor, i3);
        object = typedArray.getString(R.styleable.BubbleToggleView_bt_badgeText);
      } finally {
        typedArray.recycle();
      } 
    } else {
      object = null;
      i1 = Integer.MIN_VALUE;
      str = "Title";
      drawable1 = (Drawable)object;
    } 
    Drawable drawable4 = drawable3;
    if (drawable3 == null)
      drawable4 = ContextCompat.getDrawable(paramContext, R.drawable.default_icon); 
    Drawable drawable2 = drawable1;
    if (drawable1 == null)
      drawable2 = ContextCompat.getDrawable(paramContext, R.drawable.transition_background_drawable); 
    BubbleToggleItem bubbleToggleItem1 = new BubbleToggleItem();
    this.bubbleToggleItem = bubbleToggleItem1;
    bubbleToggleItem1.setIcon(drawable4);
    this.bubbleToggleItem.setShape(drawable2);
    this.bubbleToggleItem.setTitle(str);
    this.bubbleToggleItem.setTitleSize(f2);
    this.bubbleToggleItem.setTitlePadding(n);
    this.bubbleToggleItem.setShapeColor(i1);
    this.bubbleToggleItem.setColorActive(j);
    this.bubbleToggleItem.setColorInactive(k);
    this.bubbleToggleItem.setIconWidth(f1);
    this.bubbleToggleItem.setIconHeight(f3);
    this.bubbleToggleItem.setInternalPadding(m);
    this.bubbleToggleItem.setBadgeText((String)object);
    this.bubbleToggleItem.setBadgeBackgroundColor(i2);
    this.bubbleToggleItem.setBadgeTextColor(i3);
    this.bubbleToggleItem.setBadgeTextSize(i);
    setGravity(17);
    setPadding(this.bubbleToggleItem.getInternalPadding(), this.bubbleToggleItem.getInternalPadding(), this.bubbleToggleItem.getInternalPadding(), this.bubbleToggleItem.getInternalPadding());
    post(new Runnable() {
          public void run() {
            BubbleToggleView bubbleToggleView = BubbleToggleView.this;
            bubbleToggleView.setPadding(bubbleToggleView.bubbleToggleItem.getInternalPadding(), BubbleToggleView.this.bubbleToggleItem.getInternalPadding(), BubbleToggleView.this.bubbleToggleItem.getInternalPadding(), BubbleToggleView.this.bubbleToggleItem.getInternalPadding());
          }
        });
    createBubbleItemView(paramContext);
    setInitialState(this.isActive);
  }
  
  private void updateBadge(Context paramContext) {
    TextView textView = this.badgeView;
    if (textView != null)
      removeView((View)textView); 
    if (this.bubbleToggleItem.getBadgeText() == null)
      return; 
    this.badgeView = new TextView(paramContext);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
    layoutParams.addRule(6, this.iconView.getId());
    if (Build.VERSION.SDK_INT >= 17) {
      layoutParams.addRule(19, this.iconView.getId());
    } else {
      layoutParams.addRule(7, this.iconView.getId());
    } 
    this.badgeView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.badgeView.setSingleLine(true);
    this.badgeView.setTextColor(this.bubbleToggleItem.getBadgeTextColor());
    this.badgeView.setText(this.bubbleToggleItem.getBadgeText());
    this.badgeView.setTextSize(0, this.bubbleToggleItem.getBadgeTextSize());
    this.badgeView.setGravity(17);
    Drawable drawable = ContextCompat.getDrawable(paramContext, R.drawable.badge_background_white);
    ViewUtils.updateDrawableColor(drawable, this.bubbleToggleItem.getBadgeBackgroundColor());
    this.badgeView.setBackground(drawable);
    int i = (int)paramContext.getResources().getDimension(R.dimen.default_nav_item_badge_padding);
    this.badgeView.setPadding(i, 0, i, 0);
    this.badgeView.measure(0, 0);
    if (this.badgeView.getMeasuredWidth() < this.badgeView.getMeasuredHeight()) {
      TextView textView1 = this.badgeView;
      textView1.setWidth(textView1.getMeasuredHeight());
    } 
    addView((View)this.badgeView);
  }
  
  public void activate() {
    ViewUtils.updateDrawableColor(this.iconView.getDrawable(), this.bubbleToggleItem.getColorActive());
    this.isActive = true;
    this.titleView.setVisibility(0);
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    valueAnimator.setDuration(this.animationDuration);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            float f = ((Float)param1ValueAnimator.getAnimatedValue()).floatValue();
            BubbleToggleView.this.titleView.setWidth((int)(BubbleToggleView.this.measuredTitleWidth * f));
          }
        });
    valueAnimator.start();
    if (getBackground() instanceof TransitionDrawable) {
      ((TransitionDrawable)getBackground()).startTransition(this.animationDuration);
      return;
    } 
    if (!this.showShapeAlways && this.bubbleToggleItem.getShapeColor() != Integer.MIN_VALUE)
      ViewUtils.updateDrawableColor(this.bubbleToggleItem.getShape(), this.bubbleToggleItem.getShapeColor()); 
    setBackground(this.bubbleToggleItem.getShape());
  }
  
  public void deactivate() {
    ViewUtils.updateDrawableColor(this.iconView.getDrawable(), this.bubbleToggleItem.getColorInactive());
    this.isActive = false;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 1.0F, 0.0F });
    valueAnimator.setDuration(this.animationDuration);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            float f = ((Float)param1ValueAnimator.getAnimatedValue()).floatValue();
            BubbleToggleView.this.titleView.setWidth((int)(BubbleToggleView.this.measuredTitleWidth * f));
            if (f <= 0.0F)
              BubbleToggleView.this.titleView.setVisibility(8); 
          }
        });
    valueAnimator.start();
    if (getBackground() instanceof TransitionDrawable) {
      ((TransitionDrawable)getBackground()).reverseTransition(this.animationDuration);
      return;
    } 
    if (!this.showShapeAlways)
      setBackground(null); 
  }
  
  public boolean isActive() { return this.isActive; }
  
  public void setBadgeText(String paramString) {
    this.bubbleToggleItem.setBadgeText(paramString);
    updateBadge(getContext());
  }
  
  public void setInitialState(boolean paramBoolean) {
    setBackground(this.bubbleToggleItem.getShape());
    if (paramBoolean) {
      ViewUtils.updateDrawableColor(this.iconView.getDrawable(), this.bubbleToggleItem.getColorActive());
      this.isActive = true;
      this.titleView.setVisibility(0);
      if (getBackground() instanceof TransitionDrawable) {
        ((TransitionDrawable)getBackground()).startTransition(0);
        return;
      } 
      if (!this.showShapeAlways && this.bubbleToggleItem.getShapeColor() != Integer.MIN_VALUE) {
        ViewUtils.updateDrawableColor(this.bubbleToggleItem.getShape(), this.bubbleToggleItem.getShapeColor());
        return;
      } 
    } else {
      ViewUtils.updateDrawableColor(this.iconView.getDrawable(), this.bubbleToggleItem.getColorInactive());
      this.isActive = false;
      this.titleView.setVisibility(8);
      if (!this.showShapeAlways) {
        if (!(getBackground() instanceof TransitionDrawable)) {
          setBackground(null);
          return;
        } 
        ((TransitionDrawable)getBackground()).resetTransition();
      } 
    } 
  }
  
  public void setTitleTypeface(Typeface paramTypeface) { this.titleView.setTypeface(paramTypeface); }
  
  public void toggle() {
    if (!this.isActive) {
      activate();
      return;
    } 
    deactivate();
  }
  
  public void updateMeasurements(int paramInt) {
    byte b;
    ViewGroup.LayoutParams layoutParams = this.titleView.getLayoutParams();
    boolean bool = layoutParams instanceof RelativeLayout.LayoutParams;
    int i = 0;
    if (bool) {
      RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams)layoutParams;
      i = layoutParams1.rightMargin;
      b = layoutParams1.leftMargin;
    } else {
      b = 0;
    } 
    paramInt = paramInt - getPaddingRight() + getPaddingLeft() - i + b - (int)this.bubbleToggleItem.getIconWidth() + this.titleView.getPaddingRight() + this.titleView.getPaddingLeft();
    if (paramInt > 0 && paramInt < this.measuredTitleWidth)
      this.measuredTitleWidth = this.titleView.getMeasuredWidth(); 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\gauravk\bubblenavigation\BubbleToggleView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
package com.keijumt.passwordview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000Z\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\007\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\b\004\n\002\020\013\n\002\b\006\n\002\030\002\n\002\b\r\b\000\030\0002\0020\001B%\b\007\022\006\020\002\032\0020\003\022\n\b\002\020\004\032\004\030\0010\005\022\b\b\002\020\006\032\0020\007¢\006\002\020\bJ\030\020\027\032\0020\0302\020\b\002\020\031\032\n\022\004\022\0020\030\030\0010\032J\006\020\033\032\0020\007J\006\020\034\032\0020\007J\006\020\035\032\0020\007J\006\020\036\032\0020\037J \020 \032\0020\0222\006\020!\032\0020\0222\006\020\"\032\0020\0222\006\020#\032\0020\022H\002J\020\020$\032\0020\0302\006\020%\032\0020&H\025J\030\020'\032\0020\0302\006\020(\032\0020\0072\006\020)\032\0020\007H\024J\016\020*\032\0020\0302\006\020+\032\0020\007J\016\020,\032\0020\0302\006\020+\032\0020\007J\016\020-\032\0020\0302\006\020.\032\0020\017J\016\020/\032\0020\0302\006\020+\032\0020\007J\016\0200\032\0020\0302\006\0201\032\0020\022J\016\0202\032\0020\0302\006\020\026\032\0020\022R\020\020\t\032\004\030\0010\nX\016¢\006\002\n\000R\016\020\013\032\0020\fX\004¢\006\002\n\000R\016\020\r\032\0020\fX\004¢\006\002\n\000R\016\020\016\032\0020\017X\016¢\006\002\n\000R\016\020\020\032\0020\fX\004¢\006\002\n\000R\036\020\023\032\0020\0222\006\020\021\032\0020\022@BX\016¢\006\b\n\000\"\004\b\024\020\025R\016\020\026\032\0020\022X\016¢\006\002\n\000¨\0063"}, d2 = {"Lcom/keijumt/passwordview/CircleView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animator", "Landroid/animation/ValueAnimator;", "fillAndStrokeCirclePaint", "Landroid/graphics/Paint;", "fillCirclePaint", "inputAndRemoveAnimationDuration", "", "outLinePaint", "value", "", "progress", "setProgress", "(F)V", "radius", "animateAndInvoke", "", "onEnd", "Lkotlin/Function0;", "getFillAndStrokeCircleColor", "getFillCircleColor", "getOutLineColor", "isAnimating", "", "lerp", "a", "b", "t", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setFillAndStrokeCircleColor", "color", "setFillCircleColor", "setInputAndRemoveAnimationDuration", "duration", "setOutLineColor", "setOutlineStrokeWidth", "strokeWidth", "setRadius", "library_release"}, k = 1, mv = {1, 1, 13})
public final class CircleView extends View {
  private HashMap _$_findViewCache;
  
  private ValueAnimator animator;
  
  private final Paint fillAndStrokeCirclePaint;
  
  private final Paint fillCirclePaint;
  
  private long inputAndRemoveAnimationDuration;
  
  private final Paint outLinePaint;
  
  private float progress;
  
  private float radius;
  
  public CircleView(Context paramContext) { this(paramContext, null, 0, 6, null); }
  
  public CircleView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0, 4, null); }
  
  public CircleView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Paint paint = new Paint(1);
    paint.setColor(-7829368);
    paint.setStrokeWidth(4.0F);
    paint.setStyle(Paint.Style.STROKE);
    this.outLinePaint = paint;
    paint = new Paint(1);
    paint.setColor(-1);
    paint.setStyle(Paint.Style.FILL);
    this.fillCirclePaint = paint;
    paint = new Paint(1);
    paint.setColor(-16777216);
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.fillAndStrokeCirclePaint = paint;
    this.radius = 16.0F;
    this.inputAndRemoveAnimationDuration = 200L;
  }
  
  private final float lerp(float paramFloat1, float paramFloat2, float paramFloat3) { return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3; }
  
  private final void setProgress(float paramFloat) {
    this.progress = paramFloat;
    postInvalidateOnAnimation();
  }
  
  public void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<Object, Object>(); 
    View view2 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view1 = view2;
    if (view2 == null) {
      view1 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view1);
    } 
    return view1;
  }
  
  public final void animateAndInvoke(Function0<Unit> paramFunction0) {
    if (this.animator != null)
      return; 
    float f2 = this.progress;
    float f1 = 0.0F;
    if (f2 == 0.0F)
      f1 = 1.0F; 
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { this.progress, f1 });
    valueAnimator.setDuration(this.inputAndRemoveAnimationDuration);
    valueAnimator.addUpdateListener(new CircleView$animateAndInvoke$$inlined$apply$lambda$1(paramFunction0));
    valueAnimator.addListener((Animator.AnimatorListener)new CircleView$animateAndInvoke$$inlined$apply$lambda$2(paramFunction0));
    valueAnimator.setInterpolator((TimeInterpolator)new FastOutLinearInInterpolator());
    this.animator = valueAnimator;
    if (valueAnimator != null)
      valueAnimator.start(); 
  }
  
  public final int getFillAndStrokeCircleColor() { return this.fillAndStrokeCirclePaint.getColor(); }
  
  public final int getFillCircleColor() { return this.fillCirclePaint.getColor(); }
  
  public final int getOutLineColor() { return this.outLinePaint.getColor(); }
  
  public final boolean isAnimating() { return (this.animator != null); }
  
  protected void onDraw(Canvas paramCanvas) {
    Intrinsics.checkParameterIsNotNull(paramCanvas, "canvas");
    float f1 = this.outLinePaint.getStrokeWidth() / 2;
    float f2 = this.radius;
    paramCanvas.drawCircle(f2 + f1, f2 + f1, lerp(f2 - f1, 0.0F, this.progress), this.fillCirclePaint);
    f2 = this.radius;
    paramCanvas.drawCircle(f2 + f1, f2 + f1, lerp(f2, 0.0F, this.progress), this.outLinePaint);
    f2 = this.radius;
    paramCanvas.drawCircle(f2 + f1, f2 + f1, lerp(0.0F, f2 + f1, this.progress), this.fillAndStrokeCirclePaint);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    float f1 = this.radius;
    float f2 = 2;
    setMeasuredDimension((int)(f1 * f2 + this.outLinePaint.getStrokeWidth()), (int)(this.radius * f2 + this.outLinePaint.getStrokeWidth()));
  }
  
  public final void setFillAndStrokeCircleColor(int paramInt) {
    this.fillAndStrokeCirclePaint.setColor(paramInt);
    postInvalidateOnAnimation();
  }
  
  public final void setFillCircleColor(int paramInt) {
    this.fillCirclePaint.setColor(paramInt);
    postInvalidateOnAnimation();
  }
  
  public final void setInputAndRemoveAnimationDuration(long paramLong) { this.inputAndRemoveAnimationDuration = paramLong; }
  
  public final void setOutLineColor(int paramInt) {
    this.outLinePaint.setColor(paramInt);
    postInvalidateOnAnimation();
  }
  
  public final void setOutlineStrokeWidth(float paramFloat) { this.outLinePaint.setStrokeWidth(paramFloat); }
  
  public final void setRadius(float paramFloat) {
    this.radius = paramFloat;
    invalidate();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "it", "Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "onAnimationUpdate", "com/keijumt/passwordview/CircleView$animateAndInvoke$1$1"}, k = 3, mv = {1, 1, 13})
  static final class CircleView$animateAndInvoke$$inlined$apply$lambda$1 implements ValueAnimator.AnimatorUpdateListener {
    CircleView$animateAndInvoke$$inlined$apply$lambda$1(Function0 param1Function0) {}
    
    public final void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      CircleView circleView = CircleView.this;
      Intrinsics.checkExpressionValueIsNotNull(param1ValueAnimator, "it");
      Object object = param1ValueAnimator.getAnimatedValue();
      if (object != null) {
        circleView.setProgress(((Float)object).floatValue());
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Float");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026¨\006\006¸\006\000"}, d2 = {"com/keijumt/passwordview/CircleView$animateAndInvoke$1$2", "Landroid/animation/AnimatorListenerAdapter;", "onAnimationEnd", "", "animation", "Landroid/animation/Animator;", "library_release"}, k = 1, mv = {1, 1, 13})
  public static final class CircleView$animateAndInvoke$$inlined$apply$lambda$2 extends AnimatorListenerAdapter {
    CircleView$animateAndInvoke$$inlined$apply$lambda$2(Function0 param1Function0) {}
    
    public void onAnimationEnd(Animator param1Animator) {
      CircleView.this.animator = (ValueAnimator)null;
      Function0 function0 = this.$onEnd$inlined;
      if (function0 != null)
        Unit unit = (Unit)function0.invoke(); 
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\CircleView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
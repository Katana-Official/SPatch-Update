package com.keijumt.passwordview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.keijumt.passwordview.animation.Animator;
import com.keijumt.passwordview.animation.FillAndStrokeColorChangeAnimation;
import com.keijumt.passwordview.animation.FillColorChangeAnimation;
import com.keijumt.passwordview.animation.ShakeAnimator;
import com.keijumt.passwordview.animation.SpringAnimator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000^\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020!\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\007\n\002\b\006\n\002\020\016\n\002\b\017\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\020\030\000 A2\0020\001:\001AB%\b\007\022\006\020\002\032\0020\003\022\n\b\002\020\004\032\004\030\0010\005\022\b\b\002\020\006\032\0020\007¢\006\002\020\bJ\020\020,\032\0020-2\006\020.\032\0020\007H\002J\016\020/\032\0020-2\006\0200\032\0020\035J\032\0201\032\004\030\001022\006\0203\032\0020\0072\006\020.\032\0020\007H\002J\006\0204\032\0020-J\020\0204\032\0020-2\006\0205\032\0020\023H\002J\030\0206\032\0020-2\006\0205\032\0020\0232\006\0207\032\0020\007H\002J\030\0208\032\0020-2\006\0205\032\0020\0232\006\0207\032\0020\007H\002J\030\0209\032\0020-2\006\020:\032\0020\0352\006\020;\032\0020\035H\002J\006\020<\032\0020-J\020\020<\032\0020-2\006\0205\032\0020\023H\002J\006\020=\032\0020-J\006\020>\032\0020-J\006\020?\032\0020-J\016\020@\032\0020-2\006\020\t\032\0020\nR\020\020\t\032\004\030\0010\nX\016¢\006\002\n\000R\026\020\013\032\n \r*\004\030\0010\f0\fX\004¢\006\002\n\000R\016\020\016\032\0020\007X\004¢\006\002\n\000R\024\020\017\032\b\022\004\022\0020\0210\020X\004¢\006\002\n\000R\016\020\022\032\0020\023X\004¢\006\002\n\000R\016\020\024\032\0020\023X\004¢\006\002\n\000R\016\020\025\032\0020\026X\004¢\006\002\n\000R\016\020\027\032\0020\007X\004¢\006\002\n\000R\016\020\030\032\0020\026X\004¢\006\002\n\000R\016\020\031\032\0020\023X\004¢\006\002\n\000R\016\020\032\032\0020\007X\004¢\006\002\n\000R\016\020\033\032\0020\026X\004¢\006\002\n\000R\036\020\036\032\0020\0352\006\020\034\032\0020\035@BX\016¢\006\b\n\000\"\004\b\037\020 R\016\020!\032\0020\023X\004¢\006\002\n\000R\016\020\"\032\0020\007X\004¢\006\002\n\000R\016\020#\032\0020\007X\004¢\006\002\n\000R\016\020$\032\0020\007X\004¢\006\002\n\000R\016\020%\032\0020\026X\004¢\006\002\n\000R$\020&\032\0020\0072\006\020\034\032\0020\007@FX\016¢\006\016\n\000\032\004\b'\020(\"\004\b)\020*R\016\020+\032\0020\026X\004¢\006\002\n\000¨\006B"}, d2 = {"Lcom/keijumt/passwordview/PasswordView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "actionListener", "Lcom/keijumt/passwordview/ActionListener;", "array", "Landroid/content/res/TypedArray;", "kotlin.jvm.PlatformType", "betweenMargin", "circleViews", "", "Lcom/keijumt/passwordview/CircleView;", "colorChangeAnimationDuration", "", "correctAnimationDuration", "correctBottom", "", "correctColor", "correctTop", "incorrectAnimationDuration", "incorrectColor", "incorrectMaxWidth", "value", "", "input", "setInput", "(Ljava/lang/String;)V", "inputAndRemoveAnimationDuration", "inputColor", "notInputColor", "outlineColor", "outlineStrokeWidth", "passwordCount", "getPasswordCount", "()I", "setPasswordCount", "(I)V", "radius", "addCircleView", "", "circleCount", "appendInputText", "text", "calcMargin", "Landroid/view/ViewGroup$LayoutParams;", "circleIndex", "correctAnimation", "duration", "fillAndStrokeColorChangeAnimation", "color", "fillColorChangeAnimation", "handleInputAnimate", "oldInput", "newInput", "incorrectAnimation", "removeInputText", "removeListener", "reset", "setListener", "Companion", "library_release"}, k = 1, mv = {1, 1, 13})
public final class PasswordView extends LinearLayout {
  public static final Companion Companion = new Companion(null);
  
  private static final int DEFAULT_BETWEEN_MARGIN = 72;
  
  private static final int DEFAULT_CIRCLE_COUNT = 4;
  
  private static final int DEFAULT_COLOR_CHANGE_ANIMATION_DURATION = 200;
  
  private static final int DEFAULT_CORRECT_ANIMATION_DURATION = 150;
  
  private static final float DEFAULT_CORRECT_BOTTOM = 15.0F;
  
  private static final int DEFAULT_CORRECT_COLOR = -16711936;
  
  private static final float DEFAULT_CORRECT_TOP = 40.0F;
  
  private static final int DEFAULT_INCORRECT_ANIMATION_DURATION = 400;
  
  private static final int DEFAULT_INCORRECT_COLOR = -65536;
  
  private static final float DEFAULT_INCORRECT_MAX_WIDTH = 40.0F;
  
  private static final int DEFAULT_INPUT_AND_REMOVE_ANIMATION_DURATION = 200;
  
  private static final int DEFAULT_INPUT_COLOR = -16777216;
  
  private static final int DEFAULT_NOT_INPUT_COLOR = -1;
  
  private static final int DEFAULT_OUTLINE_COLOR = -7829368;
  
  private static final float DEFAULT_OUTLINE_STROKE_WIDTH = 4.0F;
  
  private static final float DEFAULT_RADIUS = 20.0F;
  
  private HashMap _$_findViewCache;
  
  private ActionListener actionListener;
  
  private final TypedArray array;
  
  private final int betweenMargin;
  
  private final List<CircleView> circleViews;
  
  private final long colorChangeAnimationDuration;
  
  private final long correctAnimationDuration;
  
  private final float correctBottom;
  
  private final int correctColor;
  
  private final float correctTop;
  
  private final long incorrectAnimationDuration;
  
  private final int incorrectColor;
  
  private final float incorrectMaxWidth;
  
  private String input;
  
  private final long inputAndRemoveAnimationDuration;
  
  private final int inputColor;
  
  private final int notInputColor;
  
  private final int outlineColor;
  
  private final float outlineStrokeWidth;
  
  private int passwordCount;
  
  private final float radius;
  
  public PasswordView(Context paramContext) { this(paramContext, null, 0, 6, null); }
  
  public PasswordView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0, 4, null); }
  
  public PasswordView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PasswordView);
    this.array = typedArray;
    this.passwordCount = typedArray.getInteger(R.styleable.PasswordView_password_count, 4);
    this.radius = this.array.getDimension(R.styleable.PasswordView_password_radius, 20.0F);
    this.betweenMargin = this.array.getDimensionPixelOffset(R.styleable.PasswordView_password_between_margin, 72);
    this.inputColor = this.array.getColor(R.styleable.PasswordView_password_input_color, -16777216);
    this.notInputColor = this.array.getColor(R.styleable.PasswordView_password_input_color, -1);
    this.outlineColor = this.array.getColor(R.styleable.PasswordView_password_outline_color, -7829368);
    this.correctColor = this.array.getColor(R.styleable.PasswordView_password_correct_color, -16711936);
    this.incorrectColor = this.array.getColor(R.styleable.PasswordView_password_incorrect_color, -65536);
    this.correctAnimationDuration = this.array.getInteger(R.styleable.PasswordView_password_correct_duration, 150);
    this.incorrectAnimationDuration = this.array.getInteger(R.styleable.PasswordView_password_correct_duration, 400);
    this.colorChangeAnimationDuration = this.array.getInteger(R.styleable.PasswordView_password_color_change_duration, 200);
    this.inputAndRemoveAnimationDuration = this.array.getInteger(R.styleable.PasswordView_password_input_and_remove_duration, 200);
    this.correctTop = this.array.getDimension(R.styleable.PasswordView_password_correct_top, 40.0F);
    this.correctBottom = this.array.getDimension(R.styleable.PasswordView_password_correct_bottom, 15.0F);
    this.incorrectMaxWidth = this.array.getDimension(R.styleable.PasswordView_password_incorrect_max_width, 40.0F);
    this.outlineStrokeWidth = this.array.getDimension(R.styleable.PasswordView_password_outline_stroke_width, 4.0F);
    this.circleViews = new ArrayList<CircleView>();
    this.input = "";
    this.array.recycle();
    setOrientation(0);
    addCircleView(this.passwordCount);
  }
  
  private final void addCircleView(int paramInt) {
    removeAllViews();
    for (int i = 0; i < paramInt; i++) {
      Context context = getContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "context");
      CircleView circleView = new CircleView(context, null, 0, 6, null);
      circleView.setOutLineColor(this.outlineColor);
      circleView.setOutlineStrokeWidth(this.outlineStrokeWidth);
      circleView.setFillCircleColor(this.notInputColor);
      circleView.setFillAndStrokeCircleColor(this.inputColor);
      circleView.setInputAndRemoveAnimationDuration(this.inputAndRemoveAnimationDuration);
      circleView.setRadius(this.radius);
      circleView.setLayoutParams(calcMargin(i, paramInt));
      addView(circleView);
      this.circleViews.add(circleView);
    } 
  }
  
  private final ViewGroup.LayoutParams calcMargin(int paramInt1, int paramInt2) {
    if (paramInt2 >= 0) {
      if (paramInt1 >= 0) {
        if (paramInt2 == 0)
          return null; 
        int i = this.betweenMargin / 2;
        int j = (int)(this.incorrectMaxWidth / 2);
        Object object = new LinearLayout.LayoutParams(getWidth(), getHeight());
        object.topMargin = (int)this.correctTop;
        object.bottomMargin = (int)this.correctBottom;
        if (paramInt2 == 1) {
          object.leftMargin = j;
          object.rightMargin = j;
          return (ViewGroup.LayoutParams)object;
        } 
        if (paramInt2 == 2) {
          if (paramInt1 != 0) {
            if (paramInt1 == 1) {
              object.leftMargin = i;
              object.rightMargin = j;
              return (ViewGroup.LayoutParams)object;
            } 
            object = new StringBuilder();
            object.append("circleIndex:");
            object.append(paramInt1);
            object.append(" must be greater than or equal to 0");
            throw (Throwable)new IllegalArgumentException(object.toString());
          } 
          object.leftMargin = j;
          object.rightMargin = i;
          return (ViewGroup.LayoutParams)object;
        } 
        if (paramInt1 == 0) {
          object.leftMargin = j;
          object.rightMargin = i;
          return (ViewGroup.LayoutParams)object;
        } 
        if (1 <= paramInt1 && --paramInt2 > paramInt1) {
          object.leftMargin = i;
          object.rightMargin = i;
          return (ViewGroup.LayoutParams)object;
        } 
        if (paramInt1 == paramInt2) {
          object.leftMargin = i;
          object.rightMargin = j;
          return (ViewGroup.LayoutParams)object;
        } 
        object = new StringBuilder();
        object.append("circleIndex:");
        object.append(paramInt1);
        object.append(" must be greater than or equal to 0");
        throw (Throwable)new IllegalArgumentException(object.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("circleIndex:");
      stringBuilder1.append(paramInt1);
      stringBuilder1.append(" must be greater than or equal to 0");
      throw (Throwable)new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("passwordCount:");
    stringBuilder.append(paramInt2);
    stringBuilder.append(" must be greater than or equal to 0");
    throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
  }
  
  private final void correctAnimation(long paramLong) {
    Iterator<CircleView> iterator = this.circleViews.iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      SpringAnimator springAnimator = (SpringAnimator)iterator.next();
      if (i)
        CollectionsKt.throwIndexOverflow(); 
      springAnimator = new SpringAnimator((CircleView)springAnimator);
      springAnimator.setDuration(paramLong);
      springAnimator.setStartDelay((i * 40));
      springAnimator.setMoveTopY(this.correctTop);
      springAnimator.setMoveBottomY(this.correctBottom);
      springAnimator.addListener(new PasswordView$correctAnimation$$inlined$forEachIndexed$lambda$1(this, paramLong));
      springAnimator.start();
    } 
  }
  
  private final void fillAndStrokeColorChangeAnimation(long paramLong, int paramInt) {
    Iterator<CircleView> iterator = this.circleViews.iterator();
    while (iterator.hasNext()) {
      FillAndStrokeColorChangeAnimation fillAndStrokeColorChangeAnimation = new FillAndStrokeColorChangeAnimation(iterator.next());
      fillAndStrokeColorChangeAnimation.setDuration(paramLong);
      fillAndStrokeColorChangeAnimation.setToColor(paramInt);
      fillAndStrokeColorChangeAnimation.start();
    } 
  }
  
  private final void fillColorChangeAnimation(long paramLong, int paramInt) {
    Iterator<CircleView> iterator = this.circleViews.iterator();
    while (iterator.hasNext()) {
      FillColorChangeAnimation fillColorChangeAnimation = new FillColorChangeAnimation(iterator.next());
      fillColorChangeAnimation.setDuration(paramLong);
      fillColorChangeAnimation.setToColor(paramInt);
      fillColorChangeAnimation.start();
    } 
  }
  
  private final void handleInputAnimate(String paramString1, String paramString2) {
    if (paramString2.length() > paramString1.length()) {
      int i = paramString1.length();
      int j = paramString2.length();
      while (i < j) {
        ((CircleView)this.circleViews.get(i)).animateAndInvoke(new PasswordView$handleInputAnimate$1(paramString2));
        i++;
      } 
    } else {
      int i = paramString2.length();
      int j = paramString1.length();
      while (i < j) {
        CircleView.animateAndInvoke$default(this.circleViews.get(i), null, 1, null);
        i++;
      } 
    } 
  }
  
  private final void incorrectAnimation(long paramLong) {
    Iterator<CircleView> iterator = this.circleViews.iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      ShakeAnimator shakeAnimator = (ShakeAnimator)iterator.next();
      if (i)
        CollectionsKt.throwIndexOverflow(); 
      shakeAnimator = new ShakeAnimator((CircleView)shakeAnimator);
      shakeAnimator.setDuration(paramLong);
      shakeAnimator.setShakeMaxWidth((int)this.incorrectMaxWidth);
      shakeAnimator.setStartDelay((i * 40));
      shakeAnimator.addListener(new PasswordView$incorrectAnimation$$inlined$forEachIndexed$lambda$1(this, paramLong));
      shakeAnimator.start();
    } 
  }
  
  private final void setInput(String paramString) {
    String str = this.input;
    this.input = paramString;
    if (str.length() != paramString.length() || paramString.length() <= this.circleViews.size())
      handleInputAnimate(str, paramString); 
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
  
  public final void appendInputText(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "text");
    if (paramString.length() + this.input.length() > this.passwordCount)
      return; 
    int j = paramString.length();
    for (int i = 0; i < j; i++) {
      if (((CircleView)this.circleViews.get(this.input.length() + i)).isAnimating())
        return; 
    } 
    String str = this.input;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(paramString);
    setInput(stringBuilder.toString());
  }
  
  public final void correctAnimation() {
    correctAnimation(this.correctAnimationDuration);
    fillAndStrokeColorChangeAnimation(this.colorChangeAnimationDuration, this.correctColor);
  }
  
  public final int getPasswordCount() { return this.passwordCount; }
  
  public final void incorrectAnimation() {
    incorrectAnimation(this.incorrectAnimationDuration);
    fillAndStrokeColorChangeAnimation(this.colorChangeAnimationDuration, this.incorrectColor);
  }
  
  public final void removeInputText() {
    boolean bool;
    if (this.input.length() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool)
      return; 
    if (((CircleView)this.circleViews.get(this.input.length() - 1)).isAnimating())
      return; 
    setInput(StringsKt.dropLast(this.input, 1));
  }
  
  public final void removeListener() { this.actionListener = (ActionListener)null; }
  
  public final void reset() {
    setInput("");
    fillColorChangeAnimation(this.colorChangeAnimationDuration, this.notInputColor);
    fillAndStrokeColorChangeAnimation(this.colorChangeAnimationDuration, this.inputColor);
  }
  
  public final void setListener(ActionListener paramActionListener) {
    Intrinsics.checkParameterIsNotNull(paramActionListener, "actionListener");
    this.actionListener = paramActionListener;
  }
  
  public final void setPasswordCount(int paramInt) {
    this.passwordCount = paramInt;
    addCircleView(paramInt);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\007\n\002\b\f\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\tXT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\tXT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000R\016\020\r\032\0020\004XT¢\006\002\n\000R\016\020\016\032\0020\tXT¢\006\002\n\000R\016\020\017\032\0020\004XT¢\006\002\n\000R\016\020\020\032\0020\004XT¢\006\002\n\000R\016\020\021\032\0020\004XT¢\006\002\n\000R\016\020\022\032\0020\004XT¢\006\002\n\000R\016\020\023\032\0020\tXT¢\006\002\n\000R\016\020\024\032\0020\tXT¢\006\002\n\000¨\006\025"}, d2 = {"Lcom/keijumt/passwordview/PasswordView$Companion;", "", "()V", "DEFAULT_BETWEEN_MARGIN", "", "DEFAULT_CIRCLE_COUNT", "DEFAULT_COLOR_CHANGE_ANIMATION_DURATION", "DEFAULT_CORRECT_ANIMATION_DURATION", "DEFAULT_CORRECT_BOTTOM", "", "DEFAULT_CORRECT_COLOR", "DEFAULT_CORRECT_TOP", "DEFAULT_INCORRECT_ANIMATION_DURATION", "DEFAULT_INCORRECT_COLOR", "DEFAULT_INCORRECT_MAX_WIDTH", "DEFAULT_INPUT_AND_REMOVE_ANIMATION_DURATION", "DEFAULT_INPUT_COLOR", "DEFAULT_NOT_INPUT_COLOR", "DEFAULT_OUTLINE_COLOR", "DEFAULT_OUTLINE_STROKE_WIDTH", "DEFAULT_RADIUS", "library_release"}, k = 1, mv = {1, 1, 13})
  public static final class Companion {
    private Companion() {}
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\023\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002*\001\000\b\n\030\0002\0020\001J\b\020\002\032\0020\003H\026¨\006\004¸\006\005"}, d2 = {"com/keijumt/passwordview/PasswordView$correctAnimation$1$1$1", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "onAnimationEnd", "", "library_release", "com/keijumt/passwordview/PasswordView$$special$$inlined$run$lambda$1"}, k = 1, mv = {1, 1, 13})
  public static final class PasswordView$correctAnimation$$inlined$forEachIndexed$lambda$1 implements Animator.AnimatorListener {
    PasswordView$correctAnimation$$inlined$forEachIndexed$lambda$1(PasswordView param1PasswordView, long param1Long) {}
    
    public void onAnimationEnd() {
      if (this.$index$inlined == PasswordView.this.getPasswordCount() - 1) {
        ActionListener actionListener = PasswordView.this.actionListener;
        if (actionListener != null)
          actionListener.onEndJudgeAnimation(); 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 13})
  static final class PasswordView$handleInputAnimate$1 extends Lambda implements Function0<Unit> {
    PasswordView$handleInputAnimate$1(String param1String) { super(0); }
    
    public final void invoke() {
      if (this.$newInput.length() == PasswordView.this.circleViews.size()) {
        ActionListener actionListener = PasswordView.this.actionListener;
        if (actionListener != null)
          actionListener.onCompleteInput(PasswordView.this.input); 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\023\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002*\001\000\b\n\030\0002\0020\001J\b\020\002\032\0020\003H\026¨\006\004¸\006\005"}, d2 = {"com/keijumt/passwordview/PasswordView$incorrectAnimation$1$1$1", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "onAnimationEnd", "", "library_release", "com/keijumt/passwordview/PasswordView$$special$$inlined$apply$lambda$1"}, k = 1, mv = {1, 1, 13})
  public static final class PasswordView$incorrectAnimation$$inlined$forEachIndexed$lambda$1 implements Animator.AnimatorListener {
    PasswordView$incorrectAnimation$$inlined$forEachIndexed$lambda$1(PasswordView param1PasswordView, long param1Long) {}
    
    public void onAnimationEnd() {
      if (this.$index$inlined == PasswordView.this.getPasswordCount() - 1) {
        ActionListener actionListener = PasswordView.this.actionListener;
        if (actionListener != null)
          actionListener.onEndJudgeAnimation(); 
      } 
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\PasswordView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
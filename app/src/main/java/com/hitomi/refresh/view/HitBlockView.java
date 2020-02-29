package com.hitomi.refresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import com.sk.spatch.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HitBlockView extends FunGameView {
  private static final float BALL_RADIUS = 8.0F;
  
  private static final float BLOCK_HEIGHT_RATIO = 0.03125F;
  
  private static final int BLOCK_HORIZONTAL_NUM = 3;
  
  private static final float BLOCK_POSITION_RATIO = 0.08F;
  
  private static final int BLOCK_VERTICAL_NUM = 5;
  
  private static final float BLOCK_WIDTH_RATIO = 0.01806F;
  
  private static final int DEFAULT_ANGLE = 30;
  
  static final float DIVIDING_LINE_SIZE = 1.0F;
  
  private static final float RACKET_POSITION_RATIO = 0.8F;
  
  private static final int SPEED = 6;
  
  private int angle;
  
  private float blockHeight;
  
  private int blockHorizontalNum;
  
  private float blockLeft;
  
  private Paint blockPaint;
  
  private float blockWidth;
  
  private float cx;
  
  private float cy;
  
  private boolean isleft;
  
  private List<Point> pointList;
  
  private float racketLeft;
  
  private int speed;
  
  public HitBlockView(Context paramContext) { this(paramContext, null); }
  
  public HitBlockView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public HitBlockView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initAttrs(paramContext, paramAttributeSet);
  }
  
  private boolean checkTouchBlock(float paramFloat1, float paramFloat2) {
    int j = (int)((paramFloat1 - this.blockLeft - 8.0F - this.speed) / this.blockWidth);
    int i = j;
    if (j == this.blockHorizontalNum)
      i = j - 1; 
    int k = (int)(paramFloat2 / this.blockHeight);
    j = k;
    if (k == 5)
      j = k - 1; 
    Point point = new Point();
    point.set(i, j);
    j = 0;
    Iterator<Point> iterator = this.pointList.iterator();
    while (true) {
      i = j;
      if (iterator.hasNext()) {
        if (((Point)iterator.next()).equals(point.x, point.y)) {
          i = 1;
          break;
        } 
        continue;
      } 
      break;
    } 
    if (i == 0)
      this.pointList.add(point); 
    return i ^ true;
  }
  
  private boolean checkTouchRacket(float paramFloat) {
    paramFloat -= this.controllerPosition;
    return (paramFloat >= 0.0F && paramFloat <= this.controllerSize);
  }
  
  private void drawColorBlock(Canvas paramCanvas) { // Byte code:
    //   0: iconst_0
    //   1: istore #6
    //   3: aload_0
    //   4: getfield blockHorizontalNum : I
    //   7: istore #7
    //   9: iload #6
    //   11: iload #7
    //   13: iconst_5
    //   14: imul
    //   15: if_icmpge -> 250
    //   18: iload #6
    //   20: iload #7
    //   22: idiv
    //   23: istore #8
    //   25: iload #6
    //   27: iload #7
    //   29: irem
    //   30: istore #9
    //   32: aload_0
    //   33: getfield pointList : Ljava/util/List;
    //   36: invokeinterface iterator : ()Ljava/util/Iterator;
    //   41: astore #12
    //   43: aload #12
    //   45: invokeinterface hasNext : ()Z
    //   50: ifeq -> 79
    //   53: aload #12
    //   55: invokeinterface next : ()Ljava/lang/Object;
    //   60: checkcast android/graphics/Point
    //   63: iload #9
    //   65: iload #8
    //   67: invokevirtual equals : (II)Z
    //   70: ifeq -> 43
    //   73: iconst_1
    //   74: istore #7
    //   76: goto -> 82
    //   79: iconst_0
    //   80: istore #7
    //   82: iload #7
    //   84: ifeq -> 90
    //   87: goto -> 241
    //   90: aload_0
    //   91: getfield lModelColor : I
    //   94: invokestatic red : (I)I
    //   97: istore #10
    //   99: iload #9
    //   101: iconst_1
    //   102: iadd
    //   103: istore #7
    //   105: sipush #255
    //   108: iload #10
    //   110: isub
    //   111: iload #7
    //   113: idiv
    //   114: istore #10
    //   116: sipush #255
    //   119: aload_0
    //   120: getfield lModelColor : I
    //   123: invokestatic green : (I)I
    //   126: isub
    //   127: iload #7
    //   129: idiv
    //   130: istore #11
    //   132: sipush #255
    //   135: aload_0
    //   136: getfield lModelColor : I
    //   139: invokestatic blue : (I)I
    //   142: isub
    //   143: iload #7
    //   145: idiv
    //   146: istore #7
    //   148: aload_0
    //   149: getfield blockPaint : Landroid/graphics/Paint;
    //   152: sipush #255
    //   155: iload #10
    //   157: isub
    //   158: sipush #255
    //   161: iload #11
    //   163: isub
    //   164: sipush #255
    //   167: iload #7
    //   169: isub
    //   170: invokestatic rgb : (III)I
    //   173: invokevirtual setColor : (I)V
    //   176: aload_0
    //   177: getfield blockLeft : F
    //   180: fstore_3
    //   181: iload #9
    //   183: i2f
    //   184: fstore #4
    //   186: aload_0
    //   187: getfield blockWidth : F
    //   190: fstore_2
    //   191: fload_3
    //   192: fload #4
    //   194: fload_2
    //   195: fconst_1
    //   196: fadd
    //   197: fmul
    //   198: fadd
    //   199: fstore_3
    //   200: iload #8
    //   202: i2f
    //   203: fstore #5
    //   205: aload_0
    //   206: getfield blockHeight : F
    //   209: fstore #4
    //   211: fload #5
    //   213: fload #4
    //   215: fconst_1
    //   216: fadd
    //   217: fmul
    //   218: fconst_1
    //   219: fadd
    //   220: fstore #5
    //   222: aload_1
    //   223: fload_3
    //   224: fload #5
    //   226: fload_3
    //   227: fload_2
    //   228: fadd
    //   229: fload #5
    //   231: fload #4
    //   233: fadd
    //   234: aload_0
    //   235: getfield blockPaint : Landroid/graphics/Paint;
    //   238: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   241: iload #6
    //   243: iconst_1
    //   244: iadd
    //   245: istore #6
    //   247: goto -> 3
    //   250: return }
  
  private void drawRacket(Canvas paramCanvas) {
    this.mPaint.setColor(this.rModelColor);
    paramCanvas.drawRect(this.racketLeft, this.controllerPosition, this.racketLeft + this.blockWidth, this.controllerPosition + this.controllerSize, this.mPaint);
  }
  
  private void initAttrs(Context paramContext, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.HitBlock);
    this.blockHorizontalNum = typedArray.getInt(1, 3);
    this.speed = typedArray.getInt(0, 6);
    typedArray.recycle();
  }
  
  private void makeBallPath(Canvas paramCanvas) {
    this.mPaint.setColor(this.mModelColor);
    float f1 = this.cx;
    float f2 = this.blockLeft;
    int i = this.blockHorizontalNum;
    if (f1 <= f2 + i * this.blockWidth + (i - 1) * 1.0F + 8.0F && checkTouchBlock(f1, this.cy))
      this.isleft = false; 
    if (this.cx <= this.blockLeft + 8.0F)
      this.isleft = false; 
    f1 = this.cx;
    f2 = this.racketLeft;
    if (f1 + 8.0F >= f2 && f1 - 8.0F < f2 + this.blockWidth) {
      if (checkTouchRacket(this.cy)) {
        if (this.pointList.size() == this.blockHorizontalNum * 5) {
          this.status = 2;
          return;
        } 
        this.isleft = true;
      } 
    } else if (this.cx > paramCanvas.getWidth()) {
      this.status = 2;
    } 
    f1 = this.cy;
    if (f1 <= 9.0F) {
      this.angle = 150;
    } else if (f1 >= getMeasuredHeight() - 8.0F - 1.0F) {
      this.angle = 210;
    } 
    if (this.isleft) {
      this.cx -= this.speed;
    } else {
      this.cx += this.speed;
    } 
    f1 = this.cy - (float)Math.tan(Math.toRadians(this.angle)) * this.speed;
    this.cy = f1;
    paramCanvas.drawCircle(this.cx, f1, 8.0F, this.mPaint);
    invalidate();
  }
  
  protected void drawGame(Canvas paramCanvas) {
    drawColorBlock(paramCanvas);
    drawRacket(paramCanvas);
    if (this.status == 1 || this.status == 3)
      makeBallPath(paramCanvas); 
  }
  
  protected void initConcreteView() {
    Paint paint = new Paint(1);
    this.blockPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.blockHeight = this.screenHeight * 0.03125F;
    this.blockWidth = this.screenWidth * 0.01806F;
    this.blockLeft = this.screenWidth * 0.08F;
    this.racketLeft = this.screenWidth * 0.8F;
    this.controllerSize = (int)(this.blockHeight * 1.6F);
  }
  
  protected void resetConfigParams() {
    this.cx = this.racketLeft - 16.0F;
    this.cy = (int)(getHeight() * 0.5F);
    this.controllerPosition = 1.0F;
    this.angle = 30;
    this.isleft = true;
    List<Point> list = this.pointList;
    if (list == null) {
      this.pointList = new ArrayList<Point>();
      return;
    } 
    list.clear();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\HitBlockView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
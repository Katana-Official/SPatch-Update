package com.hitomi.refresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BattleCityView extends FunGameView {
  private static final int DEFAULT_BULLET_NUM_SPACING = 360;
  
  private static final int DEFAULT_ENEMY_TANK_NUM_SPACING = 60;
  
  private static final int DEFAULT_TANK_MAGIC_TOTAL_NUM = 8;
  
  private static final float TANK_BARREL_RATIO = 0.33333334F;
  
  private static int TANK_ROW_NUM = 3;
  
  private int barrelSize;
  
  private float bulletRadius;
  
  private int bulletSpace;
  
  private int bulletSpeed = 7;
  
  private SparseArray<Queue<RectF>> eTankSparseArray;
  
  private int enemySpeed = 2;
  
  private int enemyTankSpace;
  
  private int levelNum;
  
  private Queue<Point> mBulletList;
  
  private int offsetETankX;
  
  private int offsetMBulletX;
  
  private boolean once = true;
  
  private int overstepNum;
  
  private Random random;
  
  private Point usedBullet;
  
  private int wipeOutNum;
  
  static  {
  
  }
  
  public BattleCityView(Context paramContext) { this(paramContext, null); }
  
  public BattleCityView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public BattleCityView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) { super(paramContext, paramAttributeSet, paramInt); }
  
  private int apperanceOption() { return this.random.nextInt(TANK_ROW_NUM); }
  
  private boolean checkTankCrash(int paramInt, float paramFloat1, float paramFloat2) {
    RectF rectF = ((Queue<RectF>)this.eTankSparseArray.get(paramInt)).peek();
    return (rectF != null && rectF.contains(paramFloat1, paramFloat2));
  }
  
  private boolean checkWipeOutETank(Point paramPoint) {
    int i = getTrackIndex(paramPoint.y);
    RectF rectF = ((Queue<RectF>)this.eTankSparseArray.get(i)).peek();
    if (rectF != null && rectF.contains(paramPoint.x, paramPoint.y)) {
      int j = this.wipeOutNum + 1;
      this.wipeOutNum = j;
      if (j == this.levelNum)
        upLevel(); 
      ((Queue)this.eTankSparseArray.get(i)).poll();
      return true;
    } 
    return false;
  }
  
  private void drawBullet(Canvas paramCanvas, Point paramPoint) {
    paramPoint.x -= this.bulletSpeed;
    paramCanvas.drawCircle(paramPoint.x, paramPoint.y, this.bulletRadius, this.mPaint);
  }
  
  private void drawEnemyTank(Canvas paramCanvas) {
    this.mPaint.setColor(this.lModelColor);
    int i = this.offsetETankX + this.enemySpeed;
    this.offsetETankX = i;
    if (i / this.enemyTankSpace == 1 || this.once) {
      this.offsetETankX = 0;
      this.once = false;
    } 
    int m = apperanceOption();
    int k = 0;
    int j = k;
    while (k < TANK_ROW_NUM) {
      Queue<RectF> queue = (Queue)this.eTankSparseArray.get(k);
      if (this.offsetETankX == 0 && k == m)
        queue.offer(generateEnemyTank(k)); 
      Iterator<RectF> iterator = queue.iterator();
      while (true) {
        i = j;
        if (iterator.hasNext()) {
          RectF rectF = iterator.next();
          if (rectF.left >= this.screenWidth) {
            i = this.overstepNum + 1;
            this.overstepNum = i;
            if (i >= 8) {
              this.status = 2;
              i = 1;
              break;
            } 
            j = 1;
            continue;
          } 
          drawTank(paramCanvas, rectF);
          continue;
        } 
        break;
      } 
      if (this.status == 2)
        break; 
      j = i;
      if (i != 0) {
        queue.poll();
        j = 0;
      } 
      k++;
    } 
    invalidate();
  }
  
  private void drawSelfTank(Canvas paramCanvas) {
    this.mPaint.setColor(this.rModelColor);
    boolean bool1 = checkTankCrash(getTrackIndex((int)this.controllerPosition), (this.screenWidth - this.controllerSize), this.controllerPosition);
    boolean bool2 = checkTankCrash(getTrackIndex((int)(this.controllerPosition + this.controllerSize)), (this.screenWidth - this.controllerSize), this.controllerPosition + this.controllerSize);
    if (bool1 || bool2)
      this.status = 2; 
    paramCanvas.drawRect((this.screenWidth - this.controllerSize), this.controllerPosition, this.screenWidth, this.controllerPosition + this.controllerSize, this.mPaint);
    float f1 = (this.screenWidth - this.controllerSize - this.barrelSize);
    float f2 = this.controllerPosition;
    float f3 = (this.controllerSize - this.barrelSize);
    float f4 = (this.screenWidth - this.controllerSize);
    float f5 = this.controllerPosition;
    int i = this.controllerSize;
    int j = this.barrelSize;
    paramCanvas.drawRect(f1, f2 + f3 * 0.5F, f4, f5 + (i - j) * 0.5F + j, this.mPaint);
  }
  
  private void drawTank(Canvas paramCanvas, RectF paramRectF) {
    paramRectF.set(paramRectF.left + this.enemySpeed, paramRectF.top, paramRectF.right + this.enemySpeed, paramRectF.bottom);
    paramCanvas.drawRect(paramRectF, this.mPaint);
    float f1 = paramRectF.top + (this.controllerSize - this.barrelSize) * 0.5F;
    float f2 = paramRectF.right;
    float f3 = paramRectF.right;
    int i = this.barrelSize;
    paramCanvas.drawRect(f2, f1, f3 + i, f1 + i, this.mPaint);
  }
  
  private RectF generateEnemyTank(int paramInt) {
    float f1 = -(this.controllerSize + this.barrelSize);
    float f2 = paramInt * (this.controllerSize + 1.0F) + 1.0F;
    return new RectF(f1, f2, this.barrelSize * 2.5F + f1, this.controllerSize + f2);
  }
  
  private int getTrackIndex(int paramInt) {
    int i = getMeasuredHeight();
    int j = TANK_ROW_NUM;
    i = paramInt / i / j;
    paramInt = i;
    if (i >= j)
      paramInt = j - 1; 
    i = paramInt;
    if (paramInt < 0)
      i = 0; 
    return i;
  }
  
  private void makeBulletPath(Canvas paramCanvas) {
    this.mPaint.setColor(this.mModelColor);
    int i = this.offsetMBulletX + this.bulletSpeed;
    this.offsetMBulletX = i;
    int j = i / this.bulletSpace;
    i = 0;
    if (j == 1)
      this.offsetMBulletX = 0; 
    if (this.offsetMBulletX == 0) {
      Point point = new Point();
      point.x = this.screenWidth - this.controllerSize - this.barrelSize;
      point.y = (int)(this.controllerPosition + this.controllerSize * 0.5F);
      this.mBulletList.offer(point);
    } 
    for (Point point : this.mBulletList) {
      if (checkWipeOutETank(point)) {
        this.usedBullet = point;
        continue;
      } 
      if (point.x + this.bulletRadius <= 0.0F)
        i = 1; 
      drawBullet(paramCanvas, point);
    } 
    if (i != 0)
      this.mBulletList.poll(); 
    this.mBulletList.remove(this.usedBullet);
    this.usedBullet = null;
  }
  
  private void upLevel() {
    this.levelNum += 8;
    this.enemySpeed++;
    this.bulletSpeed += 2;
    this.wipeOutNum = 0;
    int i = this.enemyTankSpace;
    if (i > 12)
      this.enemyTankSpace = i - 12; 
    i = this.bulletSpace;
    if (i > 30)
      this.bulletSpace = i - 30; 
  }
  
  protected void drawGame(Canvas paramCanvas) {
    drawSelfTank(paramCanvas);
    if (this.status == 1 || this.status == 3) {
      drawEnemyTank(paramCanvas);
      makeBulletPath(paramCanvas);
    } 
  }
  
  protected void initConcreteView() {
    this.random = new Random();
    float f = this.screenHeight;
    int i = TANK_ROW_NUM;
    this.controllerSize = (int)Math.floor(((f * 0.161F - (i + 1) * 1.0F) / i + 0.5F));
    i = (int)Math.floor((this.controllerSize * 0.33333334F + 0.5F));
    this.barrelSize = i;
    this.bulletRadius = (i - 2.0F) * 0.5F;
    resetConfigParams();
  }
  
  protected void resetConfigParams() {
    this.controllerPosition = 1.0F;
    int i = 0;
    this.status = 0;
    this.enemySpeed = 2;
    this.bulletSpeed = 7;
    this.levelNum = 8;
    this.wipeOutNum = 0;
    this.once = true;
    this.enemyTankSpace = this.controllerSize + this.barrelSize + 60;
    this.bulletSpace = 360;
    this.eTankSparseArray = new SparseArray();
    while (i < TANK_ROW_NUM) {
      LinkedList linkedList = new LinkedList();
      this.eTankSparseArray.put(i, linkedList);
      i++;
    } 
    this.mBulletList = new LinkedList<Point>();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\BattleCityView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
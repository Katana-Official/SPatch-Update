package com.kaisengao.likeview.like;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.kaisengao.likeview.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KsgLikeView extends RelativeLayout {
  private final String TAG = KsgLikeView.class.getName();
  
  private RelativeLayout.LayoutParams mLayoutParams;
  
  private List<Integer> mLikeDrawables;
  
  private KsgPathAnimator mPathAnimator;
  
  public KsgLikeView(Context paramContext) { this(paramContext, null); }
  
  public KsgLikeView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public KsgLikeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.KsgLikeView);
    paramInt = typedArray.getResourceId(R.styleable.KsgLikeView_ksg_default_image, -1);
    int i = typedArray.getInteger(R.styleable.KsgLikeView_ksg_enter_duration, 1500);
    int j = typedArray.getInteger(R.styleable.KsgLikeView_ksg_curve_duration, 4500);
    typedArray.recycle();
    init(paramInt, i, j);
  }
  
  private void init(int paramInt1, int paramInt2, int paramInt3) {
    this.mLikeDrawables = new ArrayList<Integer>();
    int i = paramInt1;
    if (paramInt1 == -1) {
      i = R.drawable.default_favor;
      Log.e(this.TAG, "please pass in the default image resource !");
    } 
    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), i));
    paramInt1 = bitmapDrawable.getIntrinsicWidth();
    i = bitmapDrawable.getIntrinsicHeight();
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(paramInt1, i);
    this.mLayoutParams = layoutParams;
    layoutParams.addRule(14);
    this.mLayoutParams.addRule(12);
    KsgPathAnimator ksgPathAnimator = new KsgPathAnimator(paramInt2, paramInt3);
    this.mPathAnimator = ksgPathAnimator;
    ksgPathAnimator.setPic(paramInt1, i);
  }
  
  public void addFavor() {
    ImageView imageView = new ImageView(getContext());
    int i = Math.abs(this.mPathAnimator.mRandom.nextInt(this.mLikeDrawables.size()));
    imageView.setImageResource(((Integer)this.mLikeDrawables.get(i)).intValue());
    this.mPathAnimator.start((View)imageView, (ViewGroup)this, this.mLayoutParams);
  }
  
  public void addLikeImage(int paramInt) { this.mLikeDrawables.add(Integer.valueOf(paramInt)); }
  
  public void addLikeImages(List<Integer> paramList) { this.mLikeDrawables.addAll(paramList); }
  
  public void addLikeImages(Integer[] paramArrayOfInteger) { this.mLikeDrawables.addAll(Arrays.asList(paramArrayOfInteger)); }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    this.mPathAnimator.setView(getWidth(), getHeight());
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mPathAnimator.setView(getWidth(), getHeight());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\kaisengao\likeview\like\KsgLikeView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */
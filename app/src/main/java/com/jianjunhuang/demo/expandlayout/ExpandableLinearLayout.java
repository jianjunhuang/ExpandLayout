package com.jianjunhuang.demo.expandlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class ExpandableLinearLayout extends LinearLayout {

  private boolean isExpanded = false;
  private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
  private int mExpandSize = 0;
  private int mCollapseSize = 0;
  private int mDuration = 1000;
  private boolean isAnimating = false;
  private boolean isRefreshSize = true;
  private float mExpansion = 0;

  public ExpandableLinearLayout(Context context) {
    this(context, null);
  }

  public ExpandableLinearLayout(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public ExpandableLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int width = getMeasuredWidth();
    int height = getMeasuredHeight();

    int size = getOrientation() == HORIZONTAL ? width : height;

    setVisibility(mExpansion == 0 ? GONE : VISIBLE);
    int expandDelta = size - Math.round(size * mExpansion);
    if (getOrientation() == HORIZONTAL) {
      setMeasuredDimension(size - expandDelta, height);
    } else {
      setMeasuredDimension(width, size - expandDelta);
    }
  }

  private ObjectAnimator createAnimator(int duration, int from, int to) {
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "expansion", from, to);
    objectAnimator.setDuration(duration);
    objectAnimator.setInterpolator(mInterpolator);
    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        if (mExpansion == value) {
          return;
        }
        mExpansion = value;
        setVisibility(VISIBLE);
        requestLayout();
      }
    });
    objectAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation, boolean isReverse) {
        isAnimating = true;
      }

      @Override
      public void onAnimationEnd(Animator animation, boolean isReverse) {
        isAnimating = false;
      }
    });
    return objectAnimator;
  }

  private static final String TAG = "ExpandableLinearLayout";

  public void setInterpolator(Interpolator mInterpolator) {
    this.mInterpolator = mInterpolator;
  }

  public void expand() {
    if (isAnimating || isExpanded) {
      return;
    }
    isExpanded = true;
    createAnimator(mDuration, 0, 1).start();
  }

  public void collapse() {
    if (isAnimating || !isExpanded) {
      return;
    }
    isExpanded = false;
    createAnimator(mDuration, 1, 0).start();
  }

  public void toggle() {
    if (isExpanded) {
      collapse();
    } else {
      expand();
    }
  }

  public void setExpansion(float mExpansion) {
    this.mExpansion = mExpansion;
  }
}

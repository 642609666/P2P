package com.atguigu.p2p.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/13 0013.
 * 功能:
 */

public class MyScrollView extends ScrollView {

    private View childView;
    private int lastY;

    private Rect mRect = new Rect();
    private boolean isAnimtionEnd = true;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (getChildCount() == 0 || !isAnimtionEnd) {
            return super.onTouchEvent(ev);
        }
        /**
         * getY();相对于父布局的Y值
         * getrawY();相对于屏幕的Y值
         *
         */
        int eventY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //保存第一次触摸的位置
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNeedMove()) {
                    //移动的距离
                    int dy = eventY - lastY;
                    //当我们没有记录的时候需要记录原来的位置
                    if (mRect.isEmpty()) {
                        mRect.set(childView.getLeft(), childView.getTop()
                                , childView.getRight(), childView.getBottom());
                    }

                    //记录一下原来的位置
                    childView.layout(childView.getLeft(), childView.getTop() + dy / 2
                            , childView.getRight(), childView.getBottom() + dy / 2);
                    lastY = eventY;
                }
                break;
            case MotionEvent.ACTION_UP:
                //当原来的位置有记录的时候并且动画是结束的时候再执行
                if (!mRect.isEmpty() && isAnimtionEnd) {
                    //获取原来的高度和现在拉动位置的差
                    int translateY = childView.getBottom() - mRect.bottom;
                    //平移动画所移动的距离
                    TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -translateY);
                    ta.setDuration(200);
                    ta.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //当动画开始执行的时候,需要设置成false
                            isAnimtionEnd = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            //清楚动画
                            childView.clearAnimation();
                            //回到原来记录的位置
                            childView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);

                            //把原来记录的位置清楚掉
                            mRect.setEmpty();
                            isAnimtionEnd = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    //开始动画
                    childView.startAnimation(ta);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isNeedMove() {

        //scrollView 的高度
        int scrollViewHeight = this.getMeasuredHeight();
        //子视图的高度
        int childHeight = childView.getMeasuredHeight();

        int dy = childHeight - scrollViewHeight;

        //拿到滑动的距离  往下滑动是变小,网上滑动是变大
        int scrollY = getScrollY();

        if (scrollY <= 0 || scrollY >= dy) {
            return true;
        }

        return false;
    }

    /**
     * 拦截事件
     * true 拦截
     */
    private int downy, lastx, downx;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isOnIntercept = false;
        int eventx = (int) ev.getX();
        int eventy = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = downy - eventy;
                lastx = downx - eventx;
                break;
            case MotionEvent.ACTION_MOVE:
                int absx = Math.abs(eventx - downx);
                int absy = Math.abs(eventy - downy);
                if (absy > absx && absy >= 20) {
                    isOnIntercept = true;
                }
                lastY = eventy;
                lastx = eventx;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isOnIntercept;
    }

    /**
     * 布局加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }
}

package com.atguigu.p2p.home.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.atguigu.p2p.R;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/13 0013.
 * 功能:
 */

public class MyProgress extends View {
    /**
     * 扫描的进度
     */
    private int sweepArc;
    /**
     * 外圈的颜色
     */
    private int roundColor;
    /**
     * 扫描的颜色
     */
    private int sweepColor;
    /**
     * 测量得到的高
     */
    private int mMeasuredHeight;
    /**
     * 测量得到的宽
     */
    private int mMeasuredWidth;
    /**
     * 圆环的宽度
     */
    private int roundWidth = 10;
    /**
     * 画笔
     */
    private Paint mPaint;

    public MyProgress(Context context) {
        super(context);
        init();
    }

    /**
     * 1.什么是自定义属性？
     * 定义可以在布局文件的标签中使用的属性。
     * 2.为什么要自定义视图属性?
     * 这样就可以通过布局的方式给视图对象指定特定的属性值, 而不用动态的设置
     * 3.理解属性值的类型(format)
     * 1. reference 引用类型值 :@id/...
     * 2. color 颜色类型值  #ff00ff
     * 3. boolean 布尔类型值 true false
     * 4. dimension 尺寸类型值 dp / px /sp
     * 5. integer 整数类型值  weight  progress max
     * 6. float 小数类型值  0.5f
     * 7. string 字符串类型值  ""
     * 8. <enum> 枚举类型值 ：水平/垂直
     */
    public MyProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        //自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.progress);
        //第一个参数获得 attrs里面的配置属性名,第二个参数设置默认值
        roundColor = typedArray.getColor(R.styleable.progress_roundColor, Color.parseColor("#440000"));
        sweepColor = typedArray.getColor(R.styleable.progress_roundColor, Color.parseColor("#FF0000"));
        sweepArc = typedArray.getInteger(R.styleable.progress_roundColor, 0);

        //回收
        typedArray.recycle();

    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);

    }

    /**
     * 重新测量,得到宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
    }

    /**
     * 绘制进度条
     * 1.画图
     * 2.画狐
     * 3.画文字
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //得到圆心的坐标X 和  Y
        int cy = mMeasuredHeight / 2;
        int cx = mMeasuredWidth / 2;

        //半径
        int radius = cx - roundWidth / 2;

        //设置圆环的颜色
        mPaint.setColor(roundColor);
        //设置圆环的宽度
        mPaint.setStrokeWidth(roundWidth);

        //参数1,2 圆心坐标  参数3 banjing 参数4 画笔
        canvas.drawCircle(cx, cy, radius, mPaint);

        //画狐
        /**
         * RectF里面的参数是float类型
         * Rect里面的参数是int类型
         */

        RectF rectF = new RectF(roundWidth / 2, roundWidth / 2,
                mMeasuredWidth - roundWidth / 2, mMeasuredHeight - roundWidth / 2);
        //设置扫描颜色
        mPaint.setColor(sweepColor);
        //第二个参数是 起始角,第三个参数是多少度
        canvas.drawArc(rectF, 0, sweepArc * 360 / 100, false, mPaint);

        //画文字
        String text = sweepArc + "%";
        Rect rect = new Rect();
        //第一个参数是文本,第二个到第三个参数是文本的截取长度,
        // 第四个参数是存放测量结果的容器
        mPaint.setColor(Color.parseColor("#0000FF"));
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(30);
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int tx = mMeasuredWidth / 2 - rect.width() / 2;
        int ty = mMeasuredHeight / 2 - rect.height() / 2;
        canvas.drawText(text, tx, ty, mPaint);
    }

    public void setProgress(int progress) {
        sweepArc = progress;
        /**
         * invalidate 是在主线程强制刷新
         * postinvalidate 是在分线程强制刷新
         */
        postInvalidate();
    }
}

package com.zhl.androiddemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static android.view.animation.Animation.INFINITE;


/**
 * 描述：
 * Created by zhaohl on 2020/11/24.
 */
public class ProgressLoadingView extends View {
    private int mWidth, mHeight;
    private int mDefaultWidth, mDefaultHeight;
    private int mProgressWidth;
    private int mMinProgressWidth;
    private Paint mPaint;
    private String mColor;
    private int duration = 250;
    private ValueAnimator animator;

    public ProgressLoadingView(Context context) {
        this(context, null);
    }

    public ProgressLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取颜色值和最小宽高度，以及进度条最小宽度
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
//        String color = typedArray.getString(R.styleable.LoadingView_progressColor);
//        mDefaultWidth = (int) typedArray.getDimension(R.styleable.LoadingView_minWidth, 600);
//        mDefaultHeight = (int) typedArray.getDimension(R.styleable.LoadingView_minHeight, 5);
//        mMinProgressWidth = (int) typedArray.getDimension(R.styleable.LoadingView_minProgressWidth, 100);
//        mProgressWidth = mMinProgressWidth;
//
//        //根据正则表达式来判断颜色格式是否正确
//        String regularStr = "^#[A-Fa-f0-9]{6}";
//        Pattern compile = Pattern.compile(regularStr);
//        if (color == null) {
//            mColor = "#808080";
//        } else {
//            Matcher matcher = compile.matcher(color);
//            if (matcher.matches()) {//如果颜色格式正确
//                mColor = color;
//            } else {
//                //如果颜色格式不正确
//                throw new IllegalArgumentException("wrong color string type!");
//            }
//        }
//        typedArray.recycle();



       /* //设置view的默认最小宽度
        mDefaultWidth=600;
        //设置view的默认最小高度
        mDefaultHeight=5;
        //设置进度条的初始宽度,这个宽度不能大于view的最小宽度，否则进度条不能向两边延伸
        mProgressWidth=100;
        //设置默认初始颜色
        mColor="#808080";*/

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        //给画笔设置颜色
        mPaint.setColor(Color.parseColor("#666666"));


    }

    public void startAnim() {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(duration);
            animator.setRepeatCount(INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    invalidate();
                }
            });
        }
        animator.start();
    }

    public void stopAnim() {
        if (animator != null) {
            animator.cancel();
        }
    }

    /**
     * 设置动画时长
     * @param duration
     */
    public void setTimePeriod(int duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //通过widthMeasureSpec,heightMeasureSpec 来获取view的测量模式和宽高
        int width = getValue(widthMeasureSpec, true);
        int height = getValue(heightMeasureSpec, false);

        //此方法用来设置设置View的具体宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 获取view的宽高值
     *
     * @param measureSpec
     * @param isWidth     是否是测量宽度
     * @return
     */
    private int getValue(int measureSpec, boolean isWidth) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                //子view的大小已经被限定死，我们只能使用其固定大小
                return size;
            case MeasureSpec.AT_MOST:
                //父控件认为子view的大小不能超过size的值，那么我们就取size和默认值之间的最小值
                return Math.min(isWidth ? mDefaultWidth : mDefaultHeight, size);
            case MeasureSpec.UNSPECIFIED:
                //父view不限定子view的大小，我们将其值设置为默认值
                return isWidth ? mDefaultWidth : mDefaultHeight;
            default:
                return isWidth ? mDefaultWidth : mDefaultHeight;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (mWidth < mProgressWidth) {
            //如果宽度小于进度条的宽度
            throw new IllegalArgumentException("the progressWidth must less than mWidth");
        }
        mPaint.setStrokeWidth(mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //首先判断进度条的宽度是否大于view宽度
        if (mProgressWidth < mWidth) {
            //如果不大于，将进度条宽度增加10
            mProgressWidth += mWidth/duration*5;//注意执行此步骤是mProgressWidth值有可能大于view宽度；
        } else {
            //如果进度条宽度大于view宽度将进度条宽度设置为初始宽度
            mProgressWidth = mMinProgressWidth;
        }
        int alpha = (int) (255-255*((float)mProgressWidth/mWidth));
        mPaint.setAlpha(alpha);
        //使用canvas来画进度条（确实就是画一条线）
        canvas.drawLine(mWidth / 2 - mProgressWidth / 2, mDefaultHeight / 2, mWidth / 2 + mProgressWidth / 2, mDefaultHeight / 2, mPaint);
    }
}

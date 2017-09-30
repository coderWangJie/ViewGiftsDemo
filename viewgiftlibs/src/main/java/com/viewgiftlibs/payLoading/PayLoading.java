package com.viewgiftlibs.payLoading;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.viewgiftlibs.R;
import com.viewgiftlibs.ViewUtil;

/**
 * 仿支付宝支付等待成功或失败圆形进度
 * Created by WangJ on 2017/7/25.
 */

public class PayLoading extends View {
    private float arcWidth = ViewUtil.dip2px(getContext(), 2);
    private int startAngle = 0;
    private int sweepAngle = 120;
    private int sweepAngleStep = 5;

    /**
     * 控件状态（0-正在等待结果；1-操作成功；2-操作失败）
     */
    private int status;

    /**
     * 状态为等待中（status=0）时的颜色
     */
    private int loadingColor;
    /**
     * 状态为成功（status=1）时的颜色
     */
    private int successColor;
    /**
     * 状态为失败（status=2）时的颜色
     */
    private int failColor;

    /**
     * 自定义属性-padding
     */
    private float padding;

    private Paint paint;
    RectF rectF;
    PathMeasure measure;

    /**
     * 对号的轨迹
     */
    Path pathTick;
    /**
     * 动态画对号的瞬时状态，随动画进度改变
     */
    Path pathTickTemp;
    /**
     * 叉号右边部分
     */
    Path pathFork1;
    /**
     * 叉号左边部分
     */
    Path pathFork2;
    Path pathFork2Temp;

    /**
     * 成功、失败时画圆的进度
     */
    float f1;

    public PayLoading(Context context) {
        super(context);
        status = 0;
        loadingColor = Color.argb(126, 57, 137, 219);
        successColor = Color.argb(126, 28, 137, 21);
        failColor = Color.argb(126, 250, 77, 72);
        padding = 0;

        measure = new PathMeasure();
        init();
    }

    public PayLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        status = 0;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PayLoading);
        loadingColor = typedArray.getColor(R.styleable.PayLoading_loadingColor, Color.argb(126, 57, 137, 219));
        successColor = typedArray.getColor(R.styleable.PayLoading_loadingColor, Color.argb(126, 28, 137, 21));
        failColor = typedArray.getColor(R.styleable.PayLoading_loadingColor, Color.argb(126, 250, 77, 72));
        padding = typedArray.getDimension(R.styleable.PayLoading_padding, 0);
        typedArray.recycle();

        measure = new PathMeasure();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setColor(loadingColor);
        paint.setStrokeWidth(arcWidth);
        paint.setStyle(Paint.Style.STROKE);

        rectF = new RectF();

        pathTick = new Path();
        pathTickTemp = new Path();
        pathFork1 = new Path();
        pathFork2 = new Path();
        pathFork2Temp = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (status == 0) {
            canvas.rotate(startAngle, getWidth() / 2, getHeight() / 2);
            canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

            if (sweepAngle >= 320 && sweepAngleStep > 0) {
                sweepAngleStep = -5;
            } else if (sweepAngle <= 120 && sweepAngleStep < 0) {
                sweepAngleStep = 5;
            }
            sweepAngle += sweepAngleStep;
            startAngle = (startAngle + 5) % 360;
            invalidate();

        } else if (status == 1) {
            paint.setColor(successColor);

            canvas.drawArc(rectF, 0, 360 * f1, false, paint);
            canvas.drawPath(pathTickTemp, paint);

        } else if (status == 2) {
            paint.setColor(failColor);

            canvas.drawArc(rectF, 0, 360 * f1, false, paint);
            canvas.drawPath(pathFork1, paint);
            canvas.drawPath(pathFork2Temp, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float radius = Math.min(getWidth(), getHeight()) / 2;

        rectF.set(getWidth() / 2 - radius + arcWidth / 2 + padding,
                getHeight() / 2 - radius + arcWidth / 2 + padding,
                getWidth() / 2 + radius - arcWidth / 2 - padding,
                getHeight() / 2 + radius - arcWidth / 2 - padding);

        pathTick.moveTo(getWidth() / 2 - radius / 2, getHeight() / 2);
        pathTick.lineTo(getWidth() / 2, getHeight() / 2 + radius / 3);
        pathTick.lineTo(getWidth() / 2 + radius / 2, getHeight() / 2 - radius / 3);

        pathFork1.moveTo(getWidth() / 2 + radius / 3, getHeight() / 2 - radius / 3);
        pathFork1.lineTo(getWidth() / 2 - radius / 3, getHeight() / 2 + radius / 3);
        pathFork2.moveTo(getWidth() / 2 - radius / 3, getHeight() / 2 - radius / 3);
        pathFork2.lineTo(getWidth() / 2 + radius / 3, getHeight() / 2 + radius / 3);
    }

    public void success() {
        if (status == 0) {
            status = 1;

            ValueAnimator valueAnimatorCircle = getCircleAnimator();

            measure.setPath(pathTick, false);
            ValueAnimator valueAnimatorTick = ValueAnimator.ofFloat(0, 1);
            valueAnimatorTick.setDuration(700);
            valueAnimatorTick.setInterpolator(new AccelerateInterpolator());
            valueAnimatorTick.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    pathTickTemp.reset();
                    pathTickTemp.lineTo(0, 0);
                    measure.getSegment(0, measure.getLength() * ((float) animation.getAnimatedValue()), pathTickTemp, true);

                    invalidate();
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            // 我这里是画圈、画对勾2个动画同时开始
            animatorSet.play(valueAnimatorCircle);
            animatorSet.play(valueAnimatorTick).after(valueAnimatorCircle);
            animatorSet.start();
        }
    }

    public void fail() {
        if (status == 0) {
            status = 2;

            ValueAnimator valueAnimator = getCircleAnimator();

            measure.setPath(pathFork1, false);
            ValueAnimator animatorFork1 = ValueAnimator.ofFloat(0, 1);
            animatorFork1.setDuration(300);
            animatorFork1.setInterpolator(new AccelerateInterpolator());
            animatorFork1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    pathFork1.reset();
                    pathFork1.lineTo(0, 0);
                    measure.getSegment(0, measure.getLength() * ((float) animation.getAnimatedValue()), pathFork1, true);

                    if ((float) animation.getAnimatedValue() == 1) {
                        measure.nextContour();
                        measure.setPath(pathFork2, false);
                    }

                    invalidate();
                }
            });

            ValueAnimator animatorFork2 = ValueAnimator.ofFloat(0, 1);
            animatorFork2.setDuration(300);
            animatorFork2.setInterpolator(new AccelerateInterpolator());
            animatorFork2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    pathFork2Temp.reset();
                    pathFork2Temp.lineTo(0, 0);
                    measure.getSegment(0, measure.getLength() * ((float) animation.getAnimatedValue()), pathFork2Temp, true);

                    invalidate();
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(valueAnimator);
            animatorSet.play(animatorFork1);
            animatorSet.play(animatorFork2).after(animatorFork1);
            animatorSet.start();
        }
    }

    private ValueAnimator getCircleAnimator() {
        ValueAnimator valueAnimatorCircle = ValueAnimator.ofFloat(0, 1);
        valueAnimatorCircle.setDuration(400);
        valueAnimatorCircle.setInterpolator(new AccelerateInterpolator());
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                f1 = (float) animation.getAnimatedValue();

                invalidate();
            }
        });
        return valueAnimatorCircle;
    }
}

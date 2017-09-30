package com.viewgiftlibs.circleProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.viewgiftlibs.R;
import com.viewgiftlibs.ViewUtil;

/**
 * 中间显示百分比的圆形进度条
 *
 * @author WangJ on 2017/7/25.
 */

public class CircleProgress extends View {
    /**
     * 圆形进度条的宽度
     */
    private float circleWidth;
    /**
     * 进度条轨道的颜色
     */
    private int trackColor = Color.GRAY;
    /**
     * 进度条颜色
     */
    private int reachColor = Color.BLUE;
    /**
     * 进度条最大值
     */
    private int maxProgress = 100;
    /**
     * 进度条当前值
     */
    private int progress = 0;
    /**
     * 中间进度值文字颜色
     */
    private int textColor = Color.BLACK;
    /**
     * 文字大小
     */
    private float textSize;

    private Paint paint;

    private int startAngle;

    private OnProgressListener progressListener;

    public CircleProgress(Context context) {
        super(context);
        circleWidth = ViewUtil.dip2px(context, 5);
        textSize = ViewUtil.dip2px(context, 22);
        startAngle = -90;

        paint = new Paint();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        circleWidth = typedArray.getDimension(R.styleable.CircleProgress_circleWidth, ViewUtil.dip2px(context, 5));
        trackColor = typedArray.getColor(R.styleable.CircleProgress_trackColor, Color.GRAY);
        reachColor = typedArray.getColor(R.styleable.CircleProgress_reachColor, Color.BLUE);
        maxProgress = typedArray.getInt(R.styleable.CircleProgress_maxProgress, 100);
        progress = typedArray.getInt(R.styleable.CircleProgress_progress, 0);
        textColor = typedArray.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        textSize = typedArray.getDimension(R.styleable.CircleProgress_textSize, ViewUtil.dip2px(context, 22));
        int startFrom = typedArray.getInt(R.styleable.CircleProgress_startFrom, 1);
        typedArray.recycle();

        if (startFrom == 1) {
            startAngle = -90;
        } else if (startFrom == 2) {
            startAngle = 0;
        } else if (startFrom == 3) {
            startAngle = 90;
        } else {
            startAngle = 180;
        }

        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        paint.reset();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(trackColor);
        paint.setStrokeWidth(circleWidth); // 线宽

        // 画圆形进度条轨道
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getHeight() - circleWidth) / 2, paint);

        // 画进度条
        RectF rectF = new RectF(circleWidth / 2, circleWidth / 2, getWidth() - circleWidth / 2, getHeight() - circleWidth / 2);
        paint.setColor(reachColor);
        canvas.drawArc(rectF, startAngle, progress * 360 / maxProgress, false, paint);

        int progressInt = progress * 100 / maxProgress;
        String txt = progressInt + "%";
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(textColor);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = (fontMetrics.bottom - fontMetrics.top);
        canvas.drawText(txt, (getWidth() - paint.measureText(txt)) / 2, (getHeight() + fontHeight) / 2 - fontMetrics.bottom, paint);
    }

    public void setMaxProgress(int max) {
        maxProgress = max;
    }

    public void setProgress(int currentProgress) {
        progress = currentProgress;
        invalidate();
        if (progressListener != null) {
            if (progress < maxProgress) {
                progressListener.onProgress(progress);
            } else {
                progressListener.onFinish();
            }
        }
    }

    public void setOnProgressListener(OnProgressListener listener) {
        progressListener = listener;
    }

    public interface OnProgressListener {
        void onProgress(int current);

        void onFinish();
    }
}

package com.geek.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.geek.utils.R;


/**
 * 圆角矩形ImageView
 *
 * @author leeshenzhou on 2016/11/25.
 */
public class RoundImageView extends ImageView {

    // 圆角的大小
    private int radius;

    // 圆角大小的默认值
    private static final int DEFAULT_RADIUS = 10;

    // 图片的类型(圆形图片 or 圆角矩形图片)
    private int roundType;

    // 边线的宽度
    private float borderWidth;

    // 边线的颜色
    private int borderColor;

    public static final int ROUND_TYPE_RECT = 0;
    public static final int ROUND_TYPE_CIRCLE = 1;

    private int mWidth;

    private RectF mRectF;

    private RectF mBorderRectF;

    private Paint mPicPaint;

    private Paint mBorderPaint;

    private Matrix mMatrix;

    private BitmapShader mBitmapShader;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 设置角度
     */
    public void setRadius(int radius) {
        int px = dp2px(radius);
        if (this.radius != px) {
            this.radius = px;
            invalidate();
        }
    }

    /**
     * 设置图片类型
     */
    public void setRoundType(int type) {
        if (this.roundType != type) {
            this.roundType = type;
            if (this.roundType != ROUND_TYPE_RECT
                    && this.roundType != ROUND_TYPE_CIRCLE) {
                this.roundType = ROUND_TYPE_RECT;
            }
            requestLayout();
            invalidate();
        }

    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        radius = a.getDimensionPixelSize(R.styleable.RoundImageView_radius,
                dp2px(DEFAULT_RADIUS));
        roundType = a.getInt(R.styleable.RoundImageView_roundType, 0);
        borderWidth = a.getFloat(R.styleable.RoundImageView_borderWidth, 0);
        borderColor = a.getColor(R.styleable.RoundImageView_borderColor,
                Color.GRAY);
        a.recycle();

        mMatrix = new Matrix();

        // 初始化图片的画笔
        mPicPaint = new Paint();
        mPicPaint.setAntiAlias(true);
        mPicPaint.setStrokeCap(Cap.ROUND);
        mPicPaint.setStrokeJoin(Join.ROUND);

        // 初始化边框的画笔
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeCap(Cap.ROUND);
        mBorderPaint.setStrokeJoin(Join.ROUND);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        if (roundType != ROUND_TYPE_CIRCLE) {
            if (borderWidth <= 0) {
                mRectF = new RectF(0, 0, getWidth(), getHeight());
            } else {
                mRectF = new RectF(0, 0, getWidth() - borderWidth, getHeight() - borderWidth);
                mBorderRectF = new RectF(0, 0, getWidth(), getHeight());
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 如果类型是圆形，则强制改变view的宽高一致，以小值为准
        if (roundType == ROUND_TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            // 如果为圆形，是不能指定其圆角大小的
            radius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        setUpShader(drawable, canvas);

        mBorderPaint.setStrokeWidth(borderWidth);
        mBorderPaint.setColor(borderColor);

        if (roundType == ROUND_TYPE_CIRCLE) {
            // 画圆形
            if (borderWidth <= 0) {
                canvas.drawCircle(radius, radius, radius, mPicPaint);
            } else {
                canvas.drawCircle(radius, radius, radius - borderWidth,
                        mPicPaint);
                // 画边线
                canvas.drawCircle(radius, radius, radius, mBorderPaint);
            }
        } else {
            if (borderWidth <= 0) {
                // 画圆角矩形
                canvas.drawRoundRect(mRectF, radius, radius, mPicPaint);
            } else {
                // 画圆角矩形
                canvas.drawRoundRect(mRectF, radius, radius, mPicPaint);
                // 画边线
                canvas.drawRoundRect(mBorderRectF, radius, radius, mBorderPaint);
            }
        }

    }

    private void setUpShader(Drawable drawable, Canvas canvas) {
        Bitmap bitmap = drawableToBitmap(drawable, canvas);
        // 将Bitmap作为着色器，就是在指定区域内绘制Bitmap
        mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);

        if (roundType == ROUND_TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int minSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
            float scale = mWidth * 1.0f / minSize;
            // shader的变换矩阵，我们这里主要用于放大或者缩小
            mMatrix.setScale(scale, scale);
        } else {
            float xScale = getWidth() * 1.0f / bitmap.getWidth();
            float yScale = getHeight() * 1.0f / bitmap.getHeight();
            mMatrix.setScale(xScale, yScale);
        }
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        mPicPaint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitmap(Drawable drawable, Canvas canvas) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }

        int height = drawable.getIntrinsicHeight();
        int width = drawable.getIntrinsicWidth();
        Bitmap bitmap;
        if (height == -1 || width == -1) {
            bitmap = Bitmap.createBitmap(getMeasuredWidth(),
                    getMeasuredHeight(), Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        }
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}

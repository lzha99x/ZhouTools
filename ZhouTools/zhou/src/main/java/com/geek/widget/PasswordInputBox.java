package com.geek.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.geek.tools.R;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 密码输入框
 *
 * @author lee.shenzhou
 */
public class PasswordInputBox extends View {

    private static final String TAG = "PasswordInputBox";

    // 默认设置几个密码框
    private int size;

    // 文字大小
    private int textSize;

    // 文字的颜色
    private int textColor;

    // 默认的颜色
    private int defColor;

    // 选中的颜色
    private int selectColor;

    // 密码框之间的间距
    private int padding;

    // 密码框类型
    private int boxType;

    public static final int BOX_TYPE_FILL = 0;
    public static final int BOX_TYPE_STROKE = 1;

    // 是否为圆角密码框
    private boolean isRoundBox;

    // 边线的宽度
    private float strokeWidth;

    // 边线的颜色
    private int strokeColor;

    // 是否显示密码
    private boolean isShowPwd;

    private Paint mPaint;

    // 绘制时控制文本绘制的范围
    private Rect mRect;

    // 输入到第几个了
    private int position = -1;

    private LinkedList<String> inputList = new LinkedList<>();

    public PasswordInputBox(Context context) {
        this(context, null, 0);
    }

    public PasswordInputBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordInputBox(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PasswordInputBox);
        size = a.getInt(R.styleable.PasswordInputBox_boxSize, 6);
        textSize = a.getDimensionPixelSize(
                R.styleable.PasswordInputBox_textSize, 45);
        textColor = a.getColor(R.styleable.PasswordInputBox_textColor,
                Color.BLACK);
        defColor = a.getColor(R.styleable.PasswordInputBox_defaultBoxColor,
                Color.WHITE);
        selectColor = a.getColor(R.styleable.PasswordInputBox_selectBoxColor,
                Color.WHITE);
        padding = a.getDimensionPixelSize(
                R.styleable.PasswordInputBox_boxPadding, 0);
        boxType = a.getInt(R.styleable.PasswordInputBox_boxType, 0);
        strokeWidth = a
                .getFloat(R.styleable.PasswordInputBox_strokeWidth, 1.0f);
        strokeColor = a.getColor(R.styleable.PasswordInputBox_strokeColor,
                Color.GRAY);
        isRoundBox = a.getBoolean(R.styleable.PasswordInputBox_roundBox, false);
        isShowPwd = a.getBoolean(R.styleable.PasswordInputBox_showPwd, false);

        a.recycle();

        mPaint = new Paint();
        mRect = new Rect();

        // 设置画笔文字大小
        mPaint.setTextSize(textSize);
        // 设置画笔为无锯齿
        mPaint.setAntiAlias(true);
    }

    /**
     * 是否显示密码
     */
    public void setShowPwd(boolean show) {
        this.isShowPwd = show;
        invalidate();
    }

    /**
     * 获取输入的文本内容
     */
    public String getInputText() {
        String text = "";
        Iterator<String> iterator = inputList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            text += str;
        }
        return text;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        // 计算出所有view的总宽度
        float viewWidth = width - padding * (size - 1);

        // 计算出每个子View的宽度
        float childWidth = viewWidth / size;

        for (int i = 0; i < size; i++) {

            // 绘制密码框
            if (boxType == BOX_TYPE_STROKE) {
                // 设置边框
                mPaint.setStrokeWidth(strokeWidth);
                // 设置画笔为空心
                mPaint.setStyle(Style.STROKE);
                mPaint.setColor(strokeColor);
                if (isRoundBox) {
                    canvas.drawRoundRect(new RectF(
                                    padding * i + childWidth * i, 0, childWidth
                                    * (i + 1) + padding * i, height), 10, 10,
                            mPaint);
                } else {
                    canvas.drawRect(padding * i + childWidth * i, 0, childWidth
                            * (i + 1) + padding * i, height, mPaint);
                }
            } else {
                // 设置画笔为实心
                mPaint.setStyle(Style.FILL);
                if (i > position) {
                    mPaint.setColor(defColor);
                } else {
                    mPaint.setColor(selectColor);
                }
                canvas.drawRect(padding * i + childWidth * i, 0, childWidth
                        * (i + 1) + padding * i, height, mPaint);
            }

            // 绘制文字
            mPaint.setColor(textColor);
            mPaint.setStyle(Style.FILL);
            int size = inputList.size();
            if (size > 0 && size > i) {
                String text;
                if (isShowPwd) {
                    text = inputList.get(i);
                    mPaint.getTextBounds(text, 0, text.length(), mRect);
                    canvas.drawText(text, padding * i + childWidth * i
                            + childWidth / 2 - mRect.width() / 2, height * 1.0f
                            / 2 + mRect.height() / 2, mPaint);
                } else {
                    text = "*";
                    mPaint.getTextBounds(text, 0, text.length(), mRect);
                    canvas.drawText(text, padding * i + childWidth * i
                            + childWidth / 2 - mRect.width() / 2, height * 1.0f
                            / 2 + mRect.height(), mPaint);
                }
            }
        }

    }

    /**
     * 设置输入文本
     */
    public void setText(String text) {
        if (text == null || text.length() >= 2) {
            throw new RuntimeException("文本不能为空且只能是单个字符");
        }
        position++;
        if (position >= size) {
            position = size - 1;
            Log.e(TAG, "no text box for you to enter");
            return;
        }
        inputList.add(text);
        invalidate();
    }

    /**
     * 清除文本
     */
    public void clear() {
        position--;
        if (inputList.size() > 0) {
            inputList.removeLast();
            invalidate();
        }
    }

    /**
     * 清空所有文本
     */
    public void clearAll() {
        position = -1;
        inputList.clear();
        invalidate();
    }

}

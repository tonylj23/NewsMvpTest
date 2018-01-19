package com.lijunc.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lijunc.myapplication.utils.MeasureUtils;

/**
 * Created by lijunc on 2017/12/27.
 */

public class SimpleButton extends View {

    private float mBorderWidth;
    private float mRadius;
    // 字体水平空隙
    private int mHorizontalPadding;
    // 字体垂直空隙
    private int mVerticalPadding;
    // icon和文字间距
    private int mIconPadding = 0;
    // 字体大小
    private float mTextSize;

    public SimpleButton(Context context) {
        this(context,null);
    }

    public SimpleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init(context,attrs);
    }

    private void _init(Context context, AttributeSet attrs) {
        mBorderWidth = MeasureUtils.dp2px(context, 0.5f);
        mRadius = MeasureUtils.dp2px(context, 5f);
        mHorizontalPadding = (int) MeasureUtils.dp2px(context, 5f);
        mVerticalPadding = (int) MeasureUtils.dp2px(context, 5f);
        mVerticalPadding = (int) MeasureUtils.dp2px(context, 3f);
        mTextSize = (int) MeasureUtils.sp2px(context, 14f);

    }


}

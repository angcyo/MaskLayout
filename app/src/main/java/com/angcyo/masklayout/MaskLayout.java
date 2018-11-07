package com.angcyo.masklayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2018/11/07
 */
public class MaskLayout extends FrameLayout {

    Drawable maskDrawable;

    Paint maskPaint;

    public MaskLayout(@NonNull Context context) {
        this(context, null);
    }

    public MaskLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MaskLayout);
        maskDrawable = array.getDrawable(R.styleable.MaskLayout_xhg_mask_drawable);
        array.recycle();

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setFilterBitmap(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        setWillNotDraw(false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (maskDrawable != null) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas.saveLayer(0f, 0f, width, height, null);
            } else {
                canvas.saveLayer(0f, 0f, width, height, null, Canvas.ALL_SAVE_FLAG);
            }
            maskDrawable.setBounds(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom());
            maskDrawable.draw(canvas);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas.saveLayer(0f, 0f, width, height, maskPaint);
            } else {
                canvas.saveLayer(0f, 0f, width, height, maskPaint, Canvas.ALL_SAVE_FLAG);
            }
            super.draw(canvas);
            canvas.restore();
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }
}

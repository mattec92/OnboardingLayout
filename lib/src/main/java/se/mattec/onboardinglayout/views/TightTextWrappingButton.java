package se.mattec.onboardinglayout.views;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.Button;

public class TightTextWrappingButton
        extends Button
{

    public TightTextWrappingButton(Context context)
    {
        super(context);
    }

    public TightTextWrappingButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TightTextWrappingButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Layout layout = getLayout();
        if (layout != null)
        {
            int width = (int) Math.ceil(getMaxLineWidth(layout)) + getCompoundPaddingLeft() + getCompoundPaddingRight();
            int height = getMeasuredHeight();
            setMeasuredDimension(width, height);
            setPadding(0, 0, 0, 0);
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private float getMaxLineWidth(Layout layout)
    {
        float maxWidth = 0.0f;
        int lines = layout.getLineCount();
        for (int i = 0; i < lines; i++)
        {
            if (layout.getLineWidth(i) > maxWidth)
            {
                maxWidth = layout.getLineWidth(i);
            }
        }
        return maxWidth;
    }

}

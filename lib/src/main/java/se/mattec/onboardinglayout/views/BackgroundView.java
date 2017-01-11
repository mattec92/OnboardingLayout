package se.mattec.onboardinglayout.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.List;

public class BackgroundView
        extends FrameLayout
{

    private Bitmap bitmap;
    private Canvas tempCanvas;
    private Paint backgroundPaint;
    private Paint paint;
    private Paint transparentPaint;

    private boolean didDraw;

    private List<HoleSpec> holeSpecs;

    public BackgroundView(Context context)
    {
        super(context);
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        setWillNotDraw(false);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(bitmap);
        paint = new Paint();
        backgroundPaint = new Paint();
        transparentPaint = new Paint();
        transparentPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (!didDraw)
        {
            tempCanvas.drawRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight(), backgroundPaint);

            if (holeSpecs != null)
            {
                for (HoleSpec holeSpec : holeSpecs)
                {
                    if (holeSpec.isCircular())
                    {
                        tempCanvas.drawOval(
                                holeSpec.getLeft(),
                                holeSpec.getTop(),
                                holeSpec.getRight(),
                                holeSpec.getBottom(),
                                transparentPaint);
                    }
                    else
                    {
                        tempCanvas.drawRect(
                                holeSpec.getLeft(),
                                holeSpec.getTop(),
                                holeSpec.getRight(),
                                holeSpec.getBottom(),
                                transparentPaint);
                    }
                }
            }
            didDraw = true;
        }

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public void setBackgroundResource(int backgroundResourceId)
    {
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), backgroundResourceId));
    }

    public void setHoleSpecs(List<HoleSpec> holeSpecs)
    {
        this.holeSpecs = holeSpecs;
    }

    public static class HoleSpec
    {

        private int left;
        private int top;
        private int right;
        private int bottom;
        private boolean isCircular;

        public HoleSpec(int left, int top, int right, int bottom, boolean isCircular)
        {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.isCircular = isCircular;
        }

        public int getLeft()
        {
            return left;
        }

        public int getTop()
        {
            return top;
        }

        public int getRight()
        {
            return right;
        }

        public int getBottom()
        {
            return bottom;
        }

        public boolean isCircular()
        {
            return isCircular;
        }

    }

}
package se.mattec.onboardinglayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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

    private int backgroundResourceId;
    private List<Hole> holes;

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

        bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(bitmap);
        paint = new Paint();
        backgroundPaint = new Paint();
        transparentPaint = new Paint();
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        tempCanvas.drawRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight(), backgroundPaint);

        if (holes != null)
        {
            for (Hole hole : holes)
            {
                if (hole.isCircular)
                {
                    tempCanvas.drawOval(
                            hole.left,
                            hole.top,
                            hole.right,
                            hole.bottom,
                            transparentPaint);
                }
                else
                {
                    tempCanvas.drawRect(
                            hole.left,
                            hole.top,
                            hole.right,
                            hole.bottom,
                            transparentPaint);
                }
            }
        }

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    public void setBackgroundColor(int backgroundResourceId)
    {
        this.backgroundResourceId = backgroundResourceId;
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), backgroundResourceId));
    }

    public void setHoles(List<Hole> holes)
    {
        this.holes = holes;
    }

    public static class Hole
    {

        private int left;
        private int top;
        private int right;
        private int bottom;
        private boolean isCircular;

        public Hole(int left, int top, int right, int bottom, boolean isCircular)
        {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.isCircular = isCircular;
        }

    }

}
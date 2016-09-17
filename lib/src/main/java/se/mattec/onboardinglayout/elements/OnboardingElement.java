package se.mattec.onboardinglayout.elements;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.Location;

public abstract class OnboardingElement
{

    protected OnboardingScreen onboardingScreen;
    protected Context context;

    protected Location location;

    protected View viewToAlign;
    protected int top;
    protected int bottom;
    protected int left;
    protected int right;

    protected View view;

    public OnboardingElement(OnboardingScreen onboardingScreen)
    {
        this.onboardingScreen = onboardingScreen;
        this.context = onboardingScreen.getContext();
    }

    protected void getLocation(View view)
    {
        viewToAlign = view;

        do
        {
            top += view.getTop();
            bottom += view.getBottom();
            left += view.getLeft();
            right += view.getRight();
        }
        while (view.getParent() != onboardingScreen.getOnboardingLayout());
    }

    public View create()
    {
        view = buildView();
        positionView(view);
        return view;
    }

    public View getView()
    {
        return view;
    }

    public void clear()
    {
        view = null;
    }

    protected abstract View buildView();

    protected abstract void positionView(View view);

    protected void positionAside(View viewToPosition)
    {
        int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (location)
        {
            case ABOVE:
            {
                params.bottomMargin = onboardingScreen.getOnboardingLayout().getHeight() - top;
                params.gravity = Gravity.BOTTOM;
                break;
            }
            case BELOW:
            {
                params.topMargin = bottom;
                params.gravity = Gravity.TOP;
                break;
            }
            case LEFT:
            {
                params.rightMargin = onboardingLayoutWidth - left;
                params.gravity = Gravity.RIGHT;
                break;
            }
            case RIGHT:
            {
                params.leftMargin = right;
                params.gravity = Gravity.LEFT;
                break;
            }
        }

        viewToPosition.setLayoutParams(params);
    }

    protected void positionAtop(View viewToPosition, int width, int height)
    {
        int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();
        int onboardingLayoutHeight = onboardingScreen.getOnboardingLayout().getHeight();

        int viewToAlignWidth = right - left;
        int viewToAlignHeight = bottom - top;

        int widthOffset = viewToAlignWidth - width;
        int heightOffset = viewToAlignHeight - height;

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);

        params.topMargin = top + heightOffset / 2;
        params.bottomMargin = onboardingLayoutHeight - bottom - heightOffset / 2;
        params.leftMargin = left + widthOffset / 2;
        params.rightMargin = onboardingLayoutWidth - right - widthOffset / 2;

        viewToPosition.setLayoutParams(params);
    }

}
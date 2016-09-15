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
        View view = buildView();
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
        final int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

    protected void positionAtop(View viewToPosition)
    {
        //TODO
    }

}
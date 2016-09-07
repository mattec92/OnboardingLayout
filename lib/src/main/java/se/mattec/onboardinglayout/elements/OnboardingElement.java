package se.mattec.onboardinglayout.elements;

import android.content.Context;
import android.view.View;

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

}
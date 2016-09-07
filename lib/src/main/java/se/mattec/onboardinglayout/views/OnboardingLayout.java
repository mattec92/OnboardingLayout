package se.mattec.onboardinglayout.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import se.mattec.onboardinglayout.OnboardingScreen;

public class OnboardingLayout
        extends FrameLayout
{

    private OnboardingScreen currentOnboardingScreen;

    public OnboardingLayout(Context context)
    {
        super(context);
        setup();
    }

    public OnboardingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setup();
    }

    public OnboardingLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup()
    {

    }

    public void setOnboardingScreen(OnboardingScreen onboardingScreen)
    {
        currentOnboardingScreen = onboardingScreen;
    }

}

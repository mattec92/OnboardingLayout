package se.mattec.onboardinglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class OnboardingLayout
        extends RelativeLayout
{

    private Onboard.OnboardingScreen currentOnboardingScreen;

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

    public void setOnboardingScreen(Onboard.OnboardingScreen onboardingScreen)
    {
        currentOnboardingScreen = onboardingScreen;
    }

}

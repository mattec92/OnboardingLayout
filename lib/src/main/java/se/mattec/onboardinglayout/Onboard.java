package se.mattec.onboardinglayout;

import se.mattec.onboardinglayout.views.OnboardingLayout;

public class Onboard
{

    public static OnboardingScreen in(OnboardingLayout onboardingLayout)
    {
        OnboardingScreen onboardingScreen = new OnboardingScreen(onboardingLayout);
        onboardingLayout.setOnboardingScreen(onboardingScreen);
        return onboardingScreen;
    }

}

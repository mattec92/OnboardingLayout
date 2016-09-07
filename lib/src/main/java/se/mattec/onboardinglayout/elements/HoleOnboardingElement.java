package se.mattec.onboardinglayout.elements;

import android.view.View;

import se.mattec.onboardinglayout.views.BackgroundView;
import se.mattec.onboardinglayout.enums.Location;
import se.mattec.onboardinglayout.OnboardingScreen;

public class HoleOnboardingElement
        extends OnboardingElement
{

    public boolean isCircular;
    public BackgroundView.Hole hole;

    public HoleOnboardingElement(OnboardingScreen onboardingScreen, boolean isCircular)
    {
        super(onboardingScreen);
        this.isCircular = isCircular;
    }

    public OnboardingScreen around(View view)
    {
        location = Location.AROUND;
        getLocation(view);
        hole = new BackgroundView.Hole(left, top, right, bottom, isCircular);
        return onboardingScreen;
    }

    @Override
    protected View buildView()
    {
        return null;
    }

    @Override
    protected void positionView(View view)
    {

    }

}
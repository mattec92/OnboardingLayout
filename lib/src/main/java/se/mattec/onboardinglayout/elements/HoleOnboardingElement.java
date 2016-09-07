package se.mattec.onboardinglayout.elements;

import android.view.View;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.Location;
import se.mattec.onboardinglayout.views.BackgroundView;

public class HoleOnboardingElement
        extends OnboardingElement
{

    private boolean isCircular;
    private BackgroundView.HoleSpec holeSpec;

    public HoleOnboardingElement(OnboardingScreen onboardingScreen, boolean isCircular)
    {
        super(onboardingScreen);
        this.isCircular = isCircular;
    }

    public OnboardingScreen around(View view)
    {
        location = Location.AROUND;
        getLocation(view);
        holeSpec = new BackgroundView.HoleSpec(left, top, right, bottom, isCircular);
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

    public BackgroundView.HoleSpec getHoleSpec()
    {
        return holeSpec;
    }

}
package se.mattec.onboardinglayout.elements;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.Location;

public class ImageOnboardingElement
        extends OnboardingElement
{

    private int imageResourceId;

    public ImageOnboardingElement(OnboardingScreen onboardingScreen, int imageResourceId)
    {
        super(onboardingScreen);
        this.imageResourceId = imageResourceId;
    }

    public OnboardingScreen above(View view)
    {
        location = Location.ABOVE;
        getLocation(view);
        return onboardingScreen;
    }

    public OnboardingScreen below(View view)
    {
        location = Location.BELOW;
        getLocation(view);
        return onboardingScreen;
    }

    public OnboardingScreen toLeftOf(View view)
    {
        location = Location.LEFT;
        getLocation(view);
        return onboardingScreen;
    }

    public OnboardingScreen toRightOf(View view)
    {
        location = Location.RIGHT;
        getLocation(view);
        return onboardingScreen;
    }

    public OnboardingScreen atop(View view)
    {
        location = Location.ATOP;
        getLocation(view);
        return onboardingScreen;
    }

    @Override
    protected View buildView()
    {
        ImageView imageView = new ImageView(context);

        imageView.setImageResource(imageResourceId);

        imageView.setVisibility(View.INVISIBLE);

        return imageView;
    }

    @Override
    protected void positionView(View view)
    {
        if (location == Location.ATOP)
        {
            //TODO: Image gets resized to same size as view to align. Make sure it can expand.
            positionAtop(view, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            view.setVisibility(View.VISIBLE);
        }
        else
        {
            positionAside(view);
            alignViewToAlignCenter(view);
        }
    }

}

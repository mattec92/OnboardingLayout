package se.mattec.onboardinglayout.elements;

import android.view.View;
import android.widget.FrameLayout;

import se.mattec.onboardinglayout.enums.Location;
import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.R;

public class BorderOnboardingElement
        extends OnboardingElement
{

    private final boolean dashed;

    public BorderOnboardingElement(OnboardingScreen onboardingScreen, boolean dashed)
    {
        super(onboardingScreen);
        this.dashed = dashed;
    }

    public OnboardingScreen around(View view)
    {
        location = Location.AROUND;
        getLocation(view);
        return onboardingScreen;
    }

    @Override
    protected View buildView()
    {
        View borderView = new View(context);

        if (dashed)
        {
            borderView.setBackgroundResource(R.drawable.dashed_border);
        }
        else
        {
            borderView.setBackgroundResource(R.drawable.border);
        }

        if (onboardingScreen.borderColorResourceId != -1)
        {
            //TODO preserve transparency
//                borderView.getBackground().setColorFilter(ContextCompat.getColor(context, onboardingScreen.borderColorResourceId), PorterDuff.Mode.SRC_IN);
        }

        view = borderView;

        return borderView;
    }

    @Override
    protected void positionView(View view)
    {
        final int onboardingLayoutWidth = onboardingScreen.onboardingLayout.getWidth();
        final int onboardingLayoutHeight = onboardingScreen.onboardingLayout.getHeight();

        int viewToAlignWidth = right - left;
        final int viewToAlignHeight = bottom - top;

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewToAlignWidth, viewToAlignHeight);

        params.topMargin = top;
        params.bottomMargin = onboardingLayoutHeight - bottom;
        params.leftMargin = left;
        params.rightMargin = onboardingLayoutWidth - right;

        view.setLayoutParams(params);
    }
}
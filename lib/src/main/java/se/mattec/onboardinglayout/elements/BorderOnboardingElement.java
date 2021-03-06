package se.mattec.onboardinglayout.elements;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.R;
import se.mattec.onboardinglayout.enums.Location;

public class BorderOnboardingElement
        extends OnboardingElement
{

    private boolean isDashed;
    private boolean oval;

    public BorderOnboardingElement(OnboardingScreen onboardingScreen, boolean isDashed, boolean oval)
    {
        super(onboardingScreen);
        this.isDashed = isDashed;
        this.oval = oval;
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

        if (isDashed)
        {
            if (oval)
            {
                borderView.setBackgroundResource(R.drawable.dashed_border_oval);
            }
            else
            {
                borderView.setBackgroundResource(R.drawable.dashed_border);
            }
        }
        else
        {
            if (oval)
            {
                borderView.setBackgroundResource(R.drawable.border_oval);
            }
            else
            {
                borderView.setBackgroundResource(R.drawable.border);
            }
        }

        if (onboardingScreen.getBorderColorResourceId() != -1)
        {
            borderView.getBackground().setColorFilter(ContextCompat.getColor(context, onboardingScreen.getBorderColorResourceId()), PorterDuff.Mode.SRC_IN);
        }

        return borderView;
    }

    @Override
    protected void positionView(View view)
    {
        int width = right - left;
        int height = bottom - top;
        positionAtop(view, width, height);
    }
}
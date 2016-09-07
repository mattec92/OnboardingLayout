package se.mattec.onboardinglayout.elements;

import android.view.View;
import android.widget.FrameLayout;

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
            //TODO preserve transparency
//                borderView.getBackground().setColorFilter(ContextCompat.getColor(context, onboardingScreen.borderColorResourceId), PorterDuff.Mode.SRC_IN);
        }

        view = borderView;

        return borderView;
    }

    @Override
    protected void positionView(View view)
    {
        final int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();
        final int onboardingLayoutHeight = onboardingScreen.getOnboardingLayout().getHeight();

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
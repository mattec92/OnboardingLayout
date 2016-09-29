package se.mattec.onboardinglayout.elements;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.R;
import se.mattec.onboardinglayout.enums.Location;

public class TextOnboardingElement
        extends OnboardingElement
{

    protected String text;

    public TextOnboardingElement(OnboardingScreen onboardingScreen, String text)
    {
        super(onboardingScreen);
        this.text = text;
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

    @Override
    protected View buildView()
    {
        TextView textView = new TextView(context);

        int padding = (int) context.getResources().getDimension(R.dimen.text_padding);
        textView.setPadding(padding, padding, padding, padding);

        textView.setText(text);

        if (onboardingScreen.getTextColorResourceId() != -1)
        {
            textView.setTextColor(ContextCompat.getColor(context, onboardingScreen.getTextColorResourceId()));
        }

        textView.setVisibility(View.INVISIBLE);

        return textView;
    }

    @Override
    protected void positionView(final View view)
    {
        positionAside(view);
        alignViewToAlignCenter(view);
    }

}
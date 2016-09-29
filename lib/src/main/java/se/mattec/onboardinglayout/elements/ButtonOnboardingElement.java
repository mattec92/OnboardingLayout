package se.mattec.onboardinglayout.elements;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.Location;

public class ButtonOnboardingElement
        extends OnboardingElement
{

    private String text;
    private View.OnClickListener onClickListener;

    public ButtonOnboardingElement(OnboardingScreen onboardingScreen, String text, View.OnClickListener onClickListener)
    {
        super(onboardingScreen);
        this.text = text;
        this.onClickListener = onClickListener;
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
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(ContextCompat.getColor(context, onboardingScreen.getButtonTextColorResourceId()));
        button.getBackground().setColorFilter(ContextCompat.getColor(context, onboardingScreen.getButtonBackgroundColorResourceId()), PorterDuff.Mode.SRC_IN);
        button.setOnClickListener(onClickListener);
        return button;
    }

    @Override
    protected void positionView(View view)
    {
        positionAside(view);
        alignViewToAlignCenter(view);
    }

}

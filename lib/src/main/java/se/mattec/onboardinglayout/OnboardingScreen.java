package se.mattec.onboardinglayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import se.mattec.onboardinglayout.elements.BorderOnboardingElement;
import se.mattec.onboardinglayout.elements.HoleOnboardingElement;
import se.mattec.onboardinglayout.elements.OnboardingElement;
import se.mattec.onboardinglayout.elements.TextOnboardingElement;
import se.mattec.onboardinglayout.views.BackgroundView;
import se.mattec.onboardinglayout.views.OnboardingLayout;

public class OnboardingScreen
{

    private OnboardingLayout onboardingLayout;
    private List<OnboardingElement> onboardingElements;
    private Context context;

    private int backgroundColorResourceId = -1;
    private int textColorResourceId = -1;
    private int borderColorResourceId = -1;

    private View backgroundView;

    public OnboardingScreen(OnboardingLayout onboardingLayout)
    {
        this.onboardingLayout = onboardingLayout;
        onboardingElements = new ArrayList<>();
        context = onboardingLayout.getContext();
    }

    public TextOnboardingElement withText(String text)
    {
        TextOnboardingElement textOnboardingElement = new TextOnboardingElement(this, text);
        onboardingElements.add(textOnboardingElement);
        return textOnboardingElement;
    }

    public BorderOnboardingElement withBorder(boolean isDashed, boolean isCircular)
    {
        BorderOnboardingElement borderOnboardingElement = new BorderOnboardingElement(this, isDashed, isCircular);
        onboardingElements.add(borderOnboardingElement);
        return borderOnboardingElement;
    }

    public HoleOnboardingElement withHole(boolean circular)
    {
        HoleOnboardingElement holeOnboardingElement = new HoleOnboardingElement(this, circular);
        onboardingElements.add(holeOnboardingElement);
        return holeOnboardingElement;
    }

    public OnboardingScreen withOverlayColor(int backgroundColorResourceId)
    {
        this.backgroundColorResourceId = backgroundColorResourceId;
        return this;
    }

    public OnboardingScreen withTextColor(int textColorResourceId)
    {
        this.textColorResourceId = textColorResourceId;
        return this;
    }

    public OnboardingScreen withBorderColor(int borderColorResourceId)
    {
        this.borderColorResourceId = borderColorResourceId;
        return this;
    }

    public OnboardingScreen show()
    {
        if (backgroundColorResourceId != -1)
        {
            List<BackgroundView.HoleSpec> holeSpecs = new ArrayList<>();
            for (OnboardingElement element : onboardingElements)
            {
                if (element instanceof HoleOnboardingElement)
                {
                    holeSpecs.add(((HoleOnboardingElement) element).getHoleSpec());
                }
            }

            BackgroundView backgroundView = new BackgroundView(context);
            backgroundView.setBackgroundColor(backgroundColorResourceId);
            backgroundView.setHoleSpecs(holeSpecs);
            backgroundView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            onboardingLayout.addView(backgroundView);
            this.backgroundView = backgroundView;
        }

        for (OnboardingElement element : onboardingElements)
        {
            View view = element.create();
            if (view != null)
            {
                onboardingLayout.addView(view);
            }
        }

        return this;
    }

    public void clear()
    {
        for (OnboardingElement element : onboardingElements)
        {
            onboardingLayout.removeView(element.getView());
            element.clear();
        }

        onboardingElements.clear();

        if (backgroundView != null)
        {
            onboardingLayout.removeView(backgroundView);
            backgroundView = null;
        }

        onboardingLayout = null;
    }

    public Context getContext()
    {
        return context;
    }

    public OnboardingLayout getOnboardingLayout()
    {
        return onboardingLayout;
    }

    public int getTextColorResourceId()
    {
        return textColorResourceId;
    }

    public int getBorderColorResourceId()
    {
        return borderColorResourceId;
    }

}
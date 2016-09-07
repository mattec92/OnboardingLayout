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

    public OnboardingLayout onboardingLayout;
    private List<OnboardingElement> onboardingElements;
    public Context context;

    private int backgroundColorResourceId = -1;
    public int textColorResourceId = -1;
    public int borderColorResourceId = -1;

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

    public BorderOnboardingElement withBorder(boolean dashed)
    {
        BorderOnboardingElement borderOnboardingElement = new BorderOnboardingElement(this, dashed);
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
            List<BackgroundView.Hole> holes = new ArrayList<>();
            for (OnboardingElement element : onboardingElements)
            {
                if (element instanceof HoleOnboardingElement)
                {
                    holes.add(((HoleOnboardingElement) element).hole);
                }
            }

            BackgroundView backgroundView = new BackgroundView(context);
            backgroundView.setBackgroundColor(backgroundColorResourceId);
            backgroundView.setHoles(holes);
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
            onboardingLayout.removeView(element.view);
            element.view = null;
        }

        onboardingElements.clear();

        if (backgroundView != null)
        {
            onboardingLayout.removeView(backgroundView);
            backgroundView = null;
        }

        onboardingLayout = null;
    }

}
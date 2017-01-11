package se.mattec.onboardinglayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import se.mattec.onboardinglayout.elements.BorderOnboardingElement;
import se.mattec.onboardinglayout.elements.ButtonOnboardingElement;
import se.mattec.onboardinglayout.elements.HoleOnboardingElement;
import se.mattec.onboardinglayout.elements.ImageOnboardingElement;
import se.mattec.onboardinglayout.elements.OnboardingElement;
import se.mattec.onboardinglayout.elements.TextOnboardingElement;
import se.mattec.onboardinglayout.elements.TextWithArrowOnboardingElement;
import se.mattec.onboardinglayout.enums.ArrowLocation;
import se.mattec.onboardinglayout.views.BackgroundView;
import se.mattec.onboardinglayout.views.OnboardingLayout;

public class OnboardingScreen
{

    private OnboardingLayout onboardingLayout;
    private List<OnboardingElement> onboardingElements;
    private Context context;

    private int backgroundColorResourceId = android.R.color.transparent;
    private int textColorResourceId = -1;
    private int borderColorResourceId = -1;
    private int buttonTextColorResourceId = -1;
    private int buttonBackgroundColorResourceId = -1;

    private View.OnClickListener onClickListener;

    private View backgroundView;

    public OnboardingScreen(OnboardingLayout onboardingLayout)
    {
        this.onboardingLayout = onboardingLayout;
        onboardingElements = new ArrayList<>();
        context = onboardingLayout.getContext();
    }

    public TextOnboardingElement withText(String text)
    {
        return withText(text, null);
    }

    public TextOnboardingElement withText(String text, TextOnboardingElement.Settings settings)
    {
        TextOnboardingElement textOnboardingElement = new TextOnboardingElement(this, text, settings);
        onboardingElements.add(textOnboardingElement);
        return textOnboardingElement;
    }

    public TextWithArrowOnboardingElement withTextAndArrow(String text, ArrowLocation arrowLocation)
    {
        TextWithArrowOnboardingElement textOnboardingElement = new TextWithArrowOnboardingElement(this, text, arrowLocation);
        onboardingElements.add(textOnboardingElement);
        return textOnboardingElement;
    }

    public BorderOnboardingElement withBorder(boolean isCircular)
    {
        BorderOnboardingElement borderOnboardingElement = new BorderOnboardingElement(this, false, isCircular);
        onboardingElements.add(borderOnboardingElement);
        return borderOnboardingElement;
    }

    public BorderOnboardingElement withDashedBorder(boolean isCircular)
    {
        BorderOnboardingElement borderOnboardingElement = new BorderOnboardingElement(this, true, isCircular);
        onboardingElements.add(borderOnboardingElement);
        return borderOnboardingElement;
    }

    public HoleOnboardingElement withHole(boolean circular)
    {
        HoleOnboardingElement holeOnboardingElement = new HoleOnboardingElement(this, circular);
        onboardingElements.add(holeOnboardingElement);
        return holeOnboardingElement;
    }

    public ImageOnboardingElement withImage(int imageResourceId)
    {
        ImageOnboardingElement imageOnboardingElement = new ImageOnboardingElement(this, imageResourceId);
        onboardingElements.add(imageOnboardingElement);
        return imageOnboardingElement;
    }

    public ButtonOnboardingElement withButton(String text, View.OnClickListener onClickListener)
    {
        ButtonOnboardingElement buttonOnboardingElement = new ButtonOnboardingElement(this, text, onClickListener);
        onboardingElements.add(buttonOnboardingElement);
        return buttonOnboardingElement;
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

    public OnboardingScreen withButtonTextColor(int buttonTextColorResourceId)
    {
        this.buttonTextColorResourceId = buttonTextColorResourceId;
        return this;
    }

    public OnboardingScreen withButtonBackgroundColor(int buttonBackgroundColorResourceId)
    {
        this.buttonBackgroundColorResourceId = buttonBackgroundColorResourceId;
        return this;
    }

    public OnboardingScreen setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
        return this;
    }

    public OnboardingScreen clearOnClick(final boolean animate)
    {
        setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                OnboardingScreen.this.clear(animate);
            }

        });
        return this;
    }

    public OnboardingScreen clearOnClick()
    {
        return clearOnClick(false);
    }

    public OnboardingScreen show(boolean animate)
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
        backgroundView.setBackgroundResource(backgroundColorResourceId);
        backgroundView.setHoleSpecs(holeSpecs);
        backgroundView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        backgroundView.setOnClickListener(onClickListener);
        onboardingLayout.addView(backgroundView);
        this.backgroundView = backgroundView;

        if (animate)
        {
            AnimationUtils.fadeIn(backgroundView);
        }

        for (OnboardingElement element : onboardingElements)
        {
            View view = element.create();
            if (view != null)
            {
                onboardingLayout.addView(view);
                if (animate)
                {
                    AnimationUtils.fadeIn(view);
                }
            }
        }

        return this;
    }

    public OnboardingScreen show()
    {
        return show(false);
    }

    public void clear(boolean animate)
    {
        for (final OnboardingElement element : onboardingElements)
        {
            if (element.getView() != null)
            {
                if (animate)
                {
                    AnimationUtils.fadeOut(element.getView(), new AnimatorListenerAdapter()
                    {

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            onboardingLayout.removeView(element.getView());
                        }
                    });
                }
                else
                {
                    onboardingLayout.removeView(element.getView());
                }
            }
            element.clear();
        }

        onboardingElements.clear();

        if (backgroundView != null)
        {
            if (animate)
            {
                AnimationUtils.fadeOut(backgroundView, new AnimatorListenerAdapter()
                {

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        onboardingLayout.removeView(backgroundView);
                        backgroundView = null;
                    }
                });
            }
            else
            {
                onboardingLayout.removeView(backgroundView);
                backgroundView = null;
            }
        }
    }

    public void clear()
    {
        clear(false);
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

    public int getButtonTextColorResourceId()
    {
        return buttonTextColorResourceId;
    }

    public int getButtonBackgroundColorResourceId()
    {
        return buttonBackgroundColorResourceId;
    }

}
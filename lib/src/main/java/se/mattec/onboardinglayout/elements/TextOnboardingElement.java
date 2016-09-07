package se.mattec.onboardinglayout.elements;

import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.R;
import se.mattec.onboardinglayout.enums.Location;

public class TextOnboardingElement
        extends OnboardingElement
{

    private String text;

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

    public OnboardingScreen left(View view)
    {
        location = Location.LEFT;
        getLocation(view);
        return onboardingScreen;
    }

    public OnboardingScreen right(View view)
    {
        location = Location.RIGHT;
        getLocation(view);
        return onboardingScreen;
    }

    @Override
    protected View buildView()
    {
        TextView textView = new TextView(context);
        textView.setText(text);

        int padding = (int) context.getResources().getDimension(R.dimen.text_padding);
        textView.setPadding(padding, padding, padding, padding);

        if (onboardingScreen.getTextColorResourceId() != -1)
        {
            textView.setTextColor(ContextCompat.getColor(context, onboardingScreen.getTextColorResourceId()));
        }

        view = textView;

        return textView;
    }

    @Override
    protected void positionView(final View view)
    {
        final int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();

        int viewToAlignWidth = right - left;
        final int viewToAlignHeight = bottom - top;

        final int viewToAlignCenterHorizontal = left + viewToAlignWidth / 2;

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (location)
        {
            case ABOVE:
            {
                params.bottomMargin = onboardingScreen.getOnboardingLayout().getHeight() - top;
                params.gravity = Gravity.BOTTOM;
                break;
            }
            case BELOW:
            {
                params.topMargin = bottom;
                params.gravity = Gravity.TOP;
                break;
            }
            case LEFT:
            {
                params.rightMargin = onboardingLayoutWidth - left;
                params.topMargin = top;
                params.gravity = Gravity.RIGHT;
                break;
            }
            case RIGHT:
            {
                params.leftMargin = right;
                params.topMargin = top;
                params.gravity = Gravity.LEFT;
                break;
            }
        }

        view.setLayoutParams(params);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {
                int height = view.getHeight();
                int width = view.getWidth();

                switch (location)
                {

                    case ABOVE:
                    {
                        //If there is enough place to center the view
                        if (viewToAlignCenterHorizontal - width / 2 > 0)
                        {
                            if (viewToAlignCenterHorizontal + width / 2 < onboardingLayoutWidth)
                            {
                                params.leftMargin = viewToAlignCenterHorizontal - width / 2;
                            }
                            else
                            {
                                params.leftMargin = onboardingLayoutWidth - width;
                            }
                        }
                        break;
                    }
                    case BELOW:
                    {
                        //If there is enough place to center the view
                        if (viewToAlignCenterHorizontal - width / 2 > 0)
                        {
                            if (viewToAlignCenterHorizontal + width / 2 < onboardingLayoutWidth)
                            {
                                params.leftMargin = viewToAlignCenterHorizontal - width / 2;
                            }
                            else
                            {
                                params.leftMargin = onboardingLayoutWidth - width;
                            }
                        }
                        break;
                    }
                    case LEFT:
                    {
                        //If the view is smaller than the view to align, center it inside the view
                        if (height < viewToAlignHeight)
                        {
                            params.topMargin = top + (viewToAlignHeight - height) / 2;
                        }
                        //Otherwise center it outside the view.
                        else
                        {
                            params.topMargin = top - (height - viewToAlignHeight) / 2;
                        }
                        break;
                    }
                    case RIGHT:
                    {
                        //If the view is smaller than the view to align, center it inside the view
                        if (height < viewToAlignHeight)
                        {
                            params.topMargin = top + (viewToAlignHeight - height) / 2;
                        }
                        //Otherwise center it outside the view.
                        else
                        {
                            params.topMargin = top - (height - viewToAlignHeight) / 2;
                        }
                        break;
                    }
                }

                view.requestLayout();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}
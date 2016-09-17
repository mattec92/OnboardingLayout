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
    protected void positionView(final View viewToPosition)
    {
        positionAside(viewToPosition);
        alignViewToAlignCenter(viewToPosition);
    }

    private void alignViewToAlignCenter(final View viewToPosition)
    {
        final int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();
        final int onboardingLayoutHeight = onboardingScreen.getOnboardingLayout().getHeight();

        int viewToAlignWidth = right - left;
        final int viewToAlignHeight = bottom - top;

        final int viewToAlignCenterHorizontal = left + viewToAlignWidth / 2;

        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewToPosition.getLayoutParams();

        viewToPosition.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {
                int height = viewToPosition.getHeight();
                int width = viewToPosition.getWidth();

                switch (location)
                {
                    case ABOVE:
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
                            int topMarginCenteredOutside = top - (height - viewToAlignHeight) / 2;
                            int bottomMostPosition = onboardingLayoutHeight - height;
                            if (topMarginCenteredOutside < bottomMostPosition)
                            {
                                params.topMargin = Math.max(0, topMarginCenteredOutside);
                            }
                            else
                            {
                                params.topMargin = bottomMostPosition;
                            }
                        }
                        break;
                    }
                }

                viewToPosition.requestLayout();
                viewToPosition.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                viewToPosition.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
                {

                    @Override
                    public void onGlobalLayout()
                    {
                        viewToPosition.setVisibility(View.VISIBLE);
                        viewToPosition.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        });
    }

}
package se.mattec.onboardinglayout.elements;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.R;
import se.mattec.onboardinglayout.enums.ArrowLocation;

public class TextWithArrowOnboardingElement
        extends TextOnboardingElement
{

    private ArrowLocation arrowLocation;

    public TextWithArrowOnboardingElement(OnboardingScreen onboardingScreen, String text, ArrowLocation arrowLocation)
    {
        super(onboardingScreen, text);
        this.arrowLocation = arrowLocation;
    }

    @Override
    protected View buildView()
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View arrowContainer = null;

        switch (location)
        {
            case ABOVE:
            {
                switch (arrowLocation)
                {
                    case RIGHT:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_below_right, null, false);
                        break;
                    case LEFT:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_below_left, null, false);
                        break;
                    case MIDDLE:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_below_middle, null, false);
                        break;
                    default:
                        throw new IllegalArgumentException("Arrow location " + arrowLocation + " undefined when positioned below.");
                }
                break;
            }
            case BELOW:
            {
                switch (arrowLocation)
                {
                    case RIGHT:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_above_right, null, false);
                        break;
                    case LEFT:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_above_left, null, false);
                        break;
                    case MIDDLE:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_above_middle, null, false);
                        break;
                    default:
                        throw new IllegalArgumentException("Arrow location " + arrowLocation + " undefined when positioned above.");
                }
                break;
            }
            case LEFT:
            {
                switch (arrowLocation)
                {
                    case ABOVE:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_top_right, null, false);
                        break;
                    case BELOW:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_bottom_right, null, false);
                        break;
                    default:
                        throw new IllegalArgumentException("Arrow location " + arrowLocation + " undefined when positioned toLeftOf.");
                }
                break;
            }
            case RIGHT:
            {
                switch (arrowLocation)
                {
                    case ABOVE:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_top_left, null, false);
                        break;
                    case BELOW:
                        arrowContainer = inflater.inflate(R.layout.text_with_arrow_bottom_left, null, false);
                        break;
                    default:
                        throw new IllegalArgumentException("Arrow location " + arrowLocation + " undefined when positioned toLeftOf.");
                }
                break;
            }
        }

        TextView textView = (TextView) arrowContainer.findViewById(R.id.text_with_arrow_text);

        textView.setText(text);

        if (onboardingScreen.getTextColorResourceId() != -1)
        {
            textView.setTextColor(ContextCompat.getColor(context, onboardingScreen.getTextColorResourceId()));
        }

        arrowContainer.setVisibility(View.INVISIBLE);

        return arrowContainer;
    }

    @Override
    protected void positionView(View view)
    {
        positionAside(view);
        alignArrowAtViewToAlignCenter(view);
    }

    private void alignArrowAtViewToAlignCenter(final View viewToPosition)
    {
        final int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();
        final int onboardingLayoutHeight = onboardingScreen.getOnboardingLayout().getHeight();

        final int viewToAlignWidth = right - left;
        final int viewToAlignHeight = bottom - top;

        final int viewToAlignCenterHorizontal = left + viewToAlignWidth / 2;

        final int arrowTopBottomMargin = (int) context.getResources().getDimension(R.dimen.arrow_top_bottom_margin);
        final int arrowLeftRightMargin = (int) context.getResources().getDimension(R.dimen.arrow_left_right_margin);
        final int arrowMiddleMargin = (int) context.getResources().getDimension(R.dimen.arrow_middle_margin);

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
                        int leftMarginExtra = 0;
                        switch (arrowLocation)
                        {
                            case RIGHT:
                                leftMarginExtra = -width / 2 + arrowLeftRightMargin;
                                break;
                            case LEFT:
                                leftMarginExtra = width / 2 - arrowLeftRightMargin;
                                break;
                            case MIDDLE:
                                leftMarginExtra = -arrowMiddleMargin;
                                break;
                        }
                        //If there is enough place to center the view
                        if (viewToAlignCenterHorizontal - width / 2 + leftMarginExtra > 0)
                        {
                            if (viewToAlignCenterHorizontal + width / 2 + leftMarginExtra < onboardingLayoutWidth)
                            {
                                params.leftMargin = viewToAlignCenterHorizontal - width / 2 + leftMarginExtra;
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
                        int topMargin = 0;
                        switch (arrowLocation)
                        {
                            case ABOVE:

                                topMargin = top + viewToAlignHeight / 2 - arrowTopBottomMargin;
                                break;
                            case BELOW:
                                topMargin = top - height + viewToAlignHeight / 2 + arrowTopBottomMargin;
                                break;
                        }

                        int bottomMostPosition = onboardingLayoutHeight - height;
                        if (topMargin < bottomMostPosition)
                        {
                            params.topMargin = Math.max(0, topMargin);
                        }
                        else
                        {
                            params.topMargin = bottomMostPosition;
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

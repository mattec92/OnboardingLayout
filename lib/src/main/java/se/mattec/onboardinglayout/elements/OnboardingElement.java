package se.mattec.onboardinglayout.elements;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.Location;

public abstract class OnboardingElement
{

    protected OnboardingScreen onboardingScreen;
    protected Context context;

    protected Location location;

    protected View viewToAlign;
    protected int top;
    protected int bottom;
    protected int left;
    protected int right;

    protected View view;

    public OnboardingElement(OnboardingScreen onboardingScreen)
    {
        this.onboardingScreen = onboardingScreen;
        this.context = onboardingScreen.getContext();
    }

    protected void getLocation(View view)
    {
        viewToAlign = view;

        View nextView = view;

        do
        {
            top += nextView.getTop();
            left += nextView.getLeft();
            nextView = (View) nextView.getParent();
        }
        while (nextView != null && nextView != onboardingScreen.getOnboardingLayout());

        bottom = top + view.getHeight();
        right = left + view.getWidth();
    }

    public View create()
    {
        view = buildView();
        positionView(view);
        return view;
    }

    public View getView()
    {
        return view;
    }

    public void clear()
    {
        view = null;
    }

    protected abstract View buildView();

    protected abstract void positionView(View view);

    protected void positionAside(View viewToPosition)
    {
        int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
                params.gravity = Gravity.RIGHT;
                break;
            }
            case RIGHT:
            {
                params.leftMargin = right;
                params.gravity = Gravity.LEFT;
                break;
            }
        }

        viewToPosition.setLayoutParams(params);
    }

    protected void positionAtop(View viewToPosition, int width, int height)
    {
        int onboardingLayoutWidth = onboardingScreen.getOnboardingLayout().getWidth();
        int onboardingLayoutHeight = onboardingScreen.getOnboardingLayout().getHeight();

        int viewToAlignWidth = right - left;
        int viewToAlignHeight = bottom - top;

        int widthOffset = 0;
        int heightOffset = 0;
        if (width != FrameLayout.LayoutParams.WRAP_CONTENT && height != FrameLayout.LayoutParams.WRAP_CONTENT)
        {
            widthOffset = viewToAlignWidth - width;
            heightOffset = viewToAlignHeight - height;
        }

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);

        params.topMargin = top + heightOffset / 2;
        params.bottomMargin = onboardingLayoutHeight - bottom - heightOffset / 2;
        params.leftMargin = left + widthOffset / 2;
        params.rightMargin = onboardingLayoutWidth - right - widthOffset / 2;

        viewToPosition.setLayoutParams(params);
    }

    protected void alignViewToAlignCenter(final View viewToPosition)
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
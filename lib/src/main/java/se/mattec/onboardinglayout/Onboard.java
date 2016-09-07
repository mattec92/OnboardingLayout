package se.mattec.onboardinglayout;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Onboard
{

    public static OnboardingScreen in(OnboardingLayout onboardingLayout)
    {
        OnboardingScreen onboardingScreen = new OnboardingScreen(onboardingLayout);
        onboardingLayout.setOnboardingScreen(onboardingScreen);
        return onboardingScreen;
    }

    public static class OnboardingScreen
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
            }

            if (backgroundView != null)
            {
                onboardingLayout.removeView(backgroundView);
            }
        }

    }

    public abstract static class OnboardingElement
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
            this.context = onboardingScreen.context;
        }

        protected void getLocation(View view)
        {
            viewToAlign = view;

            do
            {
                top += view.getTop();
                bottom += view.getBottom();
                left += view.getLeft();
                right += view.getRight();
            }
            while (view.getParent() != onboardingScreen.onboardingLayout);
        }

        protected View create()
        {
            View view = buildView();
            positionView(view);
            return view;
        }

        protected abstract View buildView();

        protected abstract void positionView(View view);

    }

    public static class TextOnboardingElement
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

            if (onboardingScreen.textColorResourceId != -1)
            {
                textView.setTextColor(ContextCompat.getColor(context, onboardingScreen.textColorResourceId));
            }

            view = textView;

            return textView;
        }

        @Override
        protected void positionView(final View view)
        {
            final int onboardingLayoutWidth = onboardingScreen.onboardingLayout.getWidth();

            int viewToAlignWidth = right - left;
            final int viewToAlignHeight = bottom - top;

            final int viewToAlignCenterHorizontal = left + viewToAlignWidth / 2;

            final OnboardingLayout.LayoutParams params = new OnboardingLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            switch (location)
            {
                case ABOVE:
                {
                    params.bottomMargin = onboardingScreen.onboardingLayout.getHeight() - top;
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
                }
            });
        }

    }

    public static class BorderOnboardingElement
            extends OnboardingElement
    {

        private final boolean dashed;

        public BorderOnboardingElement(OnboardingScreen onboardingScreen, boolean dashed)
        {
            super(onboardingScreen);
            this.dashed = dashed;
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

            if (dashed)
            {
                borderView.setBackgroundResource(R.drawable.dashed_border);
            }
            else
            {
                borderView.setBackgroundResource(R.drawable.border);
            }

            if (onboardingScreen.borderColorResourceId != -1)
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
            final int onboardingLayoutWidth = onboardingScreen.onboardingLayout.getWidth();
            final int onboardingLayoutHeight = onboardingScreen.onboardingLayout.getHeight();

            int viewToAlignWidth = right - left;
            final int viewToAlignHeight = bottom - top;

            final OnboardingLayout.LayoutParams params = new OnboardingLayout.LayoutParams(viewToAlignWidth, viewToAlignHeight);

            params.topMargin = top;
            params.bottomMargin = onboardingLayoutHeight - bottom;
            params.leftMargin = left;
            params.rightMargin = onboardingLayoutWidth - right;

            view.setLayoutParams(params);
        }
    }

    public static class HoleOnboardingElement
            extends OnboardingElement
    {

        public boolean isCircular;
        public BackgroundView.Hole hole;

        public HoleOnboardingElement(OnboardingScreen onboardingScreen, boolean isCircular)
        {
            super(onboardingScreen);
            this.isCircular = isCircular;
        }

        public OnboardingScreen around(View view)
        {
            location = Location.AROUND;
            getLocation(view);
            hole = new BackgroundView.Hole(left, top, right, bottom, isCircular);
            return onboardingScreen;
        }

        @Override
        protected View buildView()
        {
            return null;
        }

        @Override
        protected void positionView(View view)
        {

        }

    }

}

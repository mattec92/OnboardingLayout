package se.mattec.onboardinglayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

        public void show()
        {
            for (OnboardingElement element : onboardingElements)
            {
                onboardingLayout.addView(element.create());
            }

            onboardingLayout.requestLayout();
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

        public OnboardingElement(OnboardingScreen onboardingScreen)
        {
            this.onboardingScreen = onboardingScreen;
            this.context = onboardingScreen.context;
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

        protected void getLocation(View view)
        {
            viewToAlign = view;
            top = view.getTop();
            bottom = view.getBottom();
            left = view.getLeft();
            right = view.getRight();
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

        @Override
        protected View buildView()
        {
            TextView textView = new TextView(context);
            textView.setText(text);
            return textView;
        }

        @Override
        protected void positionView(View view)
        {
            OnboardingLayout.LayoutParams params = new OnboardingLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            switch (location)
            {
                case ABOVE:
                {
                    params.addRule(RelativeLayout.ABOVE, viewToAlign.getId());
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.bottomMargin = onboardingScreen.onboardingLayout.getHeight() - top;
                    break;
                }
                case BELOW:
                {
                    params.addRule(RelativeLayout.BELOW, viewToAlign.getId());
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                    params.topMargin = bottom;
                    break;
                }
                case LEFT:
                {
                    params.addRule(RelativeLayout.LEFT_OF, viewToAlign.getId());
                    params.addRule(RelativeLayout.ALIGN_TOP, viewToAlign.getId());
//                    params.rightMargin = left;
                    break;
                }
                case RIGHT:
                {
                    params.addRule(RelativeLayout.RIGHT_OF, viewToAlign.getId());
                    params.addRule(RelativeLayout.ALIGN_TOP, viewToAlign.getId());
//                    params.leftMargin = right;
                    break;
                }
            }

            view.setLayoutParams(params);
        }

    }

}
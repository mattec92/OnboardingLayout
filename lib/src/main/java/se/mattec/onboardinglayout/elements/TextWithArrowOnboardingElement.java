package se.mattec.onboardinglayout.elements;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
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

        view = arrowContainer;

        return arrowContainer;
    }

}

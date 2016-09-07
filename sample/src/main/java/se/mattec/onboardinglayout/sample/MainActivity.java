package se.mattec.onboardinglayout.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import se.mattec.onboardinglayout.Onboard;
import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.views.OnboardingLayout;

public class MainActivity
        extends AppCompatActivity
{

    private OnboardingLayout onboardingLayout;
    private Button onboardingShowButton;
    private Button onboardingClearButton;
    private View centerView;
    private View topLeftView;
    private View topRightView;
    private View bottomLeftView;
    private View bottomRightView;

    private OnboardingScreen onboardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupButton();
    }

    private void initViews()
    {
        onboardingLayout = (OnboardingLayout) findViewById(R.id.onboarding_layout);
        onboardingShowButton = (Button) findViewById(R.id.onboarding_show_button);
        onboardingClearButton = (Button) findViewById(R.id.onboarding_clear_button);
        centerView = findViewById(R.id.center_view);
        topLeftView = findViewById(R.id.top_left_view);
        topRightView = findViewById(R.id.top_right_view);
        bottomLeftView = findViewById(R.id.bottom_left_view);
        bottomRightView = findViewById(R.id.bottom_right_view);
    }

    private void setupButton()
    {
        onboardingShowButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear();
                }
                openOnboarding();
            }
        });

        onboardingClearButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear();
                }
            }
        });
    }

    private void openOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withTextColor(R.color.white)
                .withBorderColor(R.color.white)
                .withText("Above the center").above(centerView)
                .withText("Below the center").below(centerView)
                .withText("To left of the center\nTwo lines").left(centerView)
                .withText("To right of the center").right(centerView)
                .withText("Right of top left").right(topLeftView)
                .withText("Below top left").below(topLeftView)
                .withText("Left of top right").left(topRightView)
                .withText("Below top right").below(topRightView)
                .withText("Above bottom left").above(bottomLeftView)
                .withText("Right bottom left").right(bottomLeftView)
                .withText("Above bottom right").above(bottomRightView)
                .withText("Left bottom right").left(bottomRightView)
                .withBorder(true, true).around(topLeftView)
                .withBorder(false, false).around(topRightView)
                .withBorder(true, false).around(bottomRightView)
                .withBorder(false, true).around(bottomLeftView)
                .withHole(true).around(bottomLeftView)
                .withHole(false).around(bottomRightView)
                .show();
    }

}

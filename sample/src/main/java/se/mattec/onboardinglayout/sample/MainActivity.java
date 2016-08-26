package se.mattec.onboardinglayout.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import se.mattec.onboardinglayout.Onboard;
import se.mattec.onboardinglayout.OnboardingLayout;

public class MainActivity
        extends AppCompatActivity
{

    private OnboardingLayout onboardingLayout;
    private Button onboardingButton;
    private View centerView;
    private View topLeftView;
    private View topRightView;
    private View bottomLeftView;
    private View bottomRightView;

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
        onboardingButton = (Button) findViewById(R.id.onboarding_button);
        centerView = findViewById(R.id.center_view);
        topLeftView = findViewById(R.id.top_left_view);
        topRightView = findViewById(R.id.top_right_view);
        bottomLeftView = findViewById(R.id.bottom_left_view);
        bottomRightView = findViewById(R.id.bottom_right_view);
    }

    private void setupButton()
    {
        onboardingButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                openOnboarding();
            }
        });
    }

    private void openOnboarding()
    {
        Onboard.in(onboardingLayout)
                .withText("Above the center")
                .above(centerView)
                .withText("Below the center")
                .below(centerView)
                .withText("To left of the center")
                .left(centerView)
                .withText("To right of the center")
                .right(centerView)
                .show();
    }

}

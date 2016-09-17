package se.mattec.onboardinglayout.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import se.mattec.onboardinglayout.Onboard;
import se.mattec.onboardinglayout.OnboardingScreen;
import se.mattec.onboardinglayout.enums.ArrowLocation;
import se.mattec.onboardinglayout.views.OnboardingLayout;

public class MainActivity
        extends AppCompatActivity
{

    private OnboardingLayout onboardingLayout;
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initViews()
    {
        onboardingLayout = (OnboardingLayout) findViewById(R.id.onboarding_layout);
        centerView = findViewById(R.id.center_view);
        topLeftView = findViewById(R.id.top_left_view);
        topRightView = findViewById(R.id.top_right_view);
        bottomLeftView = findViewById(R.id.bottom_left_view);
        bottomRightView = findViewById(R.id.bottom_right_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.text:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                openTextOnboarding();
                break;
            }
            case R.id.arrow:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                openArrowOnboarding();
                break;
            }
            case R.id.border:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                openBorderOnboarding();
                break;
            }
            case R.id.hole:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                openHoleOnboarding();
                break;
            }
            case R.id.image:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                openImageOnboarding();
                break;
            }
            case R.id.clear:
            {
                if (onboardingScreen != null)
                {
                    onboardingScreen.clear(true);
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openTextOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withTextColor(R.color.white)
                .withText("Above the center").above(centerView)
                .withText("Below the center").below(centerView)
                .withText("To left of the center\nTwo lines").toLeftOf(centerView)
                .withText("To right of the center").toRightOf(centerView)
                .withText("Right of top left").toRightOf(topLeftView)
                .withText("Below top left").below(topLeftView)
                .withText("Left of top right").toLeftOf(topRightView)
                .withText("Below top right").below(topRightView)
                .withText("Above bottom left").above(bottomLeftView)
                .withText("Right bottom left").toRightOf(bottomLeftView)
                .withText("Above bottom right").above(bottomRightView)
                .withText("Left bottom right").toLeftOf(bottomRightView)
                .show(true);
    }

    private void openArrowOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withTextColor(R.color.white)
                .withTextAndArrow("Above the center", ArrowLocation.RIGHT).above(centerView)
                .withTextAndArrow("Below the center", ArrowLocation.LEFT).below(centerView)
                .withTextAndArrow("To left of the center\nTwo lines", ArrowLocation.ABOVE).toLeftOf(centerView)
                .withTextAndArrow("To right of the center", ArrowLocation.BELOW).toRightOf(centerView)
                .withTextAndArrow("Right of top left", ArrowLocation.ABOVE).toRightOf(topLeftView)
                .withTextAndArrow("Below top left", ArrowLocation.LEFT).below(topLeftView)
                .withTextAndArrow("Left of top right", ArrowLocation.ABOVE).toLeftOf(topRightView)
                .withTextAndArrow("Below top right", ArrowLocation.RIGHT).below(topRightView)
                .withTextAndArrow("Above bottom left", ArrowLocation.LEFT).above(bottomLeftView)
                .withTextAndArrow("Right bottom left", ArrowLocation.BELOW).toRightOf(bottomLeftView)
                .withTextAndArrow("Above bottom right", ArrowLocation.RIGHT).above(bottomRightView)
                .withTextAndArrow("Left bottom right", ArrowLocation.BELOW).toLeftOf(bottomRightView)
                .show(true);
    }

    private void openBorderOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withBorderColor(R.color.white)
                .withBorder(true).around(topLeftView)
                .withBorder(false).around(topRightView)
                .withDashedBorder(false).around(bottomRightView)
                .withDashedBorder(true).around(bottomLeftView)
                .show(true);
    }

    private void openHoleOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withHole(true).around(topLeftView)
                .withHole(false).around(topRightView)
                .withHole(true).around(bottomLeftView)
                .withHole(false).around(bottomRightView)
                .show(true);
    }

    private void openImageOnboarding()
    {
        onboardingScreen = Onboard.in(onboardingLayout)
                .withOverlayColor(R.color.black_trans)
                .withImage(R.mipmap.ic_launcher).atop(centerView)
                .withImage(R.mipmap.ic_launcher).above(centerView)
                .withImage(R.mipmap.ic_launcher).below(centerView)
                .withImage(R.mipmap.ic_launcher).toLeftOf(centerView)
                .withImage(R.mipmap.ic_launcher).toRightOf(centerView)
                .withImage(R.mipmap.ic_launcher).atop(topLeftView)
                .withImage(R.mipmap.ic_launcher).toRightOf(topLeftView)
                .withImage(R.mipmap.ic_launcher).below(topLeftView)
                .withImage(R.mipmap.ic_launcher).atop(topRightView)
                .withImage(R.mipmap.ic_launcher).toLeftOf(topRightView)
                .withImage(R.mipmap.ic_launcher).below(topRightView)
                .withImage(R.mipmap.ic_launcher).atop(bottomLeftView)
                .withImage(R.mipmap.ic_launcher).above(bottomLeftView)
                .withImage(R.mipmap.ic_launcher).toRightOf(bottomLeftView)
                .withImage(R.mipmap.ic_launcher).atop(bottomRightView)
                .withImage(R.mipmap.ic_launcher).above(bottomRightView)
                .withImage(R.mipmap.ic_launcher).toLeftOf(bottomRightView)
                .show(true);
    }

}

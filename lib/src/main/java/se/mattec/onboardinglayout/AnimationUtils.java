package se.mattec.onboardinglayout;

import android.animation.Animator;
import android.view.View;

public class AnimationUtils
{

    private static final int ANIMATION_DURATION = 300;

    public static void fadeIn(View view)
    {
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    public static void fadeOut(View view, Animator.AnimatorListener listener)
    {
        view.setAlpha(1f);
        view.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(listener)
                .start();
    }

}

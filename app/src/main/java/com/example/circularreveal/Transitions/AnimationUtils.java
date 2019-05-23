package com.example.circularreveal.Transitions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Interpolator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class AnimationUtils {

    public interface AnimationFinishedListener {
        void onAnimationFinished();
    }

    public static final String TAG = "AnimationUtils";

    /**
     * Enter animation
     *
     * @param context
     * @param view
     * @param revealSettings
     * @param startColor
     * @param endColor
     * @param duration
     * @param interpolator
     */

    public static void registerCircularRevealAnimation(final Context context, final View view, final RevealAnimationSetting revealSettings,
                                                       final int startColor, final int endColor, final int duration, final Interpolator interpolator) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    int cx = revealSettings.getCenterX();
                    int cy = revealSettings.getCenterY();
                    int width = revealSettings.getWidth();
                    int height = revealSettings.getHeight();

                    //Simply use the diagonal of the view
                    float finalRadius = (float) Math.sqrt(width * width + height * height);
                    float startRadius = (float)Math.sqrt(180*180 + 180 * 180);
                    Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, startRadius, finalRadius).setDuration(duration);
                    anim.setInterpolator(interpolator);
                    anim.start();
                    startColorAnimation(view, startColor, endColor, duration);
                }
            });
        }
    }

    static void startColorAnimation(final View view, final int startColor, final int endColor, int duration) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Set color of background of new fragment/view
                /*view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));*/
                /*view.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());*/
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    /**
     * Exit animation
     * @param context
     * @param view
     * @param revealSettings
     * @param startColor
     * @param endColor
     * @param listener
     */
    public static void startCircularExitAnimation(final Context context, final View view, final RevealAnimationSetting revealSettings, final int startColor, final int endColor, final AnimationFinishedListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = revealSettings.getCenterX();
            int cy = revealSettings.getCenterY();
            int width = revealSettings.getWidth();
            int height = revealSettings.getHeight();
            int duration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

            /*float endRadius = (float)Math.sqrt(180*180 + 180 * 180);*/
            float initRadius = (float) Math.sqrt(width * width + height * height);
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initRadius, 0);
            anim.setDuration(duration);
            anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listener.onAnimationFinished();
                }
            });
            anim.start();
            Log.d(TAG, "startCircularExitAnimation: anim.start()");
            startColorAnimation(view, startColor, endColor, duration);
        } else {
            listener.onAnimationFinished();
        }
    }

    //We use this to remove the Fragment only when the animation finished
    public interface Dismissible {
        interface OnDismissedListener {
            void onDismissed();
        }

        void dismiss(OnDismissedListener listener);
    }
}











package com.example.circularreveal;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.circularreveal.Transitions.AnimationUtils;
import com.example.circularreveal.Transitions.RevealAnimationSetting;

public class DetailFragment extends Fragment implements AnimationUtils.Dismissible {

    private LinearLayout layoutContainer;
    private static final String TAG = "DetailFragment";

    private RevealAnimationSetting revealAnimationSetting;
    View mView;

    public DetailFragment(RevealAnimationSetting revealAnimationSetting) {
        this.revealAnimationSetting = revealAnimationSetting;
    }

    public DetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        AnimationUtils.registerCircularRevealAnimation(getContext(), view, revealAnimationSetting, R.color.colorWhite, R.color.colorYellow,
                getContext().getResources().getInteger(android.R.integer.config_shortAnimTime), new LinearInterpolator());
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutContainer = view.findViewById(R.id.detail_fragment_container);
    }

    @Override
    public void dismiss(final OnDismissedListener listener) {

        float centerX = revealAnimationSetting.getCenterX();
        float centerY = revealAnimationSetting.getCenterY();
        float width = mView.getWidth();
        float height = mView.getHeight();

        Log.d(TAG, "dismiss: centerX : " + centerX + "centerY : " + centerY);
        Log.d(TAG, "dismiss: width : " + width + "height : " + height);

        RevealAnimationSetting exitSettings = new RevealAnimationSetting((int)centerX, (int) centerY,
                (int)width, (int) height);

        AnimationUtils.startCircularExitAnimation(getContext(), mView, exitSettings, R.color.colorWhite, R.color.colorYellow,
                new AnimationUtils.AnimationFinishedListener() {
                    @Override
                    public void onAnimationFinished() {
                        layoutContainer.setBackgroundColor(Color.TRANSPARENT);
                        listener.onDismissed();
                    }
                });

    }
}




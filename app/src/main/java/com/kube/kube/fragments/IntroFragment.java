package com.kube.kube.fragments;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

import java.util.Random;

/**
 * Created by ant on 2016-12-04.
 */

public class IntroFragment extends Fragment {

    Handler mHandler = null;
    private OnFragmentListener mFragmentListener = null;

    public IntroFragment(Handler h, OnFragmentListener f) {
        this.mHandler = h;
        this.mFragmentListener = f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_intro, container, false);

        ////


        ImageView start_k = (ImageView) rootView.findViewById(R.id.start_k);
        ImageView start_u = (ImageView) rootView.findViewById(R.id.start_u);
        ImageView start_b = (ImageView) rootView.findViewById(R.id.start_b);
        ImageView start_e= (ImageView) rootView.findViewById(R.id.start_e);

        ImageView starts [] = {start_k, start_u, start_b, start_e};

        for (int i = 0; i < starts.length; i++) {
            int ranNum = new Random().nextInt(200)-100;
            TranslateAnimation jumpingAnim = new TranslateAnimation(0,0,0,ranNum);
            //ObjectAnimator jumpingAnim = ObjectAnimator.ofFloat(starts[i], "translationY", 0, ranNum);
            jumpingAnim.setDuration(1000);
            jumpingAnim.setRepeatCount(3);
            jumpingAnim.setRepeatMode(ValueAnimator.REVERSE);
            starts[i].startAnimation(jumpingAnim);

            jumpingAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    overridePendingTransition(0, R.anim.fade_out);
                    Button btn_conntect_blu = (Button) rootView.findViewById(R.id.button_connection_blutooth);
                    Button btn_workspace = (Button) rootView.findViewById(R.id.button_workspace);
                    btn_conntect_blu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_START_FINDING_BLUETOOTH, 0);
                        }
                    });
                    btn_workspace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE, 0);
                        }
                    });
                    btn_conntect_blu.setVisibility(View.VISIBLE);
                    btn_workspace.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }



        ////
//
//        Handler h = new Handler();
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION, 0);
//            }
//        }, 2000);

        return rootView;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
            }
        }
    };
}

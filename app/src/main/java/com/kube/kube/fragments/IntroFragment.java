package com.kube.kube.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

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
        View rootView = inflater.inflate(R.layout.fragment_intro, container, false);

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION, 0);
            }
        }, 2000);

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

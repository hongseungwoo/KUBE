package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-04.
 */

public class PseudoFragment extends DialogFragment {

    private OnFragmentListener mFragmentListener;

    public PseudoFragment(OnFragmentListener mFragmentListener) {
        this.mFragmentListener = mFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_pseudo, container, false);

        /**
         * //TODO copy&paste code of onCreate() of PesudoActivity here
         */

        Button b = (Button) rootView.findViewById(R.id.fragment_pseudo_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE, 0);
            }
        });



        return rootView;
    }
}

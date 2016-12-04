package com.kube.kube.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-04.
 */

public class ConnectionFragment extends Fragment {

    Handler mHandler = null;
    OnFragmentListener mFragmentListener = null;

    public ConnectionFragment(Handler h, OnFragmentListener f) {
        this.mHandler = h;
        this.mFragmentListener = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connection, container, false);

//        Button b1 = (Button) rootView.findViewById(R.id.button_connection_connection);
//        Button b2 = (Button) rootView.findViewById(R.id.button_connection_intro);
        Button b3 = (Button) rootView.findViewById(R.id.button_connection_workspace);
//        Button b4 = (Button) rootView.findViewById(R.id.button_connection_find_bt);
        Button b5 = (Button) rootView.findViewById(R.id.button_connection_test);
//
//        b1.setOnClickListener(mClickListener);
//        b2.setOnClickListener(mClickListener);
        b3.setOnClickListener(mClickListener);
//        b4.setOnClickListener(mClickListener);
        b5.setOnClickListener(mClickListener);

        Button connect = (Button) rootView.findViewById(R.id.button_connection_ble);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_START_FINDING_BLUETOOTH, 0);
            }
        });

        return rootView;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
//                case R.id.button_connection_connection:
////                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION);
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION);
//                    break;
//                case R.id.button_connection_intro:
////                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO);
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO);
//                    break;
                case R.id.button_connection_workspace:
//                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE);
                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE, 0);
                    break;
//                case R.id.button_connection_find_bt:
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_START_FINDING_BLUETOOTH);
//                case R.id.button_intro_connection:
//                    break;
//                case R.id.button_intro_intro:
//                    break;
//                case R.id.button_intro_workspace:
//                    break;
//                case R.id.button_workspace_connection:
//                    break;
//                case R.id.button_workspace_intro:
//                    break;
//                case R.id.button_workspace_workspace:
//                    break;
                case R.id.button_connection_test:
                    break;
            }
        }
    };
}

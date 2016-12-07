package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-07.
 */
public class SaveDialogFragment extends DialogFragment implements View.OnClickListener{

    private OnFragmentListener mFragmentListener;

    public SaveDialogFragment(OnFragmentListener mFragmentListener) {
        this.mFragmentListener = mFragmentListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_save, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Button b1 = (Button) rootView.findViewById(R.id.dialog_save_button_1);
        Button b2 = (Button) rootView.findViewById(R.id.dialog_save_button_2);
        Button b3 = (Button) rootView.findViewById(R.id.dialog_save_button_3);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dialog_save_button_1:
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SAVE_1, 0);
                break;
            case R.id.dialog_save_button_2:
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SAVE_2, 0);
                break;
            case R.id.dialog_save_button_3:
                mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SAVE_3, 0);
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }
}

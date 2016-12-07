package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-07.
 */

public class SendDialogFragment extends DialogFragment {

    private OnFragmentListener mFragmentListener;

    private Button mButtonSend;
    private Button mButtonCancel;
    private TextView mTextTrans;
    private TextView mTextPseudo;

    private String trans;
    private String pseudo;

    public SendDialogFragment(OnFragmentListener mFragmentListener) {
        this.mFragmentListener = mFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_send, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mButtonSend = (Button) rootView.findViewById(R.id.dialog_send_button_send);
        mButtonCancel = (Button) rootView.findViewById(R.id.dialog_send_button_cancel);
        mTextTrans = (TextView) rootView.findViewById(R.id.dialog_send_text_trans);
        mTextPseudo = (TextView) rootView.findViewById(R.id.dialog_send_text_korean);

        if(trans != null && pseudo != null) {
            mTextTrans.setText(trans);
            mTextPseudo.setText(pseudo);
        }

        mButtonSend.setOnClickListener(mClickListener);
        mButtonCancel.setOnClickListener(mClickListener);

        return rootView;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_send_button_send:
                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SEND_MSG, 0);
                    dismiss();
                    break;
                case R.id.dialog_send_button_cancel:
                    dismiss();
                    break;

            }
        }
    };

    public void setTrans(String trans, String pseudo) {
        this.trans = trans;
        this.pseudo = pseudo;
    }
}

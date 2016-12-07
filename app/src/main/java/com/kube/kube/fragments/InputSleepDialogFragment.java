package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kube.kube.R;

/**
 * Created by ant on 2016-12-07.
 */

public class InputSleepDialogFragment extends DialogFragment {

    OnFragmentListener mFragmentListener;

    private Button mButtonSave;
    private Button mCancelSave;

    private EditText mEdit;

    public InputSleepDialogFragment(OnFragmentListener mFragmentListener) {
        this.mFragmentListener = mFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_input_sleep, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mButtonSave = (Button) rootView.findViewById(R.id.saveButtonSleep);
        mCancelSave = (Button) rootView.findViewById(R.id.cancelButtonSleep);
        mEdit = (EditText) rootView.findViewById(R.id.numOptionEditTextSleep);

        mButtonSave.setOnClickListener(mClickListener);
        mCancelSave.setOnClickListener(mClickListener);

        return rootView;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.saveButtonSleep:
                    String input = mEdit.getText().toString();
                    if(input.length() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "빈 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] sendingStr = {"EMPTY", input, ""};
                        mFragmentListener.onInputFragmentCallBack(sendingStr);
                        dismiss();
                    }
                    break;
                case R.id.cancelButtonSleep:
                    dismiss();
                    break;
            }
        }
    };
}

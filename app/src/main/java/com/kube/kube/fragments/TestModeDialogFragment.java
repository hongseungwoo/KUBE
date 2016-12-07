package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-04.
 */

public class TestModeDialogFragment extends DialogFragment implements View.OnClickListener{

    private OnFragmentListener mFragmentListener;

    Button[] dcImg;
    Button[] smImg;
    Button[] ldImg;
    Button[] irImg;
    Button[] usImg;

    String getStr = "NUM:DC5SM0LD3IR4US1\n"; // for test

    public TestModeDialogFragment(OnFragmentListener mFragmentListener, String getStr) {
        this.mFragmentListener = mFragmentListener;
        this.getStr = getStr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_testmode, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        StringBuilder builder = new StringBuilder();
        builder.append(getStr.charAt(6));
        builder.append(getStr.charAt(9));
        builder.append(getStr.charAt(12));
        builder.append(getStr.charAt(15));
        builder.append(getStr.charAt(18));

        String s = new String(builder);

        int dcNum = (int) s.charAt(0)-'0';
        int smNum = (int) s.charAt(1)-'0';
        int ldNum = (int) s.charAt(2)-'0';
        int irNum = (int) s.charAt(3)-'0';
        int usNum = (int) s.charAt(4)-'0';

        dcImg = new Button[6];
        smImg = new Button[6];
        ldImg = new Button[6];
        irImg = new Button[6];
        usImg = new Button[6];

        dcImg[0] = (Button) rootView.findViewById(R.id.testmode_dcimg_1);
        dcImg[1] = (Button) rootView.findViewById(R.id.testmode_dcimg_2);
        dcImg[2] = (Button) rootView.findViewById(R.id.testmode_dcimg_3);
        dcImg[3] = (Button) rootView.findViewById(R.id.testmode_dcimg_4);
        dcImg[4] = (Button) rootView.findViewById(R.id.testmode_dcimg_5);

        smImg[0] = (Button) rootView.findViewById(R.id.testmode_smimg_1);
        smImg[1] = (Button) rootView.findViewById(R.id.testmode_smimg_2);
        smImg[2] = (Button) rootView.findViewById(R.id.testmode_smimg_3);
        smImg[3] = (Button) rootView.findViewById(R.id.testmode_smimg_4);
        smImg[4] = (Button) rootView.findViewById(R.id.testmode_smimg_5);

        ldImg[0] = (Button) rootView.findViewById(R.id.testmode_ldimg_1);
        ldImg[1] = (Button) rootView.findViewById(R.id.testmode_ldimg_2);
        ldImg[2] = (Button) rootView.findViewById(R.id.testmode_ldimg_3);
        ldImg[3] = (Button) rootView.findViewById(R.id.testmode_ldimg_4);
        ldImg[4] = (Button) rootView.findViewById(R.id.testmode_ldimg_5);

        irImg[0] = (Button) rootView.findViewById(R.id.testmode_irimg_1);
        irImg[1] = (Button) rootView.findViewById(R.id.testmode_irimg_2);
        irImg[2] = (Button) rootView.findViewById(R.id.testmode_irimg_3);
        irImg[3] = (Button) rootView.findViewById(R.id.testmode_irimg_4);
        irImg[4] = (Button) rootView.findViewById(R.id.testmode_irimg_5);

        usImg[0] = (Button) rootView.findViewById(R.id.testmode_usimg_1);
        usImg[1] = (Button) rootView.findViewById(R.id.testmode_usimg_2);
        usImg[2] = (Button) rootView.findViewById(R.id.testmode_usimg_3);
        usImg[3] = (Button) rootView.findViewById(R.id.testmode_usimg_4);
        usImg[4] = (Button) rootView.findViewById(R.id.testmode_usimg_5);

        buttonSetting(dcImg, dcNum);
        buttonSetting(smImg, smNum);
        buttonSetting(ldImg, ldNum);
        buttonSetting(irImg, irNum);
        buttonSetting(usImg, usNum);

        Button b = (Button) rootView.findViewById(R.id.testmode_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    private void buttonSetting(Button[] views, int num) {
        int i;
        for(i = 0; i < num; i++) {
            views[i].setClickable(true);
            views[i].setVisibility(View.VISIBLE);
            views[i].setOnClickListener(this);
        }
        for(i = num; i < 5; i++) {
            views[i].setClickable(false);
            views[i].setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        mFragmentListener.onTestModeFragmentCallBack(v.getId());
    }
}

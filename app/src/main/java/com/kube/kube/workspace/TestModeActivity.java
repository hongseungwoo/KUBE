package com.kube.kube.workspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.kube.kube.R;

/**
 * Created by ant on 2016-12-01.
 */

public class TestModeActivity extends Activity {

    ImageView[] dcImg;
    ImageView[] smImg;
    ImageView[] ldImg;
    ImageView[] irImg;
    ImageView[] usImg;

    String getStr = "50341"; // for test

    private final int MAX_MODULE = 5;
    int[] maxNumOfModule;

    public TestModeActivity() {}
    public TestModeActivity(String getStr) {
        this.getStr = getStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_testmode);

        Intent getIntent = getIntent();

        int dcNum = Integer.valueOf(getStr.charAt(0));
        int smNum = Integer.valueOf(getStr.charAt(1));
        int ldNum = Integer.valueOf(getStr.charAt(2));
        int irNum = Integer.valueOf(getStr.charAt(3));
        int usNum = Integer.valueOf(getStr.charAt(4));

//        dcImg = new ImageView[dcNum];
//        smImg = new ImageView[smNum];
//        ldImg = new ImageView[ldNum];
//        irImg = new ImageView[irNum];
//        usImg = new ImageView[usNum];

        dcImg = new ImageView[MAX_MODULE];
        smImg = new ImageView[MAX_MODULE];
        ldImg = new ImageView[MAX_MODULE];
        irImg = new ImageView[MAX_MODULE];
        usImg = new ImageView[MAX_MODULE];

        setImageViewsClickable(dcImg, dcNum);
        setImageViewsClickable(smImg, smNum);
        setImageViewsClickable(ldImg, ldNum);
        setImageViewsClickable(irImg, irNum);
        setImageViewsClickable(usImg, usNum);
    }

    private void setImageViewsClickable(ImageView[] views, int num) {
        for(int i = 0; i < num; i++) {
            views[i].setClickable(true);
        }
        for(int i = num; i < MAX_MODULE; i++) {
            views[i].setClickable(false);
        }
    }

}


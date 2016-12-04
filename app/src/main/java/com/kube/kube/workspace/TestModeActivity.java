package com.kube.kube.workspace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.kube.kube.MainActivity;
import com.kube.kube.R;
import com.kube.kube.bluetooth.BleManager;
import com.kube.kube.utils.Constants;

/**
 * Created by ant on 2016-12-01.
 */

public class TestModeActivity extends Activity {

    Context mContext;
    private BleManager mBleManager = null;

    MainActivity.ActivityHandler mActivityHandler;

    Button[] dcImg;
    Button[] smImg;
    Button[] ldImg;
    Button[] irImg;
    Button[] usImg;

    String getStr = "NUM:DC5SM0LD3IR4US1\n"; // for test

    private final int MAX_MODULE = 5;
    int[] maxNumOfModule;

    public TestModeActivity() {}
    public TestModeActivity(String getStr, MainActivity.ActivityHandler h) {
        this.getStr = getStr;
        this.mActivityHandler = h;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_testmode);

        mContext = getApplicationContext();

        Intent getIntent = getIntent();

        StringBuilder builder = new StringBuilder();
        builder.append(getStr.charAt(6));
        builder.append(getStr.charAt(9));
        builder.append(getStr.charAt(12));
        builder.append(getStr.charAt(15));
        builder.append(getStr.charAt(18));

        Constants.MODULE_NUM_STRING = new String(builder);

        String s = Constants.MODULE_NUM_STRING;

        int dcNum = (int) s.charAt(0)-'0';
        int smNum = (int) s.charAt(1)-'0';
        int ldNum = (int) s.charAt(2)-'0';
        int irNum = (int) s.charAt(3)-'0';
        int usNum = (int) s.charAt(4)-'0';

//        dcImg = new ImageView[dcNum];
//        smImg = new ImageView[smNum];
//        ldImg = new ImageView[ldNum];
//        irImg = new ImageView[irNum];
//        usImg = new ImageView[usNum];

        dcImg = new Button[6];
        smImg = new Button[6];
        ldImg = new Button[6];
        irImg = new Button[6];
        usImg = new Button[6];

        dcImg[0] = (Button) findViewById(R.id.testmode_dcimg_1);
        dcImg[1] = (Button) findViewById(R.id.testmode_dcimg_2);
        dcImg[2] = (Button) findViewById(R.id.testmode_dcimg_3);
        dcImg[3] = (Button) findViewById(R.id.testmode_dcimg_4);
        dcImg[4] = (Button) findViewById(R.id.testmode_dcimg_5);

        smImg[0] = (Button) findViewById(R.id.testmode_smimg_1);
        smImg[1] = (Button) findViewById(R.id.testmode_smimg_2);
        smImg[2] = (Button) findViewById(R.id.testmode_smimg_3);
        smImg[3] = (Button) findViewById(R.id.testmode_smimg_4);
        smImg[4] = (Button) findViewById(R.id.testmode_smimg_5);

        ldImg[0] = (Button) findViewById(R.id.testmode_ldimg_1);
        ldImg[1] = (Button) findViewById(R.id.testmode_ldimg_2);
        ldImg[2] = (Button) findViewById(R.id.testmode_ldimg_3);
        ldImg[3] = (Button) findViewById(R.id.testmode_ldimg_4);
        ldImg[4] = (Button) findViewById(R.id.testmode_ldimg_5);

        irImg[0] = (Button) findViewById(R.id.testmode_irimg_1);
        irImg[1] = (Button) findViewById(R.id.testmode_irimg_2);
        irImg[2] = (Button) findViewById(R.id.testmode_irimg_3);
        irImg[3] = (Button) findViewById(R.id.testmode_irimg_4);
        irImg[4] = (Button) findViewById(R.id.testmode_irimg_5);

        usImg[0] = (Button) findViewById(R.id.testmode_usimg_1);
        usImg[1] = (Button) findViewById(R.id.testmode_usimg_2);
        usImg[2] = (Button) findViewById(R.id.testmode_usimg_3);
        usImg[3] = (Button) findViewById(R.id.testmode_usimg_4);
        usImg[4] = (Button) findViewById(R.id.testmode_usimg_5);

        setImageViewsVisibleAndClickable(dcImg, dcNum);
        setImageViewsVisibleAndClickable(smImg, smNum);
        setImageViewsVisibleAndClickable(ldImg, ldNum);
        setImageViewsVisibleAndClickable(irImg, irNum);
        setImageViewsVisibleAndClickable(usImg, usNum);

        Button b = (Button) findViewById(R.id.testmode_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void setImageViewsVisibleAndClickable(Button[] views, int num) {
        int i;
        for(i = 0; i < num; i++) {
            views[i].setClickable(true);
            views[i].setVisibility(View.VISIBLE);
        }
        for(i = num; i < 5; i++) {
            views[i].setClickable(false);
            views[i].setVisibility(View.INVISIBLE);
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mActivityHandler.obtainMessage(Constants.HANDLER_TEST_ACTIVITY, v.getId()).sendToTarget();

            /*switch(v.getId()) {
                case R.id.testmode_dcimg_1:
                    break;
                case R.id.testmode_dcimg_2:
                    break;
                case R.id.testmode_dcimg_3:
                    break;
                case R.id.testmode_dcimg_4:
                    break;
                case R.id.testmode_dcimg_5:
                    break;
                case R.id.testmode_smimg_1:
                    break;
                case R.id.testmode_smimg_2:
                    break;
                case R.id.testmode_smimg_3:
                    break;
                case R.id.testmode_smimg_4:
                    break;
                case R.id.testmode_smimg_5:
                    break;

                case R.id.testmode_ldimg_1:
                    break;
                case R.id.testmode_ldimg_2:
                    break;
                case R.id.testmode_ldimg_3:
                    break;
                case R.id.testmode_ldimg_4:
                    break;
                case R.id.testmode_ldimg_5:
                    break;

                case R.id.testmode_irimg_1:
                    break;
                case R.id.testmode_irimg_2:
                    break;
                case R.id.testmode_irimg_3:
                    break;
                case R.id.testmode_irimg_4:
                    break;
                case R.id.testmode_irimg_5:
                    break;

                case R.id.testmode_usimg_1:
                    break;
                case R.id.testmode_usimg_2:
                    break;
                case R.id.testmode_usimg_3:
                    break;
                case R.id.testmode_usimg_4:
                    break;
                case R.id.testmode_usimg_5:
                    break;

            }*/
        }
    };

}


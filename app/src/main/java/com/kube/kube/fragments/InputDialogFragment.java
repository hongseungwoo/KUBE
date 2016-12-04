package com.kube.kube.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kube.kube.MainActivity;
import com.kube.kube.R;

public class InputDialogFragment extends DialogFragment {
    int image=0;
    int inequalImage = 0;

    static String optionBlock;
    static String numOption;
    static String moduleNum;

    private MainActivity.ActivityHandler mActivityHandler;
    private OnFragmentListener mFragmentListener;
    private int mClickBlock;

    public InputDialogFragment(OnFragmentListener f, int clickBlock) {
        this.mFragmentListener = f;
        this.mClickBlock = clickBlock;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_input, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

//        int mClickBlock = getIntExtra("Block", 0);

        final ImageView blockImg = (ImageView)rootView.findViewById(R.id.optionImageView);
        final ImageView inequalImg = (ImageView)rootView.findViewById(R.id.inequalityImageView);
        final EditText numEdit = (EditText)rootView.findViewById(R.id.numOptionEditText);
        final TextView unitText = (TextView)rootView.findViewById(R.id.unitTextView);
        final EditText moduleEdit = (EditText)rootView.findViewById(R.id.moduleNumEditTextView);

        inequalImg.setTag("");
        Button saveBtn = (Button)rootView.findViewById(R.id.saveButton);
        Button cancelBtn = (Button)rootView.findViewById(R.id.cancelButton);

        switch (mClickBlock){
            case R.drawable.sleep:
                blockImg.setImageResource(R.drawable.empty);
                blockImg.setTag("EMPTY");
                blockImg.setVisibility(View.INVISIBLE);
                moduleEdit.setEnabled(false);
                inequalImg.setVisibility(View.INVISIBLE);
                numEdit.setHint("0-100");
                unitText.setText("초");
                break;
            case R.drawable.submotorblcok:
                blockImg.setImageResource(R.drawable.right);
                blockImg.setTag("RIGHT");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0 :
                                blockImg.setImageResource(R.drawable.right);
                                blockImg.setTag("RIGHT");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.left);
                                blockImg.setTag("LEFT");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                unitText.setText("도");
                numEdit.setHint("각도 0-100");

                break;
            case R.drawable.mainmotorblcok:
                blockImg.setImageResource(R.drawable.right);
                blockImg.setTag("RIGHT");
                unitText.setVisibility(View.INVISIBLE);
                numEdit.setHint("회전 속도 0-100");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0 :
                                blockImg.setImageResource(R.drawable.right);
                                blockImg.setTag("RIGHT");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.left);
                                blockImg.setTag("LEFT");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                break;
            case R.drawable.ledblock:
                blockImg.setTag("RED");
                blockImg.setImageResource(R.drawable.red);
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%6){
                            case 0:
                                blockImg.setImageResource(R.drawable.red);
                                blockImg.setTag("RED");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.sky);
                                blockImg.setTag("SKY");
                                break;
                            case 2:
                                blockImg.setImageResource(R.drawable.yellow);
                                blockImg.setTag("YELLOW");
                                break;
                            case 3:
                                blockImg.setImageResource(R.drawable.green);
                                blockImg.setTag("GREEN");
                                break;
                            case 4:
                                blockImg.setImageResource(R.drawable.blue);
                                blockImg.setTag("BLUE");
                                break;
                            case 5:
                                blockImg.setImageResource(R.drawable.violet);
                                blockImg.setTag("VIOLET");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                unitText.setVisibility(View.INVISIBLE);
                numEdit.setHint("밝기 0-100");
                break;
            case R.drawable.whileblock:
            case R.drawable.ifblock:
                inequalImg.setTag(">>");
                blockImg.setTag("ULTRA");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0:
                                blockImg.setImageResource(R.drawable.ultrasonic);
                                blockImg.setTag("ULTRA");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.infrared);
                                blockImg.setTag("INFRARED");
                                break;
                        }

                    }
                });
                unitText.setText("m");
                inequalImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inequalImage++;
                        switch (inequalImage%2){
                            case 0:
                                inequalImg.setImageResource(R.drawable.more);
                                inequalImg.setTag(">>");
                                break;
                            case 1:
                                inequalImg.setImageResource(R.drawable.less);
                                inequalImg.setTag("<<");
                                break;
                        }

                    }
                });
                break;
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionBlock = (String) blockImg.getTag();
                numOption = inequalImg.getTag() + numEdit.getText().toString();
                moduleNum = moduleEdit.getText().toString();
                String[] sendingStr = {optionBlock, numOption, moduleNum};
//                mActivityHandler.obtainMessage(Constants.FRAGMENT_LISTENER_GET_FRAGMENT_INPUT, sendingStr);
                mFragmentListener.onInputFragmentCallBack(sendingStr);
//                mInputFragmentListener.onInputFragmentCallBack(sendingStr);
//                String optionBlcok = (String) blockImg.getTag();
//                String numOption = (String)inequalImg.getTag() + numEdit.getText().toString();
//                String moduleNum = moduleEdit.getText().toString();
//
//                Intent intent = new Intent();
//                intent.putExtra("optionBlock", optionBlcok);
//                intent.putExtra("numOption", numOption);
//                intent.putExtra("moduleNum", moduleNum);
//                setResult(RESULT_OK, intent);
//                setResult(2, intent);
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_dialog_input);

        Intent getIntent = getIntent();
        int mClickBlock = getIntent.getIntExtra("Block", 0);


        final ImageView blockImg = (ImageView)findViewById(R.id.optionImageView);
        final ImageView inequalImg = (ImageView)findViewById(R.id.inequalityImageView);
        final EditText numEdit = (EditText)findViewById(R.id.numOptionEditText);
        final TextView unitText = (TextView)findViewById(R.id.unitTextView);
        final EditText moduleEdit = (EditText)findViewById(R.id.moduleNumEditTextView);

        inequalImg.setTag("");
        Button saveBtn = (Button)findViewById(R.id.saveButton);
        Button cancelBtn = (Button) findViewById(R.id.cancelButton);

        switch (mClickBlock){
            case R.drawable.sleep:
                blockImg.setImageResource(R.drawable.empty);
                blockImg.setTag("EMPTY");
                blockImg.setVisibility(View.INVISIBLE);
                moduleEdit.setEnabled(false);
                inequalImg.setVisibility(View.INVISIBLE);
                numEdit.setHint("0-100");
                unitText.setText("초");
                break;
            case R.drawable.submotorblcok:
                blockImg.setImageResource(R.drawable.right);
                blockImg.setTag("RIGHT");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0 :
                                blockImg.setImageResource(R.drawable.right);
                                blockImg.setTag("RIGHT");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.left);
                                blockImg.setTag("LEFT");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                unitText.setText("도");
                numEdit.setHint("각도 0-100");

                break;
            case R.drawable.mainmotorblcok:
                blockImg.setImageResource(R.drawable.right);
                blockImg.setTag("RIGHT");
                unitText.setVisibility(View.INVISIBLE);
                numEdit.setHint("회전 속도 0-100");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0 :
                                blockImg.setImageResource(R.drawable.right);
                                blockImg.setTag("RIGHT");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.left);
                                blockImg.setTag("LEFT");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                break;
            case R.drawable.ledblock:
                blockImg.setTag("RED");
                blockImg.setImageResource(R.drawable.red);
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%6){
                            case 0:
                                blockImg.setImageResource(R.drawable.red);
                                blockImg.setTag("RED");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.sky);
                                blockImg.setTag("SKY");
                                break;
                            case 2:
                                blockImg.setImageResource(R.drawable.yellow);
                                blockImg.setTag("YELLOW");
                                break;
                            case 3:
                                blockImg.setImageResource(R.drawable.green);
                                blockImg.setTag("GREEN");
                                break;
                            case 4:
                                blockImg.setImageResource(R.drawable.blue);
                                blockImg.setTag("BLUE");
                                break;
                            case 5:
                                blockImg.setImageResource(R.drawable.violet);
                                blockImg.setTag("VIOLET");
                                break;
                        }
                    }
                });
                inequalImg.setVisibility(View.INVISIBLE);
                unitText.setVisibility(View.INVISIBLE);
                numEdit.setHint("밝기 0-100");
                break;
            case R.drawable.whileblock:
            case R.drawable.ifblock:
                inequalImg.setTag(">");
                blockImg.setTag("ULTRA");
                blockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image++;
                        switch (image%2){
                            case 0:
                                blockImg.setImageResource(R.drawable.ultrasonic);
                                blockImg.setTag("ULTRA");
                                break;
                            case 1:
                                blockImg.setImageResource(R.drawable.infrared);
                                blockImg.setTag("INFRARED");
                                break;
                        }

                    }
                });
                unitText.setText("m");
                inequalImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inequalImage++;
                        switch (inequalImage%2){
                            case 0:
                                inequalImg.setImageResource(R.drawable.more);
                                inequalImg.setTag(">>");
                                break;
                            case 1:
                                inequalImg.setImageResource(R.drawable.less);
                                inequalImg.setTag("<<");
                                break;
                        }

                    }
                });
                break;
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String optionBlock = (String) blockImg.getTag();
                String numOption = (String)inequalImg.getTag() + numEdit.getText().toString();
                String moduleNum = moduleEdit.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("optionBlock", optionBlock);
                intent.putExtra("numOption", numOption);
                intent.putExtra("moduleNum", moduleNum);
//                setResult(RESULT_OK, intent);
                setResult(2, intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }*/
}


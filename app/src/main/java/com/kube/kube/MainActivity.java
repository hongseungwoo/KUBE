package com.kube.kube;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    ImageView whileBlockImage;
    ImageView whileEndBlockImage;
    ImageView ifBlockImage;
    ImageView ifEndBlockImage;
    ImageView sleepBlockImage;
    ImageView mainMotorBlockImage;
    ImageView subMotorBlockImage;
    ImageView ledBlockImage;
    WorkspaceAdapter mWorkspaceAdapter;

    int curBlock;
    int curPos;

    public static final int REQUEST_CODE_OPTION_INPUT = 1001;
    private static final String IMAGEVIEW_TAG = "DRAG_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whileBlockImage = (ImageView) findViewById(R.id.whileBlock);
        whileEndBlockImage = (ImageView) findViewById(R.id.whileEndBlock);
        ifBlockImage = (ImageView) findViewById(R.id.ifBlock);
        ifEndBlockImage = (ImageView) findViewById(R.id.ifEndBlock);
        mainMotorBlockImage = (ImageView) findViewById(R.id.mainMotorBlcok);
        subMotorBlockImage = (ImageView) findViewById(R.id.subMotorBlcok);
        ledBlockImage = (ImageView) findViewById(R.id.ledBlock);
        sleepBlockImage = (ImageView) findViewById(R.id.sleepBlock);

        whileBlockImage.setTag("while");
        whileEndBlockImage.setTag("whileEnd");
        ifBlockImage.setTag("if");
        ifEndBlockImage.setTag("ifEnd");
        mainMotorBlockImage.setTag("main");
        subMotorBlockImage.setTag("sub");
        ledBlockImage.setTag("led");
        sleepBlockImage.setTag("sleep");


        whileBlockImage.setOnLongClickListener(new myLongClickListener());
        whileEndBlockImage.setOnLongClickListener(new myLongClickListener());
        ifBlockImage.setOnLongClickListener(new myLongClickListener());
        ifEndBlockImage.setOnLongClickListener(new myLongClickListener());
        mainMotorBlockImage.setOnLongClickListener(new myLongClickListener());
        subMotorBlockImage.setOnLongClickListener(new myLongClickListener());
        ledBlockImage.setOnLongClickListener(new myLongClickListener());
        sleepBlockImage.setOnLongClickListener(new myLongClickListener());

        final GridView workspace = (GridView) findViewById(R.id.workSpace);
        mWorkspaceAdapter = new WorkspaceAdapter(MainActivity.this);
        workspace.setAdapter(mWorkspaceAdapter);
        workspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clickBlock = mWorkspaceAdapter.BlockList.get(position).blockImage;
                if(clickBlock != R.drawable.empty){
                    curPos = position;
                    Log.d("clickBlock", "     "+clickBlock+"    "+curPos);
                    Intent OptionIntent = new Intent(MainActivity.this, InputActivity.class);
                    OptionIntent.putExtra("Block", clickBlock);
                    startActivityForResult(OptionIntent, REQUEST_CODE_OPTION_INPUT);
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_CODE_OPTION_INPUT){
                String optionBlock = intent.getStringExtra("optionBlock");
                String optionText = intent.getStringExtra("optionText");
                Log.d("optionblokc", "   "+optionBlock+optionText);
                int Block = getBlockIamge(optionBlock);
                mWorkspaceAdapter.setOption(Block, optionText, curPos);
            }
        }
    }

    private int getBlockIamge(String optionBlock) {
        int image = R.drawable.empty;
        switch (optionBlock){
            case "EMPTY":
                image = R.drawable.empty;
                break;
            case "ULTRA":
                image = R.drawable.ultrasonic;
                break;
            case "INFRARED":
                image = R.drawable.infrared;
                break;
            case "RED":
                image = R.drawable.red;
                break;
            case "ORANGE":
                image = R.drawable.orange;
                break;
            case "YELLOW":
                image = R.drawable.yellow;
                break;
            case "GREEN":
                image = R.drawable.green;
                break;
            case "BLUE":
                image = R.drawable.blue;
                break;
            case "BV":
                image = R.drawable.bluishviolet;
                break;
            case "VIOLET":
                image = R.drawable.violet;
                break;
        }
        return image;
    }

    private final class myLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            // 태그 생성
            ClipData.Item item = new ClipData.Item(
                    (CharSequence) v.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, // data to be dragged
                    shadowBuilder, // drag shadow
                    v, // 드래그 드랍할  Vew
                    0 // 필요없은 플래그
            );
            switch((String)v.getTag()){
                case "while":
                    curBlock = R.drawable.whileblock;
                    break;
                case "whileEnd":
                    curBlock = R.drawable.whileendblock;
                    break;
                case "if":
                    curBlock = R.drawable.ifblock;
                    break;
                case "ifEnd":
                    curBlock = R.drawable.ifendblock;
                    break;
                case "main":
                    curBlock = R.drawable.mainmotorblcok;
                    break;
                case "sub":
                    curBlock = R.drawable.submotorblcok;
                    break;
                case "led":
                    curBlock = R.drawable.ledblock;
                    break;
                case "sleep":
                    curBlock = R.drawable.sleep;
                    break;
                case "RIGHT":
                    curBlock = R.drawable.right;
                    break;
                case "LEFT":
                    curBlock = R.drawable.left;
                    break;
            }
            mWorkspaceAdapter.setCurBlock(curBlock);

            return true;
        }

    }
}

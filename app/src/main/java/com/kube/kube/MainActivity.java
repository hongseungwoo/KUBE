package com.kube.kube;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView whileBlockImage;
    ImageView ifBlockImage;
    ImageView mainMotorBlockImage;
    ImageView subMotorBlockImage;
    ImageView ledBlockImage;
    WorkspaceAdapter mWorkspaceAdapter;

    int curBlock;
    int position;

    private static final String IMAGEVIEW_TAG = "DRAG_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whileBlockImage = (ImageView) findViewById(R.id.whileBlock);
        ifBlockImage = (ImageView) findViewById(R.id.ifBlock);
        mainMotorBlockImage = (ImageView) findViewById(R.id.mainMotorBlcok);
        subMotorBlockImage = (ImageView) findViewById(R.id.subMotorBlcok);
        ledBlockImage = (ImageView) findViewById(R.id.ledBlock);

        whileBlockImage.setTag("while");
        ifBlockImage.setTag("if");
        mainMotorBlockImage.setTag("main");
        subMotorBlockImage.setTag("sub");
        ledBlockImage.setTag("led");

        whileBlockImage.setOnLongClickListener(new myLongClickListener());
        ifBlockImage.setOnLongClickListener(new myLongClickListener());
        mainMotorBlockImage.setOnLongClickListener(new myLongClickListener());
        subMotorBlockImage.setOnLongClickListener(new myLongClickListener());
        ledBlockImage.setOnLongClickListener(new myLongClickListener());

        final GridView workspace = (GridView) findViewById(R.id.workSpace);
        mWorkspaceAdapter = new WorkspaceAdapter(MainActivity.this);
        workspace.setAdapter(mWorkspaceAdapter);

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
                case "if":
                    curBlock = R.drawable.ifblock;
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
            }
            mWorkspaceAdapter.setCurBlock(curBlock);
            Log.d("curBlock", ""+curBlock +"    "+R.drawable.whileblock);
            return true;
        }

    }
}

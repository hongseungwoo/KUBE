package com.kube.kube;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ImageView startBlockImage;
    ImageView endBlockImage;
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

        startBlockImage = (ImageView) findViewById(R.id.startBlock);
        endBlockImage = (ImageView)findViewById(R.id.endBlock);
        whileBlockImage = (ImageView) findViewById(R.id.whileBlock);
        whileEndBlockImage = (ImageView) findViewById(R.id.whileEndBlock);
        ifBlockImage = (ImageView) findViewById(R.id.ifBlock);
        ifEndBlockImage = (ImageView) findViewById(R.id.ifEndBlock);
        mainMotorBlockImage = (ImageView) findViewById(R.id.mainMotorBlcok);
        subMotorBlockImage = (ImageView) findViewById(R.id.subMotorBlcok);
        ledBlockImage = (ImageView) findViewById(R.id.ledBlock);
        sleepBlockImage = (ImageView) findViewById(R.id.sleepBlock);

        startBlockImage.setTag("START");
        endBlockImage.setTag("END");
        whileBlockImage.setTag("WHILE");
        whileEndBlockImage.setTag("WHILEEND");
        ifBlockImage.setTag("IF");
        ifEndBlockImage.setTag("IFEND");
        mainMotorBlockImage.setTag("MAIN");
        subMotorBlockImage.setTag("SUB");
        ledBlockImage.setTag("LED");
        sleepBlockImage.setTag("SLEEP");

        ImageView blockImages [] = {startBlockImage, endBlockImage,whileBlockImage,whileEndBlockImage,
                ifBlockImage,ifEndBlockImage,mainMotorBlockImage,subMotorBlockImage,ledBlockImage,sleepBlockImage};

        for(int i = 0 ; i < blockImages.length; i++){
            blockImages[i].setOnLongClickListener(new myLongClickListener());
        }

        final GridView workspace = (GridView) findViewById(R.id.workSpace);
        mWorkspaceAdapter = new WorkspaceAdapter(MainActivity.this);
        workspace.setAdapter(mWorkspaceAdapter);
        workspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clickBlock = mWorkspaceAdapter.BlockList.get(position).blockImage;
                if(clickBlock == R.drawable.whileblock
                        || clickBlock == R.drawable.ifblock
                        ||clickBlock == R.drawable.submotorblcok
                        ||clickBlock == R.drawable.mainmotorblcok
                        ||clickBlock == R.drawable.ledblock){
                    curPos = position;
                    Log.d("clickBlock", "     "+clickBlock+"    "+curPos);
                    Intent OptionIntent = new Intent(MainActivity.this, InputActivity.class);
                    OptionIntent.putExtra("Block", clickBlock);
                    startActivityForResult(OptionIntent, REQUEST_CODE_OPTION_INPUT);
                }
            }
        });
        workspace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mWorkspaceAdapter.BlockList.get(position).setModuleNum("");
                mWorkspaceAdapter.BlockList.get(position).setOptionImage(R.drawable.empty);
                mWorkspaceAdapter.BlockList.get(position).setBlockImage(R.drawable.empty);
                mWorkspaceAdapter.BlockList.get(position).setNumOption("");
                mWorkspaceAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void addOptionMenuItems(Menu menu) {
        menu.clear();
        menu.add(R.id.trans, R.id.trans, Menu.NONE, "전송");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        addOptionMenuItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trans:
                String trans = makeTransStr(0);
                Log.d("전송문", ""+trans);
                return true;
            default:
                break;
        }
        return false;
    }
    public String makeTransStr(int start){
        String transStr = "";
        ArrayList<WorkspaceItem> blockList = mWorkspaceAdapter.BlockList;
        for(int i = start;i < blockList.size();i+=5){
            int block = blockList.get(i).getBlockImage();
            switch (block) {
                case R.drawable.start:
                    start = i+1;
                    transStr+=makeTransStr(start);
                    break;

                case R.drawable.whileblock:
                    transStr += "[WHILE](";
                    if (blockList.get(i).getOptionImage() == R.drawable.infrared) {
                        transStr += "[IR" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    } else {
                        transStr += "[US" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    }
                    start = i+1;
                    transStr+=makeTransStr(start);
                    break;
                case R.drawable.whileendblock:
                    transStr += "}";
                    break;
                case R.drawable.ifblock:
                    transStr += "[IF]{";
                    if (blockList.get(i).getOptionImage() == R.drawable.infrared) {
                        transStr += "[IR" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    } else {
                        transStr += "[US" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    }
                    start = i+1;
                    transStr+=makeTransStr(start);
                    break;
                case R.drawable.ifendblock:
                    transStr += "}";
                    break;
                case R.drawable.mainmotorblcok:
                    transStr += "[DC" + blockList.get(i).getModuleNum() + "](";
                    if (blockList.get(i).optionImage == R.drawable.right)
                        transStr += "000," + blockList.get(i).getNumOption() + ")";
                    else
                        transStr += "001," + blockList.get(i).getNumOption() + ")";
                    break;
                case R.drawable.submotorblcok:
                    transStr += "[SM" + blockList.get(i).getModuleNum() + "](" + blockList.get(i).getNumOption() + ")";
                    break;
                case R.drawable.ledblock:
                    transStr += "[LD" + blockList.get(i).getModuleNum() + "](";
                    if (blockList.get(i).optionImage == R.drawable.red) {
                        transStr += "R," + blockList.get(i).getNumOption() + ")";
                    } else if (blockList.get(i).optionImage == R.drawable.green) {
                        transStr += "G," + blockList.get(i).getNumOption() + ")";
                    } else if (blockList.get(i).optionImage == R.drawable.blue) {
                        transStr += "B," + blockList.get(i).getNumOption() + ")";
                    } else if (blockList.get(i).optionImage == R.drawable.yellow) {
                        transStr += "Y," + blockList.get(i).getNumOption() + ")";
                    } else if (blockList.get(i).optionImage == R.drawable.violet) {
                        transStr += "P," + blockList.get(i).getNumOption() + ")";
                    } else if (blockList.get(i).optionImage == R.drawable.sky) {
                        transStr += "S," + blockList.get(i).getNumOption() + ")";
                    }
                    break;
                }
            }
        return transStr;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(intent != null){
            if(requestCode == REQUEST_CODE_OPTION_INPUT){
                String optionBlock = intent.getStringExtra("optionBlock");
                String numOption = intent.getStringExtra("numOption");
                String moduleNum = intent.getStringExtra("moduleNum");
                Log.d("optionblokc", "   "+optionBlock+ numOption);
                int Block = getBlockIamge(optionBlock);
                mWorkspaceAdapter.setOption(Block, numOption, moduleNum, curPos);
            }
        }
    }

    private int getBlockIamge(String Block) {
        int image = R.drawable.empty;
        switch (Block){
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
            case "SKY":
                image = R.drawable.sky;
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
            case "VIOLET":
                image = R.drawable.violet;
                break;
            case "RIGHT":
                image = R.drawable.right;
                break;
            case "LEFT":
                image = R.drawable.left;
                break;
            case "WHILE":
                image = R.drawable.whileblock;
                break;
            case "WHILEEND":
                image = R.drawable.whileendblock;
                break;
            case "IF":
                image = R.drawable.ifblock;
                break;
            case "IFEND":
                image = R.drawable.ifendblock;
                break;
            case "MAIN":
                image = R.drawable.mainmotorblcok;
                break;
            case "SUB":
                image = R.drawable.submotorblcok;
                break;
            case "LED":
                image = R.drawable.ledblock;
                break;
            case "SLEEP":
                image = R.drawable.sleep;
                break;
            case "START":
                image = R.drawable.start;
                break;
            case "END":
                image = R.drawable.end;
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
            curBlock = getBlockIamge((String)v.getTag());
            mWorkspaceAdapter.setCurBlock(curBlock);

            return true;
        }

    }


}

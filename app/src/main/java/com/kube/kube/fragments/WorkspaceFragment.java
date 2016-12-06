package com.kube.kube.fragments;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kube.kube.R;
import com.kube.kube.utils.Constants;
import com.kube.kube.workspace.WorkspaceAdapter;
import com.kube.kube.workspace.WorkspaceItem;

import java.util.ArrayList;

public class WorkspaceFragment extends Fragment {

    View rootView = null;

    Context mContext = null;
    Handler mHandler = null;
    OnFragmentListener mFragmentListener = null;

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

    Gson gson;
    String DB;

    ArrayList<WorkspaceItem> mBlockList;

    int curBlock;
    int curPos;

    private static final String IMAGEVIEW_TAG = "DRAG_IMAGE";

//    public WorkspaceFragment(Context c, Handler h, OnFragmentListener f, WorkspaceAdapter w) {
//        this.mContext = c;
//        this.mHandler = h;
//        this.mFragmentListener = f;
//        this.mWorkspaceAdapter = w;
//    }

    public WorkspaceFragment(Context c, Handler h, OnFragmentListener f) {
        this.mContext = c;
        this.mHandler = h;
        this.mFragmentListener = f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_workspace, container, false);

            startBlockImage = (ImageView) rootView.findViewById(R.id.startBlock);
            endBlockImage = (ImageView) rootView.findViewById(R.id.endBlock);
            whileBlockImage = (ImageView) rootView.findViewById(R.id.whileBlock);
            whileEndBlockImage = (ImageView) rootView.findViewById(R.id.whileEndBlock);
            ifBlockImage = (ImageView) rootView.findViewById(R.id.ifBlock);
            ifEndBlockImage = (ImageView) rootView.findViewById(R.id.ifEndBlock);
            mainMotorBlockImage = (ImageView) rootView.findViewById(R.id.mainMotorBlcok);
            subMotorBlockImage = (ImageView) rootView.findViewById(R.id.subMotorBlcok);
            ledBlockImage = (ImageView) rootView.findViewById(R.id.ledBlock);
            sleepBlockImage = (ImageView) rootView.findViewById(R.id.sleepBlock);

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

            ImageView blockImages[] = {startBlockImage, endBlockImage, whileBlockImage, whileEndBlockImage,
                    ifBlockImage, ifEndBlockImage, mainMotorBlockImage, subMotorBlockImage, ledBlockImage, sleepBlockImage};

            for (ImageView blockImage : blockImages) {
                blockImage.setOnLongClickListener(mLongClickListener);
            }

            final GridView workspace = (GridView) rootView.findViewById(R.id.workSpace);
            mWorkspaceAdapter = new WorkspaceAdapter(mContext);
            workspace.setAdapter(mWorkspaceAdapter);
            workspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int clickBlock = mWorkspaceAdapter.BlockList.get(position).blockImage;
                    if (clickBlock != R.drawable.empty &&
                            clickBlock != R.drawable.start &&
                            clickBlock != R.drawable.end &&
                            clickBlock != R.drawable.ifendblock &&
                            clickBlock != R.drawable.whileendblock) {
                        curPos = position;
                        Log.d("clickBlock", "     " + clickBlock + "    " + curPos);
                        mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_SHOW_DIALOG_INPUT, clickBlock);
                    }
                }
            });
            workspace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    mBlockList = mWorkspaceAdapter.BlockList;
                    int block = mBlockList.get(position).getBlockImage();
                    if(block == R.drawable.whileblock||block == R.drawable.whileendblock||block == R.drawable.ifblock||block == R.drawable.ifendblock)
                        deleteBlock(position-1);
                    deleteBlock(position);
                    return false;
                }
            });
        }


        return rootView;
    }

    public void deleteBlock(int position){
        mWorkspaceAdapter.BlockList.get(position).setBlockImage(R.drawable.empty);
        mWorkspaceAdapter.BlockList.get(position).setOptionImage(R.drawable.empty);
        mWorkspaceAdapter.BlockList.get(position).setModuleNum("");
        mWorkspaceAdapter.BlockList.get(position).setNumOption("");
        mWorkspaceAdapter.notifyDataSetChanged();
    }

    public void inputDatas(String optionBlock, String numOption, String moduleNum) {
        int Block = getBlockIamge(optionBlock);
        mWorkspaceAdapter.setOption(Block, numOption, moduleNum, curPos);
    }

    public synchronized String translateToModuleLanguage() {
        if(mWorkspaceAdapter.BlockList != null) {
            mBlockList = mWorkspaceAdapter.BlockList;
            MakeTransStr mMakeTrans = new MakeTransStr(mBlockList, mContext);
            String result = mMakeTrans.translate(0);
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
            return result;
        }
        else return "Error : Blocklist is empty";
    }


    private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
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
            curBlock = getBlockIamge((String) v.getTag());
            mWorkspaceAdapter.setCurBlock(curBlock);

            return true;
        }
    };

    private int getBlockIamge(String Block) {
        int image = R.drawable.empty;
        switch (Block) {
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

}



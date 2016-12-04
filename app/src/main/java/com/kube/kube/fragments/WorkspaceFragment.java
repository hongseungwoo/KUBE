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
            mWorkspaceAdapter = new WorkspaceAdapter(getActivity());
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
//                        Intent OptionIntent = new Intent(getActivity(), InputDialogFragment.class);
//                        OptionIntent.putExtra("Block", clickBlock);
//                        startActivityForResult(OptionIntent, Constants.REQUEST_CODE_OPTION_INPUT);
                    }
                }
            });
            workspace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteBlock(position);
                    return false;
                }
            });
        }

//        Button b1 = (Button) rootView.findViewById(R.id.button_workspace_connection);
//        Button b2 = (Button) rootView.findViewById(R.id.button_workspace_intro);
//        Button b3 = (Button) rootView.findViewById(R.id.button_workspace_workspace);
//
//        b1.setOnClickListener(mClickListener);
//        b2.setOnClickListener(mClickListener);
//        b3.setOnClickListener(mClickListener);

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
            MakeTransStr mMakeTrans = new MakeTransStr(mBlockList);
            String result = mMakeTrans.translate(0);
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
            return result;
        }
        else return "Error : Blocklist is empty";
    }


    /**
     * Save last state
     *
     * @param outState
     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.put
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if(intent != null){
//            if(requestCode == Constants.REQUEST_CODE_OPTION_INPUT){
//                String optionBlock = intent.getStringExtra("optionBlock");
//                String numOption = intent.getStringExtra("numOption");
//                String moduleNum = intent.getStringExtra("moduleNum");
//                Log.d("optionblokc", "   "+optionBlock+ numOption);
//                int Block = getBlockIamge(optionBlock);
//                mWorkspaceAdapter.setOption(Block, numOption, moduleNum, curPos);
//            }
//        }
//    }

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


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.button_connection_connection:
//                    break;
//                case R.id.button_connection_intro:
//                    break;
//                case R.id.button_connection_workspace:
//                    break;
//                case R.id.button_intro_connection:
//                    break;
//                case R.id.button_intro_intro:
//                    break;
//                case R.id.button_intro_workspace:
//                    break;
//                case R.id.button_workspace_connection:
////                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION);
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_CONNECTION);
//                    break;
//                case R.id.button_workspace_intro:
////                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO);
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_INTRO);
//                    break;
//                case R.id.button_workspace_workspace:
////                    mHandler.obtainMessage(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE);
//                    mFragmentListener.onFragmentCallBack(Constants.FRAGMENT_CALLBACK_CHANGE_FRAGMENT_WORKSPACE);
//                    break;
                default:
                    break;
            }
        }
    };


}



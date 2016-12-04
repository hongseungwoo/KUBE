package com.kube.kube.fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.kube.kube.R;
import com.kube.kube.utils.Constants;
import com.kube.kube.workspace.InputActivity;
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

    int curBlock;
    int curPos;

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

            for (int i = 0; i < blockImages.length; i++) {
                blockImages[i].setOnLongClickListener(mLongClickListener);
            }

            final GridView workspace = (GridView) rootView.findViewById(R.id.workSpace);
            mWorkspaceAdapter = new WorkspaceAdapter(getActivity());
            workspace.setAdapter(mWorkspaceAdapter);
            workspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int clickBlock = mWorkspaceAdapter.BlockList.get(position).blockImage;
                    if (clickBlock != R.drawable.empty) {
                        curPos = position;
                        Log.d("clickBlock", "     " + clickBlock + "    " + curPos);
//                        mFragmentListener.OnFragmentListener(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_INPUT);
                        Intent OptionIntent = new Intent(getActivity(), InputActivity.class);
                        OptionIntent.putExtra("Block", clickBlock);
                        startActivityForResult(OptionIntent, Constants.REQUEST_CODE_OPTION_INPUT);
                    }
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

    public void inputDatas(String optionBlock, String numOption, String moduleNum) {
        int Block = getBlockIamge(optionBlock);
        mWorkspaceAdapter.setOption(Block, numOption, moduleNum, curPos);
    }

    public synchronized String convert() {
        initialize();
        String result = parseSecond(parseFirst(makeTransStr(0)));
        Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
        return result;
    }

    private String makeTransStr(int start){
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

    private void initialize() {
        xx = -1;
        p = 0;
        wp = 0;
        loopPos = 0;
        mBuilder = new StringBuilder();
        isEnd = false;
        xxxPos = new int[5];
        whileStartPos = new int[5];
    }

    String[] symbol = {"IF", "WHILE", "IFEND", "WHILEEND"};
    String[] module = {"DC", "SM", "LD", "IR", "US"};
    String[] mNum = {"1", "2", "3", "4"};
    String[] bracket = {"(", ")", "{", "}", "[", "]"};
    String[] sign = {">>", "<<", ">=", "<=", "==", "!="};
    int[] num = {-1, -1, -1, -1, -1}; //DC SM LD IR US
    String[] color = {"R", "G", "B", "Y", "P", "K"};
    String jump = "JP";
    String delay = "DY";
    String currentMethod = null;

    int xx;
    StringBuilder mBuilder;
    boolean isEnd;
    int p;
    int[] xxxPos;
    int loopPos;
    int wp;
    int[] whileStartPos;

    private String[] parseFirst(String msg) {
//        String[][] storage = new String[100][10];
//        int[] xx = {0, };
//        int[] y = {0, };

        String[] command = new String[100];
//        String recent;
        int x = 0;
        int i = 0;
//        char token;
//        boolean isEnd = false;
        while(i < msg.length()) {
//            char a = msg.charAt(i);

            StringBuilder builder = new StringBuilder();
            while('0' <= msg.charAt(i) && msg.charAt(i) <= '9') {
                builder.append(msg.charAt(i++));
            }
            if(builder.length() > 0)
                command[x++] = new String(builder);

            switch(msg.charAt(i)) {
                case 'N': //No.
                    break;
                case 'o':
                    break;
                case '.':
                    break;
                case ',':
                    break;
                case ']':
                    break;
                case '[':
                    break;
                case '(':
                    break;
                case ')':
                    break;
                case '{':
                    break;
                case '}':
                    if(currentMethod.equals(symbol[2])) {
                        command[x++] = symbol[2];
                    } else if(currentMethod.equals(symbol[3])) {
                        command[x++] = symbol[3];
                    }
                    break;
                case 'I':
                    switch(msg.charAt(++i)) {
                        case 'F':
                            command[x++] = symbol[0];
                            currentMethod = symbol[2];
                            break;
                        case 'R':
                            command[x++] = module[3];
                            break;
                    }
                    break;
                case 'W':
                    command[x++] = symbol[1];
                    currentMethod = symbol[3];
                    i += 4;
                    break;
                case 'D':
                    switch(msg.charAt(++i)) {
                        case 'C': //DC
                            command[x++] = module[0];
                            break;
                        case 'Y': //DY
                            command[x++] = delay;
                            break;
                    }
                    break;
                case 'S':
                    command[x++] = module[1];
                    i++;
                    break;
                case 'L':
                    command[x++] = module[3];
                    i++;
                    break;
                case 'U':
                    command[x++] = module[4];
                    i++;
                    break;
                case '<':
                    switch(msg.charAt(++i)) {
                        case '<':
                            command[x++] = sign[1];
                            break;
//                        case '=':
//                            command[x++] = sign[3];
//                            break;
                    }
                    break;
                case '>':
                    switch(msg.charAt(++i)) {
                        case '>':
                            command[x++] = sign[0];
                            break;
//                        case '=':
//                            command[x++] = sign[2];
//                            break;
                    }
                    break;
//                case '=':
//                    command[x++] = sign[4];
//                    break;
//                case '!':
//                    command[x++] = sign[5];
//                    break;
                case 'R': //Red
                    command[x++] = color[0];
                    break;
                case 'G': //Green
                    command[x++] = color[1];
                    break;
                case 'B': //Blue
                    command[x++] = color[2];
                    break;
                case 'Y': //Yellow
                    command[x++] = color[3];
                    break;
                case 'P': //Purple
                    command[x++] = color[4];
                    break;
                case 'K': //sKyblue
                    command[x++] = color[5];
                    break;
            }
            i++;

        }
        command[x] = "&";
        return command;
    }


    private String parseSecond(String[] cmd) {
//        String str = null;
//        int pos = 0;
//
//        while(!isEnd) {
        switch(cmd[++xx]) {
            case "IF":  //[JP]([IR1]()<<050,XXX){[DC1](001,050)[DC2](001,100)}
                //cmd[] = {IF, IR, 1, <<, 50, DC, 1, 1, 50, DC, 2, 1, 100, &}
                mBuilder.append('[');
                mBuilder.append(jump);
                mBuilder.append(']');
                mBuilder.append('(');
                parseSecond(cmd); // DC1](001,050) or IR1]()
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(',');
                mBuilder.append("XXX"); xxxPos[++p] = mBuilder.length();
                mBuilder.append(')');
                mBuilder.append('{');
                parseSecond(cmd);
//                    mBuilder.append('}');
                mBuilder.replace(xxxPos[p]-3, xxxPos[p], String.format("%03d", mBuilder.length()));
                p--;
                parseSecond(cmd);
                break;
            //[WHILE]([US1]()>>100){[DC1](1,100)}[WHILE]([IR1]()<<100){[DC1](0,100)}
            case "WHILE": // [JP]([US1]()>>100,070){[DC1](001,100)}[JP](T,000)
                //cmd[] == {WHILE, US, 1, >>, 100, DC, 1, 1, 100}
                mBuilder.append('['); whileStartPos[++wp] = mBuilder.length() - 1;
                mBuilder.append(jump);
                mBuilder.append(']');
                mBuilder.append('(');
                parseSecond(cmd); // DC1](001,050) or IR1]()
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(',');
                mBuilder.append("XXX"); xxxPos[++p] = mBuilder.length();
                mBuilder.append(')');
                mBuilder.append('{');
                parseSecond(cmd);
//                    mBuilder.append('}');
                mBuilder.append('[');
                mBuilder.append(jump);
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append('T');
                mBuilder.append(',');
                mBuilder.append(String.format("%03d", whileStartPos[wp]));
                wp--;
                mBuilder.append(')');
                mBuilder.replace(xxxPos[p]-3, xxxPos[p], String.format("%03d", mBuilder.length()));
                p--;
                parseSecond(cmd);
                break;
            case "IFEND":
                mBuilder.append('}');
                break;
            case "WHILEEND":
                mBuilder.append('}');
                break;
            case "DC": // output
                mBuilder.append('[');
                mBuilder.append(module[0]); // "DC"
                mBuilder.append(cmd[++xx]); // #
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(',');
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case "SM": // output
                mBuilder.append('[');
                mBuilder.append(module[1]);
                mBuilder.append(cmd[++xx]); // #
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case "LD": // output
                mBuilder.append('[');
                mBuilder.append(module[2]);
                mBuilder.append(cmd[++xx]); // #
                mBuilder.append(']');
                mBuilder.append('(');
//                    mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(cmd[++xx]); // COLOR
                mBuilder.append(',');
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case "IR": // input
                mBuilder.append('[');
                mBuilder.append(module[3]);
                mBuilder.append(cmd[++xx]); // #
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case "US": // input
                mBuilder.append('[');
                mBuilder.append(module[4]);
                mBuilder.append(cmd[++xx]); // #
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case "DY": // [DY](XXX)
                mBuilder.append('[');
                mBuilder.append(delay);
                mBuilder.append(']');
                mBuilder.append('(');
                mBuilder.append(String.format("%03d", Integer.valueOf(cmd[++xx])));
                mBuilder.append(')');
                parseSecond(cmd);
                break;
            case ">>":
                mBuilder.append(sign[0]);
                break;
            case "<<":
                mBuilder.append(sign[1]);
                break;
            case "<=":
                mBuilder.append(sign[2]);
                break;
            case ">=":
                mBuilder.append(sign[3]);
                break;
            case "==":
                mBuilder.append(sign[4]);
                break;
            case "!=":
                mBuilder.append(sign[5]);
                break;
            case "&":
                isEnd = true;
                break;
        }
//        }

        return new String(mBuilder);
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
////                    mHandler.obtainMessage(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_CONNECTION);
//                    mFragmentListener.OnFragmentListener(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_CONNECTION);
//                    break;
//                case R.id.button_workspace_intro:
////                    mHandler.obtainMessage(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_INTRO);
//                    mFragmentListener.OnFragmentListener(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_INTRO);
//                    break;
//                case R.id.button_workspace_workspace:
////                    mHandler.obtainMessage(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_WORKSPACE);
//                    mFragmentListener.OnFragmentListener(Constants.FRAGMENT_LISTENER_CHANGE_FRAGMENT_WORKSPACE);
//                    break;
            }
        }
    };


}



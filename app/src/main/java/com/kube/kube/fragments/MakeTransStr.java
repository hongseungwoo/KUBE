package com.kube.kube.fragments;

import android.content.Context;

import android.widget.Toast;

import com.kube.kube.R;
import com.kube.kube.workspace.WorkspaceItem;

import java.util.ArrayList;

/**
 * Created by jutan on 2016-12-01.
 */

public class MakeTransStr {

    private String[] symbol = {"IF", "WHILE", "IFEND", "WHILEEND"};
    private String[] module = {"DC", "SM", "LD", "IR", "US"};
    String[] mNum = {"1", "2", "3", "4"};
    String[] bracket = {"(", ")", "{", "}", "[", "]"};
    private String[] sign = {">>", "<<", ">=", "<=", "==", "!="};
    int[] num = {-1, -1, -1, -1, -1}; //DC SM LD IR US
    private String[] color = {"R", "G", "B", "Y", "P", "K"};
    private String jump = "JP";
    private String delay = "DY";
    private String currentMethod = null;

    private int xx;
    private StringBuilder mBuilder;
    private boolean isEnd;
    private int p;
    private int[] xxxPos;
    private int loopPos;
    private int wp;
    private int[] whileStartPos;

    private ArrayList<WorkspaceItem> blockList;
    private Context mContext;


    //Constructor
    public MakeTransStr(ArrayList<WorkspaceItem> blockList, Context c){
        this.blockList = blockList;
        this.mContext = c;
    }

    /**
     * Translate to the language KUBE main module can understand
     *
     * @param start parameter of method 'makeTransStr()'
     * @return finally translated string
     */
    public String translate(int start) {
        initialize();
        String translated = parseSecond(parseFirst(makeTransStr(start)));
        StringBuilder sb = new StringBuilder(translated);
        sb.insert(0, "#");
        sb.insert(translated.length()+1, "\n");
        return new String(sb);
    }

    /**
     *
     * @param start cursor where to start translating
     * @return a String which is initially translated by the block language on workspace
     */
    private String makeTransStr(int start){
        String transStr = "";
        boolean checkEnd = false;
        for(int i = start;i < blockList.size();i+=5){
            if(checkEnd == true)
                break;
            int block = blockList.get(i).getBlockImage();
            switch (block) {
                case R.drawable.up_to_right:
                    transStr+=makeTransStr(i+1);
                    break;

                case R.drawable.whileblock:
                    transStr += "[WHILE](";
                    if (blockList.get(i).getOptionImage() == R.drawable.infrared) {
                        transStr += "[IR" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    } else {
                        transStr += "[US" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    }
                    break;
                case R.drawable.whileendblock:
                    transStr += "}";
                    checkEnd=true;
                    break;
                case R.drawable.ifblock:
                    transStr += "[IF](";
                    if (blockList.get(i).getOptionImage() == R.drawable.infrared) {
                        transStr += "[IR" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    } else {
                        transStr += "[US" + blockList.get(i).getModuleNum() + "]()" + blockList.get(i).getNumOption() + "){";
                    }
                    break;
                case R.drawable.ifendblock:
                    transStr += "}";
                    checkEnd=true;
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
                        transStr += "K," + blockList.get(i).getNumOption() + ")";
                    }
                    break;
                case R.drawable.sleep:
                    transStr += "[DY](" + blockList.get(i).getNumOption() + ")";
                    break;
                case R.drawable.end:
                    checkEnd = true;
                    break;
            }
        }
        if(checkEnd == false)
            Toast.makeText(mContext, "End 계열의 수가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        return transStr;
    }

    /**
     *  initialize private global valuables
     */
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

    /**
     *
     * @param msg return value of method 'makeTransStr()'
     * @return a String array which contains the commands
     */
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
                    command[x++] = module[2];
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


    /**
     *
     * @param cmd return value of method 'parseFirst()'
     * @return a String which is translated language without start char'#' and end char '&'
     */
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
}

package com.kube.kube.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kube.kube.R;
import com.kube.kube.workspace.WorkspaceItem;

import java.util.ArrayList;

public class PseudoActivity extends AppCompatActivity {
    ArrayList<WorkspaceItem> mBlockList;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseudo);
        gson = new Gson();
        Intent intent = getIntent();
        String list = intent.getStringExtra("Blocks");
        if(!"".equals(list)) {
            TypeToken<ArrayList<WorkspaceItem>> token = new TypeToken<ArrayList<WorkspaceItem>>() {};
            mBlockList = gson.fromJson(list, token.getType());
        }

    }

    public String transPseudo(int start){
        String pseudoStr = "";
        boolean checkEnd = false;
        for(int i = start;i < mBlockList.size();i+=5){
            if(checkEnd)
                break;
            int block = mBlockList.get(i).getBlockImage();
            switch (block) {
                case R.drawable.start:
                    pseudoStr += "작동을 시작합니다.\n";
                    break;

                case R.drawable.up_to_right:
                    pseudoStr+=transPseudo(i+1);
                    break;

                case R.drawable.whileblock:
                    pseudoStr += "[WHILE](";
                    if (mBlockList.get(i).getOptionImage() == R.drawable.infrared) {
                        pseudoStr += "[IR" + mBlockList.get(i).getModuleNum() + "]()" + mBlockList.get(i).getNumOption() + "){";
                    } else {
                        pseudoStr += "[US" + mBlockList.get(i).getModuleNum() + "]()" + mBlockList.get(i).getNumOption() + "){";
                    }
                    break;
                case R.drawable.whileendblock:
                    pseudoStr += "}";
                    checkEnd=true;
                    break;
                case R.drawable.ifblock:
                    pseudoStr += "[IF](";
                    if (mBlockList.get(i).getOptionImage() == R.drawable.infrared) {
                        pseudoStr += "[IR" + mBlockList.get(i).getModuleNum() + "]()" + mBlockList.get(i).getNumOption() + "){";
                    } else {
                        pseudoStr += "[US" + mBlockList.get(i).getModuleNum() + "]()" + mBlockList.get(i).getNumOption() + "){";
                    }
                    break;
                case R.drawable.ifendblock:
                    pseudoStr += "}";
                    checkEnd=true;
                    break;
                case R.drawable.mainmotorblcok:
                    pseudoStr += "[DC" + mBlockList.get(i).getModuleNum() + "](";
                    if (mBlockList.get(i).optionImage == R.drawable.right)
                        pseudoStr += "000," + mBlockList.get(i).getNumOption() + ")";
                    else
                        pseudoStr += "001," + mBlockList.get(i).getNumOption() + ")";
                    break;
                case R.drawable.submotorblcok:
                    pseudoStr += "[SM" + mBlockList.get(i).getModuleNum() + "](" + mBlockList.get(i).getNumOption() + ")";
                    break;
                case R.drawable.ledblock:
                    pseudoStr += "[LD" + mBlockList.get(i).getModuleNum() + "](";
                    if (mBlockList.get(i).optionImage == R.drawable.red) {
                        pseudoStr += "R," + mBlockList.get(i).getNumOption() + ")";
                    } else if (mBlockList.get(i).optionImage == R.drawable.green) {
                        pseudoStr += "G," + mBlockList.get(i).getNumOption() + ")";
                    } else if (mBlockList.get(i).optionImage == R.drawable.blue) {
                        pseudoStr += "B," + mBlockList.get(i).getNumOption() + ")";
                    } else if (mBlockList.get(i).optionImage == R.drawable.yellow) {
                        pseudoStr += "Y," + mBlockList.get(i).getNumOption() + ")";
                    } else if (mBlockList.get(i).optionImage == R.drawable.violet) {
                        pseudoStr += "P," + mBlockList.get(i).getNumOption() + ")";
                    } else if (mBlockList.get(i).optionImage == R.drawable.sky) {
                        pseudoStr += "K," + mBlockList.get(i).getNumOption() + ")";
                    }
                    break;
                case R.drawable.end:
                    checkEnd = true;
                    break;
            }
        }
        return pseudoStr;
    }
}

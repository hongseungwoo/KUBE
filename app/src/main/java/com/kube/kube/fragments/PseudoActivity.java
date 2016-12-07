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
        int count = 1;
        for(int i = start;i < mBlockList.size();i+=5){
            if(checkEnd == true)
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
                    if (mBlockList.get(i).getOptionImage() == R.drawable.infrared) {
                        pseudoStr += " 적외선 센서 " + mBlockList.get(i).getModuleNum() + "의 값이 " + mBlockList.get(i).getNumOption() + "일 동안 다음과 같은 동작을 수행합니다.\n";
                    } else {
                        pseudoStr += " 초음파 센서 " + mBlockList.get(i).getModuleNum() + "의 값이 " + mBlockList.get(i).getNumOption() + "일 동안 다음과 같은 동작을 수행합니다.\n";
                    }
                    break;
                case R.drawable.whileendblock:
                    pseudoStr += "while문을 종료합니다.\n";
                    checkEnd=true;
                    break;
                case R.drawable.ifblock:
                    if (mBlockList.get(i).getOptionImage() == R.drawable.infrared) {
                        pseudoStr += " 만약에 적외선 센서 " + mBlockList.get(i).getModuleNum() + "의 값이 " + mBlockList.get(i).getNumOption() + "이면 다음과 같은 동작을 수행합니다.\n";
                    } else {
                        pseudoStr += " 만약에 초음파 센서 " + mBlockList.get(i).getModuleNum() + "의 값이 " + mBlockList.get(i).getNumOption() + "이면 다음과 같은 동작을 수행합니다.\n";
                    }
                    break;
                case R.drawable.ifendblock:
                    pseudoStr += "if문을 종료합니다.\n";
                    checkEnd=true;
                    break;
                case R.drawable.mainmotorblcok:
                    pseudoStr += ""+ count+". " + "메인 모터 " + mBlockList.get(i).getModuleNum() + "이(가)";
                    count++;
                    if (mBlockList.get(i).optionImage == R.drawable.right)
                        pseudoStr += "시계 방향으로 " + mBlockList.get(i).getNumOption() + "속도로 동작합니다.\n";
                    else
                        pseudoStr += "반시계 방향으로 " + mBlockList.get(i).getNumOption() + "속도로 동작합니다.\n";
                    break;
                case R.drawable.submotorblcok:
                    pseudoStr += ""+ count+". " +"서브 모터 " + mBlockList.get(i).getModuleNum() + "이(가)" + mBlockList.get(i).getNumOption() + "도 만큼 동작합니다.\n";
                    count++;
                    break;
                case R.drawable.ledblock:
                    pseudoStr += ""+ count+". " +"LED " + mBlockList.get(i).getModuleNum() + "이(가) ";
                    count++;
                    if (mBlockList.get(i).optionImage == R.drawable.red) {
                        pseudoStr += "빨강색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    } else if (mBlockList.get(i).optionImage == R.drawable.green) {
                        pseudoStr += "녹색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    } else if (mBlockList.get(i).optionImage == R.drawable.blue) {
                        pseudoStr += "파랑색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    } else if (mBlockList.get(i).optionImage == R.drawable.yellow) {
                        pseudoStr += "노랑색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    } else if (mBlockList.get(i).optionImage == R.drawable.violet) {
                        pseudoStr += "보라색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    } else if (mBlockList.get(i).optionImage == R.drawable.sky) {
                        pseudoStr += "하늘색으로 " + mBlockList.get(i).getNumOption() + "밝기로 동작합니다.\n";
                    }
                    break;
                case R.drawable.sleep:
                    pseudoStr += ""+mBlockList.get(i).getNumOption()+"동안 동작을 멈춥니다.\n";
                    break;
                case R.drawable.end:
                    checkEnd = true;
                    pseudoStr+= "모든 작동을 멈춥니다.\n";
                    break;
            }
        }
        return pseudoStr;
    }
}

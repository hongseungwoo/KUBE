package com.kube.kube.workspace;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kube.kube.R;

import java.util.ArrayList;

/**
 * Created by jutan on 2016-11-17.
 */
public class WorkspaceAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<WorkspaceItem> BlockList;
    private int dorPos;
    private int curBlock;

    public WorkspaceAdapter(Context context) {
        mContext = context;
        BlockList = new ArrayList<WorkspaceItem>();
        for(int i =0; i<75; i++){
            WorkspaceItem newItem = new WorkspaceItem();
            newItem.setBlockImage(R.drawable.empty);
            newItem.setOptionImage(R.drawable.empty);
            newItem.setModuleNum("");
            newItem.setNumOption("");
            BlockList.add(newItem);
        }
    }

    public void setCurBlock(int block){
        curBlock = block;
    }

    @Override
    public int getCount() {
        return BlockList.size();
    }

    @Override
    public Object getItem(int position) {
        return BlockList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.blockitemlayout, parent, false);
        }
        convertView.setTag(position);
        convertView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                switch (event.getAction()) {

                    // 이미지를 드래그 시작될때
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("DragClickListener", "ACTION_DRAG_STARTED");

                        break;

                    // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("DragClickListener", "ACTION_DRAG_ENTERED");

                        break;

                    // 드래그한 이미지가 영역을 빠져 나갈때
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("DragClickListener", "ACTION_DRAG_EXITED");
                        break;

                    // 이미지를 드래그해서 드랍시켰을때
                    case DragEvent.ACTION_DROP:
                        Log.d("DragClickListener", "ACTION_DROP");
                        dorPos = (int) v.getTag();
                        BlockList.get(dorPos).setBlockImage(curBlock);
                        setDirection(dorPos, curBlock);
                        Log.d("DragListener" , ""+dorPos +"         "+ BlockList.get(dorPos).blockImage);
                        notifyDataSetChanged();
                        break;
                }

                return true;
            }
        });

        ImageView blockImage = (ImageView) convertView.findViewById(R.id.blockImageView);
        ImageView optionImage = (ImageView) convertView.findViewById(R.id.optionImageView1);
        TextView moduleNumText = (TextView)convertView.findViewById(R.id.moduleNumText);
        TextView numOptionText = (TextView) convertView.findViewById(R.id.numOptionTextView);



        blockImage.setImageResource(BlockList.get(position).getBlockImage());
        optionImage.setImageResource(BlockList.get(position).getOptionImage());
        numOptionText.setText(BlockList.get(position).getNumOption());
        moduleNumText.setText(BlockList.get(position).getModuleNum());
        return convertView;
    }


   public void setOption(int block, String optionNum, String moduleNum, int position){
        BlockList.get(position).setOptionImage(block);
        BlockList.get(position).setNumOption(optionNum);
        BlockList.get(position).setModuleNum("No."+moduleNum);
        Log.d("입력된 블락 옵션들 ", ""+BlockList.get(position).getOptionImage()+BlockList.get(position).getModuleNum()+BlockList.get(position).getNumOption());
        notifyDataSetChanged();
    }

    private void setDirection(int position, int block){
        Log.d("direction      block", ""+block +"  "+position);
        if(block == R.drawable.ifblock||block == R.drawable.whileblock){
            BlockList.get(position-1).setBlockImage(R.drawable.up_to_right);
        }
        if(block == R.drawable.whileendblock||block == R.drawable.ifendblock)
        {
            BlockList.get(position-1).setBlockImage(R.drawable.left_to_down);
        }
    }
}


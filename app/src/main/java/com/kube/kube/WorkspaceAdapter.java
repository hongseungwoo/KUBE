package com.kube.kube;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jutan on 2016-11-17.
 */
public class WorkspaceAdapter extends BaseAdapter{
    Context mContext;
    ArrayList<WorkspaceItem> BlockList;
    int dorPos;
    int curBlock;

    public WorkspaceAdapter(Context context) {
        mContext = context;
        BlockList = new ArrayList<WorkspaceItem>();
        for(int i =0; i<75; i++){
            WorkspaceItem newItem = new WorkspaceItem();
            newItem.setBlockImage(R.drawable.empty);
            newItem.setOptionImage(R.drawable.empty);
            newItem.setTimeOption("");
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
                        Log.d("DragListener" , ""+dorPos +"         "+ BlockList.get(dorPos).blockImage);
                        notifyDataSetChanged();
                        break;
                }

                return true;
            }
        });
        ImageView blockImage = (ImageView) convertView.findViewById(R.id.blockImageView);
        ImageView optionImage = (ImageView) convertView.findViewById(R.id.optionImageView);
        TextView timeText = (TextView) convertView.findViewById(R.id.timeTextView);

        blockImage.setOnClickListener(new blockClickListener());

        blockImage.setImageResource(BlockList.get(position).getBlockImage());
        optionImage.setImageResource(BlockList.get(position).getOptionImage());
        timeText.setText(BlockList.get(position).getTimeOption());

        return convertView;
    }

    public class blockClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

    public void setOption(int block, String time, int position){
        BlockList.get(position).setOptionImage(block);
        BlockList.get(position).setTimeOption(time);
        notifyDataSetChanged();
    }
}

package com.kube.kube;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jutan on 2016-11-14.
 */

public class WorkspaceAdapter extends BaseAdapter {
    private ArrayList<WorkspaceItem> workspaceItems;
    private Context mContext;
    int dropPos;
    int curBlock;

    public void setCurBlock(int curBlock) {
        this.curBlock = curBlock;
    }

    public WorkspaceAdapter (Context context){
        mContext = context;
        workspaceItems = new ArrayList<WorkspaceItem>();
        for(int i = 0; i < 75;i++){
            WorkspaceItem newItem = new WorkspaceItem();
            newItem.setBlockImage(R.drawable.empty);
            newItem.setOptionImage(R.drawable.empty);
            newItem.setTimeOption("0초");
            workspaceItems.add(newItem);
        }
    }

    @Override
    public int getCount() {
        return workspaceItems.size();
    }

    @Override
    public Object getItem(int position) {
        return workspaceItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.blockitemlayout, null);
            grid.setOnDragListener(new myDragListener());
            grid.setTag(position);

            ImageView blockImage = (ImageView) grid.findViewById(R.id.blockImageView);
            ImageView optionImage = (ImageView) grid.findViewById(R.id.optionImageView);
            TextView timeOptionText = (TextView) grid.findViewById(R.id.timeTextView);

            blockImage.setImageResource(workspaceItems.get(position).blockImage);
            optionImage.setImageResource(workspaceItems.get(position).optionImage);
            timeOptionText.setText(workspaceItems.get(position).timeOption);


        }
        else{
            grid = (View) convertView;
        }

        return grid;
    }

    public void setBlock(int position){
        workspaceItems.get(position).setBlockImage(curBlock);
        Log.d("workspaceItems", "postion :" +position+ "item" + workspaceItems.get(position).getBlockImage() +"   "+curBlock );
        this.notifyDataSetChanged();
    }

    class myDragListener implements View.OnDragListener {


        public boolean onDrag(View currentView, DragEvent event) {

            View saveView;
            // 이벤트 시작
            switch (event.getAction()) {

                // 이미지를 드래그 시작될때
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    saveView = (View) event.getLocalState();
                    break;

                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");
                    // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
//                    currentView.setBackground(targetShape);
                    break;

                // 드래그한 이미지가 영역을 빠져 나갈때
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");
//                    currentView.setBackground(normalShape);
                    break;

                // 이미지를 드래그해서 드랍시켰을때
                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DOP");
                    dropPos = (int) currentView.getTag();
                    setBlock(dropPos);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
//                    v.setBackground(normalShape); // go back to normal shape
                    break;

                default:
                    break;
            }
            return true;
        }
    }

}

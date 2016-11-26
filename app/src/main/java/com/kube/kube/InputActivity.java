package com.kube.kube;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class    InputActivity extends Activity {
    int image=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input);

        Intent getIntent = getIntent();
        int curBlock = getIntent.getIntExtra("Block", 0);

        
        final ImageView blockImg = (ImageView)findViewById(R.id.optionImageView);
        final ImageView inequalImg = (ImageView)findViewById(R.id.inequalityImageView);
        final EditText numEdit = (EditText)findViewById(R.id.NumEditText);
        final TextView unitText = (TextView)findViewById(R.id.UnitTextView);

        Button saveBtn = (Button)findViewById(R.id.saveButton);
        Button cancelBtn = (Button) findViewById(R.id.CancelButton);

       switch (curBlock){
           case R.drawable.sleep:
               blockImg.setImageResource(R.drawable.empty);
               blockImg.setTag("EMPTY");
               blockImg.setVisibility(View.INVISIBLE);
               inequalImg.setVisibility(View.INVISIBLE);
               numEdit.setHint("0-100");
               unitText.setText("초");
               break;
           case R.drawable.submotorblcok:
               blockImg.setImageResource(R.drawable.right);
               blockImg.setTag("RIGHT");
               blockImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       image++;
                       switch (image%2){
                           case 0 :
                               blockImg.setImageResource(R.drawable.right);
                               blockImg.setTag("RIGHT");
                               break;
                           case 1:
                               blockImg.setImageResource(R.drawable.left);
                               blockImg.setTag("LEFT");
                               break;
                       }
                   }
               });
               inequalImg.setVisibility(View.INVISIBLE);
               unitText.setText("도");
               numEdit.setHint("각도 0-100");

               break;
           case R.drawable.mainmotorblcok:
               blockImg.setImageResource(R.drawable.right);
               blockImg.setTag("RIGHT");
               unitText.setVisibility(View.INVISIBLE);
               numEdit.setHint("회전 속도 0-100");
               blockImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       image++;
                       switch (image%2){
                           case 0 :
                               blockImg.setImageResource(R.drawable.right);
                               blockImg.setTag("RIGHT");
                               break;
                           case 1:
                               blockImg.setImageResource(R.drawable.left);
                               blockImg.setTag("LEFT");
                               break;
                       }
                   }
               });
               inequalImg.setVisibility(View.INVISIBLE);
               break;
           case R.drawable.ledblock:
               blockImg.setTag("RED");
               blockImg.setImageResource(R.drawable.red);
               blockImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       image++;
                       switch (image%7){
                           case 0:
                               blockImg.setImageResource(R.drawable.red);
                               blockImg.setTag("RED");
                               break;
                           case 1:
                               blockImg.setImageResource(R.drawable.orange);
                               blockImg.setTag("ORANGE");
                               break;
                           case 2:
                               blockImg.setImageResource(R.drawable.yellow);
                               blockImg.setTag("YELLOW");
                               break;
                           case 3:
                               blockImg.setImageResource(R.drawable.green);
                               blockImg.setTag("GREEN");
                               break;
                           case 4:
                               blockImg.setImageResource(R.drawable.blue);
                               blockImg.setTag("BLUE");
                               break;
                           case 5:
                               blockImg.setImageResource(R.drawable.bluishviolet);
                               blockImg.setTag("BV");
                               break;
                           case 6:
                               blockImg.setImageResource(R.drawable.violet);
                               blockImg.setTag("VIOLET");
                               break;
                       }
                   }
               });
               inequalImg.setVisibility(View.INVISIBLE);
               unitText.setVisibility(View.INVISIBLE);
               numEdit.setHint("밝기 0-100");
               break;
           case R.drawable.whileblock:
           case R.drawable.ifblock:
               blockImg.setTag("ULTRA");
               blockImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       image++;
                       switch (image%2){
                           case 0:
                               blockImg.setImageResource(R.drawable.ultrasonic);
                               blockImg.setTag("ULTRA");
                               break;
                           case 1:
                               blockImg.setImageResource(R.drawable.infrared);
                               blockImg.setTag("INFRARED");
                               break;
                       }

                   }
               });

               unitText.setText("m");
               break;
       }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String optionBlcok = (String) blockImg.getTag();
                String optionText = numEdit.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("optionBlock", optionBlcok);
                intent.putExtra("optionText", optionText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });



    }
}

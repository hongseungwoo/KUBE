package com.kube.kube;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by jutan on 2016-11-07.
 */
public class MyCanvas extends View  {
    Bitmap whileImage, ifImage, mainMotorImage, subMotorImage, ledImage;
    int x, y = 0;

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public MyCanvas(Context context) {
        super(context);

        Resources r = context.getResources();
        whileImage = BitmapFactory.decodeResource(r, R.drawable.whileblock);
        ifImage = BitmapFactory.decodeResource(r, R.drawable.ifblock);
        mainMotorImage = BitmapFactory.decodeResource(r, R.drawable.mainmotorblcok);
        subMotorImage = BitmapFactory.decodeResource(r,R.drawable.submotorblcok);
        ledImage = BitmapFactory.decodeResource(r, R.drawable.ledblock);
    }

    public MyCanvas(Context context,AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCanvas(Context context,AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
    }

    private int numColumns, numRows; //가로축 세로축 갯수
    private int cellWidth, cellHeight; // 셀의 가로 세로 크기

    private Paint blackPaint = new Paint();
    private boolean[][] cellChecked;

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

    }
}

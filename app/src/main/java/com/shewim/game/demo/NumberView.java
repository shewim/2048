package com.shewim.game.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by TTWing on 2016/7/21.
 */
public class NumberView extends View {
    private int value = 0;
    private int width = 0,height = 0;

    public void setValue(int value){
        if(this.value != value)
        {
            this.value = value;
            invalidate();
        }
    }
    public void initValue() {
       int x = new Random().nextInt(40);
        setValue(x == 1?2:1);
    }
    public void clearValue(){
        setValue(0);
    }

    public void setScore(){

    }
    public int tryEat(NumberView view){
        int resultScore = 0;
        if(value == 0){
            setValue(view.getValue());
            view.clearValue();
        }else if(this.equals(view)) {
            setValue(value + 1);
            view.clearValue();
            return (int) Math.pow(2,value);
        }

        return 0;
    }
    public int getValue(){
        return value;
    }
    public NumberView(Context context) {
        super(context);
        init(context);
    }


    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @TargetApi(21)
    public NumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(width <= 0){
            width = getMeasuredWidth();
            height = getMeasuredHeight();
        }
        Paint paint = new Paint();
        @ColorInt int color = 0xff555555;
        color = color + value%3 * 0xff330000 + value/3%5*0xff001100 + value/15%5*0xff000011;
        paint.setColor(color);
        canvas.drawRect(0,0,width,height,paint);
        if(value > 0){
            int x = (int) Math.pow(2,value);

            Paint paint2 = new Paint();
            paint2.setColor(0xff000000);
            if(x<100){
                paint2.setTextSize(width / 2);
            }else if(x > 100 && x < 1000){
                paint2.setTextSize(width /3);
            }else{
                paint2.setTextSize(width / 4);
            }
            canvas.drawText(String.valueOf(x),width*2/5,height*3/5,paint2);
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NumberView && ((NumberView) o).getValue() == value;
    }

    public boolean isEmpty() {
        return value == 0;
    }
}

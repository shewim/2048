package com.shewim.game.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TTWing on 2016/7/21.
 */
public class NumberLayout extends LinearLayout {
    private static final int LAYOUT_SIZE = 4;
    private NumberView [][] numberViews = new NumberView[LAYOUT_SIZE][LAYOUT_SIZE];

    private boolean gameOver;
    private ArrayList<Integer> emptyList;

    public NumberLayout(Context context) {
        super(context);
        init(context);
    }

    public NumberLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public NumberLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void reStart(){
        emptyList.clear();
        for (int i = 0; i < LAYOUT_SIZE* LAYOUT_SIZE;i++){
            numberViews[i/LAYOUT_SIZE][i%LAYOUT_SIZE].clearValue();
            emptyList.add(i);
        }
        initARandomView();
        initARandomView();
    }
    private void init(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int v = Math.min(width,height)/5;
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams = new LayoutParams(v*4 + 8*4, v);
        llParams.gravity = Gravity.CENTER_HORIZONTAL;
        llParams.setMargins(0,4,0,4);
        LayoutParams viewParams = new LayoutParams(v, v);
        viewParams.setMargins(4,0,4,0);
        viewParams.gravity = Gravity.CENTER_HORIZONTAL;
        for(int i = 0; i < LAYOUT_SIZE ;i++) {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(llParams);
            for(int j = 0; j < LAYOUT_SIZE;j++){
                NumberView view = new NumberView(context);
                view.setLayoutParams(viewParams);

                numberViews[i][j] = view;
                ll.addView(view);
            }
            this.addView(ll);
        }
        emptyList = new ArrayList<>();
        for (int i = 0; i < LAYOUT_SIZE* LAYOUT_SIZE;i++){
            emptyList.add(i);
        }
        initARandomView();
        initARandomView();
    }

    private void initARandomView() {
        if(emptyList.size() > 0)
        {
            int x = new Random().nextInt(emptyList.size());
            numberViews[emptyList.get(x)/LAYOUT_SIZE][emptyList.get(x)%LAYOUT_SIZE].initValue();
            emptyList.remove(x);
        }
    }

    public boolean onGo(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        if(gameOver){
            return false;
        }
        float x = e2.getX() - e1.getX();
        float y = e2.getY() - e1.getY();
        boolean flag = false;
        int x1,y1,x2,y2;
        for(int i=0;i< LAYOUT_SIZE;i++) {
            label:for(int n = 0;n < LAYOUT_SIZE - 1;n++){
                for(int m = n+1;m < LAYOUT_SIZE; m++) {
                    if(Math.abs(x) > Math.abs(y)) {
                        //左右移动
                        if(x < 0) {
                            //
                            x1 = i;y1=n;x2=i;y2=m;
                        }else{
                            x1 = i;y1=LAYOUT_SIZE - 1 - n;x2=i;y2=LAYOUT_SIZE - 1 - m;
                        }
                    }else {
                        //上下移动
                        if(y < 0){
                            x1 = n;y1=i;x2=m;y2=i;
                        }else {
                            x1 = LAYOUT_SIZE - 1 - n;y1=i;x2=LAYOUT_SIZE - 1 - m;y2=i;
                        }
                    }
                    if(numberViews[x1][y1].isEmpty()&&!numberViews[x2][y2].isEmpty()){
                        flag = true;
                        for(int j = 0; j < emptyList.size();j++) {
                            if(emptyList.get(j) == x1*LAYOUT_SIZE + y1){
                                emptyList.add(x2*LAYOUT_SIZE + y2);
                                emptyList.remove(j);
                                break;
                            }
                        }
                    }
                    if(!numberViews[x1][y1].isEmpty()&&numberViews[x1][y1].equals(numberViews[x2][y2])){
                        flag = true;
                        emptyList.add(x2*LAYOUT_SIZE + y2);
                    }
                    if (numberViews[x1][y1].tryEat(numberViews[x2][y2])){
                        continue label;
                    }
                }
            }
        }

        if(flag){
            initARandomView();
        }
        if(emptyList.size() <= 0 && isGameOver()){
            Toast.makeText(getContext(),"Game Over",Toast.LENGTH_LONG).show();
            gameOver = true;
        }
        return false;
    }

    private boolean isGameOver() {
        for(int i= 0;i < LAYOUT_SIZE ;i++){
            for(int j = 0;j < LAYOUT_SIZE;j++){
                if(((i + 1< LAYOUT_SIZE)&&numberViews[i][j].equals(numberViews[i+1][j]))
                        ||((j + 1<LAYOUT_SIZE)&&numberViews[i][j].equals(numberViews[i][j+1]))){
                    return false;
                }
            }
        }
        return true;
    }

}
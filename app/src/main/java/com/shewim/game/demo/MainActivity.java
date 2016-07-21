package com.shewim.game.demo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private GestureDetector detector;
    private NumberLayout numberLayout;
    private TextView mTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new GestureDetector(this,this);
        numberLayout = ((NumberLayout) findViewById(R.id.number_layout));
        View btnRestart = findViewById(R.id.btn_restart);
        mTextScore = (TextView) findViewById(R.id.text_score);
        final TextView mTextHistoryScore = (TextView) findViewById(R.id.text_history_score);
        numberLayout.setOnGameOverListener(new NumberLayout.OnGameOverListener(){
            @Override
            public void OnGameOver() {

            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberLayout.reStart();
                mTextScore.setText(String.format("分数：%d", 0));
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mTextScore.setText(String.format("分数：%d",numberLayout.onGo(e1, e2, velocityX, velocityY) ));
        return false;
    }
}

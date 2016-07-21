package com.shewim.game.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final String SHARED_PREFERENCES_NAME = "com.shewim.2018.xml";
    private static final String SP_KEY_HISTORY_SCORE = "sp_key_history_score";
    private GestureDetector detector;
    private NumberLayout numberLayout;
    private TextView mTextScore;
    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor editor;
    private int highScore;
    private TextView mTextHistoryScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new GestureDetector(this,this);
        numberLayout = ((NumberLayout) findViewById(R.id.number_layout));
        View btnRestart = findViewById(R.id.btn_restart);
        mTextScore = (TextView) findViewById(R.id.text_score);
       mTextHistoryScore = (TextView) findViewById(R.id.text_history_score);
        highScore = getHistoryScore();
        mTextHistoryScore.setText(String.format("最高分数：%d",highScore ));
        numberLayout.setOnGameOverListener(new NumberLayout.OnGameOverListener(){
            @Override
            public void OnGameOver(int score) {
                if(score > highScore){
                    highScore = score;
                    mTextHistoryScore.setText(String.format("最高分数：%d", score));
                    storeHistoryScore(highScore);
                }
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = numberLayout.getCountScore();
                if(score > highScore){
                    highScore = score;
                    mTextHistoryScore.setText(String.format("最高分数：%d", score));
                    storeHistoryScore(highScore);
                }
                numberLayout.reStart();
                mTextScore.setText(String.format("分数：%d", 0));
            }
        });
    }

    @Override
    protected void onPause() {
        int score = numberLayout.getCountScore();
        if(score > highScore){
            highScore = score;
            mTextHistoryScore.setText(String.format("最高分数：%d", score));
            storeHistoryScore(highScore);
        }
        super.onPause();
    }

    private int getHistoryScore() {
        if(mySharedPreferences == null)
        {
            mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        }
        return mySharedPreferences.getInt(SP_KEY_HISTORY_SCORE,0);
    }

    private void storeHistoryScore(int score) {
        if(mySharedPreferences == null)
        {
            mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        }
        if(editor == null)
        {
            editor = mySharedPreferences.edit();
        }
        editor.putInt(SP_KEY_HISTORY_SCORE, score);
        editor.apply();
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

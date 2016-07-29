package com.shewim.game.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private static final String SHARED_PREFERENCES_NAME = "com.shewim.2018.xml";
    private static final String SP_KEY_HISTORY_SCORE = "sp_key_history_score";
    private GestureDetector detector;
    private NumberLayout numberLayout;
    private TextView mTextScore;
    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor editor;
    private int highScore;
    private TextView mTextHistoryScore;
    private TextView btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutByCode();
//        setContentView(R.layout.activity_main);
//        numberLayout = ((NumberLayout) findViewById(R.id.number_layout));
//        btnRestart = findViewById(R.id.btn_restart);
//        mTextScore = (TextView) findViewById(R.id.text_score);
//        mTextHistoryScore = (TextView) findViewById(R.id.text_history_score);
        highScore = getHistoryScore();
        mTextHistoryScore.setText(String.format("最高分数：%d",highScore ));
        numberLayout.setOnScoreListener(new NumberLayout.onScoreListener() {
            @Override
            public void onScore(int score) {
                mTextScore.setText(String.format("分数：%d",score));
            }
        });
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
    private int dpToPx( float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);

    }
    private void initLayoutByCode() {
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setBackgroundColor(0xff777777);
        numberLayout = new NumberLayout(this);
        RelativeLayout.LayoutParams params1 =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params1.setMargins(0,dpToPx(20),0,0);
        numberLayout.setId(R.id.number_layout);
        numberLayout.setLayoutParams(params1);
        rootView.addView(numberLayout);
//
//        <TextView
        mTextHistoryScore = new TextView(this);
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content" />
        RelativeLayout.LayoutParams params2 =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        android:layout_marginTop="20dp"
//        android:layout_marginLeft="10dp"
//        android:layout_marginRight="20dp"
        params2.setMargins(dpToPx(10),dpToPx(20),dpToPx(20),0);
//        android:layout_alignParentRight="true"
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.BELOW,numberLayout.getId());
//        android:text="最高分数：0"
//        android:layout_below="@+id/number_layout"
        mTextHistoryScore.setId(R.id.text_history_score);
        mTextHistoryScore.setLayoutParams(params2);
        rootView.addView(mTextHistoryScore);


//        <TextView
        mTextScore = new TextView(this);
        RelativeLayout.LayoutParams params3 =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(dpToPx(10),dpToPx(20),dpToPx(10),0);
        params3.addRule(RelativeLayout.BELOW,numberLayout.getId());
        params3.addRule(RelativeLayout.LEFT_OF,mTextHistoryScore.getId());
        mTextScore.setText(String.format("分数：%d", 0));
        mTextScore.setId(R.id.text_score);
        mTextScore.setLayoutParams(params3);
        rootView.addView(mTextScore);

//        <TextView
        btnRestart = new TextView(this);RelativeLayout.LayoutParams params4 =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params4.setMargins(dpToPx(20),dpToPx(20),0,0);
        params4.addRule(RelativeLayout.BELOW,numberLayout.getId());
        btnRestart.setLayoutParams(params4);
        btnRestart.setId(R.id.btn_restart);
        btnRestart.setText("重新开始");
        rootView.addView(btnRestart);

        setContentView(rootView);
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

}

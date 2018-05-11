package com.roger.shot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.roger.demo4roger.R;


public class ShotActivity extends AppCompatActivity {

    public View contentShot;
    public String mText;
    public View.OnTouchListener onTouchListener;
    float mMoveX;
    float mMoveY;
    float mCurDownX;
    float mCurDownY;
    float mCurMoveX, mCurMoveY;
    long mStartTime;
    long mMirrorTime;
    boolean mIsFirstShot = true;
    int mShotCount = 0;
    private View mShotBtn;
    private View mShotBtn2;
    private View mShotBtn3;
    private View mShotBtn4;
    private Button mStartBtn;
    private View mMirrorBtn;
    private View mMoveVisualAngle;
    public long mShotTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将activity设置为全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shot);
        contentShot = findViewById(R.id.content_shot);
        mMoveVisualAngle = findViewById(R.id.move_visual_angle);
        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (v.getId()) {

                    case R.id.move_visual_angle:
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                mCurDownX = event.getX();
                                mCurDownY = event.getY();

                                break;
                            case MotionEvent.ACTION_MOVE:

                                mCurMoveX = event.getX();
                                mCurMoveY = event.getY();
                                break;

                            case MotionEvent.ACTION_UP:

                                mMoveX = mMoveX + Math.abs(event.getX() - mCurDownX);
                                mMoveY = mMoveY + Math.abs(event.getY() - mCurDownY);

                                break;
                        }
                        break;

                    case R.id.shot_btn:
                    case R.id.shot_btn2:
                    case R.id.shot_btn3:
                    case R.id.shot_btn4:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (mIsFirstShot) {
                                mShotTime = System.currentTimeMillis() - mStartTime;
//                                Toast.makeText(getActivity(), mText + ",2s内狂点Shot键,统计次数", Toast.LENGTH_LONG).show();
                                mIsFirstShot = false;
                                mShotCount++;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();

                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mShotBtn.setVisibility(View.INVISIBLE);
                                                mShotBtn2.setVisibility(View.INVISIBLE);
                                                mShotBtn3.setVisibility(View.INVISIBLE);
                                                mShotBtn4.setVisibility(View.INVISIBLE);
                                                mMoveX = mMoveX + Math.abs( mCurMoveX - mCurDownX);
                                                mMoveY = mMoveY + Math.abs( mCurMoveY - mCurDownY);
                                                mText = "开镜耗时" + mMirrorTime + "ms,开枪耗时" + mShotTime + "ms";
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("测试反应速度")
                                                        .setMessage(mText + ",2s点击次数:" + mShotCount + ",x方向移动: " + mMoveX + ", Y方向移动: " + mMoveY)
                                                        .setCancelable(false)
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                AlertDialog dialog = builder.create();
                                                dialog.show();

                                                mStartBtn.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                }).start();
                            } else {
                                // 开始狂点Shot键
                                mShotCount++;
                            }
                        }

                        break;


                    case R.id.mirror_btn:

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            mMirrorTime = System.currentTimeMillis() - mStartTime;
                            mMirrorBtn.setVisibility(View.INVISIBLE);
                            contentShot.setBackgroundResource(R.drawable.bg_3);
//                mShotBtn.performClick();
                        }

                        break;


                }

                return true;
            }
        };
        mMoveVisualAngle.setOnTouchListener(onTouchListener);
        mMirrorBtn =  findViewById(R.id.mirror_btn);
        mStartBtn =  findViewById(R.id.start_btn);
        mShotBtn =  findViewById(R.id.shot_btn);
        mShotBtn2 =  findViewById(R.id.shot_btn2);
        mShotBtn3 =  findViewById(R.id.shot_btn3);
        mShotBtn4 =  findViewById(R.id.shot_btn4);
        mShotBtn.setOnTouchListener(onTouchListener);
        mShotBtn2.setOnTouchListener(onTouchListener);
        mShotBtn3.setOnTouchListener(onTouchListener);
        mShotBtn4.setOnTouchListener(onTouchListener);
        mMirrorBtn.setOnTouchListener(onTouchListener);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                contentShot.setBackgroundResource(R.drawable.bg_1);
                mShotBtn.setVisibility(View.INVISIBLE);
                mShotBtn2.setVisibility(View.INVISIBLE);
                mShotBtn3.setVisibility(View.INVISIBLE);
                mStartBtn.setVisibility(View.INVISIBLE);
                mMirrorBtn.setVisibility(View.INVISIBLE);
                final int time = 1000;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contentShot.setBackgroundResource(R.drawable.bg_2);
                                mMirrorBtn.setVisibility(View.VISIBLE);
                                mShotBtn.setVisibility(View.VISIBLE);
                                mShotBtn2.setVisibility(View.VISIBLE);
                                mShotBtn3.setVisibility(View.VISIBLE);
                                mShotBtn4.setVisibility(View.VISIBLE);
                                mStartTime = System.currentTimeMillis();
                                mIsFirstShot = true;
                                mShotCount = 0;
                                mMirrorTime = 0;
                                mShotTime = 0;
                                mMoveX = 0;
                                mMoveY = 0;
//                                mMirrorBtn.performClick();
                            }
                        });

                    }
                }).start();


                break;



        }
    }

    private Activity getActivity() {
        return this;
    }
}

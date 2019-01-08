package com.roger.shot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.roger.demo4roger.R;


public class ShotActivity extends AppCompatActivity {

    private static final String TAG = "Test";
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
    int mJumpCount = 0;
    private View mShotBtn;
    private View mShotLeftBtn;
    private View mShotBtn3;
    private View mShotBtn4;
    private View mJump;
    private Button mStartBtn;
    private View mMirrorBtn;
    private View mMoveVisualAngle;
    public long mShotTime;
    public long mShotTimeMillis;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        initSound();
        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    switch (v.getId()) {

/*                    case R.id.move_visual_angle:
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
                        break;*/

                        case R.id.shot_btn:
                            fire();
                            break;
                        case R.id.left_btn:
                            playFireSound(1f, 0f);
                            fire();
                            break;
                        case R.id.shot_top_right_btn:
                            playBoxingSound();
                            fire();
                            break;
                        case R.id.shot_right_bottom_btn:
                            playFireSound(0f, 1f);
                            fire();
                            break;

                        case R.id.aim_btn:

                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                mMirrorTime = System.currentTimeMillis() - mStartTime;
                                mMirrorBtn.setVisibility(View.INVISIBLE);
                                contentShot.setBackgroundResource(R.drawable.bg_3);
//                mShotBtn.performClick();
                            }
                            break;

                        case R.id.jump:
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                mJumpCount++;
                            }
                            break;


                    }
                }
                return true;
            }
        };
        mMoveVisualAngle.setOnTouchListener(onTouchListener);
        mMirrorBtn = findViewById(R.id.aim_btn);
        mStartBtn = findViewById(R.id.start_btn);
        mShotBtn = findViewById(R.id.shot_btn);
        mShotLeftBtn = findViewById(R.id.left_btn);
        mShotBtn3 = findViewById(R.id.shot_top_right_btn);
        mShotBtn4 = findViewById(R.id.shot_right_bottom_btn);
        mJump = findViewById(R.id.jump);
        mShotBtn.setOnTouchListener(onTouchListener);
        mShotLeftBtn.setOnTouchListener(onTouchListener);
        mShotBtn3.setOnTouchListener(onTouchListener);
        mShotBtn4.setOnTouchListener(onTouchListener);
        mMirrorBtn.setOnTouchListener(onTouchListener);
        mJump.setOnTouchListener(onTouchListener);
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

    int mTestSecond = 10;

    private void fire() {
        mShotTimeMillis = System.currentTimeMillis();
        if (mIsFirstShot) {
            mShotTime = mShotTimeMillis - mStartTime;
//                                Toast.makeText(getActivity(), mText + ",2s内狂点Shot键,统计次数", Toast.LENGTH_LONG).show();
            mIsFirstShot = false;
            mShotCount++;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(mTestSecond * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mShotBtn.setVisibility(View.INVISIBLE);
                            mShotLeftBtn.setVisibility(View.INVISIBLE);
                            mShotBtn3.setVisibility(View.INVISIBLE);
                            mShotBtn4.setVisibility(View.INVISIBLE);
                            mJump.setVisibility(View.INVISIBLE);
                            mMoveX = mMoveX + Math.abs(mCurMoveX - mCurDownX);
                            mMoveY = mMoveY + Math.abs(mCurMoveY - mCurDownY);
                            mText = "开镜耗时" + mMirrorTime + "ms,开枪耗时" + mShotTime + "ms";
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("测试反应速度")
                                    .setMessage(mText + ", " + mTestSecond + "s点击Shot次数:" + mShotCount + ", Jump:" + mJumpCount + ",x方向移动: " + mMoveX + ", Y方向移动: " + mMoveY)
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                contentShot.setBackgroundResource(R.drawable.bg_1);
                mShotBtn.setVisibility(View.INVISIBLE);
                mShotLeftBtn.setVisibility(View.INVISIBLE);
                mShotBtn3.setVisibility(View.INVISIBLE);
                mStartBtn.setVisibility(View.INVISIBLE);
                mJump.setVisibility(View.INVISIBLE);
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
                                mShotLeftBtn.setVisibility(View.VISIBLE);
                                mShotBtn3.setVisibility(View.VISIBLE);
                                mShotBtn4.setVisibility(View.VISIBLE);
                                mJump.setVisibility(View.VISIBLE);
                                mStartTime = System.currentTimeMillis();
                                mIsFirstShot = true;
                                mShotCount = 0;
                                mJumpCount = 0;
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
            case R.id.listener_btn: //听到声音
                long bluetoothDelayed = System.currentTimeMillis() - mShotTimeMillis;
                Log.d(TAG, "onClick: bluetoothDelayed = " + bluetoothDelayed);
                if (bluetoothDelayed < mTestSecond * 1000) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("测试声音延时")
                            .setMessage("声音延时 = " + bluetoothDelayed + " ms")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                break;

        }
    }

    SoundPool soundPool;
    int mSoundIDFire;//开枪音效
    int mSoundIdBoxing;//拳击音

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initSound() {
//            soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
// 第一个参数为soundPool可以支持的声音数量，这决定了Android为其开设多大的缓冲区，第二个参数为声音类型，在这里标识为系统声音，除此之外还有AudioManager.STREAM_RING以及AudioManager.STREAM_MUSIC等，系统会根据不同的声音为其标志不同的优先级和缓冲区，最后参数为声音品质，品质越高，声音效果越好，但耗费更多的系统资源。
        soundPool = new SoundPool.Builder().build();
//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        mSoundIDFire = soundPool.load(this, R.raw.fire, 1);
        mSoundIdBoxing = soundPool.load(this, R.raw.boxing, 1);
    }

    private void playFireSound(float leftVolume, float rightVolume) {
        soundPool.play(
                mSoundIDFire,
                leftVolume,   //左耳道音量【0~1】
                rightVolume,   //右耳道音量【0~1】
                0,     //播放优先级【0表示最低优先级】
                0,     //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }

    private void playBoxingSound() {
        soundPool.play(mSoundIdBoxing, 0.1f, 1f, 0, 1, 1);
    }


    private Activity getActivity() {
        return this;
    }

    // TODO:liuwenrong 2018/12/20 测试 蓝牙翻页器事件

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: ------keyCode = " + keyCode + ", event = " + event);
        event.getKeyCode();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        return super.onKeyUp(keyCode, event);
    }
}

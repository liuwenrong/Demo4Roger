package aron.demo.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import aron.Utils.ELogger;

/**
 * Created by DB on 2017/6/9.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "SurfaceView";
    public static final int MESSAGE_DRAW = 1;
    public static final int FOREC_REFRESH = 11;
    //SurfaceHolder
    private SurfaceHolder mHolder;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;
    //    public int DRAW_DELAY = 33; // 即30帧刷新率
    public int DRAW_DELAY = 16; // 即60帧刷新率
    public Thread mThread;
    private HandlerThread handlerThread;
    private Handler handler;
    private long mLastDrawTime;
    private Handler.Callback mHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_DRAW:
                    long curTime = System.currentTimeMillis();
                    if (curTime - mLastDrawTime > DRAW_DELAY) {
                        draw();
                        mLastDrawTime = curTime;
                    }
                    if (msg.arg1 == FOREC_REFRESH) {
                        draw();  // 插帧
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        draw(); // 画两次 避免双缓冲绘制的不一样,防止闪烁
                    }
//                Canvas canvas = getHolder().lockCanvas();
//                Paint paint = new Paint();
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setColor(Color.GREEN);
//                paint.setStrokeWidth(10);
//                canvas.drawColor(Color.WHITE);
//                canvas.drawPath(path, paint);
//                getHolder().unlockCanvasAndPost(canvas);
                    return true;
            }
            return false;
        }
    };

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }


    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        //添加回调
        mHolder.addCallback(this);
        resetPaint();
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);


    }

    //Surface的生命周期
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        //锁定画布并返回画布对象
        mCanvas = mHolder.lockCanvas();
        //接下去就是在画布上进行一下draw
        mCanvas.drawColor(Color.BLACK/*WHITE*/);
        //当画布内容不为空时，才post，避免出现黑屏的情况。
        if (mCanvas != null)
            mHolder.unlockCanvasAndPost(mCanvas);
//        mThread = new Thread(this);
//        mThread.start();
        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), mHandlerCallback);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
/*        if (mThread != null && mThread.isAlive()) {
            mThread.interrupt();
        }*/

    }

    private void refresh(boolean isForceRefresh) {
        Message message = Message.obtain();
        message.what = MESSAGE_DRAW;
        message.arg1 = isForceRefresh ? FOREC_REFRESH : 12;
        handler.removeMessages(MESSAGE_DRAW);
        handler.sendMessage(message);
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            if (end - start < DRAW_DELAY) {
                try {
                    Thread.sleep(DRAW_DELAY - end + start);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    int frameCount = 0;

    private synchronized void draw() {
        try {
            //锁定画布并返回画布对象
            mCanvas = mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            if (frameCount++ < 2) { //双缓冲, 画两次背景即可
                mCanvas.drawColor(Color.BLACK/*WHITE*/);
            }
            mCanvas.drawPath(mPath, mPaint);

        } catch (Exception e) {
        } finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
                ELogger.d("draw----------liuwenrong--2019/3/28--: ");
            }
        }
    }

    /**
     * 绘制触摸滑动路径
     *
     * @param event MotionEvent
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int historySize = event.getHistorySize();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
//                mPath.moveTo(x, y);
                touchDown(event);
//                refresh(false);
                break;
            case MotionEvent.ACTION_MOVE:
                // 插值,增加平滑度
/*                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    mPath.lineTo(historicalX, historicalY);
                }*/

                touchMove(event);
//                mPath.lineTo(x, y);
//                ELogger.d("onTouchEvent-move---------liuwenrong--2019/3/28--: x = " + x + ", y = " + y);
                refresh(false);
                break;
            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: up");
                // 插值,增加平滑度
/*                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    mPath.lineTo(historicalX, historicalY);
                }*/

                mPath.moveTo(x, y);
                refresh(true);
                break;
        }
        return true;
    }

    /**
     * 清屏
     *
     * @return true
     */
    public boolean reDraw() {
        //锁定画布并返回画布对象
        mCanvas = mHolder.lockCanvas();
        //接下去就是在画布上进行一下draw
        mCanvas.drawColor(Color.BLACK/*WHITE*/);
//        mPath.resetPaint();
        resetPaint();
        mCanvas.drawPath(mPath, mPaint);
        //当画布内容不为空时，才post，避免出现黑屏的情况。
        if (mCanvas != null)
            mHolder.unlockCanvasAndPost(mCanvas);
        return true;
    }

    private void resetPaint() {
        mPath = new Path();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE/*RED*/);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
        frameCount = 0;
    }

    float mX, mY;

    //手指点下屏幕时调用
    private void touchDown(MotionEvent event) {
        //隐藏之前的绘制
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        //mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }

    //手指在屏幕上滑动时调用
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3) {
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.quadTo(previousX, previousY, cX, cY);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }
    }

    private boolean isEraser;
    public boolean isEraser() {
        return isEraser;
    }
    ////橡皮擦
    public void setEraser() {
        if (!isEraser) {
            mPaint = new Paint();
            mPath = new Path();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(50);
//        mPaint.setColor(mPaintColor);
            mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
            mPaint.setDither(true);//消除拉动，使画面圓滑
            mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
            mPaint.setAlpha(0); ////
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            isEraser = true;
            frameCount = 0;
        } else {
            resetPaint();
            isEraser = false;
        }

    }
}

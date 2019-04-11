package aron.demo.ndk;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.roger.demo4roger.R;

import aron.demo.IPC.Demo;
import aron.demo.binary.BinaryLogicFunction;
import aron.demo.memoryOpt.FinalizeCase;
import aron.demo.safeThread.SafeThreadDemo;
import aron.demo.surfaceview.SurfaceViewTemplate;

public class NDKActivity extends AppCompatActivity {
    private Context mContext;
    private static final String TAG = "Main";
    private View.OnClickListener mOnClickErase = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSurfaceView != null) {
                mSurfaceView.setEraser();
                mBtnErase.setText(mSurfaceView.isEraser() ? "橡皮擦" : "画笔");
            }
            try {
                FinalizeCase.testGc(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Demo.testServicePrint();
//            createSurfaceView();
        }
    };
    public SurfaceViewTemplate mSurfaceView;
    private View.OnClickListener mClear = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSurfaceView != null) {
                mSurfaceView.reDraw();
            }
        }
    };
    public Button mBtnErase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        EditText editCopy = findViewById(R.id.edit_copy);
        String strFromNDK = NDKTools.getStringFromNDK();
        editCopy.setText(strFromNDK);
        BinaryLogicFunction.test();
        mSurfaceView = findViewById(R.id.surface_view);
        mBtnErase = findViewById(R.id.btn_erase);
        mBtnErase.setOnClickListener(mOnClickErase);
        Button btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(mClear);
        Demo.testBindService(this);
    }

    public void onClickCopy(View view) {
//        SafeThreadDemo.testInteger();
        SafeThreadDemo.testIntegerVolatile();
//        SafeThreadDemo.testIntegerAtomic();
//        SafeThreadDemo.testIntegerSynchronized();
    }

    /**
     * 新建一个surfaceView
     */
    private void createSurfaceView() {
        //生成一个新的surface view
        mSurfaceView = new SurfaceViewTemplate(mContext);
        mSurfaceView.getHolder().addCallback(new LmnSurfaceCallback());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , 800/*LinearLayout.LayoutParams.MATCH_PARENT*/, Gravity.TOP);
        mSurfaceView.setLayoutParams(layoutParams);

        this.addContentView(mSurfaceView, layoutParams);

/*        WindowManager windowManager =(WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.format = PixelFormat.TRANSLUCENT;

        int screenWidth = Device.getScreenMinSize();
        int screenHeight = Device.getScreenMaxSize();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = 600;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        layoutParams.x = (int) (22f/1072*screenWidth);
        layoutParams.y = (int) (140f/1448*screenHeight);
        windowManager.addView(surfaceView, layoutParams); // permission denied for this window type*/
    }

    /**
     * surfaceView的监听器
     */
    private class LmnSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //surfaceview创建成功后，加载视频
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

}

package aron.Utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import aron.APP;

/**
 * des:
 * Created by liuwenrong on 2019/3/27
 */
public class Device {

    public static float mDensity;
    public static int mScreenMinSize = -1;
    public static int mScreenMaxSize = -1;
    private static float mScreenDensityDpiY;
    public static int mDisplayMetricsWid;
    public static int mDisplayMetricsHei;

    public static int getScreenMinSize() {
        if (mScreenMinSize == -1) {
            initDisplayMetrics(APP.getAppContext());
        }

        return mScreenMinSize;
    }

    public static int getScreenMaxSize() {
        if (mScreenMaxSize == -1) {
            initDisplayMetrics(APP.getAppContext());
        }

        return mScreenMaxSize;
    }

    private static final void initDisplayMetrics(Context ctx) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        WindowManager windowManager = (WindowManager)ctx.getSystemService("window");
        Point realSize = new Point();
        windowManager.getDefaultDisplay().getRealSize(realSize);
        mDisplayMetricsWid = realSize.x;
        mDisplayMetricsHei = realSize.y;
        mScreenMinSize = Math.min(realSize.x, realSize.y);
        mScreenMaxSize = Math.max(realSize.x, realSize.y);
        mDensity = metrics.density;
        if (metrics.ydpi > (float)(4 * metrics.densityDpi / 5) && metrics.ydpi < (float)(6 * metrics.densityDpi / 5)) {
            mScreenDensityDpiY = metrics.ydpi;
        } else {
            mScreenDensityDpiY = (float)metrics.densityDpi;
        }

//        mScreenInchF = 6.0F;
//        mScreenType = ScreenType.getScreenType((int)mScreenInchF);
//        mLCDType = getLCDType();
    }
}

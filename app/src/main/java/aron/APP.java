package aron;

import android.app.Application;
import android.content.Context;

/**
 * des:
 * Created by liuwenrong on 2019/3/27
 */
public class APP extends Application {

    private static volatile Context mAppContext;
    public static final Context getAppContext() {
        if (mAppContext == null) {
            initAPPData();
        }

        return mAppContext;
    }

    public static final void initAPPData() {
        if (mAppContext == null) {
//            mAppContext = ;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }
}

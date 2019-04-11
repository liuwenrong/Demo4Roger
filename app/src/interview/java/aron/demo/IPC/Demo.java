package aron.demo.IPC;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.WindowManager;

/**
 * des:
 * Created by liuwenrong on 2019/4/1
 */
public class Demo {
    private static ServiceConnection mConn  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((MyService.MyServiceBinder) service).getService();

            myService.testPrint();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    public static MyService myService;

    public static void testBindService(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//        ServiceManager.getS
//        IWindowManager mws = IWindowManager.Stub.asInterface(windowManager);
        Intent intent = new Intent(context, MyService.class);
        context.bindService(intent, mConn, MyService.BIND_AUTO_CREATE);

    }

    public static void testServicePrint() {
        if (myService == null) {
            return;
        }
        myService.testPrint();
    }

}

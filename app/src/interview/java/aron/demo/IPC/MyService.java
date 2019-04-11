package aron.demo.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import aron.Utils.ELogger;

public class MyService extends Service {
    MyServiceBinder mBinder = new MyServiceBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    public class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
    public void testPrint() {
        ELogger.d("testPrint----------liuwenrong--2019/4/2--: ");
    }
}

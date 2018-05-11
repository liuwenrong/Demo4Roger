package com.roger.interview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TestActivity extends AppCompatActivity {

    public static void main(String args[]) {
        String s = new String("xyz");

        if (-0.0 == 0.0) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

    }

    private static final String TAG = "Test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        main(new String[]{});
        Log.d(TAG, "onCreate: ----------------------------180326");
        String DeviceName = android.os.Build.DEVICE;
        Log.d(TAG, "onCreate:----------180326 DeviceName = " + DeviceName);
    }
}

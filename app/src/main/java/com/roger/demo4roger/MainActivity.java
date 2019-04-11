package com.roger.demo4roger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ----------------------------180326");
        String DeviceName = android.os.Build.DEVICE;
        Log.d(TAG, "onCreate:----------180326 DeviceName = " + DeviceName);
    }

    public void onClickCopy(View view) {
    }
}

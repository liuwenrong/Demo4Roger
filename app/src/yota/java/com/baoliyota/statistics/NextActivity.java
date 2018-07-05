package com.baoliyota.statistics;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.roger.demo4roger.R;

/**
 * des:
 *
 * @author liuwenrong
 * @version 1.0, 2018/4/9
 */
public class NextActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        FragmentManager fragmentManager =  getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DemoFragment demoFragment = new DemoFragment();
        fragmentTransaction.add(R.id.fragment_container, demoFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}

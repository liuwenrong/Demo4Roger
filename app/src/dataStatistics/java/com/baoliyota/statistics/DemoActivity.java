package com.baoliyota.statistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.coolyota.analysis.CYAnalysis;
import com.roger.demo4roger.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * des:
 *
 * @author liuwenrong
 * @version 1.0, 2018/4/9
 */
public class DemoActivity extends Activity {

    private static final String PAGE_NAME = "DemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), NextActivity.class));

            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.statistics_btn:
                //事件统计
                String testClick = "testClick";
                CYAnalysis.onEvent(getContext(), testClick);

                break;
            case R.id.statistics_json_btn:

                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("phone", "13686461726");
                    jsonObj.put("username", "liuwenrong");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CYAnalysis.onEvent(this, "register", "", jsonObj.toString());

                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        CYAnalysis.onResume(this, PAGE_NAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CYAnalysis.onPause(this);
    }

    private Context getContext(){
        return this;
    }

}

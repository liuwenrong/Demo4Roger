package com.roger.interview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roger.demo4roger.R;

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
    EditText mEditCopy;//用于手机复制黏贴
    String text = "https://ptsolomo.reader.qq.com/book_res/reader/eink/1_0_0/topicV2.html?tid=302953&tf=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditCopy = findViewById(R.id.edit_copy);
        mEditCopy.setText(text);
        main(new String[]{});
        Log.d(TAG, "onCreate: ----------------------------180326");
        String DeviceName = android.os.Build.DEVICE;
        Log.d(TAG, "onCreate:----------180326 DeviceName = " + DeviceName);
    }

    public void onClickCopy(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mEditCopy.getText()));
        Toast.makeText(this, "已复制到剪切板", Toast.LENGTH_LONG).show();
    }
}

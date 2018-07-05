package com.baoliyota.preferenceDemo;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.roger.demo4roger.R;


public class preferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_preference);
        this.addPreferencesFromResource(R.xml.pref_general);
//        EditTextPreference ep = findPreference("example_text");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }



}

package com.baoliyota.statistics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coolyota.analysis.CYAnalysis;
import com.roger.demo4roger.R;

/**
 * des:
 *
 * @author liuwenrong
 * @version 1.0, 2018/4/9
 */
public class DemoFragment extends Fragment {

    private static final String PAGE_NAME = "DemoFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CYAnalysis.onResume(getActivity(), PAGE_NAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        CYAnalysis.onPause(getActivity());
    }
}

package com.baoliyota.statistics;

import android.app.Application;

import com.coolyota.analysis.CYAnalysis;

import static com.baoliyota.statistics.Constans.APPKEY;
import static com.baoliyota.statistics.Constans.BLAUNCHER_APP_ID;

/**
 * des:
 *
 * @author liuwenrong
 * @version 1.0, 2018/4/9
 */
public class StatisticsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CYAnalysis.setUploadEnabled(false); //设置是否上传到服务器,测试时防止频繁将数据传到数据库
        CYAnalysis.setDebugEnabled(false); //是否打印jar包的log,方便测试,还有配置测试或正式服务器,call before init!
        CYAnalysis.init(this, BLAUNCHER_APP_ID, APPKEY);

    }
}

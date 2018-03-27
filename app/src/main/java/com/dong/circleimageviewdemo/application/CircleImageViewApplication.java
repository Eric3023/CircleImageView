package com.dong.circleimageviewdemo.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Dong on 2018/3/27.
 */

public class CircleImageViewApplication extends Application {

    private final String APP_ID ="015ba09afb";
    private final String APP_KEY= "9217827b-f1e3-4cb3-b809-a42ad2dd852b";

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), APP_ID, true);
    }
}

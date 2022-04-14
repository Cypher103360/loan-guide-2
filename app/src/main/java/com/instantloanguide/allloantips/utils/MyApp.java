package com.instantloanguide.allloantips.utils;

import android.app.Application;
import android.content.Intent;

import com.instantloanguide.allloantips.activities.HomeActivity;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

public class MyApp extends Application {

    private static final String ONESIGNAL_APP_ID = "55aee219-b5fb-4e0a-9218-3a164aa7b74a";
    public static MyApp mInstance;


    public MyApp() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setNotificationOpenedHandler(new ExampleNotificationOpenedHandler());
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            Intent intent = new Intent(MyApp.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}

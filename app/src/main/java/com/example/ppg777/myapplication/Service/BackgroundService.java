package com.example.ppg777.myapplication.Service;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.example.ppg777.myapplication.Util.NotificationUtils;
import com.example.ppg777.myapplication.Util.WifiTool;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.ppg777.myapplication.Constants.Constans.ACTION_CHECK;
import static com.example.ppg777.myapplication.Constants.Constans.NOTIFICATION_ACTION;


/**
 * Created by ppg777 on 2016/11/24.
 */

public class BackgroundService extends Service {


    ArrayList<ScanResult> list;
    WifiTool wifiTool;

    public class MyBinder extends Binder {

        public BackgroundService getService(){
            return BackgroundService.this;
        }

    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {

        if (wifiTool == null) {
            wifiTool = new WifiTool(this);
        }


        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int action = intent.getIntExtra(NOTIFICATION_ACTION, 0);
        if (wifiTool == null) {
            wifiTool = new WifiTool(this);
        }
        switch (action){
            case ACTION_CHECK:
                NotificationUtils.UpdataNotification(this,"搜索到的WIFI:"+wifiTool.getWifi().size());
                break;
        }
        return START_NOT_STICKY;
    }



    //打开前台通知栏常驻的方法
    public void OpenNotification() {
        NotificationUtils.OpenNotification(this);
    }

    //关闭前台通知栏常驻的方法
    public void CloseNotification() {
        NotificationUtils.CloseNotification(this);
    }


    @Override
    public void onDestroy() {

        stopForeground(true);
        super.onDestroy();
    }
}

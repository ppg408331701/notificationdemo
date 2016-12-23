package com.example.ppg777.myapplication.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ppg777.myapplication.R;
import com.example.ppg777.myapplication.Service.BackgroundService;

public class MainActivity extends AppCompatActivity {


    private Intent intent;

    //服务相关
    private BackgroundService service = null;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            BackgroundService.MyBinder myBinder = (BackgroundService.MyBinder) binder;
            service = myBinder.getService();
            service.OpenNotification();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定后台服务
        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("from", "DetailActivity");
        this.bindService(intent, conn, this.BIND_AUTO_CREATE);
    }

    public void explode(View view) {
        intent = new Intent(this, TwoActivity.class);
        startActivity(intent);

    }


}
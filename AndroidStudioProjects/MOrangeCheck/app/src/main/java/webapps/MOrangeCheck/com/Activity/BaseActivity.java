package webapps.MOrangeCheck.com.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.zhy.http.okhttp.OkHttpUtils;

import ppg.com.yanlibrary.activity.BaseTopBarActivity;
import webapps.MOrangeCheck.com.Application.AppApplication;


/**
 * Created by ppg on 2016/5/3.
 */
public abstract class BaseActivity extends BaseTopBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppApplication.pushActivity(this);
    }



    @Override
    protected void onStop() {
        //可以取消同一个tag的
        //   OkHttpUtils.cancelTag(this);//取消以Activity.this作为tag的请求
        OkHttpUtils.getInstance().cancelTag(this);
        AppApplication.popActivity(this);
        super.onStop();
    }
}

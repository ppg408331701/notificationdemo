package webapps.MOrangeCheck.com.Activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import ppg.com.yanlibrary.utils.SessionUtils;
import ppg.com.yanlibrary.widget.TopBarLayout;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Tool.SmsTool;

import webapps.MOrangeCheck.com.databinding.ActivityLoginBinding;

/**
 * Created by ppg777 on 2017/2/21.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    ActivityLoginBinding binding;
    private SmsTool smsTool;
    private int AS = 0;
    private int MS = 0;


    @Override
    protected void onCreateView(TopBarLayout topBar) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.tvGetcode.setOnClickListener(this);
        binding.btLogin.setOnClickListener(this);
        binding.ivIdGroup.setOnClickListener(this);

        //读取短信验证码
        if (Build.VERSION.SDK_INT >= 23) {
            MPermissions.requestPermissions(LoginActivity.this, 1111, Manifest.permission.READ_SMS,Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            smsTool = new SmsTool(new Handler(), this, binding.edCode);
            getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsTool);
        }

        //  setEditListener();
      //  binding.btLogin.setEnabled(false);
        if (!TextUtils.isEmpty(SessionUtils.extractData(this, "mobile_phone"))) {
            binding.edPhone.setText(SessionUtils.extractData(this, "mobile_phone"));
            binding.edCode.requestFocus();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_login:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_COMPANYHOMEPAGE);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "公司名字");
                IntentTool.startByActivity(LoginActivity.this, intent);
                finish();
                break;
        }
    }


    //设置手机号码监听
    private void setEditListener() {
        binding.edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AS = s.length();
                if (AS == 11) {
                    if (MS == 6) {
                        binding.btLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.app_blue));
                        binding.btLogin.setEnabled(true);
                    }
                    binding.tvGetcode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_808080));
                } else {
                    binding.btLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_cdcdcd));
                    binding.btLogin.setEnabled(false);
                    binding.tvGetcode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_cdcdcd));
                }
            }
        });


        binding.edCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MS = s.length();
                if (MS == 6 && AS == 11) {
                    binding.btLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.app_blue));
                    binding.tvGetcode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_808080));
                    binding.btLogin.setEnabled(true);
                } else {
                    binding.btLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.gray_cdcdcd));
                    binding.btLogin.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(1111)
    public void requestSdcardSuccess() {
        smsTool = new SmsTool(new Handler(), this, binding.edCode);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsTool);
    }

    @PermissionDenied(1111)
    public void requestSdcardFailed() {
//        SnackbarUtil.showNoDisMiss(binding.btLogin, "请求权限失败", "重新请求", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MPermissions.requestPermissions(LoginActivity.this, 1111, Manifest.permission.READ_SMS);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsTool != null) {
            getContentResolver().unregisterContentObserver(smsTool);
        }

    }
}

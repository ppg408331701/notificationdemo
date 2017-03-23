package webapps.MOrangeCheck.com.Fragment.Personal.MyInfo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.databinding.FragmentMyinfoBinding;

/**
 * Created by ppg777 on 2017/3/17.
 */

public class MyInfo extends LoadingFragment implements View.OnClickListener {


    FragmentMyinfoBinding binding;

    public MyInfo() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView3("编辑", ContextCompat.getColor(mActivity,
                R.color.yellow2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_COMPLETEMYINFO);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "完善个人信息");
                IntentTool.startByFragment(mActivity, MyInfo.this, intent);
            }
        });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);
        binding = DataBindingUtil.bind(root);
        binding.rlSetAvater.setOnClickListener(this);
        binding.rlSetGender.setOnClickListener(this);
        binding.rlSetBirthday.setOnClickListener(this);
        binding.rlSetPassword.setOnClickListener(this);
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_set_avater:
                break;
            case R.id.rl_set_gender:

                break;
            case R.id.rl_set_birthday:

                break;
            case R.id.rl_set_password:

                break;
        }
    }

}

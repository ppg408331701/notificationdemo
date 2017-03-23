package webapps.MOrangeCheck.com.Fragment.Personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.databinding.FragmentPersonalBinding;

/**
 * 查看他人的个人信息
 * Created by ppg777 on 2017/3/16.
 */

public class PersonalInfo extends LoadingFragment {

    FragmentPersonalBinding binding;

    public PersonalInfo() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        binding = DataBindingUtil.bind(root);
        Glide.with(mActivity)
                .load("http://attachments.gfan.com/attachments2/day_110615/1106151509f6d875b078d5a4c4.jpg")
                .dontAnimate()
                .placeholder(R.drawable.ic_default_image)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(binding.ivPersonalAvater);
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

}

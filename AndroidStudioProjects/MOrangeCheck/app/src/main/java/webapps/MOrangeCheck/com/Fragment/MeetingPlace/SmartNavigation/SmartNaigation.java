package webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;
import webapps.MOrangeCheck.com.databinding.FragmentSmartNavigationBinding;

/**
 * Created by ppg777 on 2017/3/7.
 */

public class SmartNaigation extends LoadingFragment{


    FragmentSmartNavigationBinding binding;

    public SmartNaigation() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smart_navigation, container, false);
        binding = DataBindingUtil.bind(root);
        binding.sesameView.setSesameValues(39, 40);
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

}

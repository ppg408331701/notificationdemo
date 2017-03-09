package webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/9.
 */

public class NagationSetting extends LoadingFragment{

    public NagationSetting() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nagation_setting, container, false);
       // binding = DataBindingUtil.bind(root);
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }


}

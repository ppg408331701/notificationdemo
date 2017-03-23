package webapps.MOrangeCheck.com.Fragment.Personal.MyInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/17.
 */

public class ResetPassWord extends LoadingFragment {

    public ResetPassWord() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reset_password, container, false);

        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

}

package webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.databinding.FragmentSmartNavigationBinding;

/**
 * Created by ppg777 on 2017/3/7.
 */

public class SmartNaigation extends LoadingFragment{


    FragmentSmartNavigationBinding binding;
    private PagerAdper pagerAdper;
    List<Fragment> viewroot = new ArrayList<>();

    public SmartNaigation() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView2(R.mipmap.threepoint, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_NAGATIONSETTING);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "设置");
                IntentTool.startByFragment(mActivity, SmartNaigation.this, intent);
            }
        });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smart_navigation, container, false);
        binding = DataBindingUtil.bind(root);
        binding.sesameView.setSesameValues(24, 40);
        binding.sesameView.setDuration(5000);
        binding.sesameView.setStyle(Paint.Style.FILL);
        binding.sesameView.setColor(ContextCompat.getColor(mActivity, R.color.yellow2));
        binding.sesameView.setInterpolator(new LinearOutSlowInInterpolator());
        binding.sesameView.start();
        initViewPager();
        return root;
    }


    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

    private void initViewPager() {


        viewroot.add(0, new NaigationIndex());
        viewroot.add(1, new NaigationHistory());

        pagerAdper = new PagerAdper(viewroot, getFragmentManager());

        binding.viewPager.setAdapter(pagerAdper);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.tvIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvIndex.setTextColor(ContextCompat.getColor(mActivity, R.color.white0));
                binding.tvIndex.setBackgroundResource(R.drawable.nagiation_left_yellow);
                binding.tvHistory.setTextColor(ContextCompat.getColor(mActivity, R.color.gray4));
                binding.tvHistory.setBackgroundResource(R.drawable.nagiation_right_gray);
                binding.viewPager.setCurrentItem(0, true);
            }
        });
        binding.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvIndex.setTextColor(ContextCompat.getColor(mActivity, R.color.gray4));
                binding.tvIndex.setBackgroundResource(R.drawable.nagiation_left_gray);
                binding.tvHistory.setTextColor(ContextCompat.getColor(mActivity, R.color.white0));
                binding.tvHistory.setBackgroundResource(R.drawable.nagiation_right_yellow);
                binding.viewPager.setCurrentItem(1, true);
            }
        });
    }

    public class PagerAdper extends FragmentStatePagerAdapter {

        private List<Fragment> views;
        private String[] mTitles = new String[]{"索引", "历史"};

        public PagerAdper(List<Fragment> views, FragmentManager fm) {
            super(fm);
            this.views = views;
        }

        /**
         * 获得当前界面数
         */
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment f = (Fragment) super.instantiateItem(container, position);
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }


        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }
    }

}

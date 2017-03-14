package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.databinding.FragmentReportBinding;

/**
 * 报表
 * Created by ppg777 on 2017/2/24.
 */

public class Report extends LoadingFragment implements View.OnClickListener {


    FragmentReportBinding binding;
    private Random random = new Random();

    public Report() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView1(R.mipmap.ic_calendar, "排班",
                ContextCompat.getColor(mActivity, R.color.yellow2), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent;
                        intent = new Intent();
                        intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_ARRANGE);
                        intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "排班");
                        IntentTool.startByFragment(mActivity, Report.this, intent);
                    }
                });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        binding = DataBindingUtil.bind(root);
        binding.report.llRank.setOnClickListener(this);
        binding.report.llChoiceMouth.setOnClickListener(this);
        binding.reportFuncation.rlLate.setOnClickListener(this);
        binding.reportFuncation.rlAbsenteeism.setOnClickListener(this);
        binding.reportFuncation.rlAnnualLeave.setOnClickListener(this);
        binding.reportFuncation.rlBusiness.setOnClickListener(this);
        binding.reportFuncation.rlHoliday.setOnClickListener(this);
        binding.reportFuncation.rlLeave.setOnClickListener(this);
        binding.reportFuncation.rlOvertime.setOnClickListener(this);
        binding.reportFuncation.rlVacationDays.setOnClickListener(this);
        binding.reportFuncation.rlGetOut.setOnClickListener(this);
        binding.tvCheckMore.setOnClickListener(this);

        binding.dashboard.sesameView.setTitle("调休");
        binding.dashboard.sesameView.setSubTile("Hour");
        binding.dashboard.sesameView2.setTitle("年假");
        binding.dashboard.sesameView2.setSubTile("Day");
        binding.dashboard.tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int i = random.nextInt(40);
                binding.dashboard.sesameView.setSesameValues(i, 40);
                int i2 = random.nextInt(40);
                binding.dashboard.sesameView2.setSesameValues(i2, 40);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_rank:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_RANK);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "");
                IntentTool.startByFragment(mActivity, Report.this, intent);
                break;
            case R.id.ll_choice_mouth:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_CHOICEMONTH);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "月份选择");
                IntentTool.startByFragment(mActivity, Report.this, intent);
                break;
            case R.id.rl_late:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_REPORTITEMDETATIL);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "迟到");
                IntentTool.startByFragment(mActivity, Report.this, intent);
                break;
            case R.id.rl_leave:
                break;
            case R.id.rl_business:
                break;
            case R.id.rl_absenteeism:
                break;
            case R.id.rl_holiday:
                break;
            case R.id.rl_getOut:
                break;
            case R.id.rl_overtime:
                break;
            case R.id.rl_vacation_days:
                break;
            case R.id.rl_annual_leave:
                break;
            case R.id.tv_checkMore:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_REPORTDETAIL);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "2017/2");
                IntentTool.startByFragment(mActivity, Report.this, intent);
                break;
        }
    }
}

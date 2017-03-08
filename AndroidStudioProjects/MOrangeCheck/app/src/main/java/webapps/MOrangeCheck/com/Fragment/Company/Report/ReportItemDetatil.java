package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentReportitemdatetilBinding;

/**
 * 报表每个item的详情页
 * Created by ppg777 on 2017/2/27.
 */

public class ReportItemDetatil extends LoadingFragment {

    private FragmentReportitemdatetilBinding binding;


    private List<String> list = new ArrayList<>();
    private CommonAdapter<String> adapter;

    public ReportItemDetatil() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reportitemdatetil, container, false);
        binding = DataBindingUtil.bind(root);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL, 15));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        initData();
        return root;
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("9月" + i + "日");
        }
        adapter = new CommonAdapter<String>(mActivity, R.layout.item_report_item, list) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_date, s);
            }
        };
        binding.recyclerView.setAdapter(adapter);
    }
}

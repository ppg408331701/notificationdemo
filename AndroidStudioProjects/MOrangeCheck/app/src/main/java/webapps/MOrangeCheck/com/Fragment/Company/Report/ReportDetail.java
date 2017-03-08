package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import utils.ConvertUtils;
import webapps.MOrangeCheck.com.Factory.RecyclerViewFactory;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentReportitemdatetilBinding;

/**
 * 报表下面那个查看更多,和ReportItemDetatil共用一个xml文件
 * Created by ppg777 on 2017/2/27.
 */

public class ReportDetail extends LoadingFragment {

    private FragmentReportitemdatetilBinding binding;
    private List<String> list = new ArrayList<>();;
    private CommonAdapter<String> adapter;

    public ReportDetail() {
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
        for (int i = 0; i < 15; i++) {
            list.add("0" + i);
        }
        adapter = new CommonAdapter<String>(mActivity, R.layout.item_report_dateil, list) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_date, s);
                LinearLayout layout = holder.getView(R.id.ll_checkpoint);
                addCheckPoint(layout);
            }
        };
        binding.recyclerView.setAdapter(adapter);
    }

    private void addCheckPoint(LinearLayout layout) {
        Random random = new Random();
        int num = random.nextInt(6);
        for (int i = 0; i <num ; i++) {
            ViewGroup chcekTimeItem = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_date, layout, false);
            TextView tv_time = (TextView) chcekTimeItem.findViewById(R.id.tv_time);
//            if (num<4){
//                tv_time.setPadding(ConvertUtils.dp2px(15),0,ConvertUtils.dp2px(15),0);
//            }else {
//                tv_time.setPadding(ConvertUtils.dp2px(8),0,ConvertUtils.dp2px(8),0);
//            }
            tv_time.setPadding(ConvertUtils.dp2px(15),0,ConvertUtils.dp2px(15),0);
            tv_time.setText("09:3"+num);
            layout.addView(chcekTimeItem);
        }


    }
}

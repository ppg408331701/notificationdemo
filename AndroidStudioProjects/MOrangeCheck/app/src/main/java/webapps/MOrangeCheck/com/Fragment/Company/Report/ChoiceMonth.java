package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.Bean.MonthTimeBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.Views.TitleItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentChoiceMonthBinding;

/**
 * 选择月份
 * Created by ppg777 on 2017/1/11.
 */

public class ChoiceMonth extends LoadingFragment {

    View root;
    FragmentChoiceMonthBinding binding;


    private ArrayList<MonthTimeBean> dataList;
    private List<String> testlist;


    public ChoiceMonth() {
        super(false);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_choice_month, container, false);
        binding = DataBindingUtil.bind(root);
        binding.rvList.setItemAnimator(new DefaultItemAnimator());
        binding.rvList.setLayoutManager(new LinearLayoutManager(mActivity));

        binding.rvList.setHasFixedSize(true);

        return root;
    }


    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        if (loadingContent()) {
            testlist = getData();
            setPullAction(testlist);
            paddingData();
        }
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 44; i++) {
            if (i <= 10) {
                strings.add(String.valueOf(2000));
            }
            if (i <= 20 && i > 10) {
                strings.add(String.valueOf(2010));
            }
            if (i <= 30 && i > 20) {
                strings.add(String.valueOf(2020));
            }
            if (i <= 40 && i > 30) {
                strings.add(String.valueOf(2030));
            }

        }
        return strings;
    }


    private void setPullAction(List<String> comingslist) {
        dataList = new ArrayList<>();
        MonthTimeBean nameBean;
        for (int i = 0; i < comingslist.size(); i++) {
            nameBean = new MonthTimeBean();
            String name0 = comingslist.get(i).toString();
            nameBean.setName(name0);
            nameBean.setType(false);
            nameBean.setTag(name0);
            dataList.add(nameBean);
        }
    }

    private void paddingData() {

        final BaseQuickAdapter adapter = new BaseQuickAdapter<MonthTimeBean, BaseViewHolder>(R.layout.item_organization, dataList) {

            @Override
            protected void convert(BaseViewHolder holder, MonthTimeBean s) {
                holder.setText(R.id.organization_name, s.getName());
                if (s.isType()) {
                    holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.iv_check).setVisibility(View.GONE);
                }
            }


        };

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MonthTimeBean timeBean = (MonthTimeBean) adapter.getItem(position);
                timeBean.setType(true);
                adapter.notifyDataSetChanged();
            }
        });
        binding.rvList.setAdapter(adapter);
        binding.rvList.addItemDecoration(new TitleItemDecoration(mActivity, dataList));
        binding.rvList.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                LeftPaddingDividerItemDecoration.VERTICAL, 0));
    }
}

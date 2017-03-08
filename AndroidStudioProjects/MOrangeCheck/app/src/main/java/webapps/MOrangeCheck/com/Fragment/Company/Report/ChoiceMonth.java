package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.OnItemClickListener;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import webapps.MOrangeCheck.com.Bean.MonthTimeBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.SectionDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentChoiceMonthBinding;

/**
 * 选择月份
 * Created by ppg777 on 2017/1/11.
 */

public class ChoiceMonth extends LoadingFragment {

    View root;
    FragmentChoiceMonthBinding binding;

    private int mCurrentPosition = 0;

    private int mSuspensionHeight;
    private ArrayList<MonthTimeBean> dataList;
    private List<String> testlist;


    public ChoiceMonth() {
        super(false);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_choice_month, container, false);
        binding = DataBindingUtil.bind(root);
        initRcyView();
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

    private void paddingData() {
        final CommonAdapter adapter = new CommonAdapter<MonthTimeBean>(mActivity, R.layout.item_organization, dataList) {

            @Override
            public void convert(ViewHolder holder, MonthTimeBean s) {
                holder.setText(R.id.organization_name, s.getName());
                if (s.isType()) {
                    holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.iv_check).setVisibility(View.GONE);
                }
            }
        };
        binding.feedList.addItemDecoration(new SectionDecoration(dataList, mActivity,
                new SectionDecoration.DecorationCallback() {
                    //返回标记id (即每一项对应的标志性的字符串)
                    @Override
                    public String getGroupId(int position) {
                        if (dataList.get(position).getName() != null) {
                            return dataList.get(position).getName();
                        }
                        return "-1";
                    }

                    //获取同组中的第一个内容
                    @Override
                    public String getGroupFirstLine(int position) {
                        if (dataList.get(position).getName() != null) {
                            return dataList.get(position).getName();
                        }
                        return "";
                    }
                }));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                MonthTimeBean timeBean = (MonthTimeBean) o;
                ImageView imageView = (ImageView) parent.findViewById(R.id.iv_check);
                timeBean.setType(true);
                adapter.notifyItemChanged(position);

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        binding.feedList.setAdapter(adapter);
    }

    private void initRcyView() {
        //初始化
        binding.feedList.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.feedList.setLayoutManager(layoutManager);
        binding.feedList.setHasFixedSize(true);
        binding.feedList.setItemAnimator(new DefaultItemAnimator());


    }

    private void setPullAction(List<String> comingslist) {
        dataList = new ArrayList<>();

        for (int i = 0; i < comingslist.size(); i++) {
            MonthTimeBean nameBean = new MonthTimeBean();
            String name0 = comingslist.get(i).toString();
            nameBean.setName(name0);
            nameBean.setType(false);
            dataList.add(nameBean);
        }
    }
}

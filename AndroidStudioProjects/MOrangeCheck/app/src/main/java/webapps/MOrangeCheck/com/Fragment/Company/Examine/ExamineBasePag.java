package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.content.Intent;
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
import ppg.com.yanlibrary.widget.recyclerview.OnItemClickListener;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentExaminebaseBinding;

/**
 * 1申请,2审批,3抄送
 * Created by ppg777 on 2017/3/1.
 */

public class ExamineBasePag extends LoadingFragment {

    FragmentExaminebaseBinding binding;
    private int type = 1;

    private List<String> list = new ArrayList<>();
    private CommonAdapter<String> adapter;

    public ExamineBasePag() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_examinebase, container, false);
        binding = DataBindingUtil.bind(root);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL, 15));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        type = mActivity.getIntent().getIntExtra("type", 1);
        initView();
    }

    private void initView() {
        initTitle();
        initData();
    }

    private void initTitle(){
        if (type==1){
            mActivity.getTopBar().getTopBarTitle().setText("申请");
        }else if (type==2){
            mActivity.getTopBar().getTopBarTitle().setText("审批");
        }else {
            mActivity.getTopBar().getTopBarTitle().setText("抄送");
        }
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("9月" + i + "日");
        }
        adapter = new CommonAdapter<String>(mActivity, R.layout.item_examinebase, list) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_examinebase_date, s);
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
              Intent intent = new Intent();
              intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINEITEMDETAIL);
              intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "详情");
              IntentTool.startByFragment(mActivity, ExamineBasePag.this, intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }
}

package webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.OnItemClickListener;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import utils.ToastUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Views.CustomLinearLayoutManager;
import webapps.MOrangeCheck.com.databinding.FragmentNaigationRecylerviewBinding;

/**
 * Created by ppg777 on 2017/3/8.
 */

public class NaigationHistory extends LoadingFragment {

    private RecyclerView recyclerView;

    public NaigationHistory() {
        super(true);
    }

    private List<String> list = new ArrayList<>();

    private CommonAdapter<String> adapter;



    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_naigation_recylerview, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initData();
        return root;
    }


    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            list.add("onCreateViewRequestData" + i);
        }
        adapter = new CommonAdapter<String>(mActivity, R.layout.item_naigation, list) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_main_title, s);

            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ToastUtils.showShortToast("setOnItemClickListener"+position);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }
}

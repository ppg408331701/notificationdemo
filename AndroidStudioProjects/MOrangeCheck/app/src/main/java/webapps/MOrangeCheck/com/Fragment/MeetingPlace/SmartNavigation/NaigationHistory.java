package webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import webapps.MOrangeCheck.com.Factory.RecyclerViewFactory;
import webapps.MOrangeCheck.com.R;

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
        recyclerView = RecyclerViewFactory.createNoItemDecorationVerticalXRecyclerView(mActivity);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initData();
        return recyclerView;
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
        recyclerView.setAdapter(adapter);
    }
}

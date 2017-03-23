package webapps.MOrangeCheck.com.Fragment.MeetingPlace.Dynamic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.Bean.DynamicBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;

/**
 * 动态
 * Created by ppg777 on 2017/3/17.
 */

public class Dynamic extends LoadingFragment {


    private RecyclerView recyclerView;
    private ArrayList<Object> dataList;
    private MultipleItemQuickAdapter multipleItemQuickAdapter;

    public Dynamic() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                LeftPaddingDividerItemDecoration.VERTICAL, 0));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        setPullAction(getData());
        initData();
    }

    private void initData() {
        multipleItemQuickAdapter = new MultipleItemQuickAdapter(dataList);
        multipleItemQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                multipleItemQuickAdapter.loadMoreComplete();
            }
        }, recyclerView);
        recyclerView.setAdapter(multipleItemQuickAdapter);
    }


    private List<String> getData() {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            strings.add(String.valueOf(2030) + i);
        }
        return strings;
    }


    private void setPullAction(List<String> comingslist) {
        dataList = new ArrayList<>();
        DynamicBean dynamicBean;
        for (int i = 0; i < comingslist.size(); i++) {
            dynamicBean = new DynamicBean();
            String name0 = comingslist.get(i).toString();
            dynamicBean.setTitle("Android 对 Adapter 的 ItemType 进行封装简化");
            dynamicBean.setDate(name0);
            if (i == 0) {
                dynamicBean.setItemType(DynamicBean.IMG);
            } else {
                dynamicBean.setItemType(DynamicBean.TEXT);
            }
            dataList.add(dynamicBean);
        }
    }


    public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<DynamicBean, BaseViewHolder> {

        public MultipleItemQuickAdapter(List data) {
            super(data);
            addItemType(DynamicBean.TEXT, R.layout.item_dynamic);
            addItemType(DynamicBean.IMG, R.layout.layout_dynamic_head);
        }

        @Override
        protected void convert(BaseViewHolder holder, DynamicBean item) {
            switch (holder.getItemViewType()) {
                case DynamicBean.TEXT:
                    holder.setText(R.id.tv_title, item.getTitle());
                    holder.setText(R.id.tv_date, item.getDate());
                    break;
                case DynamicBean.IMG:
                    holder.setText(R.id.tv_title, item.getTitle());
                    holder.setText(R.id.tv_date, item.getDate());
                    Glide.with(Dynamic.this)
                            .load("https://pic2.zhimg.com/v2-15145e5d7c39b347bb82225b7ce51c39_fhd.png")
                            .crossFade()
                            .bitmapTransform(new ColorFilterTransformation(mActivity, Color.parseColor("#79263238")))
                            .into((ImageView) holder.getView(R.id.iv_img));
                    break;
            }
        }

    }

}

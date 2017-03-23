package webapps.MOrangeCheck.com.Fragment.Personal.MyInfo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;

import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.base.ViewHolder;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentMyPageBinding;

/**
 * Created by ppg777 on 2017/3/16.
 */

public class MyPage extends LoadingFragment implements View.OnClickListener{

    FragmentMyPageBinding binding;
    BaseQuickAdapter adapter;
    private List<String> list = new ArrayList<>();


    public MyPage() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView1(R.mipmap.take_photo, "设置",
                ContextCompat.getColor(mActivity, R.color.yellow2), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_page, container, false);
        binding = DataBindingUtil.bind(root);
        binding.rlInfo.setOnClickListener(this);
        Glide.with(mActivity)
                .load("http://attachments.gfan.com/attachments2/day_110615/1106151509f6d875b078d5a4c4.jpg")
                .dontAnimate()
                .placeholder(R.drawable.ic_default_image)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(binding.ivMyAvater);
        binding.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.myRecyclerView.addItemDecoration(new
                LeftPaddingDividerItemDecoration(mActivity, LeftPaddingDividerItemDecoration.VERTICAL, 0));
        binding.myRecyclerView.setHasFixedSize(true);
        initData();
        return root;
    }


    @Override
    public void onClick(View v) {
         Intent intent;
         switch (v.getId()){
             case R.id.rl_info:
                  intent = new Intent();
                 intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_MYINFO);
                 intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "个人信息");
                 IntentTool.startByFragment(mActivity, MyPage.this, intent);
              break;
             }
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("测试数据" + i);
        }
        adapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_my_info, list) {

            @Override
            protected void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.tv_main_name, s);
            }
        };
        binding.myRecyclerView.setAdapter(adapter);

    }


}

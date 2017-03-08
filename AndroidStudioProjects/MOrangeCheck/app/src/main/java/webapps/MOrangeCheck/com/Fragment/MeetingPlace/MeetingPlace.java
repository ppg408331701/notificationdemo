package webapps.MOrangeCheck.com.Fragment.MeetingPlace;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ImageLoaderUtil.ImageLoaderUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.databinding.FragmentMeetingPlaceBinding;
import webapps.MOrangeCheck.com.databinding.ItemFuncationBinding;

/**
 * Created by ppg777 on 2017/2/22.
 */

public class MeetingPlace extends LoadingFragment {

    FragmentMeetingPlaceBinding binding;

    public MeetingPlace() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_meeting_place, container, false);
        binding = DataBindingUtil.bind(root);
        Glide.with(mActivity)
                .load("http://attachments.gfan.com/attachments2/day_110615/1106151509f6d875b078d5a4c4.jpg")
                .placeholder(R.color.white0)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(mActivity,14,1))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ToastUtil.toast(mActivity,"图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        int Color =ContextCompat.getColor(mActivity,R.color.white0);
                        binding.meetingHead.tvTitle.setTextColor(Color);
                        binding.meetingHead.tvMeetingDate.setTextColor(Color);
                        binding.meetingHead.itemCheckpoint.tvCheckpoint.setTextColor(Color);
                        return false;
                    }
                })
                .into(binding.meetingHead.ivMeetingBg);
        initFuncation();
        return root;
    }

    private void   initFuncation() {
        String[] funcation_text = {"概况", "动态", "议程", "嘉宾介绍", "交通指南", "智能导览",
                "直播/回看", "文档", "聊天室/群聊", "留言板/反馈"};
        Resources resources = getResources();
        TypedArray funcation_icon = resources.obtainTypedArray(R.array.meeting_funcation_icon);
        for (int i = 0; i < funcation_text.length; i++) {
            ViewGroup FuncationItem = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_funcation, binding.llFuncation, false);
            final ItemFuncationBinding itemFuncationBinding = DataBindingUtil.bind(FuncationItem);
            itemFuncationBinding.tvFuncation.setText(funcation_text[i]);
            itemFuncationBinding.ivFuncation.setImageResource(funcation_icon.getResourceId(i, 0));
            FuncationItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (itemFuncationBinding.tvFuncation.getText().toString()){
                        case "智能导览":
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_SMARTNAIGATION);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "智能导览");
                            IntentTool.startByFragment(mActivity, MeetingPlace.this, intent);
                            break;
                    }
                }
            });
            binding.llFuncation.addView(FuncationItem);
        }
    }


}

package webapps.MOrangeCheck.com.Fragment.Company.Report.Rank;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import ImageLoaderUtil.ImageLoaderUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.Sdcard.FileUtil;
import utils.ImageUtils;
import utils.ScreenUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Views.WaveDrawable;
import webapps.MOrangeCheck.com.Views.dialog.ScreenDialog;
import webapps.MOrangeCheck.com.databinding.FragmentRankBinding;

/**
 * 波浪图请看https://github.com/tangqi92/WaveLoadingView
 * 我自己对方向和颜色做了改动
 * Created by ppg777 on 2017/3/13.
 */

public class Rank extends LoadingFragment implements View.OnClickListener {

    FragmentRankBinding binding;
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489383434923&di=07d526112bcef0b651ff3fdf8c81936e&imgtype=0&src=http://diy.qqjay.com/u/files/2015/0120/6da84d909cfc9bf0f9671e25e316743d.jpg";
    private WaveDrawable mWaveDrawable;
    private List<String> list = new ArrayList<>();
    private int totalNum2 = 5000;
    private int currenNum = 1200;


    public Rank() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.showShadow(false);
        mActivity.getTopBar().getOperationRightView2(R.mipmap.examine_rank_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap Screenbitmap = ScreenUtils.captureWithoutStatusBar(mActivity);
                        boolean saveOK = ImageUtils.save(Screenbitmap, FileUtil.getDiskCacheDir(mActivity)
                                + "/Screenbitmap.jpeg", Bitmap.CompressFormat.JPEG, true);
                        if (saveOK) {
                            ScreenDialog screenDialog = new ScreenDialog(mActivity, FileUtil.getDiskCacheDir(mActivity)
                                    + "/Screenbitmap.jpeg") {
                                @Override
                                public void determineTask() {

                                }
                            };
                            screenDialog.show();
                        }
                    }
                }, 500);

            }
        });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rank, container, false);
        binding = DataBindingUtil.bind(root);
        binding.ivRankMore.setOnClickListener(this);
        initImageView();
        initHumanListView();
        initBottomView();
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_rank_more:
                currenNum += 1000;
                mWaveDrawable.setLevel(currenNum);
                if (Math.abs(totalNum2 - currenNum) > 4000) {
                    mWaveDrawable.setWaveAmplitude(1);
                } else if (Math.abs(totalNum2 - currenNum) > 3000) {
                    mWaveDrawable.setWaveAmplitude(5);
                } else if (Math.abs(totalNum2 - currenNum) > 1500) {
                    mWaveDrawable.setWaveAmplitude(12);
                } else if (Math.abs(totalNum2 - currenNum) > 1000) {
                    mWaveDrawable.setWaveAmplitude(16);
                }
                break;
        }
    }

    private void initImageView() {
        SimpleTarget simpleTarget = new SimpleTarget() {
            @Override
            public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                mWaveDrawable = new WaveDrawable((Drawable) resource);
                binding.layoutRankImage.ivRankHead.setImageDrawable(mWaveDrawable);
                mWaveDrawable.setLevel(currenNum);
                mWaveDrawable.setWaveLength(220);
                mWaveDrawable.setWaveSpeed(0);
                if (Math.abs(totalNum2 - currenNum) > 4000) {
                    mWaveDrawable.setWaveAmplitude(1);
                } else if (Math.abs(totalNum2 - currenNum) > 3000) {
                    mWaveDrawable.setWaveAmplitude(5);
                } else if (Math.abs(totalNum2 - currenNum) > 1500) {
                    mWaveDrawable.setWaveAmplitude(12);
                } else if (Math.abs(totalNum2 - currenNum) > 1000) {
                    mWaveDrawable.setWaveAmplitude(16);
                }
            }
        };
        Glide.with(this)
                .load(url)
                .crossFade(300)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(simpleTarget);
    }

    private void initHumanListView() {
        for (int i = 0; i < 4; i++) {
            list.add(url);
        }
        //插入位置,在最前面开始
        int index = binding.llHumanList.getChildCount() - 1;
        //把每个头像和三个点andadditem的宽度设为屏幕的1/5
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtils.getScreenWidth() / 5)
                , ViewGroup.LayoutParams.MATCH_PARENT);
        binding.ivRankMore.setLayoutParams(params);
        for (int i = 0; i < list.size(); i++) {
            final ViewGroup item_rank_huam = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_rank_huam, binding.llHumanList, false);
            item_rank_huam.setLayoutParams(params);
            ImageView avatar = (ImageView) item_rank_huam.findViewById(R.id.iv_rank_avatar);
            ImageView num = (ImageView) item_rank_huam.findViewById(R.id.iv_rank_num);
            ImageLoaderUtil.init().loadImageWithRound(url, R.drawable.loading, avatar);
            if (i == 0) {
                num.setImageResource(R.mipmap.rank_1);
            } else if (i == 1) {
                num.setImageResource(R.mipmap.rank_2);
            } else if (i == 2) {
                num.setImageResource(R.mipmap.rank_3);
            } else {
                num.setVisibility(View.GONE);
            }
            binding.llHumanList.addView(item_rank_huam, index++);
        }

    }

    private void initBottomView() {
        binding.layoutRankBottom.nothinCircularProgressar.setSesameValues(44, 100);
    }


}

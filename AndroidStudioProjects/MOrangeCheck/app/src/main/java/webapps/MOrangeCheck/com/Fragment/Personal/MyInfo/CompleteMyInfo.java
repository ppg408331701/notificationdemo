package webapps.MOrangeCheck.com.Fragment.Personal.MyInfo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.List;

import ImageLoaderUtil.ImageLoaderUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import utils.TimeUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Views.dialog.SelectGenderDialog;
import webapps.MOrangeCheck.com.databinding.FragmentCompleteMyinfoBinding;

/**
 * 完善个人信息
 * Created by ppg777 on 2017/3/16.
 */

public class CompleteMyInfo extends LoadingFragment implements View.OnClickListener {

    FragmentCompleteMyinfoBinding binding;
    private final int TAKEPHOTO_CODE = 991;
    private TimePickerDialog mDialogAll;
    /**
     * 年
     */
    long Years = 65L * 365 * 1000 * 60 * 60 * 24L;


    public CompleteMyInfo() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView3("保存", ContextCompat.getColor(mActivity,
                R.color.yellow2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_complete_myinfo, container, false);
        binding = DataBindingUtil.bind(root);
        binding.rlSetAvater.setOnClickListener(this);
        binding.rlSetGender.setOnClickListener(this);
        binding.rlSetBirthday.setOnClickListener(this);
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
            case R.id.rl_set_avater:
                BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
                singleImgConfig.needCamera();
                Boxing.of(singleImgConfig).withIntent(mActivity, BoxingActivity.class).start(this, TAKEPHOTO_CODE);
                break;
            case R.id.rl_set_gender:
                SelectGenderDialog dialog = new SelectGenderDialog(mActivity) {
                    @Override
                    public void determineTask(int gender) {
                        if (gender == 1) {
                            binding.tvMyGender.setText("男");
                        } else {
                            binding.tvMyGender.setText("女");
                        }
                    }
                };
                dialog.show();
                break;
            case R.id.rl_set_birthday:
                mDialogAll = new TimePickerDialog.Builder()
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setHourText("时")
                        .setMinuteText("分")
                        .setCyclic(true)
                        .setMinMillseconds(System.currentTimeMillis() - Years)
                        .setMaxMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(ContextCompat.getColor(mActivity, R.color.timetimepicker_default_text_color))
                        .setType(Type.ALL)
                        .setWheelItemTextNormalColor(ContextCompat.getColor(mActivity, R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(ContextCompat.getColor(mActivity, R.color.yellow2))
                        .setWheelItemTextSize(13)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String timeString = TimeUtils.millis2String(millseconds, TimeUtils.A_PATTERN);
                                binding.tvMyBirthday.setText(timeString);

                            }
                        })
                        .build();
                mDialogAll.show(getFragmentManager(), "year-month-day");
                break;
        }
    }

    //设置图片
    private void initAvater(List<BaseMedia> imageMedias) {
        BaseMedia baseMedia = imageMedias.get(0);
        String path = "";
        if (baseMedia instanceof ImageMedia) {
            path = ((ImageMedia) baseMedia).getThumbnailPath();
        } else {
            path = baseMedia.getPath();
        }
        Glide.with(this)
                .load("file://" + path)
                .placeholder(R.drawable.ic_default_image)
                .crossFade(500)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(binding.ivAvatar);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKEPHOTO_CODE) {
            if (binding.ivAvatar == null) {
                return;
            }
            List<BaseMedia> imageMedias = new ArrayList<>();
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias == null) {
                return;
            }
            for (BaseMedia media : medias) {
                if (!(media instanceof ImageMedia)) {
                    return;
                }
                final ImageMedia imageMedia = (ImageMedia) media;
                // the compress task may need time
                if (imageMedia.compress(new ImageCompressor(mActivity))) {
                    imageMedia.removeExif();
                    imageMedias.add(imageMedia);

                }
            }
            if (imageMedias.size() > 0) {
                initAvater(imageMedias);
            }
        }
    }


}

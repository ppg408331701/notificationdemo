package webapps.MOrangeCheck.com.Fragment.Company.WorkOutside;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ImageLoaderUtil.ImageLoaderUtil;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import rx.functions.Action1;
import utils.ScreenUtils;
import utils.SnackbarUtils;
import utils.TimeUtils;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.Application.AppApplication;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Tool.LT;
import webapps.MOrangeCheck.com.Tool.LbsMapLocation;
import webapps.MOrangeCheck.com.databinding.FragmentOutsideBinding;

/**
 * 外勤
 * Created by ppg777 on 2017/3/14.
 */

public class WorkOutSide extends LoadingFragment implements View.OnClickListener {

    FragmentOutsideBinding binding;
    private final int TAKEPHOTO_CODE = 991;
    private BaseQuickAdapter adapter;
    private List<BaseMedia> imageMedias = new ArrayList<>();
    private double mLatitude;
    private double mLongitude;

    public WorkOutSide() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView3("签到", ContextCompat.getColor(mActivity,
                R.color.yellow2), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_outside, container, false);
        binding = DataBindingUtil.bind(root);
        binding.nestedScrollView.setFillViewport(true);
        binding.llOutsideTakephoto.setOnClickListener(this);
        binding.rlLocation.setOnClickListener(this);
        binding.tvResetLocation.setOnClickListener(this);
//        FlexboxLayoutManager manager = new FlexboxLayoutManager();
//        //设置主轴排列方式
//        manager.setFlexDirection(FlexDirection.ROW);
//        //设置是否换行
//        manager.setFlexWrap(FlexWrap.WRAP);
//        manager.setAlignItems(AlignItems.FLEX_START);
//        manager.setAutoMeasureEnabled(true);
        binding.rvImage.setLayoutManager(new GridLayoutManager(mActivity, 3));
//        binding.rvImage.addItemDecoration(new DividerGridItemDecoration(mActivity));
        initPhotoList();
        binding.rvImage.setAdapter(adapter);
        binding.tvCurrentTime.setText(TimeUtils.millis2String(Calendar.getInstance()
                .getTimeInMillis(), TimeUtils.D_PATTERN));
        if (Build.VERSION.SDK_INT >= 23) {
            Getpermission(1);
        } else {
            StartLocation();
        }
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
            case R.id.rl_location:
                if (Build.VERSION.SDK_INT >= 23) {
                    Getpermission(3);
                } else {
                    intent = new Intent();
                    intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_CHOICESITE);
                    intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "选择地址");
                    IntentTool.startByFragment(mActivity, WorkOutSide.this, intent);
                }
                break;
            case R.id.ll_outside_takephoto:
                if (Build.VERSION.SDK_INT >= 23) {
                    Getpermission(2);
                } else {
                    tackPhoto();
                }
                break;
            case R.id.tv_reset_Location:
                if (Build.VERSION.SDK_INT >= 23) {
                    Getpermission(1);
                } else {
                    StartLocation();
                }
                break;
        }
    }


    private void initPhotoList() {
        adapter = new BaseQuickAdapter<BaseMedia, BaseViewHolder>(R.layout.item_outside_image, imageMedias) {

//            @Override
//            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outside_image, parent, false);
//                view.setMinimumHeight(ScreenUtils.getScreenWidth() / 4);
//                view.setMinimumWidth(ScreenUtils.getScreenWidth() / 4);
//                return new BaseViewHolder(view);
//            }


            @Override
            protected void convert(BaseViewHolder helper, BaseMedia baseMedia) {
                LT.ee("baseMedia=" + baseMedia.getSize());
                ImageView imageView = helper.getView(R.id.media_item);
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                ImageLoaderUtil.init().loadFileImage(path, R.drawable.ic_default_image, imageView);
                imageView.setPadding(10, 10, 10, 10);
                imageView.setMinimumHeight(ScreenUtils.getScreenWidth() / 4);
                imageView.setMinimumWidth(ScreenUtils.getScreenWidth() / 4);
            }

        };

    }

    /**
     * 传1湖片区定位权限,传2获取拍照和读取储存权限
     *
     * @param tyep
     */
    private void Getpermission(int tyep) {
        if (tyep == 1) {
            new RxPermissions(mActivity)
                    .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                StartLocation();
                            } else {
                                SnackbarUtils.showNoDisMiss(getRoot(), "获取权限失败,无法定位.", ContextCompat.getColor(mActivity
                                        , R.color.orange_primary_dark), "重新获取", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Getpermission(1);
                                    }
                                });
                            }
                        }
                    });
        } else if (tyep == 2) {
            new RxPermissions(mActivity)
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                tackPhoto();
                            } else {
                                SnackbarUtils.showNoDisMiss(getRoot(), "获取权限失败,无法定位.", ContextCompat.getColor(mActivity
                                        , R.color.orange_primary_dark), "重新获取", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Getpermission(1);
                                    }
                                });
                            }
                        }
                    });
        } else if (tyep == 3) {
            new RxPermissions(mActivity)
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                Intent intent = new Intent();
                                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_CHOICESITE);
                                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "选择地址");
                                IntentTool.startByFragment(mActivity, WorkOutSide.this, intent);
                            } else {
                                SnackbarUtils.showNoDisMiss(getRoot(), "获取权限失败,无法定位.", ContextCompat.getColor(mActivity
                                        , R.color.orange_primary_dark), "重新获取", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Getpermission(1);
                                    }
                                });
                            }
                        }
                    });
        }

    }

    /**
     * 开始定位
     */
    private void StartLocation() {
        binding.tvCurrentPoint.setText("定位中...");
        LbsMapLocation lbsMapLocation = new LbsMapLocation(mActivity);
        lbsMapLocation.initLocation(new LbsMapLocation.onLocationEnd() {
            @Override
            public void getMyPositionOK(double Latitude, double Longitude, String adress, AMapLocation amapLocation) {
                binding.tvCurrentPoint.setText(adress);
                mLatitude = Latitude;
                mLongitude = Longitude;
                AppApplication.getSpUtils().putString("LocationCity", amapLocation.getCityCode());
                AppApplication.getSpUtils().putString("Latitude", String.valueOf(Latitude));
                AppApplication.getSpUtils().putString("Longitude", String.valueOf(Longitude));
            }

            @Override
            public void getMyPositionFailed(String Result) {
                binding.tvCurrentPoint.setText("定位失败");
                SnackbarUtils.showIndefiniteSnackbar(getRoot(), Result, 5000, ContextCompat.getColor(mActivity
                        , R.color.orange_primary_dark), ContextCompat.getColor(mActivity, R.color.orange_primary_text));
            }
        });
    }

    private void tackPhoto() {
        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        singleImgConfig.needCamera().needGif().withMaxCount(9);
        Boxing.of(singleImgConfig).withIntent(mActivity, BoxingActivity.class).start(this, TAKEPHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKEPHOTO_CODE) {
            if (binding.rvImage == null || adapter == null) {
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
                adapter.addData(imageMedias);
            }
        }
    }


}

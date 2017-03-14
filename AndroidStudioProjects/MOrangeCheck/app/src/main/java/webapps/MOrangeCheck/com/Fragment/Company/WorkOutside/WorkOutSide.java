package webapps.MOrangeCheck.com.Fragment.Company.WorkOutside;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.GridDividerItemDecoration;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import utils.ScreenUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;
import webapps.MOrangeCheck.com.databinding.FragmentOutsideBinding;

/**
 * Created by ppg777 on 2017/3/14.
 */

public class WorkOutSide extends LoadingFragment implements View.OnClickListener {

    FragmentOutsideBinding binding;
    private final int TAKEPHOTO_CODE = 991;
    private CommonAdapter adapter;
    private List<BaseMedia> imageMedias = new ArrayList<>();

    public WorkOutSide() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getTopBar().getOperationRightView3("签到", ContextCompat.getColor(mActivity, R.color.yellow2), new View.OnClickListener() {
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
        binding.rvImage.setLayoutManager(new GridLayoutManager(mActivity, 3));
        binding.rvImage.addItemDecoration(new GridDividerItemDecoration(mActivity, GridDividerItemDecoration.GRID_DIVIDER_VERTICAL));
        initPhotoList();
        binding.rvImage.setAdapter(adapter);
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
            case R.id.ll_outside_takephoto:
                BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
                singleImgConfig.needCamera().needGif().withMaxCount(9);
                Boxing.of(singleImgConfig).withIntent(mActivity, BoxingActivity.class).start(this, TAKEPHOTO_CODE);
                break;
        }
    }


    private void initPhotoList() {
        adapter = new CommonAdapter<BaseMedia>(mActivity, R.layout.item_outside_image, imageMedias) {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outside_image, parent, false);
                view.setMinimumHeight(ScreenUtils.getScreenWidth() / 4);
                view.setMinimumWidth(ScreenUtils.getScreenWidth() / 4);
                return new ViewHolder(mActivity, view, parent, -1);
            }

            @Override
            public void convert(ViewHolder holder, BaseMedia baseMedia) {
                LT.ee("baseMedia=" + baseMedia.getSize());
                ImageView imageView = holder.getView(R.id.media_item);
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                Glide.with(imageView.getContext())
                        .load("file://" + path)
                        .placeholder(R.drawable.ic_default_image)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);

                //BoxingMediaLoader.getInstance().displayThumbnail(imageView, path, 150, 150);
            }
        };

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
                adapter.setMoreDatas(imageMedias);
            }
        }
    }
}

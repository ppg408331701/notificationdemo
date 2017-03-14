package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import utils.ScreenUtils;
import webapps.MOrangeCheck.com.R;

/**
 * 图片浏览的fragment
 * 全屏黑底
 * Created by ppg777 on 2017/3/9.
 */

public class ImageViewFragment extends DialogFragment {


    String picurl = "http://imgsrc.baidu.com/forum/w=580/sign=4be4551c" +
            "544e9258a63486e6ac83d1d1/b912c8fcc3cec3fdbec41a0dd488d43f869427cb.jpg";
    String picgif = "http://imgsrc.baidu.com/baike/pic/item/7af40ad162d9f2d339d2a789abec8a136227cc91.jpg";
    private View root;

    /**
     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * setStyle() 的第一个参数有四个可选值：
         * STYLE_NORMAL|STYLE_NO_TITLE|STYLE_NO_FRAME|STYLE_NO_INPUT
         * 其中 STYLE_NO_TITLE 和 STYLE_NO_FRAME 可以关闭标题栏
         * 每一个参数的详细用途可以直接看 Android 源码的说明
         */
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        root = inflater.inflate(R.layout.image_view, container, false);
        //Do something
        // 设置宽度为屏宽、靠近屏幕底部。
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        return root;

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotoView photoView = (PhotoView) root.findViewById(R.id.img1);
        photoView.enable();
        Glide.with(this)
                .load(picurl)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .crossFade()
                .into(photoView);
    }
}

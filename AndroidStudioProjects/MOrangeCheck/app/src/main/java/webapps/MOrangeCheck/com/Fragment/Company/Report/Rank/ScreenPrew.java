package webapps.MOrangeCheck.com.Fragment.Company.Report.Rank;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import utils.ImageUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;

/**
 * 截图后预览的fragment
 * 不是全屏
 * Created by ppg777 on 2017/3/13.
 */

public class ScreenPrew extends DialogFragment {

    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        root = inflater.inflate(R.layout.dialog_fragment_screen, container, false);

       //  设置宽度为屏宽、靠近屏幕底部。
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(50, 50, 50, 50);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        return root;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String path = getArguments().getString("Screenbitmap");
        Bitmap Screenbitmap = ImageUtils.getBitmap(path);
        LT.ee(path);
        ImageView photoView = (ImageView) root.findViewById(R.id.iv_screen);
        Glide.with(this)
                .load(ImageUtils.bitmap2Bytes(Screenbitmap, Bitmap.CompressFormat.JPEG))
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(photoView);
    }
}

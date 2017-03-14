package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import utils.ImageUtils;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/1.
 */

public abstract class ScreenDialog extends BaseDialog<ScreenDialog> {


    ImageView imageView;
    String path;

    public ScreenDialog(Context context) {
        super(context);
    }

    public ScreenDialog(Context context, String path) {
        super(context);
        this.path = path;
    }


    @Override
    public View onCreateView() {
        widthScale(0.88f);
        heightScale(0.92f);

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_fragment_screen, null);
        imageView = (ImageView) inflate.findViewById(R.id.iv_screen);
        initPic();

        inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(7)));

        return inflate;
    }

    public void initPic() {
        Bitmap Screenbitmap = ImageUtils.getBitmap(path);
        Glide.with(imageView.getContext())
                .load(ImageUtils.bitmap2Bytes(Screenbitmap, Bitmap.CompressFormat.JPEG))
                .placeholder(R.color.transparent)
                .crossFade(300)
                .into(imageView);
    }


    @Override
    public void setUiBeforShow() {


    }

    public abstract void determineTask();
}

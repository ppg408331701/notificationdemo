package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/1.
 */

public abstract class TopDialogUtils extends BaseDialog<TopDialogUtils> {

    private TextView tv_title;
    private TextView cancel_button;
    private TextView ok_button;
    private TextView content_text;
    private View.OnClickListener listener;

    public TopDialogUtils(Context context) {
        super(context);
    }




    @Override
    public View onCreateView() {
        widthScale(0.85f);

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_base, null);
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        content_text =(TextView) inflate.findViewById(R.id.content_text);
        cancel_button = (TextView) inflate.findViewById(R.id.cancel_button);
        ok_button =(TextView) inflate.findViewById(R.id.ok_button);

        inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(10)));

        return inflate;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public TextView getContent_text() {
        return content_text;
    }

    public void setContent_text(TextView content_text) {
        this.content_text = content_text;
    }

    public TextView getCancel_button() {
        return cancel_button;
    }

    public void setCancel_button(TextView cancel_button) {
        this.cancel_button = cancel_button;
    }

    public TextView getOk_button() {
        return ok_button;
    }

    public void setOk_button(TextView ok_button) {
        this.ok_button = ok_button;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setUiBeforShow() {
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineTask();
                dismiss();
            }
        });

    }

    public abstract void determineTask();
}

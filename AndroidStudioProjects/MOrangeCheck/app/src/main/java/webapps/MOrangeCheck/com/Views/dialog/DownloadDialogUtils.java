package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import webapps.MOrangeCheck.com.Application.AppApplication;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/1.
 */

public abstract class DownloadDialogUtils extends BaseDialog<DownloadDialogUtils> {

    private TextView tvTitle;
    private TextView tvContent;
    private CheckBox cxShow;
    private Button btCancle;
    private Button btSure;


    public DownloadDialogUtils(Context context, String title, String content) {
        super(context);
    }

    public DownloadDialogUtils(Context context, String content) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        heightScale(0.7f);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_download, null);

        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        cxShow = (CheckBox) inflate.findViewById(R.id.cx_show);
        btCancle = (Button) inflate.findViewById(R.id.bt_cancle);
        btSure = (Button) inflate.findViewById(R.id.bt_sure);

        inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(7)));

        return inflate;
    }


    @Override
    public void setUiBeforShow() {
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineTask();
                dismiss();
            }
        });
        //显示checkbox
        if (AppApplication.getSpUtils().getBoolean("showDownload", true)) {
            cxShow.setVisibility(View.VISIBLE);
            cxShow.setChecked(false);
            cxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppApplication.getSpUtils().putBoolean("showDownload", false);
                    } else {
                        AppApplication.getSpUtils().putBoolean("showDownload", true);
                    }
                }
            });
        } else {
            cxShow.setVisibility(View.GONE);
        }

    }

    public abstract void determineTask();
}

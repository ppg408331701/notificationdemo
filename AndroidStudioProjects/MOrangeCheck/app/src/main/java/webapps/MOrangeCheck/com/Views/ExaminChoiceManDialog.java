package webapps.MOrangeCheck.com.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import webapps.MOrangeCheck.com.R;

/**
 * 选择审批人
 * Created by ppg777 on 2017/3/1.
 */

public class ExaminChoiceManDialog extends BaseDialog<ExaminChoiceManDialog> {

    private TextView tv_title;
    private TextView cancel_button;
    private TextView ok_button;
    private View root;
    private View.OnClickListener listener;

    public ExaminChoiceManDialog(Context context) {
        super(context);
    }

    public ExaminChoiceManDialog(Context context, View root) {
        super(context);
        this.root = root;

    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        heightScale(0.7f);
        // dismissAnim(this, new ZoomOutExit());
        tv_title = (TextView) root.findViewById(R.id.tv_title);
        cancel_button = (TextView) root.findViewById(R.id.cancel_button);
        ok_button = (TextView) root.findViewById(R.id.ok_button);
        root.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return root;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public TextView getCancel_button() {
        return cancel_button;
    }

    public TextView getOk_button() {
        return ok_button;
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

        ok_button.setOnClickListener(listener);
    }
}

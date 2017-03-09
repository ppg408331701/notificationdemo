package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/9.
 */

public abstract class ExamineBottomDialog extends BaseDialog<ExamineBottomDialog> {

    private TextView cancel_button;
    private TextView ok_button;
    private EditText content_edittext;
    private View.OnClickListener listener;

    public ExamineBottomDialog(Context context) {
        super(context);
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_examinebottom, null);
        content_edittext =(EditText) inflate.findViewById(R.id.content_edittext);
        cancel_button = (TextView) inflate.findViewById(R.id.cancel_button);
        ok_button =(TextView) inflate.findViewById(R.id.ok_button);

        inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(10)));
        return inflate;
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

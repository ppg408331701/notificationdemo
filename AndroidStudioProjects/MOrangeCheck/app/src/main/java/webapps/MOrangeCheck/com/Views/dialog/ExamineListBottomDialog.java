package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;

/**
 * Created by ppg777 on 2017/3/9.
 */

public abstract class ExamineListBottomDialog extends BaseDialog<ExamineListBottomDialog> {

    private TextView cancel_button;
    private TextView ok_button;
    private EditText content_edittext;
    private View.OnClickListener listener;
    private TextView tv_title;

    private View root;

    public ExamineListBottomDialog(Context context) {
        super(context);
    }
    public ExamineListBottomDialog(Context context,View root) {
        super(context);
        this.root=root;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        heightScale(0.7f);
        // dismissAnim(this, new ZoomOutExit());
//        View inflate = View.inflate(mContext, R.layout.dialog_examinebottom, null);
        content_edittext = (EditText) root.findViewById(R.id.content_edittext);
        cancel_button = (TextView) root.findViewById(R.id.cancel_button);
        ok_button = (TextView) root.findViewById(R.id.ok_button);
        tv_title = (TextView) root.findViewById(R.id.tv_title);

        root.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(7)));
        return root;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public EditText getContent_edittext() {
        return content_edittext;
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
                determineTask(content_edittext);
                dismiss();
            }
        });
    }




    public abstract void determineTask(EditText text);

}

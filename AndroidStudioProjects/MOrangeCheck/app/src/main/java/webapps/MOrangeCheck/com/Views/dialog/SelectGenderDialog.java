package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg777 on 2017/3/17.
 */

public abstract class SelectGenderDialog extends BaseDialog<SelectGenderDialog> {

    int gender;

    public SelectGenderDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {

        widthScale(0.85f);

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_seletct_gender, null);
        RadioGroup group = (RadioGroup) inflate.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.rb1){
                    gender=1;
                }else {
                    gender=2;
                }
                determineTask(gender);
                dismiss();
            }
        });
        inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(7)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    public abstract void determineTask(int gender);
}

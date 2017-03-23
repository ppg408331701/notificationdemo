package webapps.MOrangeCheck.com.Views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import java.util.Calendar;

import utils.TimeUtils;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LunarUtils.Lunar;
import webapps.MOrangeCheck.com.Views.VerTextView;
import webapps.MOrangeCheck.com.Views.VerticalTextView;

/**
 * 黄历dialog
 * Created by ppg777 on 2017/3/13.
 */

public abstract class AlmanacADialog extends BaseDialog<AlmanacADialog> {


    private Context context;

    private TextView mLunarDate;
    private VerticalTextView mNumDate;
    private TextView mAlmanacDate;
    private TextView mWeekDay;
    private TextView mBigDay;
    private TextView mPrediction;
    private TextView mAnaContent;
    private TextView mAnaName;


    public AlmanacADialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public View onCreateView() {
        widthScale(0.95f);
        heightScale(0.73f);


        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_almanac, null);


        mLunarDate = (TextView) inflate.findViewById(R.id.lunar_date);
        mNumDate = (VerticalTextView) inflate.findViewById(R.id.num_date);
        mAlmanacDate = (TextView) inflate.findViewById(R.id.almanac_date);
        mWeekDay = (TextView) inflate.findViewById(R.id.week_day);
        mBigDay = (TextView) inflate.findViewById(R.id.big_day);
        mPrediction = (TextView) inflate.findViewById(R.id.prediction);
        mAnaContent = (TextView) inflate.findViewById(R.id.ana_content);
        mAnaName = (TextView) inflate.findViewById(R.id.ana_name);
        //获取日期
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int mouth = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Lunar date = new Lunar(year, mouth, day);
        //设置
        mLunarDate.setText(date.getMonthInChinese() +"月"+ date.getDayInChinese());
        mNumDate.setText(TimeUtils.millis2String(Calendar.getInstance().getTimeInMillis(), TimeUtils.B_PATTERN));
        mAlmanacDate.setText(date.getGan() + date.getZhi() + "年 ");
        mWeekDay.setText(TimeUtils.getWeek(Calendar.getInstance().getTimeInMillis()));
        mBigDay.setText("" + day);


       // inflate.setBackground(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(7)));

        return inflate;
    }


    @Override
    public void setUiBeforShow() {


    }

    public abstract void determineTask();
}

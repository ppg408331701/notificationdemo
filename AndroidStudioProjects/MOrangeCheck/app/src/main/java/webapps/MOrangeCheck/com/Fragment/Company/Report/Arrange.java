package webapps.MOrangeCheck.com.Fragment.Company.Report;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;
import webapps.MOrangeCheck.com.Views.compactcalendarview.CompactCalendarView;
import webapps.MOrangeCheck.com.Views.compactcalendarview.domain.Event;
import webapps.MOrangeCheck.com.databinding.FragmentArrangeBinding;

/**
 * 排班
 * Created by ppg777 on 2017/2/24.
 */

public class Arrange extends LoadingFragment {

    FragmentArrangeBinding binding;
    private CommonAdapter adapter;
    private List<String>  list = new ArrayList<>();;

    public Arrange() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_arrange, container, false);
        binding = DataBindingUtil.bind(root);

        binding.compactcalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        binding.compactcalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        binding.compactcalendarView.shouldDrawIndicatorsBelowSelectedDays(true);

        binding.compactcalendarView.shouldSelectFirstDayOfMonthOnScroll(false);
        binding.compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                replaceData();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
        binding.add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar yestoday = Calendar.getInstance();
                yestoday.add(Calendar.DAY_OF_YEAR, 2);
                // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
                Event ev1 = new Event(Color.RED, yestoday.getTimeInMillis(), "Some extra data that I want to store.");
                binding.compactcalendarView.addEvent(ev1, true);
                LT.ee("ev1= " + yestoday.getTimeInMillis());
                yestoday.add(Calendar.DAY_OF_YEAR, 1);
                // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
                Event ev2 = new Event(Color.BLUE, yestoday.getTimeInMillis(), "Some extra data that I want to store.");
                binding.compactcalendarView.addEvent(ev2, true);
                LT.ee("ev2= " + yestoday.getTimeInMillis());

            }
        });
        binding.calanderList.setHasFixedSize(true);
        binding.calanderList.setItemAnimator(new DefaultItemAnimator());
        binding.calanderList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        binding.calanderList.setLayoutManager(new LinearLayoutManager(mActivity));
        initData();
        return root;
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("测试数据" + i);
        }
        adapter = new CommonAdapter<String>(mActivity, R.layout.item_calander, list) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_title, s);
            }
        };
        binding.calanderList.setAdapter(adapter);
        binding.scrollView.smoothScrollTo(0, 0);
    }

    private void replaceData(){
        Random random= new Random();
        int num  =random.nextInt(10);
        list.clear();
        for (int i = 0; i < num; i++) {
            list.add("测试数据" + i);
        }
        adapter.notifyDataSetChanged();

    }
}

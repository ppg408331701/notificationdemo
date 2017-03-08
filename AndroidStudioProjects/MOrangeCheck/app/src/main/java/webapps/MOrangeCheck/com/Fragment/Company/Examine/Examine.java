package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import Adapter.OmnipotentAdapter.LinBaseadapter;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.databinding.FragmentExamineBinding;

/**
 * 审核/申请
 * Created by ppg777 on 2017/3/1.
 */

public class Examine extends LoadingFragment implements View.OnClickListener {

    FragmentExamineBinding binding;
    private List<String> list = new ArrayList<>();
    private LinBaseadapter<String> adapter;
    List<Badge> badges = new ArrayList<>();

    public Examine() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_examine, container, false);
        binding = DataBindingUtil.bind(root);
        binding.llChaosong.setOnClickListener(this);
        binding.llShenpi.setOnClickListener(this);
        binding.llShengqing.setOnClickListener(this);
        bindView();
        initFuncation();
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_shengqing:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINEBASEPAG);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "");
                intent.putExtra("type", 1);
                IntentTool.startByFragment(mActivity, Examine.this, intent);
                break;
            case R.id.ll_shenpi:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINEBASEPAG);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "");
                intent.putExtra("type", 2);
                IntentTool.startByFragment(mActivity, Examine.this, intent);
                break;
            case R.id.ll_chaosong:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINEBASEPAG);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "");
                intent.putExtra("type", 3);
                IntentTool.startByFragment(mActivity, Examine.this, intent);
                break;
        }
    }

    private void bindView() {
        //可以拖拽消除的view
        Badge qBadgeView = new QBadgeView(mActivity).bindTarget(binding.ivShenpi)
                .setBadgeNumber(2)
                .setBadgeTextSize(13, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(-3, -3, true)
                .setShowShadow(true)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                    }
                });
        badges.add(qBadgeView);
        Badge qBadgeView2 = new QBadgeView(mActivity).bindTarget(binding.ivChaosong)
                .setBadgeNumber(2)
                .setBadgeTextSize(13, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(-3, -3, true)
                .setShowShadow(true)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                    }
                });
        badges.add(qBadgeView2);
    }

    private void initFuncation() {
        String[] funcation_text = {"请假", "加班", "出差", "调班", "调休", "漏打卡", "外出", "采购", "通用"};
        for (int i = 0; i < funcation_text.length; i++) {
            list.add(funcation_text[i]);
        }
        Resources resources = getResources();
        final TypedArray funcation_icon = resources.obtainTypedArray(R.array.examine_funcation_icon);

        adapter = new LinBaseadapter<String>(mActivity, list, R.layout.item_examine_grid) {

            @Override
            public void convert(Adapter.OmnipotentAdapter.ViewHolder holder, String s) {
                holder.setText(R.id.tv_examine_txt, s)
                        .setImageResId(R.id.iv_examine_icon,
                                funcation_icon.getResourceId(holder.getPosition(), 0));

            }
        };
        binding.gvFuncation.setAdapter(adapter);
        binding.gvFuncation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent();
                        intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_COMMONAPPLY);
                        intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "请假审批");
                        intent.putExtra("showTimeLayout",true);
                        IntentTool.startByFragment(mActivity, Examine.this, intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        intent = new Intent();
                        intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_COMMONAPPLY);
                        intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "通用审批");
                        intent.putExtra("showTimeLayout",false);
                        IntentTool.startByFragment(mActivity, Examine.this, intent);
                        break;

                }
            }
        });
    }


}


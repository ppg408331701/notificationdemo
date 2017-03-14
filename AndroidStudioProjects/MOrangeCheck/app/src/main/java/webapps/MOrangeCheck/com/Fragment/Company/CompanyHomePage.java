package webapps.MOrangeCheck.com.Fragment.Company;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import utils.ScreenUtils;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Views.dialog.AlmanacADialog;
import webapps.MOrangeCheck.com.Views.dialog.TopDialogUtils;
import webapps.MOrangeCheck.com.databinding.FragmentCompanyhomepageBinding;
import webapps.MOrangeCheck.com.databinding.ItemCheckpointBinding;
import webapps.MOrangeCheck.com.databinding.ItemFuncationBinding;

/**
 * 公司类型时的主页
 * Created by ppg777 on 2017/2/21.
 */

public class CompanyHomePage extends LoadingFragment implements View.OnClickListener {

    FragmentCompanyhomepageBinding binding;
    List<Badge> badges = new ArrayList<>();

    public CompanyHomePage() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_companyhomepage, container, false);
        binding = DataBindingUtil.bind(root);
        binding.ivMore.setOnClickListener(this);
        binding.tvCompanyTitle.setOnClickListener(this);
        initCheckPoint();
        initFuncation();
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        mActivity.getTopBar().getOperationLeftView1(R.drawable.ic_account_circle_black_24dp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_MEETINGPLACEFRAGMENT);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "东盟博览会");
                IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
            }
        });
        Glide.with(mActivity)
                .load("http://attachments.gfan.com/attachments2/day_110615/1106151509f6d875b078d5a4c4.jpg")
                .dontAnimate()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(mActivity.getTopBar().getViewL1());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_more:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_REPORTFRAGMENT);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "更多");
                IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                break;
            case R.id.tv_company_title:
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_WORKOUTSIDE);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "外勤");
                IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                break;
        }
    }

    /**
     * 初始化功能列表
     */
    private void initFuncation() {
        //获取功能的名称
        String[] funcation_text = {"消息", "申请/审批", "动态/公共", "文档", "小工具"};
        Resources resources = getResources();
        TypedArray funcation_icon = resources.obtainTypedArray(R.array.company_funcation_icon);
        for (int i = 0; i < funcation_text.length; i++) {
            ViewGroup FuncationItem = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_funcation, binding.llFuncation, false);
            ItemFuncationBinding itemFuncationBinding = DataBindingUtil.bind(FuncationItem);
            itemFuncationBinding.tvFuncation.setText(funcation_text[i]);
            itemFuncationBinding.ivFuncation.setImageResource(funcation_icon.getResourceId(i, 0));
            if (funcation_text[i].equals("消息") || funcation_text[i].equals("申请/审批")) {
                //可以拖拽消除的view
                Badge qBadgeView = new QBadgeView(mActivity).bindTarget(itemFuncationBinding.tvFuncation)
                        .setBadgeNumber(2)
                        .setBadgePadding(3,true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP)
                        .setGravityOffset(2, 2, true)
                        .setShowShadow(true)
                        .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                            @Override
                            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                            }
                        });
                badges.add(qBadgeView);
            }
            switch (i){
                case 0:
                    FuncationItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINE);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "申请与审批");
                            IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                        }
                    });
                    break;
                case 1:
                    FuncationItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINE);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "申请与审批");
                            IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                        }
                    });
                    break;
                case 2:
                    FuncationItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINE);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "申请与审批");
                            IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                        }
                    });
                    break;
                case 3:
                    FuncationItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINE);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "申请与审批");
                            IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                        }
                    });
                    break;
                case 4:
                    FuncationItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_EXAMINE);
                            intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "申请与审批");
                            IntentTool.startByFragment(mActivity, CompanyHomePage.this, intent);
                        }
                    });
                    break;
            }
            binding.llFuncation.addView(FuncationItem);
        }

    }

    /**
     * 初始化签到按钮,把以checkPoint_json判断今天是否点击过
     */
    private void initCheckPoint() {
        String[] timelist = {"08:30", "12:00"};
        if (binding.llCheck.getChildCount() > 0) binding.llCheck.removeAllViews();
        for (int i = 0; i < timelist.length; i++) {
            ViewGroup chcekTimeItem = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_checkpoint, binding.llCheck, false);
            final ItemCheckpointBinding itemCheckpointBinding = DataBindingUtil.bind(chcekTimeItem);
            itemCheckpointBinding.tvCheckpoint.setText(timelist[i]);
//            try {
//                SPUtils spUtils = new SPUtils("checkPoint");
//                String CheckItemJson = spUtils.getString(i + timelist[i]);
//                //先判断有没有json
//                if (!TextUtils.isEmpty(CheckItemJson)) {
//                    JSONObject checkObj = new JSONObject(CheckItemJson);
//                    //再判断有没有今天的数据,有就加载
//                    if (checkObj.optString("today").equals(DateUtil.getDateStr(TimeUtils.getNowTimeMills()))) {
//
//                    } else {
//
//                    }
//                } else {
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //设置为屏幕的1/4的宽度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemCheckpointBinding.llCheckPoint.getLayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWidth() / 4);
            itemCheckpointBinding.llCheckPoint.setLayoutParams(layoutParams);
            //点击事件
            itemCheckpointBinding.llCheckPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TopDialogUtils dialogUtils = new TopDialogUtils(mActivity) {
                        @Override
                        public void determineTask() {
                            AlmanacADialog almanacADialog = new AlmanacADialog(mActivity) {
                                @Override
                                public void determineTask() {

                                }
                            };
                            almanacADialog.show();
                            itemCheckpointBinding.ivCheckpoint.setImageResource(R.mipmap.fingerprintok);
                        }
                    };
                    dialogUtils.show();
                    dialogUtils.getTv_title().setText("打卡");
                    dialogUtils.getContent_text().setText("不在打卡时间范围内,是否打卡?");
                }
            });
            binding.llCheck.addView(chcekTimeItem);
        }
    }


}

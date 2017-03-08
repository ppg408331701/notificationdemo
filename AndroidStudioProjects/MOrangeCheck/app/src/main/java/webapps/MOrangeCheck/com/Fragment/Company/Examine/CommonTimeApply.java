package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ImageLoaderUtil.ImageLoaderUtil;
import cn.qqtheme.framework.picker.FilePicker;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.OnItemClickListener;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import utils.FileUtils;
import utils.StringUtils;
import utils.TimeUtils;
import webapps.MOrangeCheck.com.Bean.ExamineManBean;
import webapps.MOrangeCheck.com.Bean.FileBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.Tool.MyIntent;
import webapps.MOrangeCheck.com.Views.ExaminChoiceManDialog;
import webapps.MOrangeCheck.com.databinding.DialogExamineChioceManBinding;
import webapps.MOrangeCheck.com.databinding.FragmentCommonTimeApplyBinding;
import webapps.MOrangeCheck.com.databinding.ItemFileLayoutBinding;

/**
 * 带有时间的通用申请
 * Created by ppg777 on 2017/3/2.
 */

public class CommonTimeApply extends LoadingFragment implements View.OnClickListener {

    FragmentCommonTimeApplyBinding binding;
    /**
     * 保存审批人的list
     */
    private List<ExamineManBean> list = new ArrayList<>();
    /**
     * 保存已选择审批人的list
     */
    private List<ExamineManBean> Choicelist = new ArrayList<>();
    /**
     * 保存抄送人的list
     */
    private List<ExamineManBean> list2 = new ArrayList<>();
    /**
     * 保存已选择抄送人的list
     */
    private List<ExamineManBean> Choicelist2 = new ArrayList<>();
    /**
     * 审批人填充器
     */
    private CommonAdapter adapter;
    /**
     * 抄送人填充器
     */
    private CommonAdapter adapter2;
    /**
     * 保存附件的list
     */
    private List<FileBean> fileList = new ArrayList<>();
    /**
     * 保存时间的dialog
     */
    private TimePickerDialog mDialogAll;
    /**
     * 年
     */
    long Years = 2L * 365 * 1000 * 60 * 60 * 24L;
    /**
     * 月
     */
    long Month = 90 * 1000 * 60 * 60 * 24L;

    /**
     * 开始时间,结束时间
     */
    long startTime = 0L, endTime = 0L;

    public CommonTimeApply() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_common_time_apply, container, false);
        binding = DataBindingUtil.bind(root);
        binding.layoutExamineApplyCopy.copyIvAddimg.setOnClickListener(this);
        binding.layoutExamineApplyMan.manIvAddimg.setOnClickListener(this);
        binding.layoutCommonApplyEdit.llExiamineUpload.setOnClickListener(this);
        binding.layoutExamineApplyTime.rlEndTime.setOnClickListener(this);
        binding.layoutExamineApplyTime.rlStartTime.setOnClickListener(this);
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        if (mActivity.getIntent().getBooleanExtra("showTimeLayout", false)) {
            binding.layoutExamineApplyTime.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutExamineApplyTime.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.man_iv_addimg:
                choiceMan(1);
                break;
            case R.id.copy_iv_addimg:
                choiceCopy(2);
                break;
            case R.id.ll_exiamine_upload:
                onFilePicker();
                break;
            case R.id.rl_start_time:
                SetTime(1);
                break;
            case R.id.rl_end_time:
                SetTime(2);
                break;
        }
    }

    /**
     * 选择时间
     */
    private void SetTime(final int type) {
        mDialogAll = new TimePickerDialog.Builder()
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(true)
                .setMinMillseconds(System.currentTimeMillis() - Month)
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(ContextCompat.getColor(mActivity, R.color.timetimepicker_default_text_color))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(ContextCompat.getColor(mActivity, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(mActivity, R.color.yellow2))
                .setWheelItemTextSize(13)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String timeString = TimeUtils.millis2String(millseconds, TimeUtils.D_PATTERN);
                        if (type == 1) {
                            startTime = millseconds;
                            binding.layoutExamineApplyTime.tvStartTime.setText(timeString);
                        } else {
                            endTime = millseconds;
                            binding.layoutExamineApplyTime.tvEndTime.setText(timeString);
                        }
                        calculateTime();
                    }
                })
                .build();
        mDialogAll.show(getFragmentManager(), "all");
    }

    /**
     * 计算总时间
     */
    private void calculateTime() {
        if (startTime > 0L && endTime > 0L) {
            String TimeSpan = "共 " + TimeUtils.getFitTimeSpanPpg(startTime, endTime, 3);
            binding.layoutExamineApplyTime.tvHolidayTime.setText(StringUtils.highlight(TimeSpan, "[0-9]*"));
        }
    }


    //文件选择器//具体可看https://github.com/gzu-liyujiang/AndroidPicker
    public void onFilePicker() {
        FilePicker picker = new FilePicker(mActivity, FilePicker.FILE);
        picker.setTitleText("选择附件");
        picker.setHeight((int) (picker.getScreenHeightPixels() * 0.75));
        picker.setShowHideDir(false);
        picker.setShowUpDir(true);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
                File file = FileUtils.getFileByPath(currentPath);
                if (file == null) return;
                FileBean fileBean = new FileBean();
                fileBean.setFilePath(file.getAbsolutePath());
                fileBean.setFileName(FileUtils.getFileName(file));
                fileBean.setFilesuffix(FileUtils.getFileExtension(file));
                fileBean.setFileSize(FileUtils.getFileSize2(file));
                for (FileBean bean : fileList) {
                    if (bean.getFilePath().equals(currentPath)) {
                        ToastUtil.toast(mActivity, "该附件已存在");
                        return;
                    }
                }
                fileList.add(fileBean);
                initFileList();
            }
        });
        picker.show();
    }

    private void initFileList() {
        binding.layoutCommonApplyEdit.llAccessory.removeAllViews();
        for (final FileBean fileBean : fileList) {
            final ViewGroup item_file = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_file_layout, binding.layoutCommonApplyEdit.llAccessory, false);
            ItemFileLayoutBinding itemFileLayoutBinding = DataBindingUtil.bind(item_file);
            itemFileLayoutBinding.tvName.setText(fileBean.getFileName());
            itemFileLayoutBinding.tvFilesize.setText(fileBean.getFileSize());
            itemFileLayoutBinding.ivImg.setImageResource(getFileTypeImg(fileBean.getFilesuffix()));
            itemFileLayoutBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.toast(mActivity, "已删除附件");
                    fileList.remove(fileBean);
                    initFileList();
                }
            });
            itemFileLayoutBinding.llFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        excludeQQSend(FileUtils.getFileByPath(fileBean.getFilePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.toast(mActivity, "CommonTimeApply->打开文件出错");
                    }
                }
            });
            binding.layoutCommonApplyEdit.llAccessory.addView(item_file);
        }
    }

    private List<ExamineManBean> getChoiceData(int flag) {
        List<ExamineManBean> list = new ArrayList<>();
        ExamineManBean manBean;
        for (int i = 0; i < 3; i++) {
            manBean = new ExamineManBean();
            manBean.setImg("http://imgsrc.baidu.com/forum/w=580/sign=4be4551c" +
                    "544e9258a63486e6ac83d1d1/b912c8fcc3cec3fdbec41a0dd488d43f869427cb.jpg");
            manBean.setName("Mr.李四" + i);
            manBean.setPostion("研发主管");
            manBean.setListPostion(i);
            manBean.setClickNum(0);
            list.add(manBean);
        }
        return list;

    }


    /**
     * 改成圆角
     * 弹出dialog1选择人选
     */
    private void choiceMan(final int flag) {
        if (list == null || list.size() == 0) {
            list = getChoiceData(flag);
        }
        if (adapter == null) {
            adapter = new CommonAdapter<ExamineManBean>(mActivity, R.layout.item_examine_man, list) {

                @Override
                public void convert(ViewHolder holder, ExamineManBean s) {
                    holder.setText(R.id.tv_name, s.getName());
                    holder.setText(R.id.tv_position, s.getPostion());
                    holder.setText(R.id.tv_num, "#" + s.getClickNum());
                    if (s.getClickNum() > 0) {
                        holder.getView(R.id.tv_num).setVisibility(View.VISIBLE);
                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.backgroud_theme);
                    } else {
                        holder.getView(R.id.tv_num).setVisibility(View.GONE);
                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.white0);
                    }
                    ImageLoaderUtil.init().loadImageWithRound(s.getImg(),R.color.white0,(ImageView) holder.getView(R.id.iv_img));

                }
            };
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    ExamineManBean examineManBean = (ExamineManBean) o;
                    if (examineManBean.getClickNum() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getClickNum() > examineManBean.getClickNum()) {
                                list.get(i).setClickNum(list.get(i).getClickNum() - 1);
                            }
                        }
                        examineManBean.setClickNum(0);
                        adapter.notifyDataSetChanged();
                    } else {
                        int max = list.get(0).getClickNum();
                        for (int i = 0; i < list.size(); i++) {
                            if (max < list.get(i).getClickNum())
                                max = list.get(i).getClickNum();
                        }

                        examineManBean.setClickNum(max + 1);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                    return false;
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
        View dialog = View.inflate(mActivity, R.layout.dialog_examine_chioce_man, null);
        DialogExamineChioceManBinding chioceManBinding = DataBindingUtil.bind(dialog);
        chioceManBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        chioceManBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        chioceManBinding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity
                , LeftPaddingDividerItemDecoration.VERTICAL, 12));
        chioceManBinding.recyclerView.setAdapter(adapter);
        final ExaminChoiceManDialog choiceManDialog = new ExaminChoiceManDialog(mActivity, dialog);
        choiceManDialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当已选择的list有数据时,清空除了+号按钮的所有view并清空Choicelist
                if (Choicelist.size() > 0) {
                    binding.layoutExamineApplyMan.llExamineMan.removeViews(0,
                            binding.layoutExamineApplyMan.llExamineMan.getChildCount() - 1);
                    Choicelist.clear();
                }
                //把已选择的对象加入Choicelist
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClickNum() > 0) {
                        Choicelist.add(list.get(i));
                    }
                }
                initChoiceLayout(Choicelist, flag);
                choiceManDialog.dismiss();
            }
        });
        choiceManDialog.show();
        choiceManDialog.getTv_title().setText("选择审批人");

    }

    /**
     * 改成圆角
     * 弹出dialog2选择人选
     */
    private void choiceCopy(final int flag) {
        if (list2 == null || list2.size() == 0) {
            list2 = getChoiceData(flag);
        }
        if (adapter2 == null) {
            adapter2 = new CommonAdapter<ExamineManBean>(mActivity, R.layout.item_examine_man, list2) {

                @Override
                public void convert(ViewHolder holder, ExamineManBean s) {
                    holder.setText(R.id.tv_name, s.getName());
                    holder.setText(R.id.tv_position, s.getPostion());
                    holder.setText(R.id.tv_num, "#" + s.getClickNum());
                    if (s.getClickNum() > 0) {
                        holder.getView(R.id.tv_num).setVisibility(View.VISIBLE);
                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.backgroud_theme);
                    } else {
                        holder.getView(R.id.tv_num).setVisibility(View.GONE);
                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.white0);
                    }
                    ImageLoaderUtil.init().loadImageWithRound(s.getImg(),R.color.white0,(ImageView) holder.getView(R.id.iv_img));
                }
            };
            adapter2.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    ExamineManBean examineManBean = (ExamineManBean) o;
                    if (examineManBean.getClickNum() > 0) {
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).getClickNum() > examineManBean.getClickNum()) {
                                list2.get(i).setClickNum(list2.get(i).getClickNum() - 1);
                            }
                        }
                        examineManBean.setClickNum(0);
                        adapter2.notifyDataSetChanged();
                    } else {
                        int max = list2.get(0).getClickNum();
                        for (int i = 0; i < list2.size(); i++) {
                            if (max < list2.get(i).getClickNum())
                                max = list2.get(i).getClickNum();
                        }

                        examineManBean.setClickNum(max + 1);
                        adapter2.notifyDataSetChanged();
                    }
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                    return false;
                }
            });
        } else {
            adapter2.notifyDataSetChanged();
        }
        View dialog = View.inflate(mActivity, R.layout.dialog_examine_chioce_man, null);
        DialogExamineChioceManBinding chioceManBinding = DataBindingUtil.bind(dialog);
        chioceManBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        chioceManBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        chioceManBinding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity
                , LeftPaddingDividerItemDecoration.VERTICAL, 10));

        chioceManBinding.recyclerView.setAdapter(adapter2);
        final ExaminChoiceManDialog choiceManDialog = new ExaminChoiceManDialog(mActivity, dialog);
        choiceManDialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Choicelist2.size() > 0) {
                    binding.layoutExamineApplyCopy.llExamineMan.removeViews(0,
                            binding.layoutExamineApplyCopy.llExamineMan.getChildCount() - 1);
                    Choicelist2.clear();
                }
                for (int i = 0; i < list2.size(); i++) {
                    if (list2.get(i).getClickNum() > 0) {
                        Choicelist2.add(list2.get(i));
                    }
                }

                initChoiceLayout(Choicelist2, flag);
                choiceManDialog.dismiss();
            }
        });
        //  choiceManDialog.setCanceledOnTouchOutside(false);
        choiceManDialog.show();
        choiceManDialog.getTv_title().setText("选择抄送人");

    }

    /**
     * 填充layout
     *
     * @param flag
     */
    private void initChoiceLayout(final List<ExamineManBean> Paddinglist, final int flag) {
        //取得插入位置,+号按钮的前一位,由于是线性布局,所以会把+号按钮不断往后挤压
        int index = binding.layoutExamineApplyMan.llExamineMan.getChildCount() - 1;
        int index2 = binding.layoutExamineApplyCopy.llExamineMan.getChildCount() - 1;
        for (int i = 0; i < Paddinglist.size(); i++) {
            final ViewGroup item_examine_avatar = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_examine_avatar, binding.layoutExamineApplyMan.llExamineMan, false);
            ImageView avatar = (ImageView) item_examine_avatar.findViewById(R.id.iv_avatar);
            //加载图片
            ImageLoaderUtil.init().loadImage(Paddinglist.get(i).getImg(),R.color.white0,avatar);
            if (flag == 1) {
                item_examine_avatar.setTag(i);
                item_examine_avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //通过已选对象的getListPostion取到list对象,操作对象数字减一
                        ExamineManBean removeBean = list.get(Choicelist.get((int) v.getTag()).getListPostion());
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getClickNum() > removeBean.getClickNum()) {
                                list.get(i).setClickNum(list.get(i).getClickNum() - 1);
                            }
                        }

                        list.get(Choicelist.get((int) v.getTag()).getListPostion()).setClickNum(0);
                        //清除页面上的view
                        binding.layoutExamineApplyMan.llExamineMan.removeView(item_examine_avatar);
                    }
                });
                binding.layoutExamineApplyMan.llExamineMan.addView(item_examine_avatar, index);
                index++;
            } else {
                item_examine_avatar.setTag(i);
                item_examine_avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExamineManBean removeBean = list2.get(Choicelist2.get((int) v.getTag()).getListPostion());
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).getClickNum() > removeBean.getClickNum()) {
                                list2.get(i).setClickNum(list2.get(i).getClickNum() - 1);
                            }
                        }

                        list2.get(Choicelist2.get((int) v.getTag()).getListPostion()).setClickNum(0);
                        //清除页面上的view
                        binding.layoutExamineApplyCopy.llExamineMan.removeView(item_examine_avatar);
                    }
                });
                binding.layoutExamineApplyCopy.llExamineMan.addView(item_examine_avatar, index2);
                index2++;
            }


        }


    }

    /**
     * 获得文件的类型图片
     */
    private int getFileTypeImg(String Extension) {
        if (Extension.contains("jpg")) {
            return R.mipmap.file_jpg;
        } else if (Extension.contains("pdf")) {
            return R.mipmap.file_pdf;
        } else if (Extension.contains("word")) {

        } else if (Extension.contains("xml")) {

        } else if (Extension.contains("png")) {

        }
        return R.mipmap.file_jpg;
    }

    /**
     * 排除qq发送给好友,qq几乎响应所以隐式启动的类型
     */
    public void excludeQQSend(File file) {
        String type = MyIntent.getMIMEType(file);
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setDataAndType(Uri.fromFile(file), type);
        List<ResolveInfo> resInfo = mActivity.getPackageManager().queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_VIEW);
                targeted.setDataAndType(Uri.fromFile(file), type);
                ActivityInfo activityInfo = info.activityInfo;
                // judgments : activityInfo.packageName, activityInfo.name, etc.
                if (activityInfo.packageName.contains("com.tencent.mobileqq")) {
                    continue;
                }
                targeted.setPackage(activityInfo.packageName);
                targetedShareIntents.add(targeted);
            }
            if (targetedShareIntents.size() != 0) {
//                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to Open");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(it);
            } else {
                Toast.makeText(mActivity, "没有可以打开该类型的程序", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


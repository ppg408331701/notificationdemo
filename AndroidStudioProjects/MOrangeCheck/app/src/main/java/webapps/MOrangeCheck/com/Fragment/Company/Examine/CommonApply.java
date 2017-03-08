//package webapps.MOrangeCheck.com.Fragment.Company.Examine;
//
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.qqtheme.framework.picker.FilePicker;
//import jp.wasabeef.glide.transformations.CropCircleTransformation;
//import ppg.com.yanlibrary.fragment.LoadingFragment;
//import ppg.com.yanlibrary.utils.ToastUtil;
//import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
//import ppg.com.yanlibrary.widget.recyclerview.OnItemClickListener;
//import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
//import utils.FileUtils;
//import webapps.MOrangeCheck.com.Bean.ExamineManBean;
//import webapps.MOrangeCheck.com.Bean.FileBean;
//import webapps.MOrangeCheck.com.R;
//import webapps.MOrangeCheck.com.Tool.LT;
//import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
//import webapps.MOrangeCheck.com.Views.ExaminChoiceManDialog;
//import webapps.MOrangeCheck.com.databinding.DialogExamineChioceManBinding;
//import webapps.MOrangeCheck.com.databinding.FragmentCommonApplyBinding;
//import webapps.MOrangeCheck.com.databinding.ItemFileLayoutBinding;
//
///**
// * Created by ppg777 on 2017/3/2.
// */
//
//public class CommonApply extends LoadingFragment implements View.OnClickListener {
//
//    FragmentCommonApplyBinding binding;
//    private List<ExamineManBean> list = new ArrayList<>();
//    private List<ExamineManBean> Choicelist = new ArrayList<>();
//    private List<ExamineManBean> list2 = new ArrayList<>();
//    private List<ExamineManBean> Choicelist2 = new ArrayList<>();
//    private CommonAdapter adapter;
//    private CommonAdapter adapter2;
//    private List<FileBean> fileList = new ArrayList<>();
//
//    public CommonApply() {
//        super(true);
//    }
//
//    @Override
//    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_common_apply, container, false);
//        binding = DataBindingUtil.bind(root);
//        binding.layoutExamineApplyCopy.copyIvAddimg.setOnClickListener(this);
//        binding.layoutExamineApplyMan.manIvAddimg.setOnClickListener(this);
//        binding.layoutCommonApplyEdit.llExiamineUpload.setOnClickListener(this);
//        return root;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.man_iv_addimg:
//                choiceMan(1);
//                break;
//            case R.id.copy_iv_addimg:
//                choiceCopy(2);
//                break;
//            case R.id.ll_exiamine_upload:
//                onFilePicker();
//                break;
//        }
//    }
//
//    //文件选择器//具体可看https://github.com/gzu-liyujiang/AndroidPicker
//    public void onFilePicker() {
//        FilePicker picker = new FilePicker(mActivity, FilePicker.FILE);
//        picker.setTitleText("选择附件");
//        picker.setHeight((int) (picker.getScreenHeightPixels() * 0.75));
//        picker.setShowHideDir(false);
//        picker.setShowUpDir(true);
//
//        //   picker.setAllowExtensions(new String[]{".apk"});
//        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
//            @Override
//            public void onFilePicked(String currentPath) {
//                File file = FileUtils.getFileByPath(currentPath);
//                if (file == null) return;
//                FileBean fileBean = new FileBean();
//                fileBean.setFilePath(currentPath);
//                fileBean.setFileName(FileUtils.getFileName(file));
//                fileBean.setFilesuffix(FileUtils.getFileExtension(file));
//                fileBean.setFileSize(FileUtils.getFileSize2(file));
//                for (FileBean bean : fileList) {
//                    if (bean.getFilePath().equals(currentPath)){
//                        ToastUtil.toast(mActivity,"该附件已存在");
//                        return;
//                    }
//                }
//                fileList.add(fileBean);
//                initFileList();
//            }
//        });
//        picker.show();
//    }
//
//    private void initFileList() {
//        binding.layoutCommonApplyEdit.llAccessory.removeAllViews();
//        for (int i = 0; i < fileList.size(); i++) {
//            final ViewGroup item_file = (ViewGroup) mActivity.getLayoutInflater().
//                    inflate(R.layout.item_file_layout, binding.layoutCommonApplyEdit.llAccessory, false);
//            ItemFileLayoutBinding itemFileLayoutBinding = DataBindingUtil.bind(item_file);
//            itemFileLayoutBinding.tvName.setText(fileList.get(i).getFileName());
//            itemFileLayoutBinding.tvFilesize.setText(fileList.get(i).getFileSize());
//            itemFileLayoutBinding.ivImg.setImageResource(getFileTypeImg(fileList.get(i).getFilesuffix()));
//            itemFileLayoutBinding.ivDelete.setTag(i);
//            itemFileLayoutBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.toast(mActivity,"已删除附件");
//                    fileList.remove((int)v.getTag());
//                    initFileList();
//                }
//            });
//            binding.layoutCommonApplyEdit.llAccessory.addView(item_file);
//        }
//    }
//
//    private List<ExamineManBean> getChoiceData(int flag) {
//        List<ExamineManBean> list = new ArrayList<>();
//        ExamineManBean manBean;
//        for (int i = 0; i < 10; i++) {
//            manBean = new ExamineManBean();
//            manBean.setImg("http://imgsrc.baidu.com/forum/w=580/sign=4be4551c" +
//                    "544e9258a63486e6ac83d1d1/b912c8fcc3cec3fdbec41a0dd488d43f869427cb.jpg");
//            manBean.setName("Mr.李四" + i);
//            manBean.setPostion("研发主管");
//            manBean.setListPostion(i);
//            manBean.setClickNum(0);
//            list.add(manBean);
//        }
//        return list;
//
//    }
//
//
//    /**
//     * 弹出dialog1选择人选
//     */
//    private void choiceMan(final int flag) {
//        if (list == null || list.size() == 0) {
//            list = getChoiceData(flag);
//        }
//        if (adapter == null) {
//            adapter = new CommonAdapter<ExamineManBean>(mActivity, R.layout.item_examine_man, list) {
//
//                @Override
//                public void convert(ViewHolder holder, ExamineManBean s) {
//                    holder.setText(R.id.tv_name, s.getName());
//                    holder.setText(R.id.tv_position, s.getPostion());
//                    holder.setText(R.id.tv_num, "#" + s.getClickNum());
//                    if (s.getClickNum() > 0) {
//                        holder.getView(R.id.tv_num).setVisibility(View.VISIBLE);
//                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.backgroud_theme);
//                    } else {
//                        holder.getView(R.id.tv_num).setVisibility(View.GONE);
//                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.white0);
//                    }
//                    Glide.with(CommonApply.this)
//                            .load(s.getImg())
//                            .crossFade(500)
//                            .bitmapTransform(new CropCircleTransformation(mActivity))
//                            .into((ImageView) holder.getView(R.id.iv_img));
//                }
//            };
//            adapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
//                    ExamineManBean examineManBean = (ExamineManBean) o;
//                    if (examineManBean.getClickNum() > 0) {
//                        for (int i = 0; i < list.size(); i++) {
//                            if (list.get(i).getClickNum() > examineManBean.getClickNum()) {
//                                list.get(i).setClickNum(list.get(i).getClickNum() - 1);
//                            }
//                        }
//                        examineManBean.setClickNum(0);
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        int max = list.get(0).getClickNum();
//                        for (int i = 0; i < list.size(); i++) {
//                            if (max < list.get(i).getClickNum())
//                                max = list.get(i).getClickNum();
//                        }
//
//                        examineManBean.setClickNum(max + 1);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
//                    return false;
//                }
//            });
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//        View dialog = View.inflate(mActivity, R.layout.dialog_examine_chioce_man, null);
//        DialogExamineChioceManBinding chioceManBinding = DataBindingUtil.bind(dialog);
//        chioceManBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        chioceManBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        chioceManBinding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity
//                , LeftPaddingDividerItemDecoration.VERTICAL, 10));
//        chioceManBinding.recyclerView.setAdapter(adapter);
//        final ExaminChoiceManDialog choiceManDialog = new ExaminChoiceManDialog(mActivity, dialog);
//        choiceManDialog.setListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //当已选择的list有数据时,清空除了+号按钮的所有view并清空Choicelist
//                if (Choicelist.size() > 0) {
//                    binding.layoutExamineApplyMan.llExamineMan.removeViews(0,
//                            binding.layoutExamineApplyMan.llExamineMan.getChildCount() - 1);
//                    Choicelist.clear();
//                }
//                //把已选择的对象加入Choicelist
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getClickNum() > 0) {
//                        Choicelist.add(list.get(i));
//                    }
//                }
//                initChoiceLayout(Choicelist, flag);
//                choiceManDialog.dismiss();
//            }
//        });
//        choiceManDialog.show();
//        choiceManDialog.getTv_title().setText("选择审批人");
//
//    }
//
//    /**
//     * 弹出dialog2选择人选
//     */
//    private void choiceCopy(final int flag) {
//        if (list2 == null || list2.size() == 0) {
//            list2 = getChoiceData(flag);
//        }
//        if (adapter2 == null) {
//            adapter2 = new CommonAdapter<ExamineManBean>(mActivity, R.layout.item_examine_man, list2) {
//
//                @Override
//                public void convert(ViewHolder holder, ExamineManBean s) {
//                    holder.setText(R.id.tv_name, s.getName());
//                    holder.setText(R.id.tv_position, s.getPostion());
//                    holder.setText(R.id.tv_num, "#" + s.getClickNum());
//                    if (s.getClickNum() > 0) {
//                        holder.getView(R.id.tv_num).setVisibility(View.VISIBLE);
//                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.backgroud_theme);
//                    } else {
//                        holder.getView(R.id.tv_num).setVisibility(View.GONE);
//                        holder.setBackgroundRes(R.id.item_examine_layout, R.color.white0);
//                    }
//                    Glide.with(CommonApply.this)
//                            .load(s.getImg())
//                            .crossFade(500)
//                            .bitmapTransform(new CropCircleTransformation(mActivity))
//                            .into((ImageView) holder.getView(R.id.iv_img));
//                }
//            };
//            adapter2.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
//                    ExamineManBean examineManBean = (ExamineManBean) o;
//                    if (examineManBean.getClickNum() > 0) {
//                        for (int i = 0; i < list2.size(); i++) {
//                            if (list2.get(i).getClickNum() > examineManBean.getClickNum()) {
//                                list2.get(i).setClickNum(list2.get(i).getClickNum() - 1);
//                            }
//                        }
//                        examineManBean.setClickNum(0);
//                        adapter2.notifyDataSetChanged();
//                    } else {
//                        int max = list2.get(0).getClickNum();
//                        for (int i = 0; i < list2.size(); i++) {
//                            if (max < list2.get(i).getClickNum())
//                                max = list2.get(i).getClickNum();
//                        }
//
//                        examineManBean.setClickNum(max + 1);
//                        adapter2.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
//                    return false;
//                }
//            });
//        } else {
//            adapter2.notifyDataSetChanged();
//        }
//        View dialog = View.inflate(mActivity, R.layout.dialog_examine_chioce_man, null);
//        DialogExamineChioceManBinding chioceManBinding = DataBindingUtil.bind(dialog);
//        chioceManBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        chioceManBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        chioceManBinding.recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity
//                , LeftPaddingDividerItemDecoration.VERTICAL, 10));
//
//        chioceManBinding.recyclerView.setAdapter(adapter2);
//        final ExaminChoiceManDialog choiceManDialog = new ExaminChoiceManDialog(mActivity, dialog);
//        choiceManDialog.setListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Choicelist2.size() > 0) {
//                    binding.layoutExamineApplyCopy.llExamineMan.removeViews(0,
//                            binding.layoutExamineApplyCopy.llExamineMan.getChildCount() - 1);
//                    Choicelist2.clear();
//                }
//                for (int i = 0; i < list2.size(); i++) {
//                    if (list2.get(i).getClickNum() > 0) {
//                        Choicelist2.add(list2.get(i));
//                    }
//                }
//
//                initChoiceLayout(Choicelist2, flag);
//                choiceManDialog.dismiss();
//            }
//        });
//        //  choiceManDialog.setCanceledOnTouchOutside(false);
//        choiceManDialog.show();
//        choiceManDialog.getTv_title().setText("选择抄送人");
//
//    }
//
//    /**
//     * 填充layout
//     *
//     * @param flag
//     */
//    private void initChoiceLayout(final List<ExamineManBean> Paddinglist, final int flag) {
//        //取得插入位置,+号按钮的前一位,由于是线性布局,所以会把+号按钮不断往后挤压
//        int index = binding.layoutExamineApplyMan.llExamineMan.getChildCount() - 1;
//        int index2 = binding.layoutExamineApplyCopy.llExamineMan.getChildCount() - 1;
//        for (int i = 0; i < Paddinglist.size(); i++) {
//            final ViewGroup item_examine_avatar = (ViewGroup) mActivity.getLayoutInflater().
//                    inflate(R.layout.item_examine_avatar, binding.layoutExamineApplyMan.llExamineMan, false);
//            ImageView avatar = (ImageView) item_examine_avatar.findViewById(R.id.iv_avatar);
//            Glide.with(CommonApply.this).load(Paddinglist.get(i).getImg())
//                    .crossFade(500)
//                    .bitmapTransform(new CropCircleTransformation(mActivity))
//                    .into(avatar);
//            if (flag == 1) {
//                item_examine_avatar.setTag(i);
//                item_examine_avatar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //通过已选对象的getListPostion取到list对象,操作对象数字减一
//                        ExamineManBean removeBean = list.get(Choicelist.get((int) v.getTag()).getListPostion());
//                        for (int i = 0; i < list.size(); i++) {
//                            if (list.get(i).getClickNum() > removeBean.getClickNum()) {
//                                list.get(i).setClickNum(list.get(i).getClickNum() - 1);
//                            }
//                        }
//
//                        list.get(Choicelist.get((int) v.getTag()).getListPostion()).setClickNum(0);
//                        //清除页面上的view
//                        binding.layoutExamineApplyMan.llExamineMan.removeView(item_examine_avatar);
//                    }
//                });
//                binding.layoutExamineApplyMan.llExamineMan.addView(item_examine_avatar, index);
//                index++;
//            } else {
//                item_examine_avatar.setTag(i);
//                item_examine_avatar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ExamineManBean removeBean = list2.get(Choicelist2.get((int) v.getTag()).getListPostion());
//                        for (int i = 0; i < list2.size(); i++) {
//                            if (list2.get(i).getClickNum() > removeBean.getClickNum()) {
//                                list2.get(i).setClickNum(list2.get(i).getClickNum() - 1);
//                            }
//                        }
//
//                        list2.get(Choicelist2.get((int) v.getTag()).getListPostion()).setClickNum(0);
//                        //清除页面上的view
//                        binding.layoutExamineApplyMan.llExamineMan.removeView(item_examine_avatar);
//                    }
//                });
//                binding.layoutExamineApplyCopy.llExamineMan.addView(item_examine_avatar, index2);
//                index2++;
//            }
//
//
//        }
//
//
//    }
//
//    private int getFileTypeImg(String Extension) {
//        if (Extension.contains("jpg")) {
//            return R.mipmap.file_jpg;
//        } else if (Extension.contains("pdf")) {
//            return R.mipmap.file_pdf;
//        } else if (Extension.contains("word")) {
//
//        } else if (Extension.contains("xml")) {
//
//        } else if (Extension.contains("png")) {
//
//        }
//        return R.mipmap.file_jpg;
//    }
//}

package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.MultiItemTypeAdapter;
import ppg.com.yanlibrary.widget.recyclerview.base.ViewHolder;
import utils.ConvertUtils;
import utils.StringUtils;
import utils.TimeUtils;
import webapps.MOrangeCheck.com.Bean.ExaminePassBean;
import webapps.MOrangeCheck.com.Bean.FileBean;
import webapps.MOrangeCheck.com.Bean.TimelineBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.Views.LineType;
import webapps.MOrangeCheck.com.Views.TimelineView;
import webapps.MOrangeCheck.com.Views.dialog.ExamineBottomDialog;
import webapps.MOrangeCheck.com.Views.dialog.ExamineListBottomDialog;
import webapps.MOrangeCheck.com.databinding.DialogExaminelistbottomBinding;
import webapps.MOrangeCheck.com.databinding.FragmentExamintitemDetailBinding;
import webapps.MOrangeCheck.com.databinding.ItemFileLayoutBinding;

/**
 * 申请,审批,抄送item的详情
 * Created by ppg777 on 2017/3/3.
 */

public class ExamineItemDetail extends LoadingFragment implements View.OnClickListener {

    FragmentExamintitemDetailBinding binding;
    private List<TimelineBean> list = new ArrayList<>();
    private List<FileBean> fileList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    private TimelineAdapter adapter;
    private int yellow2;
    private int gray5;
    private int orange22;
    private CommonAdapter bottomListAdapter;


    public ExamineItemDetail() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yellow2 = ContextCompat.getColor(mActivity, R.color.yellow2);
        gray5 = ContextCompat.getColor(mActivity, R.color.gray5);
        orange22 = ContextCompat.getColor(mActivity, R.color.orange22);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_examintitem_detail, container, false);
        binding = DataBindingUtil.bind(root);
        binding.llOk.setOnClickListener(this);
        binding.llNo.setOnClickListener(this);
        binding.llPass.setOnClickListener(this);
        binding.llNote.setOnClickListener(this);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        initHolyday();
        initFileLayout();
        initTimeLine();
        return root;
    }


    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        final ExamineBottomDialog bottomDialog;
        switch (v.getId()) {
            case R.id.ll_ok:
                bottomDialog = new ExamineBottomDialog(mActivity) {
                    @Override
                    public void determineTask() {

                    }
                };
                bottomDialog.show();
                bottomDialog.getTv_title().setText("同意");
                break;
            case R.id.ll_no:
                bottomDialog = new ExamineBottomDialog(mActivity) {
                    @Override
                    public void determineTask() {

                    }
                };
                bottomDialog.show();
                bottomDialog.getTv_title().setText("拒绝");
                break;
            case R.id.ll_pass:
                initBottomList();
                break;
            case R.id.ll_note:
                bottomDialog = new ExamineBottomDialog(mActivity) {
                    @Override
                    public void determineTask() {

                    }
                };
                bottomDialog.show();
                bottomDialog.getTv_title().setText("批注");
                break;
        }
    }

    /**
     * 转交,先构造好页面,再传入dialog中,比较麻烦
     */
    private void initBottomList() {
        bottomListAdapter = new CommonAdapter<ExaminePassBean>(mActivity, R.layout.item_examine_man, getChoiceData(1)) {
            @Override
            public void convert(ViewHolder holder, ExaminePassBean passBean, int postion) {
                holder.setText(R.id.tv_name, passBean.getName())
                        .setText(R.id.tv_position, passBean.getPostion())
                        .getView(R.id.tv_num).setVisibility(View.GONE);
                if (passBean.isCheck()) {
                    holder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.iv_check).setVisibility(View.GONE);
                }

            }
        };
        View listbottomdialog = View.inflate(mActivity, R.layout.dialog_examinelistbottom, null);
        DialogExaminelistbottomBinding chioceManBinding = DataBindingUtil.bind(listbottomdialog);
        chioceManBinding.dialogRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        chioceManBinding.dialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        chioceManBinding.dialogRecyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity
                , LeftPaddingDividerItemDecoration.VERTICAL, 2.5f));
        chioceManBinding.dialogRecyclerView.setAdapter(bottomListAdapter);
        bottomListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ExaminePassBean passBean = (ExaminePassBean) bottomListAdapter.getmDatas().get(position);
                if (passBean.isCheck()){
                    passBean.setCheck(false);
                }else {
                    passBean.setCheck(true);
                }


                bottomListAdapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        ExamineListBottomDialog listBottomDialog = new ExamineListBottomDialog(mActivity, listbottomdialog) {
            @Override
            public void determineTask(EditText text) {
                ToastUtil.toast(mActivity, text.getText().toString());
            }
        };
        listBottomDialog.show();
        listBottomDialog.getTv_title().setText("转交");
    }


    private List<ExaminePassBean> getChoiceData(int flag) {
        List<ExaminePassBean> list = new ArrayList<>();
        ExaminePassBean manBean;
        for (int i = 0; i < 10; i++) {
            manBean = new ExaminePassBean();
            manBean.setImg("http://imgsrc.baidu.com/forum/w=580/sign=4be4551c" +
                    "544e9258a63486e6ac83d1d1/b912c8fcc3cec3fdbec41a0dd488d43f869427cb.jpg");
            manBean.setName("Mr.李四" + i);
            manBean.setPostion("研发主管");
            manBean.setCheck(false);
            list.add(manBean);
        }
        return list;

    }


    /**
     * 加载时间线
     */
    private void initTimeLine() {


        TimelineBean timelineBean;
        for (int i = 0; i < 4; i++) {
            timelineBean = new TimelineBean();
            timelineBean.setmName("吴彦祖");
            timelineBean.setmDate(TimeUtils.getFriendlyTimeSpanByNow(System.currentTimeMillis()));
            timelineBean.setmStatus(0);
            timelineBean.setmMessage("注意把关相关工作");
            if (i == 2) {
                timelineBean.setmStatus(2);
            }
            if (i == 3) {
                timelineBean.setmStatus(1);
            }
            list.add(timelineBean);
        }
        adapter = new TimelineAdapter();

        binding.recyclerView.setAdapter(adapter);
    }

    /**
     * 请假时间
     */
    private void initHolyday() {
        binding.llInsert.setPadding(ConvertUtils.dp2px(15), 0, ConvertUtils.dp2px(15), ConvertUtils.dp2px(15));
        final ViewGroup item_holiday = (ViewGroup) mActivity.getLayoutInflater().
                inflate(R.layout.layout_holiday_item, binding.llInsert, false);
        TextView tv_holiday_text = (TextView) item_holiday.findViewById(R.id.tv_holiday_text);
        TextView tv_holiday_time = (TextView) item_holiday.findViewById(R.id.tv_holiday_time);
        long startTime = Calendar.getInstance().getTimeInMillis();
        tv_holiday_text.setText(TimeUtils.millis2String(startTime, TimeUtils.E_PATTERN) + "(" + TimeUtils.getWeek(startTime) +
                ") ━ " + TimeUtils.millis2String(startTime, TimeUtils.E_PATTERN) + "(" + TimeUtils.getWeek(startTime) + ")");

        if (startTime > 0L && startTime > 0L) {
            String TimeSpan = "共 " + TimeUtils.getFitTimeSpanPpg(startTime, startTime + 1000000, 3);
            tv_holiday_time.setText(StringUtils.highlight(TimeSpan, "[0-9]*"));
        }
        binding.llInsert.addView(item_holiday);
    }




    /**
     * 附件列表
     */
    private void initFileLayout() {
        FileBean fileBean;
        for (int i = 0; i < 4; i++) {
            fileBean = new FileBean();
            fileBean.setFileName("askahsfawsnfkqa.jpg");
            fileBean.setFilesuffix("jpg");
            fileBean.setFilePath("/askahsfawsnfkqa");
            fileBean.setFileSize("10.02MB");
            fileList.add(fileBean);
        }
        if (fileList.size() > 0) {
            binding.llInsert.setPadding(ConvertUtils.dp2px(15), 0, ConvertUtils.dp2px(15), ConvertUtils.dp2px(15));
        }
        for (int i = 0; i < fileList.size(); i++) {
            final ViewGroup item_file = (ViewGroup) mActivity.getLayoutInflater().
                    inflate(R.layout.item_file_layout, binding.llInsert, false);
            ItemFileLayoutBinding itemFileLayoutBinding = DataBindingUtil.bind(item_file);
            itemFileLayoutBinding.tvName.setText(fileList.get(i).getFileName());
            itemFileLayoutBinding.tvFilesize.setText(fileList.get(i).getFileSize());
            itemFileLayoutBinding.ivImg.setImageResource(getFileTypeImg(fileList.get(i).getFilesuffix()));
            itemFileLayoutBinding.ivDelete.setVisibility(View.GONE);
            itemFileLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            binding.llInsert.addView(item_file);
        }
    }

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


    class TimelineAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(mActivity, LayoutInflater.from(mActivity)
                    .inflate(R.layout.item_timeline, parent, false));
            return holder;
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setText(R.id.tv_name, (list.get(position)).getmName())
                    .setText(R.id.tv_date, (list.get(position)).getmDate())
                    .setText(R.id.tv_message, (list.get(position)).getmMessage());
            TimelineView timelineView = holder.getView(R.id.time_marker);
            timelineView.initLine(holder.getItemViewType());
            if ((list.get(position)).getmStatus() == 0) {
                holder.setText(R.id.tv_state, "已同意").setTextColor(R.id.tv_state, yellow2);
                //当状态为已同意时,把标记设为实心圆
                timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.marker));
                //当状态为已同意并且是第一个时,把下半部的线设为橙色
                if (holder.getItemViewType() == LineType.BEGIN)
                    timelineView.setEndLine(yellow2, LineType.NORMAL);
                if (holder.getItemViewType() == LineType.NORMAL) {
                    //当当状态为已同意并且为中间的item时,上上下的线都设为橙色
                    timelineView.setEndLine(yellow2, LineType.NORMAL);
                    timelineView.setStartLine(yellow2, LineType.NORMAL);
                }
            } else if ((list.get(position)).getmStatus() == 1) {
                holder.setText(R.id.tv_state, "等待审核").setTextColor(R.id.tv_state, gray5);
                timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.shape_oval_grey));
            } else {
                holder.setText(R.id.tv_state, "被拒绝").setTextColor(R.id.tv_state, orange22);
                timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.shape_oval_grey));
            }
            if (holder.getAdapterPosition() == adapter.getItemCount() - 1) {
                holder.getView(R.id.fl_square_bottom).setBackgroundResource(R.color.white0);
            }

        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    //        adapter = new CommonAdapter<TimelineBean>(mActivity, R.layout.item_timeline, list) {
//
//            @Override
//            public int getItemViewType(int position) {
//                return TimelineView.getTimeLineViewType(position, getItemCount());
//            }
//
//            @Override
//            public void convert(ViewHolder holder, TimelineBean timelineBean, int positon) {
//                holder.setText(R.id.tv_name, timelineBean.getmName())
//                        .setText(R.id.tv_date, timelineBean.getmDate())
//                        .setText(R.id.tv_message, timelineBean.getmMessage());
//                TimelineView timelineView = holder.getView(R.id.time_marker);
//                timelineView.initLine(holder.getItemViewType());
//                if (timelineBean.getmStatus() == 0) {
//                    holder.setText(R.id.tv_state, "已同意").setTextColor(R.id.tv_state, yellow2);
//                    //当状态为已同意时,把标记设为实心圆
//                    timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.marker));
//                    //当状态为已同意并且是第一个时,把下半部的线设为橙色
//                    if (holder.getItemViewType() == LineType.BEGIN)
//                        timelineView.setEndLine(yellow2, LineType.NORMAL);
//                    if (holder.getItemViewType() == LineType.NORMAL) {
//                        //当当状态为已同意并且为中间的item时,上上下的线都设为橙色
//                        timelineView.setEndLine(yellow2, LineType.NORMAL);
//                        timelineView.setStartLine(yellow2, LineType.NORMAL);
//                    }
//                } else if (timelineBean.getmStatus() == 1) {
//                    holder.setText(R.id.tv_state, "等待审核").setTextColor(R.id.tv_state, gray5);
//                    timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.shape_oval_grey));
//                } else {
//                    holder.setText(R.id.tv_state, "被拒绝").setTextColor(R.id.tv_state, orange22);
//                    timelineView.setMarker(ContextCompat.getDrawable(mActivity, R.drawable.shape_oval_grey));
//                }
//                if (holder.getAdapterPosition() == adapter.getItemCount() - 1) {
//                    holder.getView(R.id.fl_square_bottom).setBackgroundResource(R.color.white0);
//                }
//
//            }
//        };
}

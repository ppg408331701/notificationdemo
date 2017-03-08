package webapps.MOrangeCheck.com.Fragment.Company.Examine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.widget.recyclerview.CommonAdapter;
import ppg.com.yanlibrary.widget.recyclerview.ViewHolder;
import utils.ConvertUtils;
import utils.IntentUtils;
import utils.TimeUtils;
import webapps.MOrangeCheck.com.Bean.FileBean;
import webapps.MOrangeCheck.com.Bean.TimelineBean;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Views.LineType;
import webapps.MOrangeCheck.com.Views.TimelineView;
import webapps.MOrangeCheck.com.databinding.FragmentExamintitemDetailBinding;
import webapps.MOrangeCheck.com.databinding.ItemFileLayoutBinding;

/**
 * 申请,审批,抄送item的详情
 * Created by ppg777 on 2017/3/3.
 */

public class ExamineItemDetail extends LoadingFragment {

    FragmentExamintitemDetailBinding binding;
    private List<TimelineBean> list = new ArrayList<>();
    private List<FileBean> fileList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    private CommonAdapter adapter;
    private int yellow2;
    private int gray5;
    private int orange22;


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
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        initFileLayout();
        initTimeLine();
        return root;
    }


    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();

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
        adapter = new CommonAdapter<TimelineBean>(mActivity, R.layout.item_timeline, list) {

            @Override
            public int getItemViewType(int position) {
                return TimelineView.getTimeLineViewType(position, getItemCount());
            }

            @Override
            public void convert(ViewHolder holder, TimelineBean timelineBean) {
                holder.setText(R.id.tv_name, timelineBean.getmName())
                        .setText(R.id.tv_date, timelineBean.getmDate())
                        .setText(R.id.tv_message, timelineBean.getmMessage());
                TimelineView timelineView = holder.getView(R.id.time_marker);
                timelineView.initLine(holder.getItemViewType());
                if (timelineBean.getmStatus() == 0) {
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
                } else if (timelineBean.getmStatus() == 1) {
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
        };
        binding.recyclerView.setAdapter(adapter);
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
}
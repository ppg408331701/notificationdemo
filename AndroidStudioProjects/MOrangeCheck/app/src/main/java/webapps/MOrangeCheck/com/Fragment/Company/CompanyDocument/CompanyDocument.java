package webapps.MOrangeCheck.com.Fragment.Company.CompanyDocument;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import utils.SDCardUtils;
import utils.SnackbarUtils;
import webapps.MOrangeCheck.com.Application.AppApplication;
import webapps.MOrangeCheck.com.Bean.DocumentBean;
import webapps.MOrangeCheck.com.Constants.Constans;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.Tool.MyIntent;
import webapps.MOrangeCheck.com.Views.dialog.DownloadDialogUtils;

/**
 * Created by ppg777 on 2017/3/23.
 */

public class CompanyDocument extends LoadingFragment {

    String url = "http://www.utchina.com/package/ut.apk";

    private View root;
    private RecyclerView recyclerView;
    private ArrayList<DocumentBean> dataList = new ArrayList<>();
    private BaseQuickAdapter<DocumentBean, BaseViewHolder> adapter;
    DecimalFormat format = new DecimalFormat("#%");

    public CompanyDocument() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                LeftPaddingDividerItemDecoration.VERTICAL, 0));
        initAdapter();
        LT.ee(this.getTag());
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        if (loadingContent()) {
            setPullAction(getData());
        }
    }

    private void initAdapter() {
        adapter = new BaseQuickAdapter<DocumentBean, BaseViewHolder>(R.layout.item_document, dataList) {

            @Override
            protected void convert(BaseViewHolder helper, DocumentBean item) {
                helper.setText(R.id.tv_name, item.getTitle())
                        .setText(R.id.tv_filesize, item.getSize());
                if (item.isDownload()) {
                    helper.getView(R.id.iv_img).setVisibility(View.GONE);
                    helper.getView(R.id.tv_progress).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_progress, item.getProgress());
                } else {
                    helper.getView(R.id.iv_img).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_progress).setVisibility(View.GONE);

                }
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, final View view, final int position) {
                final DocumentBean documentBean = (DocumentBean) adapter.getItem(position);
                if (AppApplication.getSpUtils().getBoolean("showDownload", true)) {
                    DownloadDialogUtils dialogUtils = new DownloadDialogUtils(mActivity, documentBean.getTitle() +
                            "\n大小:" + documentBean.getSize()) {
                        @Override
                        public void determineTask() {
                            dowanloadBefore(documentBean, position);
                        }
                    };
                    dialogUtils.show();
                } else {
                    dowanloadBefore(documentBean, position);
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void dowanloadBefore(DocumentBean bean, int position) {
        if (SDCardUtils.isSDCardEnable()) {
            DonwloadDocument(bean, position);
        } else {
            SnackbarUtils.showShortSnackbar(root, "读取不到储存目录,无法下载", ContextCompat.getColor(mActivity,
                    R.color.orange_primary_dark), ContextCompat.getColor(mActivity,
                    R.color.orange_primary_text));
        }
    }

    private void DonwloadDocument(final DocumentBean bean, final int position) {
        OkGo.get(url)
                .tag(this)
                .cacheKey(this.getTag())
                //文件下载时，可以指定下载的文件目录和文件名
                .execute(new FileCallback(SDCardUtils.getSDCardPath() + Constans.MYFOLDER, bean.getTitle()) {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        bean.setDownload(true);
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        ToastUtil.toast(mActivity, file.getPath());
                        // excludeQQSend(FileUtils.getFileByPath(file.getPath()));
                        bean.setDownload(false);
                        if (isCurrentListViewItemVisible(position)) {
                            BaseViewHolder helper = getViewHolder(position);
                            helper.getView(R.id.iv_img).setVisibility(View.VISIBLE);
                            helper.getView(R.id.tv_progress).setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        bean.setDownload(false);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        bean.setProgress(format.format(progress));
                        bean.setDownload(true);
                        if (isCurrentListViewItemVisible(position)) {
                            BaseViewHolder holder = getViewHolder(position);
                            holder.setText(R.id.tv_progress, bean.getProgress());
                        }
                    }
                });
    }

    private BaseViewHolder getViewHolder(int position) {
        return (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            strings.add(String.valueOf(2030) + i);
        }
        return strings;
    }


    private void setPullAction(List<String> comingslist) {
        dataList = new ArrayList<>();
        DocumentBean documentBean;
        for (int i = 0; i < comingslist.size(); i++) {
            documentBean = new DocumentBean();
            String name0 = comingslist.get(i).toString();
            documentBean.setTitle(i + "让我发送flkaksfasv按时.apk");
            documentBean.setSize(name0);
            dataList.add(documentBean);
        }
        if (dataList.size() > 0) {
            adapter.setNewData(dataList);
        } else {
            adapter.setEmptyView(R.layout.empty_page, recyclerView);
        }

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

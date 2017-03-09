package ppg.com.yanlibrary.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import ppg.com.yanlibrary.R;
import ppg.com.yanlibrary.activity.BaseTopBarActivity;

import ppg.com.yanlibrary.widget.SweetAlert.SweetAlertDialog;
import ppg.com.yanlibrary.widget.progressbar.RotateLoading;
import utils.ConvertUtils;
import utils.ScreenUtils;

/**
 * 提供异步加载功能
 *
 * @author jie.yang
 */
@SuppressLint("ParcelCreator")
public abstract class LoadingFragment extends Fragment {

    protected  BaseTopBarActivity mActivity;


    protected RotateLoading mProgressBar = null;
    protected View mContent = null;
    protected FrameLayout mRoot = null;
    private volatile boolean isStop = false;
    private boolean launchAnimation = false;
    FrameLayout frameLayout;

    LayoutInflater mInflater;
    ViewGroup mContainer;
    Bundle mSavedInstanceState;

    CreateViewCallBack mCreateViewCallBack;

    private boolean mMomentInit = true;
    private boolean mDelayAsyn = false;
    private SweetAlertDialog dialog;

//	private boolean isHide = false;

    public View getRoot() {
        return mRoot;
    }

    public LoadingFragment(boolean momentInit) {
        mMomentInit = momentInit;
    }

    public void setDelayAsyn(boolean delayAsyn) {
        mDelayAsyn = delayAsyn;
    }

    public void setLaunchAnimation(boolean launchAnimation) {
        this.launchAnimation = launchAnimation;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseTopBarActivity) context;
    }

    public void setCreateViewCallBack(CreateViewCallBack createViewCallBack) {
        mCreateViewCallBack = createViewCallBack;
    }

    public static interface CreateViewCallBack {
        public void onCreateViewCallBack(View root);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        mSavedInstanceState = savedInstanceState;
        // 方案一，此方案在系统4.0以上正确，但是2.3出现BUG，估计是不能重写layout
        // 添加加载条 , 强制把加载条设置到容器中间
        /* View root = mRoot = onLoadingCreateView(inflater, container, savedInstanceState);
           ProgressBar bar = mProgressBar = new ProgressBar(getActivity()) {
			@Override
			public void layout(int l, int t, int r, int b) {
				int rw = mRoot.getMeasuredWidth() >> 1;
				int size = DensityUtil.dip2px(getActivity(), 20);
				int rh = mRoot.getMeasuredHeight() >> 1;
				super.layout(rw - size, rh - size,
						rw + size, rh + size);
			}
		};

		if(root instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) root;
			group.addView(bar);
		}*/


        // 方案二，兼容各个版本
        FrameLayout root = mRoot = new FrameLayout(getActivity()) {
            //这段代码不会立马执行
            protected void onLayout(boolean changed, int left, int top,
                                    int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                View v = getChildAt(getChildCount() - 1);
                int rw = getMeasuredWidth() >> 1;
                //得到当前
                int size = ConvertUtils.dp2px(30);
                //>>移位 移动一位就是减半  也就是得到当前laoout的一般宽度
                int rh = getMeasuredHeight() >> 1;
                //定制 进度条的位置
                v.layout(rw - size, rh - size,
                        rw + size, rh + size);
            }
        };
        root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        root.setBackgroundColor(ContextCompat.getColor(mActivity,R.color.backgroud_theme));

        mProgressBar = new RotateLoading(getActivity());
        mProgressBar.start();
//        }
        // 立刻初始化内容数据
        if (mMomentInit) {
            //子类实现onLoadingCreateView
            View layout = mContent = onLoadingCreateView(inflater, container, savedInstanceState);
            root.addView(layout);
            mProgressBar.setVisibility(View.GONE);
            if (launchAnimation) {
                mContent.setVisibility(View.INVISIBLE);
                mContent.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_show));
                new Handler() {
                    public void dispatchMessage(Message msg) {
                        if (!isStop) {
                            loadingAnimation(mContent);
                        }
                    }

                    ;
                }.sendMessage(new Message());
            }
        }
        //加载网络错误时的emptyDialog
        initEmptyDialog();
        //加载网络错误时或者空页面的emptyPage
        frameLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams f = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout.setVisibility(View.GONE);
        //加载网络错误时或者空页面的布局
        View bt_layout = mInflater.inflate(R.layout.load_button, null);
        View empty_page = mInflater.inflate(R.layout.empty_page, null);
        //加入布局后才能使用
        frameLayout.addView(bt_layout);
        frameLayout.addView(empty_page);
        //找到按钮
        View bt = frameLayout.findViewById(R.id.tv_load_more);
//        FrameLayout.LayoutParams btf = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        btf.gravity = Gravity.CENTER;
//        bt.setLayoutParams(btf);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorPageClick(v);
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                onEmptyDialogCancelClick();
            }
        });
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                onEmptyDialogConfirmClick();
            }
        });
        root.addView(frameLayout, f);
        root.addView(mProgressBar);
        //requestData();
        if (!mDelayAsyn)
            //默认调用这个
            onCreateViewRequestData();
        if (mCreateViewCallBack != null)
            mCreateViewCallBack.onCreateViewCallBack(root);
        return root;
    }

    public void executeDelayAsyn() {
        if (mDelayAsyn) {
            onCreateViewRequestData();
            mDelayAsyn = false;
        }
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(hidden) {
//            ImageLoader.createImageLoader(getActivity()).clearMemoryCache();
//            ToastUtil.toast2_bottom(getActivity(), "!!");
//        }
//    }

    public boolean isDestroyView() {
        return isStop;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isStop = true;
        mContent = null;
        mInflater = null;
        mContainer = null;
        mSavedInstanceState = null;
        mRoot = null;
    }

    public boolean isLoadingContent() {
        return mContent != null ? true : false;
    }

    protected void onErrorPageClick(View view) {
        ErrorPageHide();
    }

    protected void onEmptyDialogCancelClick() {
        emptyDialogHide();
    }

    protected void onEmptyDialogConfirmClick() {
        emptyDialogHide();
    }

    /**
     * 网络错误时的页面
     */
    public void ErrorPageShow() {
        frameLayout.findViewById(R.id.emptypage).setVisibility(View.GONE);
        frameLayout.findViewById(R.id.error_page).setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void ErrorPageHide() {
        frameLayout.findViewById(R.id.emptypage).setVisibility(View.GONE);
        frameLayout.findViewById(R.id.error_page).setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        frameLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_hide));
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_show));
    }

    /**
     * 空页面
     */
    public void emptyPageShow() {
        frameLayout.findViewById(R.id.error_page).setVisibility(View.GONE);
        frameLayout.findViewById(R.id.emptypage).setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void emptyPageHide() {
        frameLayout.findViewById(R.id.error_page).setVisibility(View.GONE);
        frameLayout.findViewById(R.id.emptypage).setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        frameLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_hide));
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_show));
    }


    public void initEmptyDialog() {
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE, false);
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.backgroud_theme));
        dialog.setCustomImage(R.drawable.wifi);
        dialog.setCancelText("取消");
        dialog.setTitleText("网络连接异常");
        dialog.setContentText("是否重试?");
        dialog.setConfirmText("是的");
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 网络错误时的对话框
     */
    public void emptyDialogShow() {
        dialog.show();

        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_hide));
    }

    public void emptyDialogHide() {
        dialog.dismiss();
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_hide));
    }

    public boolean loadingContent() {
        if (!isStop) {
            if (mContent == null) {
                mContent = onLoadingCreateView(mInflater, mContainer, mSavedInstanceState);
                mRoot.addView(mContent, 0);
            }
            mContent.setVisibility(View.VISIBLE);
            mContent.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_show));
            mProgressBar.setVisibility(View.GONE);
            return true;
        } else
            return false;
    }



    public boolean loadingWithAnimationContent() {
        Log.v("abc", "mContent");
        if (!isStop) {
            if (mContent == null) {
                Log.v("abc", "mContent");
                mContent = onLoadingCreateView(mInflater, mContainer, mSavedInstanceState);
                mRoot.addView(mContent, 0);
                new Handler() {
                    public void dispatchMessage(Message msg) {
                        if (!isStop) {
                            loadingAnimation(mContent);
                        }
                    }

                    ;
                }.sendMessage(new Message());
            }
            mContent.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.GONE);
            return true;
        } else
            return false;
    }

    protected void loadingAnimation(View content) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 如果是Android 5.0 就加入水波纹过场特效
//            content.setVisibility(View.VISIBLE);
//            Animator animator = ViewAnimationUtils.createCircularReveal(content, content.getWidth() / 2, content.getHeight() / 2,
//                    0, content.getWidth()/ 2);
//            animator.setInterpolator(new AccelerateInterpolator());
//            animator.setDuration(200);
//            animator.start();
//        }else {
        content.setVisibility(View.VISIBLE);
        AlphaAnimation alpha = new AlphaAnimation(0.2f, 1f);
        alpha.setDuration(200);
        content.startAnimation(alpha);
//        }
    }

    public boolean restartLoadingContent() {
        if (!isStop) {
            if (mContent != null)
                mRoot.removeView(mContent);
            mContent = onLoadingCreateView(mInflater, mContainer, mSavedInstanceState);
            mRoot.addView(mContent, 0);
            mContent.setVisibility(View.VISIBLE);
            mContent.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_show));
            mProgressBar.setVisibility(View.GONE);
            return true;
        } else
            return false;
    }

    public void showProgressBar() {
        if (mContent != null && mProgressBar != null) {
            mContent.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }


    private ProgressDialog progressDialog;

    /**
     * 简单的进度启动与关闭
     *
     * @param message
     */
    protected void progressStart(String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            //((Activity) progressDialog.getContext()).getWindow().setLayout(50, 50);
        }
    }

    protected void progressClose() {
        if (null == progressDialog)
            return;
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public abstract View onLoadingCreateView(LayoutInflater inflater, ViewGroup container,
                                             Bundle savedInstanceState);

    /**
     * 可以在这个方法请求网络数据，这个方法会在fragment第一次创建调用
     */
    protected void onCreateViewRequestData() {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

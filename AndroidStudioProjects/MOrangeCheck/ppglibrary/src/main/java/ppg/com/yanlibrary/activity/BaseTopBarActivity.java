package ppg.com.yanlibrary.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.R;
import ppg.com.yanlibrary.utils.SystemBarTintManager;
import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.widget.TopBarLayout;
import xprogressdialog.XProgressDialog;

/**
 * Created by ppg on 2016/9/23.
 */

public abstract class BaseTopBarActivity extends BaseActvity {
    public static final String INTENT_TITLE_KEY = "title";
    public static final String INTENT_SHOW_MENU_KEY = "show_menu";
    public static final String INTENT_FRAGMENT_INDEX_KEY = "fragment_index";

    private long timeDValue = 0; // 计算时间差值，判断是否需要退出

    private BaseTopBarActivity.KeyDownListener listener;
    private TopBarLayout topBar;
    private FrameLayout viewRoot = null;
    private View mTopBarRoot = null;
    protected XProgressDialog xProgressDialog = null;

    private BaseTopBarActivity.FunctionType mFunctionType = BaseTopBarActivity.FunctionType.single;

    private List<String> fragmentTag = new ArrayList<String>();

    //	private Toolbar toolbar;
    public void setTopBarTitle(String title) {
        topBar.setTopBarTitle(title);
    }

    protected abstract void onCreateView(TopBarLayout topBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_top_bar);

        if (xProgressDialog == null) {
            xProgressDialog = new XProgressDialog(this, 2);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.backgroud_theme);
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
            findViewById(R.id.top_bar_root).setPadding(0, config.getStatusBarHeight(), 0, 0);
        }


        initToolbar();


        if (mFunctionType == BaseTopBarActivity.FunctionType.single) {
            installSimpleFragment(getIntent().getIntExtra(INTENT_FRAGMENT_INDEX_KEY, -1));
            defaultFragmentInited(getSupportFragmentManager().findFragmentById(R.id.detail_container), 0);
        } else if (mFunctionType == BaseTopBarActivity.FunctionType.multilevel) {
            int index = 0;
            defaultFragmentInited(getSupportFragmentManager().findFragmentById(R.id.detail_container), index);
        }
    }

    /**
     * 是否显示topbar下面的阴影,默认显示
     *
     * @param flag
     */
    public void showShadow(boolean flag) {
        View detailTopBarShadow = findViewById(R.id.detail_top_bar_shadow);
        detailTopBarShadow.setVisibility(flag ? View.VISIBLE : View.GONE);

    }

    protected void installSimpleFragment(int index) {
    }

    /**
     * 当fragment加载完成后的会调用这个方法
     *
     * @param fragment
     * @param index
     */
    protected void defaultFragmentInited(Fragment fragment, int index) {
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private void initToolbar(/*Toolbar toolbar*/) {
        // 初始化顶部条
        View topBarRoot = mTopBarRoot = findViewById(R.id.detail_top_bar);
        topBar = new TopBarLayout(topBarRoot);
        // 设置标题
        TextView title = (TextView) topBarRoot.findViewById(R.id.NavigateTitle);
        if (getIntent() != null) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra(INTENT_TITLE_KEY))) {
                title.setText(getIntent().getStringExtra(INTENT_TITLE_KEY));
            }
        }
        onCreateView(topBar);
    }

    public void SetTitleTextColor(int color) {
        // 设置标题
        TextView title = (TextView) findViewById(R.id.detail_top_bar).findViewById(R.id.NavigateTitle);
        title.setTextColor(color);
    }

    /**
     * 隐藏头布局
     *
     * @param ishide
     */
    public void SettingTopHide(boolean ishide) {
        findViewById(R.id.detail_top_bar).setVisibility(View.GONE);
//        findViewById(R.id.detail_top_bar_shadow).setVisibility(View.GONE);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.detail_container);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        frameLayout.setLayoutParams(layoutParams);

    }

    /**
     * 显示头布局
     */
    public void SettingTopShow() {
        findViewById(R.id.detail_top_bar).setVisibility(View.VISIBLE);
//        findViewById(R.id.detail_top_bar_shadow).setVisibility(View.VISIBLE);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.detail_container);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.top_bar_h), 0, 0);
        frameLayout.setLayoutParams(layoutParams);
    }


    public FrameLayout getViewRoot() {
        if (viewRoot == null)
            viewRoot = (FrameLayout) findViewById(R.id.top_bar_root);
        return viewRoot;
    }

    public void setFunctionModel(BaseTopBarActivity.FunctionType functionModel) {
        mFunctionType = functionModel;
    }

    public TopBarLayout getTopBar() {
        return topBar;
    }

    public void showTopBarOnlyBack() {
        topBar.showOnlyBack(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BaseTopBarActivity.this.setResult(Activity.RESULT_CANCELED);
                BaseTopBarActivity.this.finish();
            }
        });
    }

    public void HideOperation_LR() {
        topBar.HideOperation_LR();
    }

    public void showTopBarOnlyBack(View.OnClickListener backClickListener) {
        topBar.showOnlyBack(backClickListener);
    }

    public void setKeyDownListener(BaseTopBarActivity.KeyDownListener Listener) {
        this.listener = Listener;
    }

    public void showXProgerssDialog(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            xProgressDialog.setMessage(msg);
            xProgressDialog.setCancelOutsideableAble(false);
        }
        xProgressDialog.show();
    }

    public void showXProgerssDialog() {
        xProgressDialog.show();
    }

    public void showXProgerssNoMissDialog() {
        xProgressDialog.setCancelableAble(false);
        xProgressDialog.show();
    }

    public void dismissXProgerssDialog() {
        xProgressDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mFunctionType == BaseTopBarActivity.FunctionType.single) {
            boolean isGo = false;
            if (keyDownProxy != null) {
                isGo = keyDownProxy.onKeyDown(keyCode, event);
                if (isGo)
                    return true;
            }

            boolean ret = false;
            if (listener != null)
                ret = listener.onKeyDown(keyCode, event);
            if (ret)
                return true;
            else
                return super.onKeyDown(keyCode, event);
        } else if (mFunctionType == BaseTopBarActivity.FunctionType.multilevel) {
            boolean isGo = false;
            if (keyDownProxy != null) {
                isGo = keyDownProxy.onKeyDown(keyCode, event);
                if (isGo)
                    return true;
            }

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // 判断退出
                if (timeDValue == 0) {
                    ToastUtil.toast(this,
                            getResources().getString(R.string.exit_hint));
                    timeDValue = System.currentTimeMillis();
                    return true;
                } else {
                    timeDValue = System.currentTimeMillis() - timeDValue;
                    if (timeDValue >= 1500) { // 大于1.5秒不处理。
                        timeDValue = 0;
                        return true;
                    } else {// 退出应用
                        // 完成第一次启动
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                }
            }
            return super.onKeyDown(keyCode, event);
        } else
            return super.onKeyDown(keyCode, event);
    }

    public static interface KeyDownListener {
        public boolean onKeyDown(int keyCode, KeyEvent event);
    }

    private void clearAllFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        int size = fragmentTag.size();
        for (int i = 0; i < size; ++i)
            ft.remove(manager.findFragmentByTag(fragmentTag.get(i)));
        ft.commitAllowingStateLoss();
    }

    protected void replaceFragment(Fragment fragment) {
        String tag = fragment.toString();
        FragmentManager manager = BaseTopBarActivity.this.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.detail_container, fragment, tag);
        ft.commitAllowingStateLoss();
        fragmentTag.add(tag);
        //必须在最后调用commit()
        //如果在同一个container中添加了多个fragments，添加的顺序决定了他们在view层级中显示的顺序
        //
        //如果你在执行一个移除fragment操作的事务时不调用addToBackStack()。那么当这个transaction被提交后fragment会被销毁，并且用户不可能回退回去。
        //相反，如果当移除fragment时，你调用addToBackStack()，那么这个fragment会stopped，并且如果用户导航回去它会resumed
        //小提示：对于每一个fragment的事务，在commit()之前通过调用setTransition()，你可以使用一个过渡动画
        //
        //调用commit()并不是马上就执行这次事务，恰恰相反，一旦activity的UI线程有能力去完成，FragmentTransaction就把这次提交列入计划到activity的UI线程运行
        //
        //如果必要，不管怎样，你可以从你的UI线程调用executePendingTransactions()来通过commit()立即执行提交了的transaction。通常这样做并不是必须的，除非transaction是其他线程工作的依赖
        //
        //警告：只有在activity之前（当用户离开这个activity时）你可以用commit()提交一个transaction保存他的状态
        //如果你尝试在这个时间点之后commit，将会收到一个异常。这是因为如果activity需要恢复，在commit之后的state可能会丢失。在你觉得可以丢失这次commit的情况下，可以使用commitAllowingStateLoss()
        manager.executePendingTransactions();
    }

    /**
     * 用于设置页面模式
     */
    public enum FunctionType {
        single,
        multilevel
    }
}

package ppg.com.yanlibrary.widget;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ppg.com.yanlibrary.R;
import ppg.com.yanlibrary.utils.SupportUtil_SDK21;


/**
 * @author jie.yang
 */
public class TopBarLayout {

    //	private RelativeLayout mContainer;
    private View mRoot;
    private View mPrePanel;

    View viewR1;
    View viewR2;
    ImageView viewL1;
    View viewL2;
    View viewR3;
    View viewR4;
    ImageView imageView;

    public ImageView getViewL1() {
        return viewL1;
    }



    public void hideBack() {
        viewL1.setVisibility(View.GONE);

    }

    public View getRoot() {
        return mRoot;
    }

    public TopBarLayout(View root) {
        mRoot = root;
        viewR2 = mRoot.findViewById(R.id.NavigateOperation_R2);
        viewR4 = mRoot.findViewById(R.id.NavigateOperation_R4);
        viewL1 = (ImageView)mRoot.findViewById(R.id.NavigateOperation_L1);
        viewL2 = mRoot.findViewById(R.id.NavigateOperation_L2);
        viewR3 = mRoot.findViewById(R.id.NavigateOperation_R3);

    }

    public View getViewL2() {
        return viewL2;
    }

    public void setBackgroundColor(int color) {
        mRoot.setBackgroundColor(color);
    }

    public void setBackground(int rid) {
        mRoot.setBackgroundResource(rid);
    }

    public RelativeLayout getContainer() {
        return (RelativeLayout) mRoot;
    }

    /**
     * 只显示回退按钮
     *
     * @return
     */
    public View showOnlyBack(OnClickListener listener) {
        View view = mRoot.findViewById(R.id.NavigateBackLinearLayout);
        view.setVisibility(View.VISIBLE);
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }

    /**
     * 左边按钮的图片和回调
     *
     * @return
     */
    public View getOperationLeftView1(int SID, OnClickListener listener) {
        View view = mRoot.findViewById(R.id.NavigateBackLinearLayout);
        ImageView imview = (ImageView) mRoot.findViewById(R.id.NavigateOperation_L1);
        imview.setImageResource(SID);
        view.setVisibility(View.VISIBLE);
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }

    /**
     * 右边按钮的图片和回调
     *
     * @return
     */
    public View getOperationRightView1(int SID, String text, int color, OnClickListener listener) {
        View view = mRoot.findViewById(R.id.NavigateRightLinearLayout);
        ImageView imview = (ImageView) mRoot.findViewById(R.id.NavigateOperation_R5);
        imview.setImageResource(SID);
        TextView textView = (TextView) mRoot.findViewById(R.id.NavigateOperation_R6);
        textView.setText(text);
        textView.setTextColor(color);
        view.setVisibility(View.VISIBLE);
        //view.setOnClickListener(listener);
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }


    public void hideTopBar() {
        mRoot.setVisibility(View.GONE);
    }

    public void showTopBar() {
        mRoot.setVisibility(View.VISIBLE);
    }


    public void HideOperation_LR() {
        mRoot.setVisibility(View.VISIBLE);
        viewR3.setVisibility(View.GONE);
        viewR1.setVisibility(View.GONE);
        viewR2.setVisibility(View.GONE);
        viewL1.setVisibility(View.GONE);
        viewL2.setVisibility(View.GONE);
        removePrePanel();
    }

    public void hideTopBarTitle() {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateTitle);
        view.setVisibility(View.GONE);
    }

    public void showTopBarTitle() {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateTitle);
        view.setVisibility(View.VISIBLE);
    }

    public void setTopBarTitle(String title) {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateTitle);
        view.setVisibility(View.VISIBLE);
        view.setText(title);
    }

    public TextView getTopBarTitle() {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateTitle);
        return view;
    }

    public void setTopBarTitle(int rid) {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateTitle);
        view.setVisibility(View.VISIBLE);
        view.setText(rid);
    }


    public TextView getOperationLeftView2(String text, OnClickListener listener) {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateOperation_L2);
        view.setText(text);
        view.setVisibility(View.VISIBLE);
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }

    public TextView getOperationLeftView2(String text) {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateOperation_L2);
        view.setText(text);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    public void setTopNavigateBackgroundColor(int color) {
        mRoot.setBackgroundColor(color);
    }

    public View getTopNavigateBack() {
        return mRoot;
    }

    public ImageView getOperationBack() {
        ImageView view = (ImageView) mRoot.findViewById(R.id.NavigateOperation_L1);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    public ImageView hideOperationBack() {
        ImageView view = (ImageView) mRoot.findViewById(R.id.NavigateOperation_L1);
        view.setVisibility(View.GONE);
        return view;
    }

    public ImageButton getOperationRightView2(int RID, OnClickListener listener) {
        ImageButton view = (ImageButton) mRoot.findViewById(R.id.NavigateOperation_R2);
        view.setVisibility(View.VISIBLE);
        view.setImageResource(RID);
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }


    public TextView getOperationRightView3(String title, OnClickListener listener) {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateOperation_R3);
        view.setVisibility(View.VISIBLE);
        view.setText(title);
        //水波纹特效,需要的时候去掉注释
        SupportUtil_SDK21.SupporTouchFeedback(view.getContext(), view, true, R.drawable.top_bar_button, listener);
        return view;
    }

    public TextView getOperationRightView3() {
        TextView view = (TextView) mRoot.findViewById(R.id.NavigateOperation_R3);
        return view;
    }

    public void addPrePanel(View child) {
        if (child != null) {
            mPrePanel = child;
            ((RelativeLayout) mRoot).addView(child, new RelativeLayout
                    .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                    , RelativeLayout.LayoutParams.MATCH_PARENT));
        }
    }

    public void removePrePanel() {
        if (mPrePanel != null)
            ((RelativeLayout) mRoot).removeView(mPrePanel);
    }
}

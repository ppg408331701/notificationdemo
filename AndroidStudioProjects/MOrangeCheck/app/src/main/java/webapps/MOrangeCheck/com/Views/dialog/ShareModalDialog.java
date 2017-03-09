package webapps.MOrangeCheck.com.Views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import webapps.MOrangeCheck.com.R;


/**
 * Created by leon on 16/7/1.
 */
public class ShareModalDialog extends Dialog implements View.OnClickListener {

    private ImageView mClose;
    private OnModalFormClick mListener;
    private TextView mTvMobile;
    protected Animation curAnima;


    public ShareModalDialog(Context context) {
        this(context, R.style.quick_option_dialog);
    }

    public ShareModalDialog(Context context, String mobile) {
        this(context, R.style.quick_option_dialog);
        if (mTvMobile != null){
            mTvMobile.setText(mobile);
        }
       // ShareSDK.initSDK(context);
    }


    public ShareModalDialog(Context context, boolean flag, OnCancelListener listener) {
        super(context, flag, listener);
    }

    @SuppressLint("InflateParams")
    private ShareModalDialog(Context context, int defStyle) {
        super(context, defStyle);
        View dialogModal = getLayoutInflater().inflate(R.layout.dialog_share_modal, null);
        dialogModal.findViewById(R.id.clickWeChatFriend).setOnClickListener(this);
        dialogModal.findViewById(R.id.clickWeChatCircles).setOnClickListener(this);
        dialogModal.findViewById(R.id.clickQQFriend).setOnClickListener(this);
        dialogModal.findViewById(R.id.clickWeiBo).setOnClickListener(this);
        dialogModal.findViewById(R.id.clickSMS).setOnClickListener(this);
        dialogModal.findViewById(R.id.clickMail).setOnClickListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogModal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShareModalDialog.this.dismiss();
                return true;
            }
        });
        curAnima = getTranslateAnimation(250*2,0,150);
        super.setContentView(dialogModal);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager wm = getWindow().getWindowManager();
        Display defaultDisplay = wm.getDefaultDisplay();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = defaultDisplay.getWidth();
        attributes.alpha = 0.95f;
        getWindow().setAttributes(attributes);
    }

    public void setOnModalDialogClickListener(OnModalFormClick lis) {
        mListener = lis;
    }

    protected Animation getTranslateAnimation(int start, int end, int durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0, 0, start, end);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onModalClick(v);
        }
        dismiss();
    }

    public interface OnModalFormClick {
        void onModalClick(View v);
    }
}

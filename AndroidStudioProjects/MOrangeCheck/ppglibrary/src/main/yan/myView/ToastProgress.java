package myView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import ppg.com.yanlibrary.R;



/**
 * Created by ppg on 2016/3/28.
 */
public class ToastProgress {

    static Context context;
    static String text;
    public  static Dialog progressDialog;

    public ToastProgress(Context context, String text) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        this.context = context;
        this.text = text;
    }

    public ToastProgress(Context context) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        this.context = context;
    }

    public static void Show(Context context, String text) {
        progressDialog.setContentView(R.layout.mydialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setVisibility(View.VISIBLE);
        msg.setText(text);
        progressDialog.show();
    }

    public static void Show() {
        progressDialog.setContentView(R.layout.mydialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
    }

    public void setMessage(String msg){
        this.text=msg;
    }

    public boolean isShowing(){
        if (progressDialog.isShowing()){
            return true;
        }else {
            return false;
        }
    }

    public static void Dismiss() {
        progressDialog.dismiss();
    }
}

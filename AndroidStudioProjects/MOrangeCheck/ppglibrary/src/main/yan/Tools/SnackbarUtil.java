package Tools;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by ppg on 2016/7/20.
 */
public class SnackbarUtil {

  private static Snackbar snackbar;

    /**
     * @param activity 显示的根activity
     * @param msg   显示的文字
     */
    public static void showOK(Activity activity,String msg) {
         snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param activity 显示的根activity
     * @param msg   显示的文字
     */
    public static void show(Activity activity,String msg) {
        snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param activity 显示的根activity
     * @param msg   显示的文字
     * @param action   显示的动作文字
     */
    public static void showNoDisMiss(Activity activity,String msg,String action) {
        snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param activity 显示的根activity
     * @param msg   显示的文字
     * @param action   显示的动作文字
     */
    public static void showNoDisMiss(Activity activity,String msg,String action,View.OnClickListener listener) {
        snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT)
                .setAction(action,listener);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param activity 显示的根activity
     * @param msg   显示的文字
     * @param action   显示动作的文字
     */
    public static void showAction(Activity activity,String msg,String action) {
        snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }


    /**
     * @param rootview 显示的根布局
     * @param msg   显示的文字
     */
    public static void showOK(View rootview,String msg) {
        snackbar = Snackbar.make(rootview, msg, Snackbar.LENGTH_SHORT)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param rootview 显示的根布局
     * @param msg   显示的文字
     */
    public static void show(View rootview,String msg) {
        snackbar = Snackbar.make(rootview, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param rootview 显示的根布局
     * @param msg   显示的文字
     * @param action   显示的动作文字
     */
    public static void showNoDisMiss(View rootview,String msg,String action) {
        snackbar = Snackbar.make(rootview, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param rootview 显示的根布局
     * @param msg   显示的文字
     * @param action   显示的动作文字
     */
    public static void showNoDisMiss(View rootview,String msg,String action,View.OnClickListener listener) {
        snackbar = Snackbar.make(rootview, msg, Snackbar.LENGTH_SHORT)
                .setAction(action,listener);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }

    /**
     * @param rootview 显示的根布局
     * @param msg   显示的文字
     * @param action   显示动作的文字
     */
    public static void showAction(View rootview,String msg,String action) {
        snackbar = Snackbar.make(rootview, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setActionTextColor(Color.parseColor("#ff4081"));
        snackbar.show();
    }
}
package ppg.com.yanlibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {

    private static int backColor = 0x667BEDD2;
    private static Toast ts;

    /**
     * 中间提示
     *
     * @param context
     * @param str
     */
    public static void toast1_center(Context context, String str) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }
        if (ts == null) {
            ts = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else {
            ts.setText(str);
        }
        ts.setGravity(Gravity.CENTER, 0, 0);
        ts.show();



    }

    /**
     * 底部提示
     *
     * @param context
     * @param str
     */
    public static void toast2_bottom(Context context, String str) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }

        if (ts == null) {
            ts = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else {
            ts.setText(str);
        }
        ts.setGravity(Gravity.BOTTOM, 0, 150);
        ts.show();
    }

    /**
     * 底部提示
     *
     * @param context
     * @param str
     */
    public static void toast(Context context, String str) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }

        if (ts == null) {
            ts = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else {
            ts.setText(str);
        }
        ts.show();
    }

}
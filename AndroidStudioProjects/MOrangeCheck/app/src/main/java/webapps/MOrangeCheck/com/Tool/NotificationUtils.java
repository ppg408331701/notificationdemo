package webapps.MOrangeCheck.com.Tool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import webapps.MOrangeCheck.com.Activity.LoginActivity;
import webapps.MOrangeCheck.com.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static webapps.MOrangeCheck.com.Constants.Constans.ACTION_BUTTON;
import static webapps.MOrangeCheck.com.Constants.Constans.BUTTON_PREV_ID;
import static webapps.MOrangeCheck.com.Constants.Constans.INTENT_BUTTONID_TAG;
import static webapps.MOrangeCheck.com.Constants.Constans.NOTIFICATION_FLAG;
import static webapps.MOrangeCheck.com.Constants.Constans.webapps;


/**
 * Created by ppg777 on 2016/12/14.
 */

public class NotificationUtils {


    public static NotificationManager mNotificationManager;

    //前台通知
    public static void OpenNotification(Context context) {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.ic_android);
        //API3.0 以上的时候显示按钮，否则消失
        if (true) {
            mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.mipmap.ic_launcher);
        } else {
            mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.ic_android);
        }
        //点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);

        //点击的事件处理
        Intent button2Intent = new Intent(ACTION_BUTTON);
        button2Intent.putExtra(INTENT_BUTTONID_TAG, webapps);

        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_prev = PendingIntent.getBroadcast(context, 101, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent intent2 = PendingIntent.getBroadcast(context, 102, button2Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_prev);
        mRemoteViews.setOnClickPendingIntent(R.id.login, intent2);

        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(context, Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)//标识是否是一个正在进行中的通知
                .setSmallIcon(R.drawable.ic_android);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(NOTIFICATION_FLAG, notify);
//        ((Service)context).startForeground(1,notify);//设置为前台服务级别的通知.不会随app死亡而被杀死,必须是service生成的通知才行
    }

    /**
     * 更新通知
     * @param context
     * @param msg 更新的内容
     */
    public static void UpdataNotification(Context context, String msg) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        mRemoteViews.setTextViewText(R.id.custom_song_singer, msg);
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(context, Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_android);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        mNotificationManager.notify(NOTIFICATION_FLAG, notify);
    }


    /**
     * //关闭前台
     *
     * @param context
     */
    public static void CloseNotification(Context context) {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        mNotificationManager.cancel(NOTIFICATION_FLAG);
    }

    /**
     * 当app在屏幕中时,点击不会有反应,
     * 当app隐藏时,点击弹出栈顶的activity
     * 当app死亡时,点击从设定好的LoginActivity开始
     */
    public static PendingIntent getDefalutIntent(Context context, int flags) {
//        Intent intent = new Intent();
//        intent.setClass(context, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, flags);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, flags);
        return pendingIntent;
    }

}

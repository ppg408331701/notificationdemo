package com.example.ppg777.myapplication.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.ppg777.myapplication.R;
import com.example.ppg777.myapplication.Service.BackgroundService;


import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.ppg777.myapplication.Constants.Constans.*;


//这个类用于接受环信消息来之后,在手机通知栏点击通知之后的逻辑处理,因为需要切换页面到消息的fragment.使用了接受者来判断.
public class NotificationClickReceiver extends BroadcastReceiver {

    private Notification notify;
    public static NotificationManager mNotificationManager;





    @Override
    public void onReceive(Context context, Intent intent) {
        //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
        String action = intent.getAction();
        if(action.equals(ACTION_BUTTON)){
            //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
            int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
            Intent action_intent;
            switch (buttonId) {
                case BUTTON_PREV_ID:
                    action_intent = new Intent(context, BackgroundService.class);
                    action_intent.putExtra(NOTIFICATION_ACTION, ACTION_CHECK);
                    Updata(context);
                    context.startService(action_intent);
                    break;
                case webapps:
                    action_intent = new Intent(context, BackgroundService.class);
                    action_intent.putExtra(NOTIFICATION_ACTION, ACTION_OUT);
                    context.startService(action_intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void Updata(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        mRemoteViews.setTextViewText(R.id.custom_song_singer, "嗖嗖嗖");
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(context, Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        mNotificationManager.notify(NOTIFICATION_FLAG, notify);
    }


    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public static PendingIntent getDefalutIntent(Context context, int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }



}
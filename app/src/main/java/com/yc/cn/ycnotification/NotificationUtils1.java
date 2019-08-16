package com.yc.cn.ycnotification;

/**
 * @author: ${bruce}
 * @project: smartlock
 * @package: com.boray.smartlock.receiver
 * @description:
 * @date: 2019/8/13
 * @time: 18:30
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.WindowManager;

import static android.support.v4.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * desc:通知生成类
 */
public class NotificationUtils1 extends ContextWrapper {
    /**
     * 通知管理对象
     */
    private NotificationManager notificationManager;
    /**
     * channel的ID
     */
    public static final String id = "channel_id";
    /**
     * channel的名称
     */
    public static final String name = "channel_name";
    /**
     * notification id
     */
    public static final int notification_id = 1;

    /**
     * 通知生成类的构造方法
     */
    public NotificationUtils1(Context context) {
        super(context);
        initWindowManager(context);
    }

    private WindowManager.LayoutParams mWindowParams;
    private WindowManager mWindowManager;

    private void initWindowManager(Context context){
        mWindowParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
    }

    /**
     * 模拟发送一个普通通知
     *
     * @param iconRes
     * @param title
     * @param content
     * @param pendingIntent
     */
    public void sendNotification(int iconRes, String title, String content, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //26及以上
            createNotificationChannel();
            Notification notification = getChannelNotification(iconRes, title, content, pendingIntent).build();
            notificationManager.notify(notification_id, notification);
        } else {
            getNotificationManager();
            Notification notification = getNotification(iconRes, title, content, pendingIntent).build();
            notificationManager.notify(2, notification);
        }
    }


    /**
     * 创建NotificationChannel
     */
    public void createNotificationChannel() {
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
//            notificationChannel.canBypassDnd();//可否绕过请勿打扰模式
//            notificationChannel.canShowBadge();//桌面lanchener显示角标
//            notificationChannel.enableLights(true);//闪光
//            notificationChannel.shouldShowLights();//闪光
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//锁屏显示通知
//            notificationChannel.enableVibration(true);//是否允许震动
//            notificationChannel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
//            notificationChannel.getAudioAttributes();//获取系统响铃配置
//            notificationChannel.getGroup();//获取消息渠道组
//            notificationChannel.setBypassDnd(true);
//            notificationChannel.setDescription("description");
//            notificationChannel.setLightColor(Color.GREEN);//制定闪灯是灯光颜色
//            notificationChannel.setShowBadge(true);
//            getNotificationManager().createNotificationChannel(notificationChannel);

            //第一个参数：channel_id
            //第二个参数：channel_name
            //第三个参数：设置通知重要性级别
            //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
            //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
            NotificationChannel channel = new NotificationChannel(id, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
//        channel.canBypassDnd();//是否绕过请勿打扰模式
            channel.enableLights(true);//闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.setLightColor(Color.RED);//闪关灯的灯光颜色
            channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(true);//是否允许震动
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
            channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            channel.shouldShowLights();//是否会有灯光
            getNotificationManager().createNotificationChannel(channel);
        }

    }

    /**
     * 获取通知管理者对象
     *
     * @return
     */
    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    /**
     * 对应Android8.0生成notification的方法，通过此方法获取notification
     */
    public Notification.Builder getChannelNotification(int iconRes, String title, String content, PendingIntent pendingIntent) {
        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getApplicationContext(), id);
            builder.setSmallIcon(iconRes);
            builder.setAutoCancel(true);
            builder.setChannelId(id);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle(title);
            //设置显示通知时间
            builder.setShowWhen(true);
            builder.setContentText(content);
            builder.setNumber(3);
            builder.setOnlyAlertOnce(false);
            //悬停通知
            builder.setTicker(content);
            builder.setDefaults(~0);
            builder.setPriority(Notification.PRIORITY_DEFAULT);
//            builder.setFullScreenIntent(pendingIntent, true);

            builder.setContentIntent(pendingIntent);
        }


        return builder;
    }

    private int priority = Notification.PRIORITY_DEFAULT;
    /**
     * 对应Android8.0以下的notification对象
     */
    public NotificationCompat.Builder getNotification(int iconRes, String title, String content, PendingIntent pendingIntent) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), id);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setSmallIcon(iconRes);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(title); //设置标题
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置振动声音等,需要振动权限
        builder.setContentIntent(pendingIntent); //自定义打开的界面


        //悬停通知
        builder.setTicker(title);
        builder.setDefaults(~0);
        builder.setPriority(Notification.PRIORITY_HIGH);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本>=21才能设置悬挂式通知栏
//            builder.setCategory(String.valueOf(Notification.FLAG_ONGOING_EVENT))
//                    .setVisibility(Notification.VISIBILITY_PUBLIC);
//        }

//        builder.setFullScreenIntent(pendingIntent, true);

        //点击自动删除通知
        builder.setAutoCancel(true);

        return builder;
    }


    public static boolean isNotificationEnabled(Context context,String channelId) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        boolean returnValue = managerCompat.areNotificationsEnabled();
        if(manager == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return returnValue;
        }
        NotificationChannel channel = manager.getNotificationChannel(channelId);
        if(channel == null){
            channel = new NotificationChannel(channelId,"我的推送类别",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

            //下面的获取操作必需，创建的channel和获取到的channel的IMPORTANCE可能不一样，OPPO默认IMPORTANCE_NONE。
            channel = manager.getNotificationChannel(channelId);
        }
        return returnValue && channel.getImportance() != NotificationManager.IMPORTANCE_NONE;
    }
}

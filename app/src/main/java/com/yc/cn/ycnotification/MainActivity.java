package com.yc.cn.ycnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.ycbjie.notificationlib.NotificationUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager mNotificationManager;
    private TextView tv_0;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;
    private TextView tv_8;
    private TextView tv_9;
    private TextView tv_10;
    private TextView tv_11;
    private TextView tv_12;
    private TextView tv_13;
    private TextView tv_14;
    private TextView tv_15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        initView();
        initListener();
        initNotificationManager();
        //WL
//        initWindowManager();
//        createFloatView("感谢各位老铁!");
    }

    private void initView() {
        tv_0 = (TextView) findViewById(R.id.tv_0);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        tv_9 = (TextView) findViewById(R.id.tv_9);
        tv_10 = (TextView) findViewById(R.id.tv_10);
        tv_11 = (TextView) findViewById(R.id.tv_11);
        tv_12 = (TextView) findViewById(R.id.tv_12);
        tv_13 = (TextView) findViewById(R.id.tv_13);
        tv_14 = (TextView) findViewById(R.id.tv_14);
        tv_15 = (TextView) findViewById(R.id.tv_15);
    }

    private void initListener() {
        tv_0.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        tv_8.setOnClickListener(this);
        tv_9.setOnClickListener(this);
        tv_10.setOnClickListener(this);
        tv_11.setOnClickListener(this);
        tv_12.setOnClickListener(this);
        tv_13.setOnClickListener(this);
        tv_14.setOnClickListener(this);
        tv_15.setOnClickListener(this);
    }

    private void initNotificationManager() {
        // 创建一个NotificationManager的引用
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    //------------------------悬浮------//
    private WindowManager wm;
    private boolean showWm =true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;
    private View view;
    private void initWindowManager(){
        wm = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        params  = new WindowManager.LayoutParams();
        //注意是TYPE_SYSTEM_ERROR而不是TYPE_SYSTEM_ALERT
        //前面有SYSTEM才可以遮挡状态栏，不然的话只能在状态栏下显示通知栏
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        params.format = PixelFormat.TRANSPARENT;
        //设置必须触摸通知栏才可以关掉
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        // 设置通知栏的长和宽
        params.width = wm.getDefaultDisplay().getWidth();
        params.height = 200;
        params.gravity = Gravity.TOP;
    }

    private void createFloatView(String str) {

        view = LayoutInflater.from(this).inflate(R.layout.activity_test, null);
        //在这里你可以解析你的自定义的布局成一个View
        if (showWm){
            wm.addView(view, params);
            showWm = false;
        }else {
            wm.updateViewLayout(view,params);
        }


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        wm.removeViewImmediate(view);
                        view = null;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                }
                return true;
            }
        });

    }
    //------------------------悬浮------//

    public PendingIntent getClickPendingIntent(Context context, String msg) {
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what",2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,2,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        return resultPendingIntent;
    }

    private NotificationManager manager = null;
    private Notification.Builder builder = null;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(context);
        builder.setShowWhen(false)
                .setSmallIcon(R.drawable.ic_notification_music_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_music_logo))
                .setContentTitle("自定义标题")
                .setContentText("自定义内容")
                .setDefaults(NotificationCompat.FLAG_ONGOING_EVENT)
                .setPriority(Notification.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本>=21才能设置悬挂式通知栏
            builder.setCategory(String.valueOf(Notification.FLAG_ONGOING_EVENT))
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
            Intent intent = new Intent();
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
            builder.setFullScreenIntent(pi, true);
            manager.notify(null, 0, builder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_0:
//                showNotification(MainActivity.this);
                if(NotificationUtils1.isNotificationEnabled(this,"1")){
                    String msg = "sas";//点击事情，处理协议
                    String text = "xxxxxx111";
                    PendingIntent pendingIntent = getClickPendingIntent(this, msg);
                    NotificationUtils1 notificationUtils = new NotificationUtils1(this);
                    notificationUtils.sendNotification(R.mipmap.ic_launcher, "sassas", text, pendingIntent);
                }

                break;
            case R.id.tv_1:
                cancelAllNotification();
                break;
            case R.id.tv_2:
                if(!RomUtil.isEmui()){
                    sendNotification1();
                }
                break;
            case R.id.tv_3:
                sendNotification2();
                break;
            case R.id.tv_4:
                sendNotification3();
                break;
            case R.id.tv_5:
                sendNotification4();
                break;
            case R.id.tv_6:
                Intent intent = new Intent(this,ReminderReceiver.class);
                sendBroadcast(intent);
                break;
            case R.id.tv_7:

                break;
            case R.id.tv_8:
                sendNotification8();
                break;
            case R.id.tv_9:
                sendNotification9();
                break;
            case R.id.tv_10:
                sendNotification10();
                break;
            case R.id.tv_11:
                sendNotification11();
                break;
            case R.id.tv_12:
                sendNotification12();
                break;
            case R.id.tv_13:
                sendNotification13();
                break;
            case R.id.tv_14:
                sendNotification14();
                break;
            case R.id.tv_15:
                sendNotification15();
                break;
        }
    }

    private void cancelAllNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.clearNotification();
    }


    private void sendNotification1() {
        //这三个属性是必须要的，否则异常
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(1,"这个是标题","这个是内容",R.mipmap.ic_launcher);

    }


    private void sendNotification2() {
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what",2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,2,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // 定义Notification的各种属性
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setContentIntent(resultPendingIntent)
                .sendNotificationCompat(2,"这个是标题2", "这个是内容2", R.mipmap.ic_launcher);
    }


    private void sendNotification3() {
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what",3);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,3,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //发送pendingIntent

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置内容点击处理intent
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("来通知消息啦")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setVibrate(vibrate)
                //必须设置的属性，发送通知
                .sendNotification(3,"这个是标题3", "这个是内容3", R.mipmap.ic_launcher);
    }


    private void sendNotification4() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.setContent(getRemoteViews());
        Notification notification = notificationUtils.getNotification("这个是标题4", "这个是内容4", R.mipmap.ic_launcher);
        notificationUtils.getManager().notify(4,notification);
    }


    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_mobile_play);
        // 设置 点击通知栏的上一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11));
        // 设置 点击通知栏的下一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12));
        // 设置 点击通知栏的播放暂停按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13));
        // 设置 点击通知栏的根容器时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14));
        remoteViews.setTextViewText(R.id.tv_title, "标题");     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家");   // 设置通知栏上艺术家
        return remoteViews;
    }


    /** 获取一个Activity类型的PendingIntent对象 */
    private PendingIntent getActivityPendingIntent(int what) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        intent.putExtra("what", what);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }



    private void sendNotification8() {
        for(int a=0 ; a<3 ; a++){
            //这三个属性是必须要的，否则异常
            NotificationUtils notificationUtils = new NotificationUtils(this);
            notificationUtils.sendNotification(8,"这个是标题8","这个是内容8",R.mipmap.ic_launcher);

        }
    }


    private void sendNotification9() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置状态栏的标题
                .setTicker("有新消息呢9")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_NO_CLEAR)
                //必须设置的属性，发送通知
                .sendNotification(9,"有新消息呢9", "这个是标题9", R.mipmap.ic_launcher);
    }


    private void sendNotification10() {

        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what",10);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,10,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("有新消息呢10")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                .setDefaults(Notification.DEFAULT_ALL)
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_AUTO_CANCEL)
                //必须设置的属性，发送通知
                .sendNotification(10,"有新消息呢10", "这个是标题10", R.mipmap.ic_launcher);
    }


    private void sendNotification11() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setOngoing(false)
                .setTicker("来通知消息啦")
                .setContent(getRemoteViews())
                //.setSound(Uri.parse("android.resource://com.yc.cn.ycnotification/" + R.raw.hah))
                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .sendNotification(11,"我是伴有铃声效果的通知11", "美妙么?安静听~11", R.mipmap.ic_launcher);

    }



    private void sendNotification12() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        long[] vibrate = new long[]{0, 500, 1000, 1500};
//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("我是伴有震动效果的通知")
//                .setContentText("颤抖吧,逗比哈哈哈哈哈~")
//                //使用系统默认的震动参数,会与自定义的冲突
//                //.setDefaults(Notification.DEFAULT_VIBRATE)
//                //自定义震动效果
//                .setVibrate(vibrate);
//        //另一种设置震动的方法
//        //Notification notify = builder.build();
//        //调用系统默认震动
//        //notify.defaults = Notification.DEFAULT_VIBRATE;
//        //调用自己设置的震动
//        //notify.vibrate = vibrate;
//        //mManager.notify(3,notify);
//        mNotificationManager.notify(12, builder.build());

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(vibrate)
                .sendNotification(12,"我是伴有震动效果的通知", "颤抖吧,逗比哈哈哈哈哈~", R.mipmap.ic_launcher);

    }


    private void sendNotification13() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setDefaults(Notification.DEFAULT_ALL)
                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(13,"仔细看,我就执行一遍","好了,已经一遍了~",R.mipmap.ic_launcher);

    }


    private void sendNotification14() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setDefaults(Notification.DEFAULT_ALL)

                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(14,"显示进度条14","显示进度条内容，自定定义",R.mipmap.ic_launcher);
    }


    /**
     * 错误代码
     */
    private void ssendNotification151() {
        String id = "channel_1";
        String description = "123";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, "123", importance);
            //  mChannel.setDescription(description);
            //  mChannel.enableLights(true);
            //  mChannel.setLightColor(Color.RED);
            //  mChannel.enableVibration(true);
            //  mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(this, id)
                    .setContentTitle("Title")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("您有一条新通知")
                    .setContentText("这是一条逗你玩的消息")
                    .setAutoCancel(true)
//                    .setContentIntent(pintent)
                    .build();
            mNotificationManager.notify(1, notification);
        }
    }



    private void sendNotification15() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(15,"新消息来了","周末到了，不用上班了",R.mipmap.ic_launcher);


    }

}

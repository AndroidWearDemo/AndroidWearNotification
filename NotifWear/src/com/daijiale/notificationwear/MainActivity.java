/*
 * author：daijiale
 * data：2015.1.23
 * */

package com.daijiale.notificationwear;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.app.RemoteInput;

import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends Activity implements OnClickListener {

    private static final int NOTIFICATION_ID_1 = 001;

    private static final int NOTIFICATION_ID_2 = 002;

    private static final int NOTIFICATION_ID_3 = 003;

    private static final int NOTIFICATION_ID_4 = 004;

    private static final int NOTIFICATION_ID_5 = 005;

    private static final int NOTIFICATION_ID_6 = 006;

    private static final int NOTIFICATION_ID_7 = 007;

    private static final int NOTIFICATION_ID_8 = 000;

    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    private static int messageNum = 0;

    private Context context = this;

    private NotificationManager manager;

    private Bitmap icon;

    private static final int[] btns = new int[] {
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            // R.id.btn7, R.id.btn8,
            R.id.btn9
    };

    private static final String EXTRA_EVENT_ID = null;

    private static final String map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // 获取通知服务
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 注册监听器
        for (int btn : btns) {
            findViewById(btn).setOnClickListener(this);
        }

        // 内部R.ID修改控件样式
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.btn5);
        Button btn6 = (Button) findViewById(R.id.btn6);
        /*
         * Button btn7 = (Button) findViewById(R.id.btn7); Button btn8 =
         * (Button) findViewById(R.id.btn8);
         */
        Button btn9 = (Button) findViewById(R.id.btn9);

        btn1.setText("一般的Wear通知(铃)");
        btn2.setText("按钮响应事件的Wear通知(震动)");
        btn3.setText("自定义特性的Wear通知(震动)");
        btn4.setText("BigText内容的Wear通知");
        btn5.setText("多页的Wear通知");
        btn6.setText("语音输入的Wear通知");
        // btn7.setText("叠加消息的Wear通知");
        // btn8.setText("更多功能还在开发ing，敬请期待！！！");
        btn9.setText("清零所有测试数据");

        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                NormalWearNotificationView();
                break;
            case R.id.btn2:
                ActionWearNotificationView();
                break;

            case R.id.btn3:
                ReplyWearNotificationView();
                break;

            case R.id.btn4:
                BigWearNotificationView();
                break;

            case R.id.btn5:
                MorePagesWearNotificationView();
                break;

            case R.id.btn6:
                VoicePrintWearNotificationView();
                break;

            /*
             * case R.id.btn7: backScreen(); break; case R.id.btn8:
             * showProgressBar(); break;
             */

            case R.id.btn9:
                dismiss();
                break;
            default:
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 清零所有通知
    private void dismiss() {
        manager.cancelAll();

    }

    // 增加了语音输入的WearNotification
    /*
     * 创建一个支持语音输入动作，创建RemoteInput.Builder的实例添加到你的通知action中， 这个类的构造函数接收一个String，
     * 这样系统就把这个做为你的语音输入的key。 你就可以在你的手持设备app中把这个String用来检索文本输入。
     */
    private void VoicePrintWearNotificationView() {

        // Key for the string that's delivered in the action's intent

        String replyLabel = getResources().getString(R.string.reply_choices);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(replyLabel)
                .build();

        // Create an intent for the reply action
        Intent replyIntent = new Intent(this, OtherActivity.class);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_launcher, getString(R.string.label, replyPendingIntent),
                replyPendingIntent).addRemoteInput(remoteInput).build();

        // Build the notification and add the action via WearableExtender
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("消息标题")
                .setContentText("消息正文").extend(new WearableExtender().addAction(action)).build();

        // Issue the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID_6, notification);
        manager.cancel(NOTIFICATION_ID_5);
        manager.cancel(NOTIFICATION_ID_4);
        manager.cancel(NOTIFICATION_ID_3);
        manager.cancel(NOTIFICATION_ID_2);
        manager.cancel(NOTIFICATION_ID_1);

    }

    // 多页的WearNotification
    /*
     * 为了创建一个多页的Notification，你需要：
     * 通过NotificationCompat.Builder创建主Notification（首页）， 这个首页也是你想要呈现在手持设备上的。
     * 通过NotificationCompat.Builder为wear添加更多的页面。
     * 通过addPage()方法为主Notification应用这些添加的页面，或者通过addPage()添加一个Collection的多个页面。
     */
    private void MorePagesWearNotificationView() {

        // Create builder for the main notification
        Intent viewIntent = new Intent(this, SpecialActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("第一页")
                .setContentText("亲，还有第二页噢").setContentIntent(viewPendingIntent);

        // Create a big text style for the second page
        BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("第二页").bigText(
                "这是一段很长的Text，用来测试用！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");

        // Create second page notification
        Notification secondPageNotification = new NotificationCompat.Builder(this).setStyle(
                secondPageStyle).build();

        // Add second page with wearable extender and extend the main
        // notification
        Notification twoPageNotification = new WearableExtender().addPage(secondPageNotification)
                .extend(notificationBuilder).build();

        // Issue the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID_5, twoPageNotification);
        manager.cancel(NOTIFICATION_ID_4);

        manager.cancel(NOTIFICATION_ID_6);
        manager.cancel(NOTIFICATION_ID_3);
        manager.cancel(NOTIFICATION_ID_2);
        manager.cancel(NOTIFICATION_ID_1);

    }

    // 一般情况下的WearNotification
    private void NormalWearNotificationView() {

        // Build intent for notification content
        Intent viewIntent = new Intent(this, SpecialActivity.class);
        String eventId = "test";
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)               
                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("消息标题")
                .setContentText("消息正文").setContentIntent(viewPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)//读取提示音
                ;
                
       
        
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        
        // Build the notification and issues it with notification manager.
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder.build());
        manager.cancel(NOTIFICATION_ID_5);
        manager.cancel(NOTIFICATION_ID_4);
        manager.cancel(NOTIFICATION_ID_3);
        manager.cancel(NOTIFICATION_ID_2);
        manager.cancel(NOTIFICATION_ID_6);
    }

    // 增加了Action按钮的Wear通知
    /*
     * 说明：在手持设备上，action表现为在Notification上附加的一个额外按钮。在可穿戴设备上，
     * action表现为Notification左滑后出现的大按钮。当用户点击action时，能够触发手持设备上对应的intent。
     */
    private void ActionWearNotificationView() {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        // 添加一个在地图上查看事件位置的action
        String location = "Beijing";
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)

                .setContentTitle("消息标题").setContentText("消息正文").setContentIntent(mapPendingIntent)
                .addAction(R.drawable.ic_launcher, getString(R.string.map), mapPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)//增加震动
                ;

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder.build());
        manager.cancel(NOTIFICATION_ID_1);
        manager.cancel(NOTIFICATION_ID_5);
        manager.cancel(NOTIFICATION_ID_4);
        manager.cancel(NOTIFICATION_ID_3);
        manager.cancel(NOTIFICATION_ID_6);

    }

    // 自定义Action的WearNotification
    /*
     * 说明：指定可穿戴设备独有的Actions如果你想要可穿戴式设备上的action与手持式设备不一样的话，可以使用WearableExtender.
     * addAction
     * ()，一旦你通过这种方式添加了action，可穿戴式设备便不会显示任何其他通过NotificationCompat.Builder
     * .addAction()添加的action。
     * 这是因为，只有通过WearableExtender.addAction()添加的action才能只在可穿戴设备上显示且不在手持式设备上显示。
     */
    private void ReplyWearNotificationView() {
        // Create an intent for the reply action
        Intent actionIntent = new Intent(this, SpecialActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_launcher, getString(R.string.label, actionPendingIntent),
                actionPendingIntent).build();

        // Build the notification and add the action via WearableExtender
        Notification notificationBuilder = new NotificationCompat.Builder(this)       
        .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.hello_world))
                .extend(new NotificationCompat.WearableExtender()
                        .setContentIcon(R.drawable.ic_launcher))
                /* .extend(new WearableExtender().addAction(action)) */
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)//增加震动
                .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID_3, notificationBuilder);
        manager.cancel(NOTIFICATION_ID_2);
        manager.cancel(NOTIFICATION_ID_5);
        manager.cancel(NOTIFICATION_ID_4);
        manager.cancel(NOTIFICATION_ID_6);

        manager.cancel(NOTIFICATION_ID_1);
    }

    // 增加BigView的WearNotification
    private void BigWearNotificationView() {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        // 添加一个在地图上查看事件位置的action
        String location = "Beijing";
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);

        BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText("这是一个很长的text，用来做测试用！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("消息标题").setContentText("消息正文").setContentIntent(mapPendingIntent)
                .addAction(R.drawable.ic_launcher, getString(R.string.map), mapPendingIntent)
                .setStyle(bigStyle);
                
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID_4, notificationBuilder.build());
        manager.cancel(NOTIFICATION_ID_3);
        manager.cancel(NOTIFICATION_ID_5);

        manager.cancel(NOTIFICATION_ID_6);
        manager.cancel(NOTIFICATION_ID_2);
        manager.cancel(NOTIFICATION_ID_1);
    }

}

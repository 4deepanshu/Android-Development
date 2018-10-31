package com.schoolshieldparent_ui.pushnotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.IntentCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.activity.Activity_AllChild_LockScreen;
import com.schoolshieldparent_ui.view.activity.Activity_Applications;
import com.schoolshieldparent_ui.view.activity.Activity_Children;
import com.schoolshieldparent_ui.view.activity.Activity_CustomLock;
import com.schoolshieldparent_ui.view.activity.Activity_Home;
import com.schoolshieldparent_ui.view.activity.Activity_Notification;
import com.schoolshieldparent_ui.view.activity.Activity_Splash;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static MyFirebaseMessagingService instance;
    private String data3;
    final static String GROUP_KEY_EMAILS = "group_key_emails";
    public static Activity activity;
    AtomicInteger notificationCounter = null;
    private int notificationId1 = 0;
    private NotificationManagerCompat notificationManager;
    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    NotificationCompat.Builder mBuilder;
    Bitmap remote_picture = null;
    String allMessages = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager =
                NotificationManagerCompat.from(this);

        try {
            instance = this;
            String type = remoteMessage.getData().get("type");
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String string = type;

            if (type.equalsIgnoreCase("login")) {
                try {
                    Activity_Home.getInstance().getChild(getString(R.string.refresh));
                } catch (NullPointerException e) {

                }
                try {
                    Activity_Children.getInstance().getChildList(getString(R.string.refresh));
                } catch (NullPointerException e) {

                }
            } else if (type.equalsIgnoreCase("logout")) {
                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");
                WebServiceResult.LogoutParent(SharedPref.getString(MyApplication.PARENT_ID), "logout");
            } else if (type.equalsIgnoreCase("notify_lock_add")) {

           /* String studentName = remoteMessage.getData().get( "stu_name" );
            String title = remoteMessage.getData().get( "title" );*/

                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");

                showApplicationLockedAddNotification(message, title, getApplicationContext());

                if (Activity_CustomLock.instance != null) {
                    Activity_CustomLock.getInstance().getCustomLocks(getString(R.string.refresh));
                }
                if (Activity_Notification.instance != null) {
                    Activity_Notification.getInstance().getNotificationData(getString(R.string.refresh));
                }


            } else if (type.equalsIgnoreCase("notify_lock_delete")) {
           /* String studentName = remoteMessage.getData().get( "stu_name" );
            String title = remoteMessage.getData().get( "title" );*/

                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");

                showApplicationLockedDeletedNotification(message, title, getApplicationContext());

                if (Activity_CustomLock.getInstance() != null) {
                    Activity_CustomLock.getInstance().getCustomLocks(getString(R.string.refresh));
                }

                if (Activity_Applications.instance != null) {
                    Activity_Applications.getInstance().getAllApps(getString(R.string.refresh));
                }


            } else if (type.equalsIgnoreCase("notify_Screen_lock_add")) {
            /*String studentName = remoteMessage.getData().get( "stu_name" );
            String title = remoteMessage.getData().get( "title" );*/

                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");


                showScreenLockedAddNotification(message, title, getApplicationContext());

                if (Activity_CustomLock.instance != null) {
                    Activity_AllChild_LockScreen.getInstance().getChildren(getString(R.string.refresh));
                }


            } else if (type.equalsIgnoreCase("notify_screen_lock_delete")) {
           /* String studentName = remoteMessage.getData().get( "stu_name" );
            String title = remoteMessage.getData().get( "title" );*/

                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");

                showScreenLockedDeletedNotification(message, title, getApplicationContext());

            } else if (type.equalsIgnoreCase("emergency")) {
                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");
                emergencyNotification(message, title, getApplicationContext());


            } else if (type.equalsIgnoreCase("emergency")) {
                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");
                emergencyNotification(message, title, getApplicationContext());


            } else if (type.equalsIgnoreCase("Statics")) {
                String message = remoteMessage.getData().get("notification_message");
                String title = remoteMessage.getData().get("notification_title");
                setgroupMsg(title, message);

            }

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }

    public void setgroupMsg(String title, String type) {
        if (SharedPref.getNotiString(MyApplication.NOTIFICATIONS).equalsIgnoreCase("")) {
            allMessages += type + ",";
            SharedPref.setNotiString(MyApplication.NOTIFICATIONS, allMessages);
        } else {
            allMessages = "";
            allMessages += SharedPref.getNotiString(MyApplication.NOTIFICATIONS);
            allMessages += type + ",";
            SharedPref.setNotiString(MyApplication.NOTIFICATIONS, allMessages);
        }
        String[] notificationsData = allMessages.split(",");
        SharedPref.setString(MyApplication.NOTIFICATIONS, allMessages);
        MyApplication.getInstance().allNotifications.add(type);
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.statics);
        Intent resultIntent = new Intent(this, Activity_Splash.class);
        mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(notificationsData.length + " new messages")
                .setContentIntent(PendingIntent.getActivity(this, notificationID, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setSmallIcon(R.drawable.statics).setLargeIcon(icon1);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(title);
        for (int i = 0; i < notificationsData.length; i++) {
            inboxStyle.addLine(notificationsData[i]);
        }
        mBuilder.setStyle(inboxStyle);
        mNotificationManager.notify(notificationID, mBuilder.build());

    }


    private void showApplicationLockedAddNotification(String message, String title, Context context) {
        int icon = R.drawable.applock;
        Intent intent = new Intent(context, Activity_Splash.class);
       /* String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanetLock ) + " " + getString( R.string.lockapplied ) + " " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.lockapplied ) + " " + childname + " " + getString( R.string.device );
        }*/

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message).build();


        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }


    private void showScreenLockedAddNotification(String message, String title, Context context) {
        int icon = R.drawable.screen_lock;
        Intent intent = new Intent(context, Activity_Splash.class);
       /* String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanetLockScreen ) + " " + getString( R.string.lockapplied ) + " " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.screenlock ) + " " + childname + " " + getString( R.string.device );
        }*/

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message).build();


        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }


    private void showApplicationLockedDeletedNotification(String message, String title, Context context) {
        int icon = R.drawable.appunlock;
        Intent intent = new Intent(context, Activity_Splash.class);
       /* String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanetLock ) + " " + getString( R.string.deleted ) + "  " + getString( R.string.from ) + " " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.lockdeleted ) + "  " + getString( R.string.from ) + " " + childname + " " + getString( R.string.device );
        }
*/
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message).build();


        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }


    private void showScreenLockedDeletedNotification(String message, String title, Context context) {
        int icon = R.drawable.screen_unlock;
        Intent intent = new Intent(context, Activity_Splash.class);
      /*  String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanentscreenlockdeted ) + "  " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.screenlockdeted ) + " " + childname + " " + getString( R.string.device );
        }*/

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message).build();


        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }


    private void emergencyNotification(String message, String title, Context context) {
        int icon = R.drawable.emergency;
        Intent intent = new Intent(context, Activity_Splash.class);
      /*  String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanentscreenlockdeted ) + "  " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.screenlockdeted ) + " " + childname + " " + getString( R.string.device );
        }*/

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message).build();


        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    public void updateLogout(UniversalResponce body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            if (SharedPref.clearPref(instance.getApplicationContext())) {
                try {
                    if (Activity_Home.AppInBackground == true) {
                        Intent intent = new Intent(getBaseContext(), Activity_Splash.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                    }
                } catch (Exception e) {
                }


            }
        } else {
            Toast.makeText(activity, activity.getString(R.string.unableToLogout), Toast.LENGTH_LONG)
                    .show();

        }
    }


    private void showStaticsNotification(String message, String title, Context context) {
        int icon = R.drawable.notification;
        Intent intent = new Intent(context, Activity_Splash.class);
       /* String message;
        if (title.equalsIgnoreCase( "" )) {
            message = getString( R.string.permanetLock ) + " " + getString( R.string.lockapplied ) + " " + childname + " " + getString( R.string.device );

        } else {
            message = title + " " + getString( R.string.lockapplied ) + " " + childname + " " + getString( R.string.device );
        }*/

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        android.app.Notification.Builder mBuilder = new android.app.Notification.Builder(context);

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.InboxStyle(mBuilder)
                    .addLine("First Message")

                    .setBigContentTitle("Here Your Messages")
                    .setSummaryText("+3 more")
                    .build();
        }

        int mNotificationId;
        Random random = new Random();
        mNotificationId = random.nextInt(10000 - 1) + 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(89, notification);
    }


    public static MyFirebaseMessagingService getInstance() {
        return instance;
    }
}
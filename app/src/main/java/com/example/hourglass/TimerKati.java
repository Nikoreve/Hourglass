//package com.example.hourglass;
//
//import android.Manifest;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.SystemClock;
//import android.util.Log;
//import android.widget.Chronometer;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.core.content.ContextCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//
//public class TimerKati extends Service {
//    private static final int NOTIFICATION_ID = 1;
//    Notification notification;
//    NotificationManagerCompat notificationManagerCompat;
//    NotificationManager notificationManager;
//    private Chronometer chronometer;
//    long elapsedTime1 = 0;
//    MainActivity mainActivityObject;
//    long totalTimeInMillis = 0;
//
//    private BroadcastReceiver notificationUpdateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            pauseNotification();
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        int minutes = mainActivityObject.getUserPref1MinutesNP(getApplicationContext());
//        int seconds = mainActivityObject.getUserPrefSecondsNP1(getApplicationContext());
////        int minutes = Integer.parseInt(onePlayerWithPauseMinutesTV.getText().toString());
////        int seconds = Integer.parseInt(onePlayerWithPauseSecondsTV.getText().toString());
//        long minutesInMillis = minutes * 60 * 1000;
//        long secondsInMillis = seconds * 1000;
//        totalTimeInMillis = minutesInMillis + secondsInMillis;
//
////        Intent fullScreenIntent = new Intent(getActivity(), OnePlayerWithPauseFragment.class);
////        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(),0,fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//        chronometer.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
//
//        // Register the broadcast receiver with the desired action
//        IntentFilter intentFilter = new IntentFilter("com.example.NOTIFICATION_UPDATE_ACTION");
//        LocalBroadcastManager.getInstance(this).registerReceiver(notificationUpdateReceiver, intentFilter);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        // Unregister the broadcast receiver
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationUpdateReceiver);
//    }
//
//    public void createNotification() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//
//        Intent intent = new Intent(getApplicationContext(), StartActivity.class); // Here pass your activity where you want to redirect.
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), (int) (Math.random() * 100), intent, 0);
//
//        long min = elapsedTime1 / 60000;
//        long sec = (elapsedTime1 - min * 60000) / 1000;
//        long minPos = min < 0 ? -min : min;
//        long secPos = sec < 0 ? -sec : sec;
//        String mmin = minPos < 10 ? "0" + minPos : minPos + "";
//        String ssec = secPos < 10 ? "0" + secPos : secPos + "";
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "myCh")
//                .setSmallIcon(R.drawable.hourglass_icon)
//                .setContentTitle(getString(R.string.notificationTitleRunning))
//                .setShowWhen(true)
//                .setContentText(mmin + ":" + ssec + "")
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setOngoing(true)
//                .setSilent(true)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(contentIntent);
//        //builder.setTicker()
//        notification = builder.build();
////                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
////    notificationManagerCompat =NotificationManagerCompat.from(getContext());
//
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
////        notificationManagerCompat.notify(1, notification);
//        notificationManager.notify(1, notification);
//    }
//
//
//    public void pauseNotification() {
//
//        long elapsedTime3 = SystemClock.elapsedRealtime() - chronometer.getBase();
//        long min = elapsedTime3 / 60000;
//        long sec = (elapsedTime3 - min * 60000) / 1000;
//        long minPos = min < 0 ? -min : min;
//        long secPos = sec < 0 ? -sec : sec;
//        String mmin = minPos < 10 ? "0" + minPos : minPos + "";
//        String ssec = secPos < 10 ? "0" + secPos : secPos + "";
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "myCh")
//                .setSmallIcon(R.drawable.hourglass_icon)
//                .setContentTitle(getString(R.string.notificationTitleStop))
//                .setShowWhen(true)
//                .setSilent(true)
//                .setContentText(mmin + ":" + ssec + "");
//
//        notification = builder.build();
////                            notification.defaults |= Notification.VISIBILITY_PUBLIC;
//        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManagerCompat.notify(1, notification);
//    }
//
//}
//

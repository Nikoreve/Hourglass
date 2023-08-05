//package com.example.hourglass;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//public class NotificationServiceHourglass extends Service {
//
//    OnePlayerWithPauseFragment onePlayerWithPauseFragment;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        // Register the broadcast receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(notificationUpdateReceiver, new IntentFilter("com.example.NOTIFICATION_UPDATE_ACTION"));
//
//        // Create and configure the notification
//        Notification notification = onePlayerWithPauseFragment.createNotification();
//
//        // Start the service in the foreground
//        startForeground(1, notification);
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
//    private BroadcastReceiver notificationUpdateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Handle the received broadcast and update the notification
//            String message = intent.getStringExtra("message");
////            updateNotificationContent(message);
//            updateNotificationContent(message);
//        }
//    };
//
//    private void updateNotificationContent(String message) {
//        // Update the notification content using the received message
//        NotificationCompat.Builder updatedBuilder = new NotificationCompat.Builder(this, "myCh")
//                .setContentTitle("My Notification")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.hourglass_icon);
//
//        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//        notificationManager.notify(1, updatedBuilder.build());
//    }
//
//}

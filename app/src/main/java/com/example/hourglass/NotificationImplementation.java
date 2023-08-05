package com.example.hourglass;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public interface NotificationImplementation {

    public void createNotification();

    public void pauseNotification();

    public void stopNotification();


}

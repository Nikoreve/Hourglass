package com.example.hourglass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){
        String message = intent.getStringExtra("MessageAddedHere");
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

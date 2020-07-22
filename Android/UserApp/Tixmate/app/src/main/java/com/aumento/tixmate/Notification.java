package com.aumento.tixmate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {

    private static int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_1";

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "broadCast Start", Toast.LENGTH_SHORT).show();
        String msg = intent.getStringExtra("msg");
        String title = intent.getStringExtra("title");

        NOTIFICATION_ID = (int) System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create the content intent for the notification, which launches this activity
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_logo)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //Deliver the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

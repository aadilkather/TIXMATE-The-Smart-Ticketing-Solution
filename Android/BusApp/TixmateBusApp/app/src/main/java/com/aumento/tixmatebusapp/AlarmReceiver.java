package com.aumento.tixmatebusapp;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final String CHANNEL_ID = "channel_1";

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        testActivity inst = testActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");



        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //After 1s stop the alarm
        // You can adjust the time depending upon your requirement.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ringtone.stop();
            }
        }, 5000);


        NotificationCompat.Builder b = new NotificationCompat.Builder(context,CHANNEL_ID);

        b
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentInfo("Info");


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, b.build());

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

        // create an intent to the ringtone service
        Intent service_intent = new Intent(context, AlarmService.class);

        // pass the extra string from Receiver to the Ringtone Playing Service
        service_intent.putExtra("extra", "asdfg");
        // pass the extra integer from the Receiver to the Ringtone Playing Service
        service_intent.putExtra("whale_choice", "asdfg");

        // start the ringtone service
        context.startService(service_intent);
    }


}

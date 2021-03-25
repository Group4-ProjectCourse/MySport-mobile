package com.mysport.mysport_mobile.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification {
    public static void makeNotice(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationChannel channel = new NotificationChannel("My notification", "My notification", IMPORTANCE_DEFAULT)
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            val manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel)
        }

        buttonNotify = findViewById(R.id.get_notify)
        buttonNotify.setOnClickListener {
            var builder = NotificationCompat.Builder(this, "My notification")
            builder.setContentTitle("My notification Title")
            builder.setContentText("Hello from my application, this a simple notification")
            builder.setSmallIcon(R.drawable.ic_email)
            builder.setAutoCancel(true)

            //NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this)
            val managerCompat = NotificationManagerCompat.from(this)
            managerCompat.notify(1, builder.build())
        }

    }
}

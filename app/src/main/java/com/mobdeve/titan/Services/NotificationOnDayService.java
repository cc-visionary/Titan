package com.mobdeve.titan.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.mobdeve.titan.R;
import com.mobdeve.titan.UserAddAppointmentActivity;

import java.util.Date;

public class NotificationOnDayService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int uniqueID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;

        //Create the content intent for the notification, which launches this activity
        Intent contentIntent = new Intent(context, UserAddAppointmentActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, uniqueID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.black_logo)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body_on_day))
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //Deliver the notification
        notificationManager.notify(uniqueID, builder.build());
    }
}

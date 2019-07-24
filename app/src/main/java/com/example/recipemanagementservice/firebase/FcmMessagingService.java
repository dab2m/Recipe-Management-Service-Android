package com.example.recipemanagementservice.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.activity.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Berk on 24.07.2019.
 */
public class FcmMessagingService extends FirebaseMessagingService {
    // This class for Notifications with Server

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this);
        notificationbuilder.setContentTitle(title);
        //notificationbuilder.setContentText(message);
        notificationbuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationbuilder.setAutoCancel(true);
        notificationbuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationbuilder.build());
    }
}
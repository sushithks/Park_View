package com.tf_staff.parkemlandscape.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tf_staff.parkemlandscape.app_user.NavigationEntities.AlertActivity;


public class FireBaseMessageHandler extends FirebaseMessagingService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    public FireBaseMessageHandler() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("From: " , remoteMessage.getFrom());
        String message = "";
        String id = "";
        if (remoteMessage.getData().size() > 0) {
            Log.e("Message data payload: ", remoteMessage.getData() + "");
        }
        if (remoteMessage.getNotification() != null) {
            Log.e("Message: ", remoteMessage.getNotification().getBody() + "");
            Log.e("Id: ", remoteMessage.getNotification().getTitle() + "");
            id = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
        }
        if (message != null) {
            if (message.contains("")) {
                String messageFormat = message;
                Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
                intent.putExtra("message", messageFormat);
                intent.putExtra("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("monsharedpref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                String messageFormat = message + AppConstants.OFFER_CONTENT;
//                editor.putString("offer", messageFormat);
//                editor.commit();
//                createNotification("Mon Forwarded Notification", messageFormat);
            }
        }
}
    private void createNotification(String title, String body) {
//        Context context = getBaseContext();
//        Intent intent = new Intent(getApplicationContext(), ForwardedNotificationDialogActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setContentText(body);
//
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}

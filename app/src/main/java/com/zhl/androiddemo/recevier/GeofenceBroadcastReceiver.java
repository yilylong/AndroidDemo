package com.zhl.androiddemo.recevier;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.zhl.androiddemo.LocationActivity;
import com.zhl.androiddemo.R;

import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

/**
 * 描述：地理围栏接受广播
 * Created by zhaohl on 2020-2-24.
 */
public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "mytag";
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "geofencingEvent_error_code: "+geofencingEvent.getErrorCode());
            return;
        }
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Get the transition details as a String.
            String geofenceTransitionDetails = "地理围栏通知";
            if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
                geofenceTransitionDetails = "您已进入指定区域了，你可以打卡了";
            }
            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.d(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.d(TAG, "地理围栏接收失败");
        }
    }

    private void sendNotification(String geofenceTransitionDetails) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext,"channel_imporpant");
        builder.setSmallIcon(R.mipmap.map);
        builder.setContentTitle("提醒");
        builder.setContentText(geofenceTransitionDetails);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(mContext, LocationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1002, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat.from(mContext).notify(10001,builder.build());
    }
}

package com.example.com.freetimes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver  {

    @Override
    public void onReceive(Context context, Intent intent) {

        String thing=intent.getStringExtra("thing");
        int flag=intent.getIntExtra("flag",1);
        Log.d("Alarm:", thing);

        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pIntent = PendingIntent.getActivity(context,1,intent,0);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentTitle("FreeTimes")
                .setSmallIcon(R.drawable.icon)
                .setContentText(thing)
                .setFullScreenIntent(pIntent,true)
                .setAutoCancel(true)
                .build();
        Log.d("DaysActivity", "notification ");
        notification.defaults = Notification.DEFAULT_ALL;
        manager.notify(1,notification);

        Intent i = new Intent(context, LongRunningService.class);
        if(flag!=0){
            i.putExtra("isRepeat",true);
        }else {
            i.putExtra("isRepeat",false);
        }
        context.startService(i);

    }

}

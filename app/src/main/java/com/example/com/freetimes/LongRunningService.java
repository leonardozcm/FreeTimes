package com.example.com.freetimes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LongRunningService extends Service {
    private List<Event>eventList=new ArrayList<>();
    private Event event;

    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        generatelist();
        int position;//
        long selectTime=0;
        long systemTime=System.currentTimeMillis();//当前毫秒数
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(systemTime);
for(position=0;position<eventList.size();position++){
            event=eventList.get(position);
            calendar.set(Calendar.MONTH,event.getMonth()-1);
    calendar.set(Calendar.DATE,event.getDay());
    calendar.set(Calendar.HOUR_OF_DAY,event.getHappen_hour());
    calendar.set(Calendar.MINUTE,event.getHappen_minus());
    calendar.set(Calendar.SECOND,0);
    selectTime=calendar.getTimeInMillis();
    if(selectTime>systemTime)break;
        }
        long triggerAtTime=selectTime-systemTime;
        Log.d("DaysActivity", "service ");
        Intent i=new Intent(this,AlarmReceiver.class);
        //TODOsize=1时停止读取
        i.putExtra("thing",eventList.get(position).getThing());
        i.putExtra("event_size",eventList.size());
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime, pi);
        return super.onStartCommand(intent,flags,startId);
    }

    private void generatelist(){
        eventList= DataSupport.order("month,day,happen_hour,happen_minus").find(Event.class);
    }

    public void onDestroy() {
        super.onDestroy();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

    }
}

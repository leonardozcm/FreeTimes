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


        boolean isRepeat=intent.getBooleanExtra("isRepeat",true);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        generatelist();
        int position;
        long selectTime=0;
        long systemTime=System.currentTimeMillis();//当前毫秒数
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(systemTime);
for(position=0;position<eventList.size();position++){
            event=eventList.get(position);
            calendar.set(Calendar.MONTH,event.getMonth()-1);
    calendar.set(Calendar.DAY_OF_MONTH,event.getDay());
    calendar.set(Calendar.HOUR_OF_DAY,event.getHappen_hour());
    calendar.set(Calendar.MINUTE,event.getHappen_minus());
    calendar.set(Calendar.SECOND,0);
    selectTime=calendar.getTimeInMillis();
    if(selectTime>systemTime)break;
        }
        if(!isRepeat||position==eventList.size()){
new Thread(new Runnable() {
    @Override
    public void run() {
   stopSelf();
    }
}).start();
        }else {
            Log.d("service",Integer.toString(position)+eventList.get(position).getThing()+Integer.toString(eventList.get(position).getHappen_hour())+" size:"+eventList.size());
            Log.d("service",Long.toString(selectTime-systemTime));
            Intent i=new Intent(this,AlarmReceiver.class);
            i.putExtra("thing",eventList.get(position).getThing());
            Log.d("Intent", i.getStringExtra("thing"));
            i.putExtra("flag",eventList.size()-position-1);//是否是最后一个任务
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
            manager.set(AlarmManager.RTC_WAKEUP,selectTime, pi);
        }
        return super.onStartCommand(intent,flags,startId);
    }

    private void generatelist(){
        eventList.clear();
        eventList= DataSupport.order("month,day,happen_hour,happen_minus").find(Event.class);
    }

    public void onDestroy() {
        super.onDestroy();

        Log.d("DaysActivity","1111");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

    }
}

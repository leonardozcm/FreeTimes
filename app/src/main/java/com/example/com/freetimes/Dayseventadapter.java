package com.example.com.freetimes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 59771 on 2017/9/12.
 */

public class Dayseventadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER=0;
    public static final int TYPE_NORMAL=1;
    private View mHeaderView;//不妥的做法，但是暂时没有好的实现方法
    private List<Event> eventsList=new ArrayList<>();
    private int hour;
    private int minutes;

    public View getHeaderView(){
        return mHeaderView;
    }
    public void setHeaderView(View headerView){
        mHeaderView=headerView;
        notifyItemInserted(0);
    }

public Dayseventadapter(List<Event> EventList){
    eventsList=EventList;
}
  class ViewHolder extends RecyclerView.ViewHolder{
        TextView events_name;
        TextView start_time;
        LinearLayout layout;
        public ViewHolder(View view){
            super(view);
            if(view==mHeaderView){
                return;
            }
            layout=(LinearLayout)view.findViewById(R.id.days_event_layout) ;
            events_name=(TextView)view.findViewById(R.id.events_name);
            start_time=(TextView)view.findViewById(R.id.start_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView!=null&&viewType==TYPE_HEADER){
            return new ViewHolder(mHeaderView);
        }
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.days_event_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.start_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //dialog();
                final int position=holder.getAdapterPosition()-1;
                final Event origin_event=eventsList.get(position);
                final String origin_thing=origin_event.getThing();
                final int origin_month=origin_event.getMonth();
                final int origin_day=origin_event.getDay();
                final int origin_hour=origin_event.getHappen_hour();
                final int origin_minutes=origin_event.getHappen_minus();
                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutInflater=LayoutInflater.from(v.getContext());
                final View dialogview=layoutInflater.inflate(R.layout.scrollview,(ViewGroup)v.findViewById(R.id.dialogview));
                builder.setView(dialogview);
                TimePicker timePicker=(TimePicker)dialogview.findViewById(R.id.timepicker);
                timePicker.setIs24HourView(true);
                Calendar c=Calendar.getInstance();
                timePicker.setCurrentHour(origin_hour);
                timePicker.setCurrentMinute(origin_minutes);
                hour = c.get(Calendar.HOUR);
                minutes = c.get(Calendar.MINUTE);
   timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
       @Override
       public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
           hour=hourOfDay;
           minutes=minute;

       }
   });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //添加储存数据逻辑
                        eventsList.get(position).setHappen_hour(hour);
                        eventsList.get(position).setHappen_minus(minutes);
                        notifyItemChanged(position+1);
                     Event event=new Event();
                        event.setHappen_hour(hour);event.setHappen_minus(minutes);
                        event.updateAll("thing = ? and month = ?and day =? and happen_hour = ? and happen_minus=?",origin_thing,Integer.toString(origin_month),Integer.toString(origin_day),Integer.toString(origin_hour),Integer.toString(origin_minutes));
                        Intent intent = new Intent(view.getContext(),LongRunningService.class);
                        intent.putExtra("isRepeat",true);
                        view.getContext().startService(intent);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
            if(viewholder instanceof ViewHolder){
                Event event=eventsList.get(position-1);
                ((ViewHolder)viewholder).events_name.setText(event.getThing());
                ((ViewHolder)viewholder).start_time.setText(String.format("%02d",event.getHappen_hour())+":"+String.format("%02d",event.getHappen_minus()));
                switch ((int)(Math.random()*5)){
                    case 0: ((ViewHolder)viewholder).layout.setBackgroundResource(R.drawable.blue1);break;
                    case 1: ((ViewHolder)viewholder).layout.setBackgroundResource(R.drawable.blue2);break;
                    case 2: ((ViewHolder)viewholder).layout.setBackgroundResource(R.drawable.blue3);break;
                    case 3: ((ViewHolder)viewholder).layout.setBackgroundResource(R.drawable.blue4);break;
                    case 4: ((ViewHolder)viewholder).layout.setBackgroundResource(R.drawable.blue5);break;
                    default:break;
                }
            }

    }

    @Override
    public int getItemCount() {

        if(mHeaderView==null){
            return eventsList.size();
        }else {
            return eventsList.size()+1;
        }

    }

    /*
    加入header需要重写的函数
     */

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView==null)
            return TYPE_NORMAL;
        if (position==0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }
    /*
用于滑动的方法
 */
    public void onItemDismiss(int position){
        if(position==0){

        }else {
            int month=eventsList.get(position-1).getMonth();
            int day=eventsList.get(position-1).getDay();
            int hours=eventsList.get(position-1).getHappen_hour();
            int minus=eventsList.get(position-1).getHappen_minus();
            String thing=eventsList.get(position-1).getThing();
            DataSupport.deleteAll(Event.class,"month=? and day=? and thing=? and happen_hour=? and happen_minus=?",Integer.toString(month),Integer.toString(day),thing,Integer.toString(hours),Integer.toString(minus));
            eventsList.remove(position-1);
            notifyItemRemoved(position);
        }

    }

}

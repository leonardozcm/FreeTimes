package com.example.com.freetimes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 59771 on 2017/9/16.
 */

public class Fastaddadapter extends RecyclerView.Adapter<Fastaddadapter.ViewHolder> {
   // private String[] addeventlist;
   private List<String> addeventlist=new ArrayList<>();
    private  List<Event> eventsList=new ArrayList<>();
    private Dayseventadapter dayseventadapter;
    private int day;
    private int month;
    int hour;
    int minutes;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView=(TextView)view.findViewById(R.id.fast_add_event_text);
        }
    }

    public Fastaddadapter(List<String> maddeventlist,List<Event> EventList,int Month,int Day,Dayseventadapter mdayseventadapter){
        addeventlist=maddeventlist;
        eventsList=EventList;
        month=Month;
        day=Day;
       dayseventadapter=mdayseventadapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fast_add_eventitem,parent,false);
       final ViewHolder holder=new ViewHolder(view);
        holder.textView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
              //  Log.d("DaysActivity", "onLongClick: is OK");
                final int position=holder.getAdapterPosition();

                if(position!=addeventlist.size()-1){
                    final String str=addeventlist.get(position);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater=LayoutInflater.from(v.getContext());
                    final View dialogview=layoutInflater.inflate(R.layout.addfast_delete,(ViewGroup)v.findViewById(R.id.addfast_delete));
                    TextView textView=(TextView)dialogview.findViewById(R.id.delete_hint) ;
                    textView.setText("你确定要删除 "+str+" 添加按钮？");
                    builder.setView(dialogview);
                    builder.setPositiveButton("确定删除",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(position!=addeventlist.size()-1){
                                DaysActivity.stringList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }

                return true;
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
               //String str=null;
                if(position!=addeventlist.size()-1){
                    final String str=addeventlist.get(position);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater=LayoutInflater.from(v.getContext());
                    final View dialogview=layoutInflater.inflate(R.layout.addeventview,(ViewGroup)v.findViewById(R.id.addeventview));
                    EditText editText=(EditText)dialogview.findViewById(R.id.add_event) ;
                    editText.setText(str);
                    builder.setView(dialogview);
                    TimePicker timePicker=(TimePicker)dialogview.findViewById(R.id.addeventview_timepicker);
                    timePicker.setIs24HourView(true);
                    Calendar c=Calendar.getInstance();
                    timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
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
                          Event event=new Event(str,month,day,hour,minutes);
                            eventsList.add(event);
                           dayseventadapter.notifyItemInserted(eventsList.size()+1);
                            event.save();
                            Intent intent = new Intent(dialogview.getContext(), LongRunningService.class);
                            intent.putExtra("isRepeat",true);
                            dialogview.getContext().startService(intent);
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }else{
                    final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater=LayoutInflater.from(v.getContext());
                    final View dialogview=layoutInflater.inflate(R.layout.add_newevent,(ViewGroup)v.findViewById(R.id.add_newevent));
                    final EditText editText=(EditText)dialogview.findViewById(R.id.addnewevent_EditText);
                    builder.setView(dialogview);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String str=editText.getText().toString();
                                 DaysActivity.setFast_add_event(str);
                            notifyItemInserted(DaysActivity.stringList.size()-2);
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str=addeventlist.get(position);
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return addeventlist.size();
    }
}

package com.example.com.freetimes;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import java.util.Calendar;

/**
 * Created by 59771 on 2017/9/16.
 */

public class Fastaddadapter extends RecyclerView.Adapter<Fastaddadapter.ViewHolder> {
    private String[] addeventlist;
    int hour;
    int minutes;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView=(TextView)view.findViewById(R.id.fast_add_event_text);
        }
    }

    public Fastaddadapter(String[] maddeventlist){
        addeventlist=maddeventlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fast_add_eventitem,parent,false);
       final ViewHolder holder=new ViewHolder(view);
        holder.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String str=null;
                if(position!=addeventlist.length-1){
                    str=addeventlist[position];
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
                            //TODO 数据储存逻辑
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }else{
                    //TODO 新建建议活动逻辑
                }


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str=addeventlist[position];
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return addeventlist.length;
    }
}

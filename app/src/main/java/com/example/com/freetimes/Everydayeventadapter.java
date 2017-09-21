package com.example.com.freetimes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59771 on 2017/9/21.
 */

public class Everydayeventadapter extends RecyclerView.Adapter<Everydayeventadapter.ViewHolder> {
    private List<Event> eventList=new ArrayList<>();

    static  class ViewHolder extends RecyclerView.ViewHolder{
         TextView dialog_eventname;
         TextView dialog_eventtime;
        ImageButton dialog_eventdelete;
        public ViewHolder(View view){
            super(view);
            dialog_eventname=(TextView)view.findViewById(R.id.dialog_eventname);
            dialog_eventtime=(TextView)view.findViewById(R.id.dialog_eventtime);
            dialog_eventdelete=(ImageButton)view.findViewById(R.id.dialog_eventdelete);
        }
    }

    public Everydayeventadapter(List<Event> EventList){
        eventList=EventList;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_eventitem,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.dialog_eventdelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               int position=viewHolder.getAdapterPosition();
                Event event=eventList.get(position);
                String thing=event.getThing();
                int hour=event.getHappen_hour();
                int minuates=event.getHappen_minus();
                eventList.remove(position);
                notifyItemRemoved(position);
                DataSupport.deleteAll(Event.class,"happen_hour = ? and happen_minus = ? and thing = ?",Integer.toString(hour),Integer.toString(minuates),thing);

                Intent intent = new Intent(v.getContext(), LongRunningService.class);
                intent.putExtra("isRepeat",true);
                v.getContext().startService(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      Event event=eventList.get(position);
        holder.dialog_eventname.setText(event.getThing());
        holder.dialog_eventtime.setText(event.getHappen_hour()+":"+event.getHappen_minus());
    }

    @Override
    public int getItemCount() {
     return eventList.size();
    }
}

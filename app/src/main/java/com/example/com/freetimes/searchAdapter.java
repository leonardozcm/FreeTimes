package com.example.com.freetimes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 搜索框的adapter
 * Created by 59771 on 2017/9/18.
 */

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {
    private List<Event> eventList=new ArrayList<>();

    public searchAdapter(List<Event> EventList){
        eventList=EventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView search_day;
        TextView search_name;

        public ViewHolder(View v){
            super(v);
            view=v;
            search_day=(TextView)view.findViewById(R.id.search_day);
            search_name=(TextView)view.findViewById(R.id.search_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int position=holder.getAdapterPosition();
                        Log.d(TAG, Integer.toString(position));
                        Event event=eventList.get(position);
                        int month=event.getMonth();
                        int day=event.getDay();
                        Intent intent=new Intent(v.getContext(),DaysActivity.class);
                        intent.putExtra("month",month);
                        intent.putExtra("date",day);
                        v.getContext().startActivity(intent);
                    }
                });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event=eventList.get(position);
        holder.search_name.setText(event.getThing());
        holder.search_day.setText(event.getMonth()+"月"+event.getDay()+"日");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

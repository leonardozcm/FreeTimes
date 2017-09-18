package com.example.com.freetimes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        TextView search_day;
        TextView search_name;

        public ViewHolder(View view){
            super(view);
            search_day=(TextView)view.findViewById(R.id.search_day);
            search_name=(TextView)view.findViewById(R.id.search_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
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

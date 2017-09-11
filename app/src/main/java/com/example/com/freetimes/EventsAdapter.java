package com.example.com.freetimes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 59771 on 2017/9/10.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>  {
    private Context mContext;

    private List<Event> mEventsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventstext;

        public ViewHolder(View view){
            super(view);
            eventstext=(TextView)view.findViewById(R.id.event_title);
        }
    }

    public EventsAdapter(List<Event> Eventslist){
        mEventsList=Eventslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
      /*  if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event=mEventsList.get(position);
        holder.eventstext.setText(event.getThing());
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }
}

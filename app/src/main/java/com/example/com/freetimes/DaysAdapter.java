package com.example.com.freetimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 59771 on 2017/9/8.
 */

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    private Context mContext;

    private List<Day> mDaysList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        LinearLayout dayslayout;
        TextView daystext;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
           dayslayout=(LinearLayout)view.findViewById(R.id.Days_layout);
            daystext=(TextView)view.findViewById(R.id.date_title);
        }
    }

    public DaysAdapter(List<Day> Dayslist){
        mDaysList=Dayslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.days_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Intent intent=new Intent(v.getContext(),DaysActivity.class);
                Day day=mDaysList.get(position);
                int date=day.getDate();
                int month=day.getMonth();
                intent.putExtra("month",month);
                intent.putExtra("date",date);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Day day=mDaysList.get(position);
        holder.daystext.setText(day.getDay());
        holder.dayslayout.setBackgroundResource(day.getImageId());
    }

    @Override
    public int getItemCount() {
        return mDaysList.size();
    }
}

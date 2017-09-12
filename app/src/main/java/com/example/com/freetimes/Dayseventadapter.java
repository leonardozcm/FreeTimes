package com.example.com.freetimes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 59771 on 2017/9/12.
 */

public class Dayseventadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER=0;
    public static final int TYPE_NORMAL=1;
    private View mHeaderView;//不妥的做法，但是暂时没有好的实现方法
    private ArrayList<Event> eventsList=new ArrayList<>();


    public View getHeaderView(){
        return mHeaderView;
    }
    public void setHeaderView(View headerView){
        mHeaderView=headerView;
        notifyItemInserted(0);
    }


  class ViewHolder extends RecyclerView.ViewHolder{
        TextView events_name;
        TextView start_time;
        TextView end_time;
        public ViewHolder(View view){
            super(view);
            if(view==mHeaderView){
                return;
            }
            events_name=(TextView)view.findViewById(R.id.events_name);
            start_time=(TextView)view.findViewById(R.id.start_time);
            end_time=(TextView)view.findViewById(R.id.end_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView!=null&&viewType==TYPE_HEADER){
            return new ViewHolder(mHeaderView);
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.days_event_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
            if(viewholder instanceof ViewHolder){
                Event event=eventsList.get(position-1);
                ((ViewHolder)viewholder).events_name.setText(event.getThing());
                ((ViewHolder)viewholder).start_time.setText("Starts at:"+event.getHappen_hour()+":"+event.getHappen_minus());
                ((ViewHolder)viewholder).end_time.setText("Ends at"+event.getEnd_hour()+":"+event.getEnd_minus());
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

    public void addDatas(ArrayList<Event> datas) {
        eventsList.addAll(datas);
        notifyDataSetChanged();
    }
}

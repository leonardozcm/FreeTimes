package com.example.com.freetimes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59771 on 2017/9/12.
 */

public class Dayseventadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER=0;
    public static final int TYPE_NORMAL=1;
    private View mHeaderView;//不妥的做法，但是暂时没有好的实现方法
    private List<Event> eventsList=new ArrayList<>();


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
        TextView end_time;
      LinearLayout layout;
        public ViewHolder(View view){
            super(view);
            if(view==mHeaderView){
                return;
            }
            layout=(LinearLayout)view.findViewById(R.id.days_event_layout) ;
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
                ((ViewHolder)viewholder).end_time.setText("Ends   at:"+event.getEnd_hour()+":"+event.getEnd_minus());
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
        int day=eventsList.get(position-1).getDay();
        int hours=eventsList.get(position-1).getHappen_hour();
        int minus=eventsList.get(position-1).getHappen_minus();
        String thing=eventsList.get(position-1).getThing();
        DataSupport.deleteAll(Event.class,"day=? and thing=? and happen_hour=? and happen_minus=?",Integer.toString(day),thing,Integer.toString(hours),Integer.toString(minus));
       eventsList.remove(position-1);
        notifyItemRemoved(position);

    }

}

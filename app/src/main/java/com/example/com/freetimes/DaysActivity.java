package com.example.com.freetimes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.example.com.freetimes.Util.NewItemDecoration;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DaysActivity extends BaseActivity {
  static public int update_times=0;
private DrawerLayout mDrawerLayout;
    private RecyclerView fast_add_event;
    private List<Event> eventsList=new ArrayList<>();
    private Dayseventadapter dayseventadapter;
    private int data;
    int hour;
    int minutes;
    static public List<String> stringList=new ArrayList<>();
   // private String[] stringList={"起床","约会","充热水卡","抄实验报告","自习","洗衣服","打天梯","换被套","ADD MORE..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        LitePal.getDatabase();

        Toolbar toolbar=(Toolbar)findViewById(R.id.days_toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.days_drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
/*
设置标题
 */
        Intent intent=getIntent();
        data=intent.getIntExtra("date",1)+1;
        if(actionBar!=null) {
            actionBar.setTitle("9月" + data + "日");
        }
        /*
        加载事件视图
         */
            initEvents();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.event_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new NewItemDecoration());
        dayseventadapter=new Dayseventadapter(eventsList);
        recyclerView.setAdapter(dayseventadapter);
        setHeadView(recyclerView);

/*
设置Helper
 */
        ItemTouchHelper.Callback callback=new DayItemTouchHelperCallback(dayseventadapter);
        ItemTouchHelper touchHelper=new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        /*
        DrawerLayout中的选择视图
         */
        if(update_times==0){
            initfastadd();;update_times++;
        }
        mDrawerLayout=(android.support.v4.widget.DrawerLayout)findViewById(R.id.days_drawer_layout);
        fast_add_event=(RecyclerView)findViewById(R.id.fast_add_event);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        fast_add_event.setLayoutManager(linearLayoutManager1);
        fast_add_event.addItemDecoration(new NewItemDecoration());
        fast_add_event.setAdapter(new Fastaddadapter(stringList,eventsList,data,dayseventadapter));

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.days_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_all:
                DataSupport.deleteAll(Event.class,"day=? ",Integer.toString(data));
                        for(int n=0;n<eventsList.size();n++) {
                            dayseventadapter.notifyItemRemoved(1);
                        }

                //dayseventadapter.notifyItemRangeRemoved(1,eventsList.size());
                eventsList.clear();
                break;
            case R.id.add:
                   final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                LayoutInflater layoutInflater=LayoutInflater.from(this);
                final View dialogview=layoutInflater.inflate(R.layout.addeventview,(ViewGroup)findViewById(R.id.addeventview));
                builder.setView(dialogview);
                TimePicker timePicker=(TimePicker)dialogview.findViewById(R.id.addeventview_timepicker);
                timePicker.setIs24HourView(true);
                Calendar c=Calendar.getInstance();
                hour=c.get(Calendar.HOUR_OF_DAY);
                minutes=c.get(Calendar.MINUTE);
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
                        //添加储存数据逻辑
                        EditText editText=(EditText)dialogview.findViewById(R.id.add_event);
                        String str=editText.getText().toString();
                        Event event=new Event(str,data,hour,minutes);
                        eventsList.add(event);
                        dayseventadapter.notifyItemInserted(eventsList.size()+1);
                        event.save();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                break;
            case R.id.choose:/*Test：添加数据库*/
                for(int n=0;n<5;n++){
                    Event event=new Event();
                    switch (n%5){
                        case 0:  event=new Event("起床",data,6,30);
                            break;
                        case 1:  event=new Event("早饭",data,7,00);
                            break;
                        case 2:  event=new Event("上午课",data,8,00);
                            break;
                        case 3:  event=new Event("下午课",data,13,30);
                            break;
                        case 4:  event=new Event("睡觉",data,22,00);
                            break;
                        default:break;
                    }
                    eventsList.add(event);
                    event.save();
                    dayseventadapter.notifyItemInserted(n+1);
                }
                Toast.makeText(DaysActivity.this,"ADD SUCCEED",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                DaysActivity.this.finish();break;
            default:break;
        }
        return true;
    }

    private void initEvents(){
        eventsList= DataSupport.where("day=?",Integer.toString(data)).order("happen_hour,happen_minus").find(Event.class);
       // eventsList= DataSupport.where("day=?",Integer.toString(data)).find(Event.class);
    }
    /*
    设置headview
     */
    private void setHeadView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.days_header, view, false);
       dayseventadapter .setHeaderView(header);
    }
    /*
    添加“快速添加”标签接口
     */
   static public void setFast_add_event(String str){
           stringList.add(stringList.size()-1,str);
    }
    /*
    快速事件初始化
     */
    private void initfastadd(){
        stringList.add(new String("起床"));
        stringList.add(new String("约会"));
        stringList.add(new String("充热水卡"));
        stringList.add(new String("抄实验报告"));
        stringList.add(new String("洗衣服"));
        stringList.add(new String("打天梯"));
        stringList.add(new String("换被套"));
        stringList.add(new String("ADD MORE..."));
    }
}

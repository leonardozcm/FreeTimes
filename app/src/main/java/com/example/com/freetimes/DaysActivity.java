package com.example.com.freetimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.com.freetimes.Util.ItemDecoration;

import java.util.ArrayList;

public class DaysActivity extends BaseActivity {
private DrawerLayout mDrawerLayout;
    private ArrayList<Event> eventsList=new ArrayList<>();
    private Dayseventadapter dayseventadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

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
        int date=intent.getIntExtra("date",1)+1;
         actionBar.setTitle("9月"+date+"日");
        /*
        加载事件视图
         */
        initEvents();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.event_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoration()) ;
        dayseventadapter=new Dayseventadapter();
        recyclerView.setAdapter(dayseventadapter);
        dayseventadapter.addDatas(eventsList);
        setHeadView(recyclerView);

/*
启动数据库
 */
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.days_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.select_all:
                break;
            case R.id.delete:
                break;
            case R.id.choose:
                break;
            case android.R.id.home:
                DaysActivity.this.finish();break;
            default:break;
        }
        return true;
    }

    private void initEvents(){
        for(int n=0;n<30;n++){
            int day=0;
            switch (n%5){
                case 0: Event event0=new Event("起床",day+1,6,30,7,00);
                    eventsList.add(event0);break;
                case 1: Event event1=new Event("早饭",day+1,7,00,7,30);
                    eventsList.add(event1);break;
                case 2: Event event2=new Event("上午课",day+1,8,00,9,30);
                    eventsList.add(event2);break;
                case 3: Event event3=new Event("下午课",day+1,13,30,14,30);
                    eventsList.add(event3);break;
                case 4: Event event4=new Event("睡觉",day+1,22,00,24,00);
                    eventsList.add(event4);break;
                default:break;
            }
        }
    }

    /*
    设置headview
     */
    private void setHeadView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.days_header, view, false);
       dayseventadapter .setHeaderView(header);
    }
}

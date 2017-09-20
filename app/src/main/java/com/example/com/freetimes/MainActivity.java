package com.example.com.freetimes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.com.freetimes.R.menu.toolbar;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
private DrawerLayout mDrawerLayout;
    private TabLayout layoutTab;
    private ViewPager viewPagerTab;

    private String[] Tablist=new String[]{"DATE","EVENT"};
    private List<Fragment>fragmentList;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Event> searchlist=new ArrayList<>();
    private String searchfor;
    private searchAdapter msearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置toolbar标题
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        /*
        *侧边栏
         */
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.search_icon);
            actionBar.setHomeButtonEnabled(true);
        }

        /*
        加载日期/事件视图
         */
        initView();
        initData();

        /*
        加载搜索框视图
         */
        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.search_result);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final EditText editText=(EditText)findViewById(R.id.search_msg);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchfor=editText.getText().toString();
                if(searchfor.length()!=0){
                    searchlist= DataSupport.where("thing like ?","%"+searchfor+"%").find(Event.class);
                    msearchAdapter=new searchAdapter(searchlist);
                    recyclerView.setAdapter(msearchAdapter);
                    msearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        /*
        启动service
         */
       /* Intent intent = new Intent(MainActivity.this, LongRunningService.class);
        intent.putExtra("isRepeat",true);
        startService(intent);*/
    }

    private void initView(){
        layoutTab = (TabLayout) findViewById(R.id.layoutTab);
        viewPagerTab = (ViewPager) findViewById(R.id.viewpagerTab);
        fragmentList = new ArrayList<>();

            TabLayoutFragment tabLayoutFragment = new TabLayoutFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", String.valueOf(0));
            tabLayoutFragment.setArguments(bundle);
            fragmentList.add(tabLayoutFragment);

        TabLayoutFragment2 tabLayoutFragment2 = new TabLayoutFragment2();
        Bundle bundle2 = new Bundle();
        bundle2.putString("flag", String.valueOf(1));
        tabLayoutFragment2.setArguments(bundle2);
        fragmentList.add(tabLayoutFragment2);

    }
    private void initData() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),Tablist,fragmentList);
        viewPagerTab.setAdapter(viewPagerAdapter);
        viewPagerTab.setOffscreenPageLimit(3);
        viewPagerTab.addOnPageChangeListener(this);
        layoutTab.setupWithViewPager(viewPagerTab);
        layoutTab.setTabsFromPagerAdapter(viewPagerAdapter);
    }
    /*
    生成菜单
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(toolbar,menu);
        return true;
    }
/*
菜单选择
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this,"期待设置功能上线~",Toast.LENGTH_SHORT).show();break;
            case R.id.delete:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                LayoutInflater layoutInflater=LayoutInflater.from(this);
                final View dialogview=layoutInflater.inflate(R.layout.addfast_delete,(ViewGroup)this.findViewById(R.id.addfast_delete));
                TextView textView=(TextView)dialogview.findViewById(R.id.delete_hint) ;
                textView.setText("你确定要删除所有事项吗？\n删除后无法恢复");
                builder.setView(dialogview);
                builder.setPositiveButton("确定删除",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Event.class);
                        Intent intent = new Intent(dialogview.getContext(), LongRunningService.class);
                        intent.putExtra("isRepeat",true);
                        startService(intent);
                       Toast.makeText(dialogview.getContext(),"已删除所有事项",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
               break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:break;
        }
        return true;
    }

    /*
    划页重写三接口函数
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

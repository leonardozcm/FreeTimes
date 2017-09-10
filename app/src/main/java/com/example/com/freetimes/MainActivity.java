package com.example.com.freetimes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.com.freetimes.R.id.viewpagerTab;
import static com.example.com.freetimes.R.menu.toolbar;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
private DrawerLayout mDrawerLayout;
    private TabLayout layoutTab;
    private ViewPager viewPagerTab;

    private String[] Tablist=new String[]{"DATE","EVENT"};
    private List<Fragment>fragmentList;
    private ViewPagerAdapter viewPagerAdapter;

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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setHomeButtonEnabled(true);
        }

        /*
        加载日期视图
         */
        initView();
        initData();
    }

    private void initView(){
        layoutTab = (TabLayout) findViewById(R.id.layoutTab);
        viewPagerTab = (ViewPager) findViewById(viewpagerTab);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TabLayoutFragment tabLayoutFragment = new TabLayoutFragment();
            Bundle bundle = new Bundle();
            bundle.putString("flag", String.valueOf(i));
            tabLayoutFragment.setArguments(bundle);
            fragmentList.add(tabLayoutFragment);
        }
    }
    private void initData() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),Tablist,fragmentList);
        viewPagerTab.setAdapter(viewPagerAdapter);
        viewPagerTab.setOffscreenPageLimit(3);
        viewPagerTab.addOnPageChangeListener(this);
//        代码中优先级高于xml
//        layoutTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        layoutTab.setupWithViewPager(viewPagerTab);
        layoutTab.setTabsFromPagerAdapter(viewPagerAdapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.backup:
             ;break;

            case R.id.settings:
                Toast.makeText(this,"settings",Toast.LENGTH_SHORT).show();break;
            case R.id.delete:
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:break;
        }
        return true;
    }
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

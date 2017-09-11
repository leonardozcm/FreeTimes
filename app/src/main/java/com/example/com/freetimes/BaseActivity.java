package com.example.com.freetimes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.com.freetimes.Util.ActivityCollector;

/**
 * Created by 59771 on 2017/9/11.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

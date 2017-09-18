package com.example.com.freetimes.Util;

import java.util.Calendar;

/**
 * 自动填充时间工具
 * Created by 59771 on 2017/9/18.
 */

public class DateAutoMaker {


    public static int getDATE(int n){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+n);
        return calendar.get(Calendar.DATE);
    }

    public static int getMOUTH(int n){
       Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+n);
        return calendar.get(Calendar.MONTH)+1;
    }

}

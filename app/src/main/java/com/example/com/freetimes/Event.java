package com.example.com.freetimes;

import org.litepal.crud.DataSupport;

/**
 * Created by 59771 on 2017/9/8.
 */

public class Event extends DataSupport{

    private String thing;//事件内容
    private int month;//月份
    private int day;//事件日期

    //开始时间
    private int happen_hour;
    private int happen_minus;

 /*
 为了快速输入定义了三种构造函数
  */
 public Event(){}

    public Event(String thing,int month,int day,int happen_hour,int happen_minus){
        this.thing=thing;
        this.month=month;
        this.day=day;
        this.happen_hour=happen_hour;
        this.happen_minus=happen_minus;
    }
/*
设置事件内容
 */
    public void setHappen_hour(int happen_hour) {
        this.happen_hour = happen_hour;
    }
    public void setHappen_minus(int happen_minus) {
        this.happen_minus = happen_minus;
    }
    public void setThing(String thing) {
        this.thing = thing;
    }
    public void setDay(int day) {this.day = day;}
    public void setMonth(int month) {
        this.month = month;
    }

    /*
读取数据
 */
    public String getThing() {
        return thing;
    }

    public int getDay() {return day;}

    public int getHappen_hour() {
        return happen_hour;
    }

    public int getHappen_minus() {
        return happen_minus;
    }

    public int getMonth() {
        return month;
    }

}

package com.example.com.freetimes;

import org.litepal.crud.DataSupport;

/**
 * Created by 59771 on 2017/9/8.
 */

public class Event extends DataSupport{


    private String thing;//事件内容
    private int day;//事件日期

    //开始时间
    private int happen_hour;
    private int happen_minus;


    //结束时间
    private int end_hour;
    private int end_minus;
 /*
 为了快速输入定义了三种构造函数
  */
 public Event(){}

    public Event(String thing,int day,int happen_hour){
        this.thing=thing;
        this.day=day;
        this.happen_hour=happen_hour;
    }

    public Event(String thing,int day,int happen_hour,int happen_minus){
        this.thing=thing;
        this.day=day;
        this.happen_hour=happen_hour;
        this.happen_minus=happen_minus;
    }
    public Event(String thing,int day,int happen_hour,int happen_minus,int end_hour,int end_minus){
        this.thing=thing;
        this.day=day;
        this.happen_hour=happen_hour;
        this.happen_minus=happen_minus;
        this.end_hour=end_hour;
        this.end_minus=end_minus;
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
    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }
    public void setEnd_minus(int end_minus) {
        this.end_minus = end_minus;
    }
    public void setDay(int day) {this.day = day;}
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

    public int getEnd_hour() {
        return end_hour;
    }

    public int getEnd_minus() {
        return end_minus;
    }

}

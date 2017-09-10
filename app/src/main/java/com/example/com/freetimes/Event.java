package com.example.com.freetimes;

/**
 * Created by 59771 on 2017/9/8.
 */

public class Event {


    public String thing;//事件内容
    public String posi;//事件地点

   //开始时间
    public int happen_hour;
    public int happen_minus;

 /*
 为了快速输入定义了三种构造函数
  */

    public Event(String thing,int happen_hour){
        this.thing=thing;
        this.happen_hour=happen_hour;
    }

    public Event(String thing,int happen_hour,int happen_minus){
        this.thing=thing;
        this.happen_hour=happen_hour;
        this.happen_minus=happen_minus;
    }

    public Event(String thing,int happen_hour,int happen_minus,String posi){
        this.thing=thing;
        this.happen_hour=happen_hour;
        this.happen_minus=happen_minus;
        this.posi=posi;
    }

/*
设置事件内容
 */
    public void setPosi(String posi) {
        this.posi = posi;
    }

    public void setHappen_hour(int happen_hour) {
        this.happen_hour = happen_hour;
    }

    public void setHappen_minus(int happen_minus) {
        this.happen_minus = happen_minus;
    }
    public void setThing(String thing) {
        this.thing = thing;
    }
}

package com.example.com.freetimes;

/**
 * Created by 59771 on 2017/9/8.
 */

public class Day {
    private int date;

    private int imageId;

   // public List<Event> events=new ArrayList<Event>();

    public Day(int date,int imageId){
        this.date=date;
        this.imageId=imageId;
    }

    public String getDate(){
        return "9月"+date+"日";
    }

    public int getImageId(){
        return imageId;
    }

  /*  public void addEvent(Event event){
        events.add(event);
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    public void clear(){
        events.clear();
    }

    public List<Event> getEvents(){
        return events;
    }*/

}

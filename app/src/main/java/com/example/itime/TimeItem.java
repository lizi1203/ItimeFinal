package com.example.itime;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TimeItem implements Serializable {
    private String title;
    private Date date;
    private String description;
    private Bitmap bm;

    public TimeItem(String title, Date date, String description, Bitmap bm) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.bm = bm;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) { this.bm =bm; }


    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        fromCalendar.set(Calendar.MINUTE, startDate.getMinutes());
        fromCalendar.set(Calendar.SECOND, startDate.getSeconds());
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        toCalendar.set(Calendar.MINUTE, endDate.getMinutes());
        toCalendar.set(Calendar.SECOND, endDate.getSeconds());
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((fromCalendar.getTime().getTime() - toCalendar.getTime().getTime()) / 1000 );
    }

}

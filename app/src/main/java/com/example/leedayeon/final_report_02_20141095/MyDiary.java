package com.example.leedayeon.final_report_02_20141095;

import java.io.Serializable;

/**
 * Created by LeeDaYeon on 2016-06-24.
 */
//        _id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, content TEXT, place TEXT, weather TEXT)
public class MyDiary implements Serializable{
    private int _id;
    private String date;
    private String title;
    private String content;
    private String place;
    private String weather;

    private int year;
    private int month;
    private int day;

    public MyDiary(int _id, String content, String date, int day, int month, String place, String title, String weather, int year) {
        this._id = _id;
        this.content = content;
        this.date = date;
        this.day = day;
        this.month = month;
        this.place = place;
        this.title = title;
        this.weather = weather;
        this.year = year;
    }

    public MyDiary(String content, String date, int day, int month, String place, String title, String weather, int year) {
        this.content = content;
        this.date = date;
        this.day = day;
        this.month = month;
        this.place = place;
        this.title = title;
        this.weather = weather;
        this.year = year;
    }

    public MyDiary(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public MyDiary(String content, String date, String place, String title, String weather) {
        this.content = content;
        this.date = date;
        this.place = place;
        this.title = title;
        this.weather = weather;
    }

    public MyDiary(int id, String date, String title, String content, String place, String weather) {
        this._id = id;
        this.content = content;
        this.date = date;
        this.place = place;
        this.title = title;
        this.weather = weather;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
}

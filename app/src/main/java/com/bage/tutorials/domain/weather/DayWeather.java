package com.bage.tutorials.domain.weather;

import java.util.List;

public class DayWeather {

    private String day; // 日 比如 8 日

    private String date; // 日期 2020-02-04

    private String week; // 星期 星期二

    private String wea; // 温度 比如 晴

    private String weaImg;

    private int air;

    private int humidity;

    private String airLevel; // 空气质量

    private String airTips;

    private String tem1;

    private String tem2;

    private String tem;

    private List<String> win;

    private String winSpeed;

    private List<HourWeather> hours;

    private List<TipInfo> tips;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWeaImg() {
        return weaImg;
    }

    public void setWeaImg(String weaImg) {
        this.weaImg = weaImg;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(String airLevel) {
        this.airLevel = airLevel;
    }

    public String getAirTips() {
        return airTips;
    }

    public void setAirTips(String airTips) {
        this.airTips = airTips;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public List<String> getWin() {
        return win;
    }

    public void setWin(List<String> win) {
        this.win = win;
    }

    public String getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(String winSpeed) {
        this.winSpeed = winSpeed;
    }

    public List<HourWeather> getHours() {
        return hours;
    }

    public void setHours(List<HourWeather> hours) {
        this.hours = hours;
    }

    public List<TipInfo> getTips() {
        return tips;
    }

    public void setTips(List<TipInfo> tips) {
        this.tips = tips;
    }
}
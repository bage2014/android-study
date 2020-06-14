package com.bage.tutorials.domain.weather;

import java.util.List;

public class WeekWeather{
    private BasicInfo basicInfo; // 基本信息
    private List<DayWeather> days; // 每天天气情况

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<DayWeather> getDays() {
        return days;
    }

    public void setDays(List<DayWeather> days) {
        this.days = days;
    }
}
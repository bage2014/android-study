package com.bage.tutorials.domain.weather;

public class HourWeather {

    private String hour; // 时
    private String wea; // 天气描述，比如 晴
    private String tem; // 温度
    private String winDirect; // 风向
    private String winSpeed; // 风速

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWinDirect() {
        return winDirect;
    }

    public void setWinDirect(String winDirect) {
        this.winDirect = winDirect;
    }

    public String getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(String winSpeed) {
        this.winSpeed = winSpeed;
    }
}
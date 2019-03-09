package com.example.weather.weatherRecordList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("value")
    @Expose
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @SerializedName("year")
    @Expose
    private int year;

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @SerializedName("month")
    @Expose
    private int month;

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }
}

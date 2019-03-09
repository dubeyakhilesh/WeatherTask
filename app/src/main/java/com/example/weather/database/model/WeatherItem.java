package com.example.weather.database.model;

public class WeatherItem {
    /*--Variables--*/
    String value, year, month;

    /*--Tables, Columns and Query--*/
    public static final String TABLE_NAME = "weather";
    public static final String VALUE = "value";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + VALUE + " TEXT, " + YEAR + " TEXT, "
            + MONTH + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL_RECORD = "SELECT * FROM " + TABLE_NAME;
    public static final String SELECT_ALL_RECORD_YEAR_BASIS = "SELECT * FROM " + TABLE_NAME + " WHERE " + YEAR + " =?";

    public WeatherItem(String value, String year, String month){
        this.value = value;
        this.year = year;
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public String getValue() {
        return value;
    }

    public String getYear() {
        return year;
    }
}

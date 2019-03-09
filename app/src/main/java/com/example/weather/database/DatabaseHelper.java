package com.example.weather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weather.database.model.WeatherItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    /*--Database details--*/
    public static final String DATABASE_NAME = "weatherDb";
    public static final int DATABASE_VERSION = 1;

    /*--Other Variables--*/
    Context context;

    /*--Constructor--*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*--Default methods--*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WeatherItem.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(WeatherItem.DROP_TABLE);
        onCreate(db);


    }

    /*----------------------------Weather Details-----------------------------------------------*/
    public long insertAreaRecord(WeatherItem weatherItem) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherItem.VALUE, weatherItem.getValue());
        contentValues.put(WeatherItem.MONTH, weatherItem.getMonth());
        contentValues.put(WeatherItem.YEAR, weatherItem.getYear());
        long value = sqLiteDatabase.insert(WeatherItem.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return value;
    }

    public ArrayList<WeatherItem> fetchAllWeatherRecord() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(WeatherItem.SELECT_ALL_RECORD, null);
        ArrayList<WeatherItem> weatherItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex(WeatherItem.VALUE));
                String year = cursor.getString(cursor.getColumnIndex(WeatherItem.YEAR));
                String month = cursor.getString(cursor.getColumnIndex(WeatherItem.MONTH));
                WeatherItem weatherItem = new WeatherItem(value, year, month);
                weatherItems.add(weatherItem);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return weatherItems;
    }

    public ArrayList<WeatherItem> fetchAllWeatherRecordYear(String years) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] values = {years};
        Cursor cursor = sqLiteDatabase.rawQuery(WeatherItem.SELECT_ALL_RECORD_YEAR_BASIS, values);
        ArrayList<WeatherItem> weatherItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex(WeatherItem.VALUE));
                String year = cursor.getString(cursor.getColumnIndex(WeatherItem.YEAR));
                String month = cursor.getString(cursor.getColumnIndex(WeatherItem.MONTH));
                WeatherItem weatherItem = new WeatherItem(value, year, month);
                weatherItems.add(weatherItem);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return weatherItems;
    }

    public long deleteAllRecord() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long value = sqLiteDatabase.delete(WeatherItem.TABLE_NAME, null, null);
        sqLiteDatabase.close();
        return value;
    }
}

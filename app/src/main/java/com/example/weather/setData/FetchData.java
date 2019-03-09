package com.example.weather.setData;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.database.DatabaseHelper;
import com.example.weather.database.model.WeatherItem;
import com.example.weather.interfacesItem.SelectItem;
import com.example.weather.utility.AppManager;
import com.example.weather.weatherRecordList.MainActivity;
import com.example.weather.weatherRecordList.dialog.SelectYear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FetchData extends AppCompatActivity implements SelectItem {
    DatabaseHelper databaseHelper;
    String metricKey = "", metricValue = "", location = "";

    FrameLayout flMetric, flLocation;
    Button btnFetch;
    TextView txtMetric, txtLocation;
    ArrayList<String> metrics, locations;
    SelectItem selectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView txtTitle =  (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.fetch_title));
        txtTitle.setVisibility(View.VISIBLE);

        selectItem = this;
        metrics = new ArrayList<>();
        metrics.add("Max Temperature");
        metrics.add("Min Temperature");
        metrics.add("Rainfall (mm)");
        locations = new ArrayList<>();
        locations.add("UK");
        locations.add("England");
        locations.add("Scotland");
        locations.add("Wales");

        txtMetric = (TextView)findViewById(R.id.txtMetric);
        txtLocation = (TextView)findViewById(R.id.txtLocation);

        flMetric = (FrameLayout)findViewById(R.id.flMetric);
        flMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectYear selectYear = new SelectYear(FetchData.this, 0, getString(R.string.fetch_metric), metrics, selectItem);
                selectYear.show();
            }
        });

        flLocation = (FrameLayout)findViewById(R.id.flLocation);
        flLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectYear selectYear = new SelectYear(FetchData.this, 1, getString(R.string.fetch_location), locations, selectItem);
                selectYear.show();
            }
        });

        btnFetch = (Button) findViewById(R.id.btnFetch);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });

        databaseHelper = new DatabaseHelper(this);
    }

    private void fetchData(){
        if(metricKey.length() <= 0){
            AppManager.showToast(this, getString(R.string.fetch_selectMetricData));
            return;
        }

        if(location.length() <= 0){
            AppManager.showToast(this, getString(R.string.fetch_selectLocation));
            return;
        }

        MyAsync myAsync = new MyAsync();
        String url = "https://s3.eu-west-2.amazonaws.com/interview-question-data/metoffice/" + metricKey + "-" + location + ".json";
        myAsync.execute(url);
    }

    public String getOkhttpResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request;
        request = new Request.Builder().url(url).build();
        okhttp3.Response response;
        response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    @Override
    public void selectItem(int type, int position, String value) {
        switch (type){
            case 0:
                metricValue = value;
                txtMetric.setText(value);
                switch (position){
                    case 0:
                        metricKey = "Tmax";
                        break;
                    case 1:
                        metricKey = "Tmin";
                        break;
                    case 2:
                        metricKey = "Rainfall";
                        break;
                }
                break;
            case 1:
                location = value;
                txtLocation.setText(value);
                break;
        }
    }

    class MyAsync extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = AppManager.initProgress(FetchData.this);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String weatherData = null;
            try {
                weatherData = getOkhttpResponse(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return weatherData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result != null){
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    int firstYear = 0, lastYear = 0;
                    if(jsonArray.length() > 0) {
                        databaseHelper.deleteAllRecord();
                        firstYear = jsonArray.getJSONObject(0).getInt("year");
                        lastYear = jsonArray.getJSONObject(jsonArray.length() -1).getInt("year");
                    }
                    long insertRecord = 0;
                    for (int i = 0; i < jsonArray.length(); i ++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        double value = jsonObject.getDouble("value");
                        int year = jsonObject.getInt("year");
                        int month = jsonObject.getInt("month");

                        String monthName = "";
                        switch (month){
                            case 1:
                                monthName = "January";
                                break;
                            case 2:
                                monthName = "February";
                                break;
                            case 3:
                                monthName = "March";
                                break;
                            case 4:
                                monthName = "April";
                                break;
                            case 5:
                                monthName = "May";
                                break;
                            case 6:
                                monthName = "June";
                                break;
                            case 7:
                                monthName = "July";
                                break;
                            case 8:
                                monthName = "August";
                                break;
                            case 9:
                                monthName = "September";
                                break;
                            case 10:
                                monthName = "October";
                                break;
                            case 11:
                                monthName = "November";
                                break;
                            case 12:
                                monthName = "December";
                                break;
                        }

                        WeatherItem weatherItem = new WeatherItem(""+value, "" +year, monthName);
                        insertRecord = databaseHelper.insertAreaRecord(weatherItem);
                    }
                    if(insertRecord > 0){
                        AppManager.showToast(FetchData.this, getString(R.string.fetch_recordSave));
                        moveToNext(firstYear, lastYear, metricValue, location);
                    }else{
                        AppManager.showToast(FetchData.this, getString(R.string.fetch_someThingWrong));
                    }
                    /*for (int i = firstYear; i <= lastYear; i++){
                        years.add("" + i);
                    }
                    getData("" + firstYear);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    private void moveToNext(int firstYear, int lastYear, String selectedMetric, String selectedLocation){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("firstYear", firstYear);
        intent.putExtra("lastYear", lastYear);
        intent.putExtra("selectedMetric", selectedMetric);
        intent.putExtra("selectedLocation", selectedLocation);
        startActivity(intent);
    }
}

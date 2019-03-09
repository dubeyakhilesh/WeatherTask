package com.example.weather.weatherRecordList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.database.DatabaseHelper;
import com.example.weather.database.model.WeatherItem;
import com.example.weather.interfacesItem.SelectItem;
import com.example.weather.utility.AppManager;
import com.example.weather.weatherRecordList.adapter.WeatherAdapter;
import com.example.weather.weatherRecordList.dialog.SelectYear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements MainModel.Model, SelectItem {
    MainPresenter mainPresenter;
    public static String COMPLETE_URL = "";
    DatabaseHelper databaseHelper;
    ArrayList<WeatherItem> weatherItems;
    WeatherAdapter weatherAdapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout srlList;
    RecyclerView rvList;
    FrameLayout flYear;

    ArrayList<String> years;
    TextView txtYearName;
    SelectItem selectedItem;
    int firstYear = 0;
    int lastYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, this);
        mainPresenter.onInitialize();
    }

    @Override
    public void initialize() {
        init();
    }

    @Override
    public void showMessage(String message) {
        mainPresenter.onShowMessage(message);
    }

    @Override
    public void hideSoftKeyBoard(View view) {
        mainPresenter.onHideSoftKeyBoard(view);
    }

    private void init(){
        Intent intent = getIntent();

        firstYear = intent.getIntExtra("firstYear", 0);
        lastYear = intent.getIntExtra("lastYear", 0);

        String selectedMetric = intent.getStringExtra("selectedMetric");
        String selectedLocation = intent.getStringExtra("selectedLocation");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView txtTitle =  (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(selectedMetric +" weather record of " + selectedLocation);
        txtTitle.setVisibility(View.VISIBLE);

        txtYearName = (TextView)findViewById(R.id.txtYearName);
        selectedItem = this;
        rvList = (RecyclerView)findViewById(R.id.rvList);
        srlList = (SwipeRefreshLayout)findViewById(R.id.srlList);
        srlList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("" + firstYear);
            }
        });

        flYear = (FrameLayout)findViewById(R.id.flYear);
        flYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(years != null && years.size() > 0){
                    SelectYear selectYear = new SelectYear(MainActivity.this, 2, getString(R.string.select_year), years, selectedItem);
                    selectYear.show();
                }
            }
        });

        years = new ArrayList<>();
        for (int i = firstYear; i <= lastYear; i++){
            years.add("" + i);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        weatherItems = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherItems);
        databaseHelper = new DatabaseHelper(this);
        rvList.setAdapter(weatherAdapter);
        rvList.setLayoutManager(linearLayoutManager);
        //rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getData("" + firstYear);
    }


    @Override
    public void selectItem(int type, int position, String value) {
        firstYear = Integer.parseInt(value);
        getData(value);
    }

    private void getData(String year){
        if(srlList != null && srlList.isRefreshing())
            srlList.setRefreshing(false);
        txtYearName.setText(getString(R.string.weather_year) + " " + year);
        weatherItems.clear();
        ArrayList<WeatherItem> wi = databaseHelper.fetchAllWeatherRecordYear(year);
        weatherItems.addAll(wi);
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

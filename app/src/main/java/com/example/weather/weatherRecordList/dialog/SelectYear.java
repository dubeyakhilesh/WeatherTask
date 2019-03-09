package com.example.weather.weatherRecordList.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.interfacesItem.SelectItem;
import com.example.weather.utility.RecyclerTouchListener;
import com.example.weather.weatherRecordList.adapter.YearAdapter;

import java.util.ArrayList;

public class SelectYear extends Dialog {
    /*-- define variable --*/
    TextView txtTitle;
    Context context;
    ArrayList<String> years;
    RecyclerView rvList;
    YearAdapter yearAdapter;
    SelectItem selectedItem;
    String title;
    int type;

    ImageView imgClose;

    /*-- define constructor--*/
    public SelectYear(@NonNull Context context, int type, String title, ArrayList<String> years, SelectItem selectedItem) {
        super(context);
        this.context = context;
        this.type = type;
        this.title = title;
        this.years = years;
        this.selectedItem = selectedItem;
    }

    /*-- default dialog method --*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_year);
        init();
    }

    private void init(){
        rvList = (RecyclerView)findViewById(R.id.rvList);
        yearAdapter = new YearAdapter(context, years);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvList.setAdapter(yearAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(selectedItem != null){
                    selectedItem.selectItem(type, position, years.get(position));
                    dismiss();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        imgClose = (ImageView)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

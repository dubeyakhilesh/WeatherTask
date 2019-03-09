package com.example.weather.weatherRecordList;

import android.content.Context;
import android.view.View;

import com.example.weather.base.Presenter;

public class MainPresenter implements Presenter, MainModel.Presenter {
    Context context;
    MainModel.Model model;
    MainPresenter(Context context, MainModel.Model model){
        this.context = context;
        this.model = model;
    }

    @Override
    public void onDestroy() {
        model = null;
    }

    @Override
    public void onInitialize() {
        model.initialize();
    }

    @Override
    public void onShowMessage(String message) {
        model.showMessage(message);
    }

    @Override
    public void onHideSoftKeyBoard(View view) {
        model.hideSoftKeyBoard(view);
    }
}

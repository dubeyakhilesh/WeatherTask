package com.example.weather.setData;

import android.content.Context;
import android.view.View;

import com.example.weather.base.Presenter;

public class FetchDataPresenter implements Presenter, FetchDataModel.Presenter {
    Context context;
    FetchDataModel.Model model;
    public FetchDataPresenter(Context context, FetchDataModel.Model model){

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onInitialize() {

    }

    @Override
    public void onShowMessage(String message) {

    }

    @Override
    public void onHideSoftKeyBoard(View view) {

    }
}

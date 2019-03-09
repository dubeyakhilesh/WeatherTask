package com.example.weather.weatherRecordList;

import android.view.View;

public interface MainModel {
    /*--interface model--*/
    interface Model{
        void initialize();
        void showMessage(String message);
        void hideSoftKeyBoard(View view);
    }

    /*--interface presenter--*/
    interface Presenter{
        void onInitialize();
        void onShowMessage(String message);
        void onHideSoftKeyBoard(View view);
    }
}

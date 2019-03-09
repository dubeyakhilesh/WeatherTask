package com.example.weather.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XT-DS-20 on 13 Dec 2018.
 * @CompanyName: Xipe Tech
 * @ProjectName: compete4Hope Android App
 * @Project Version: 1.0
 * @PageName: AppManager.java
 * @Module Name: Utility
 * @Description : Define all globe use methods
 * @Author: Akhilesh Dubey
 *
 */

public class AppManager {
    /*--Method to concatenate url--*/
    public static String getCompleteUrl(String host, String ws, String name) {
        return host + ws + name;
    }

    /*--Method to show toast message--*/
    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    /*--Method to hide soft key board--*/
    public static void hideSoftKeyboard(Activity activity, View v) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /*--Method to check internet--*/
    public static boolean isConnectingToInternet(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    /*--Method to change character color--*/
    public static void setYellowColor(TextView textView, String str, int n){
        SpannableString WordtoSpan = new SpannableString(str);
        WordtoSpan.setSpan(new ForegroundColorSpan(Color.YELLOW), n, (n+1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(WordtoSpan);
    }

    /*--Method to validate email--*/
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*--Method to validate string--*/
    public static boolean isValidString(String string) {
        if (string != null && string.length() > 0 && !string.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    /*--Method to validate json object--*/
    public static boolean isValidJson(JSONObject jsonObject, String key){
        if (!jsonObject.isNull(key)) {
            if (jsonObject.has(key)) {
                return true;
            }
        }
        return false;
    }

    /*--Set progress dialog--*/
    public static ProgressDialog initProgress(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progress_message));
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    /*--check number--*/
    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    /*--convert to decimal--*/
    public static String convertToDecimal(String strNum) {
        String str = "";
        if(isNum(strNum)) {
            str = String.format("%.2f", Double.parseDouble(strNum));
        }else{
            str = strNum;
        }
        return str;
    }

    /*--convert to decimal--*/
    public static Double convertDecimal(String strNum) {
        if(isNum(strNum)) {
            strNum = convertToDecimal(strNum);
            return Double.parseDouble(strNum);
        }else{
            return  Double.parseDouble("0");
        }
    }

}








package com.example.weather.weatherRecordList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.database.model.WeatherItem;
import com.example.weather.weatherRecordList.model.WeatherData;

import java.util.ArrayList;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherListItem> {
    /*-- define variable --*/
    Context context;
    ArrayList<WeatherItem> weatherItems, backUp;

    /*-- Constructor --*/
    public WeatherAdapter(Context context, ArrayList<WeatherItem> weatherItems){
        this.context = context;
        this.weatherItems = weatherItems;
        backUp = new ArrayList<>();
        backUp.addAll(weatherItems);
    }

    @Override
    public WeatherListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        WeatherListItem weatherListItem = new WeatherListItem(view);
        return weatherListItem;
    }

    @Override
    public void onBindViewHolder(WeatherListItem holder, int position) {
        WeatherItem weatherItem = weatherItems.get(position);

        holder.txtMonth.setText(context.getString(R.string.weather_month) + " " + weatherItem.getMonth());
        holder.txtValue.setText(context.getString(R.string.weather_value) + " " + weatherItem.getValue());
        holder.txtYear.setText(context.getString(R.string.weather_year) + " " + weatherItem.getYear());
    }

    @Override
    public int getItemCount() {
        return weatherItems.size();
    }

    class WeatherListItem extends RecyclerView.ViewHolder{
        TextView txtMonth, txtYear, txtValue;
        public WeatherListItem(View itemView) {
            super(itemView);
            txtMonth = (TextView)itemView.findViewById(R.id.txtMonth);
            txtYear = (TextView)itemView.findViewById(R.id.txtYear);
            txtValue = (TextView)itemView.findViewById(R.id.txtValue);
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        weatherItems.clear();
        if (charText.length() == 0) {
            weatherItems.addAll(backUp);
        } else {
            for (WeatherItem weatherItem : backUp) {
                if (weatherItem.getMonth().toLowerCase(Locale.getDefault()).contains(charText) ||
                        weatherItem.getValue().toLowerCase(Locale.getDefault()).contains(charText) ||
                        weatherItem.getYear().toLowerCase(Locale.getDefault()).contains(charText)) {
                    weatherItems.add(weatherItem);
                }
            }
        }
        // update list
        notifyDataSetChanged();
    }
}

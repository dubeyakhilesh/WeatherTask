package com.example.weather.weatherRecordList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.database.model.WeatherItem;

import java.util.ArrayList;
import java.util.Locale;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.YearItem> {
    /*-- define variable --*/
    Context context;
    ArrayList<String> years, backUp;

    /*-- Constructor --*/
    public YearAdapter(Context context, ArrayList<String> years){
        this.context = context;
        this.years = years;
        backUp = new ArrayList<>();
        backUp.addAll(years);

    }

    @Override
    public YearItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_item, parent, false);
        YearItem yearItem = new YearItem(view);
        return yearItem;
    }

    @Override
    public void onBindViewHolder(YearItem holder, int position) {
        holder.txtYear.setText(years.get(position));
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    class YearItem extends RecyclerView.ViewHolder{
        TextView txtYear;
        public YearItem(View itemView) {
            super(itemView);
            txtYear = (TextView)itemView.findViewById(R.id.txtYear);
        }
    }

    // Filter Class
    /*public void filter(String charText) {
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
    }*/
}

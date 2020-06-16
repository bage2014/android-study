package com.bage.tutorials.ui.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bage.tutorials.R;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.domain.weather.DayWeather;
import com.bage.tutorials.domain.weather.WeekWeather;
import com.bage.tutorials.ui.tv.TVPlayingActivity;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.List;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyViewHolder> {
    private List<DayWeather> list;
    private Context context;

    public WeatherRecyclerViewAdapter(Context context, List<DayWeather> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public WeatherRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_item, parent, false);
        MyViewHolder viewHolder = new WeatherRecyclerViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvDay.setText(list.get(position).getDay());
        holder.tvWeather.setText(list.get(position).getTem());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        TextView tvWeather;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.item_tv_day);
            tvWeather = itemView.findViewById(R.id.item_tv_weather);

            //添加点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DayWeather dayWeather = list.get(getLayoutPosition());
                    LoggerUtils.info(WeatherRecyclerViewAdapter.class, JsonUtils.toJson(dayWeather));
                }
            });
        }
    }


}
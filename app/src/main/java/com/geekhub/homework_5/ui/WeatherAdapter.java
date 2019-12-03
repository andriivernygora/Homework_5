package com.geekhub.homework_5.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhub.homework_5.R;
import com.geekhub.homework_5.data.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.List;

class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView mDate;
        TextView mForecast;
        TextView mHigh;
        TextView mLow;

        WeatherViewHolder(View view) {
            super(view);
            mDate = view.findViewById(R.id.date);
            mForecast = view.findViewById(R.id.forecast);
            mHigh = view.findViewById(R.id.high);
            mLow = view.findViewById(R.id.low);
        }

        void bindViewHolder(final WeatherItem weatherItem) {
            mDate.setText(formatDate(weatherItem.getTimestamp()));
            mForecast.setText(weatherItem.getForecast());
            mHigh.setText(formatTemperature(itemView.getContext(), weatherItem.getHigh()));
            mLow.setText(formatTemperature(itemView.getContext(), weatherItem.getLow()));
        }

        private String formatDate(long timestamp) {
            long timeInMillis = timestamp * 1000L;
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEEE");
            return shortenedDateFormat.format(timeInMillis);
        }

        private String formatTemperature(Context context, double temperature) {
            return context.getString(R.string.format_temperature, temperature);
        }
    }

    private List<WeatherItem> mWeatherItems;

    WeatherAdapter(List<WeatherItem> weatherItems) {
        mWeatherItems = weatherItems;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        holder.bindViewHolder(mWeatherItems.get(position));
    }

    public void addItem(WeatherItem data) {
        mWeatherItems.add(data);
        notifyItemInserted(getItemCount());
    }


    @Override
    public int getItemCount() {
        return mWeatherItems.size();
    }
}
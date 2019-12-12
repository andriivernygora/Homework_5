package com.geekhub.homework_5.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        TextView mCity;
        TextView mForecast;
        TextView mHigh;
        TextView mLow;
        ImageView mIcon;

        WeatherViewHolder(View view) {
            super(view);
            mDate = view.findViewById(R.id.date);
            mCity = view.findViewById(R.id.city);
            mForecast = view.findViewById(R.id.forecast);
            mHigh = view.findViewById(R.id.high);
            mLow = view.findViewById(R.id.low);
            mIcon = view.findViewById(R.id.iconView);
        }

        void bindViewHolder(final WeatherItem weatherItem) {
            mCity.setText(weatherItem.getCityName());
            mDate.setText(formatDate(weatherItem.getTimestamp()));
            mForecast.setText(weatherItem.getForecast());
            mHigh.setText(formatTemperature(itemView.getContext(), weatherItem.getHigh()));
            mLow.setText(formatTemperature(itemView.getContext(), weatherItem.getLow()));

            String srt = "d" + weatherItem.getIcon();
            switch (srt) {
                case "d01d":
                    mIcon.setImageResource(R.drawable.d01d);
                    break;
                case "d02d":
                    mIcon.setImageResource(R.drawable.d02d);
                    break;
                case "d03d":
                    mIcon.setImageResource(R.drawable.d03d);
                    break;
                case "d04d":
                    mIcon.setImageResource(R.drawable.d04d);
                    break;
                case "d09d":
                    mIcon.setImageResource(R.drawable.d09d);
                    break;
                case "d10d":
                    mIcon.setImageResource(R.drawable.d10d);
                    break;
                case "d11d":
                    mIcon.setImageResource(R.drawable.d11d);
                    break;
                case "d13d":
                    mIcon.setImageResource(R.drawable.d13d);
                    break;
            }
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

    @Override
    public int getItemCount() {
        return mWeatherItems.size();
    }
}
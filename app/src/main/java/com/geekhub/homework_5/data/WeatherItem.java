package com.geekhub.homework_5.data;

public class WeatherItem {

    private final String mCityname;
    private final String mIcon;
    private final long mTimestamp;
    private final String mForecast;
    private final double mHigh;
    private final double mLow;

    WeatherItem(String cityName, String icon, long timestamp, String forecast, double high, double low) {
        mIcon = icon;
        mCityname = cityName;
        mTimestamp = timestamp;
        mForecast = forecast;
        mHigh = high;
        mLow = low;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getCityName() {
        return mCityname;
    }
    public String getIcon() {
        return mIcon;
    }

    public String getForecast() {
        return mForecast;
    }

    public double getHigh() {
        return mHigh;
    }

    public double getLow() {
        return mLow;
    }
}

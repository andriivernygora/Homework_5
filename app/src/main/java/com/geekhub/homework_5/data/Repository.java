package com.geekhub.homework_5.data;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import static com.geekhub.homework_5.MyApplication.getAppContext;

public class Repository {

    private static final String LOG_TAG = "WeatherRepository";
    private String cityName;

    public void makeRequest(@NonNull RepositoryCallback repositoryCallback) {
        final WeakReference<RepositoryCallback> callbackReference
                = new WeakReference<>(repositoryCallback);
        OkHttpClient client = new OkHttpClient();
        String url = constructURL();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                RepositoryCallback repositoryCallback = callbackReference.get();
                if (repositoryCallback != null) {
                    repositoryCallback.onDataFailed();
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response)
                    throws IOException {
                String result = response.body().string();
                Log.d("www", result);
                RepositoryCallback repositoryCallback = callbackReference.get();
                if (repositoryCallback != null) {
                    try {
                        List<WeatherItem> weatherItems = getWeatherDataFromJson(result);
                        repositoryCallback.onDataLoaded(weatherItems);
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        repositoryCallback.onDataFailed();
                    }
                }
            }
        });
    }

    private String constructURL() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String units = sharedPreferences.getString("weather_system", "");
        cityName = sharedPreferences.getString("weather_city", "Kiev");

        final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
        final String ID_ICON = "icon";
        final String NAME_PARAM = "q";
        final String UNITS_PARAM = "units";
        final String CNT_PARAM = "cnt";
        final String APPID_PARAM = "APPID";
        final String OPEN_WEATHER_MAP_API_KEY = "dd5581a8bf3c80ea0c75561912f09cf0";
        final int numDays = 40;
        final String iconID = "10d";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(NAME_PARAM, cityName)
                .appendQueryParameter(ID_ICON, iconID)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(CNT_PARAM, Integer.toString(numDays))
                .appendQueryParameter(APPID_PARAM, OPEN_WEATHER_MAP_API_KEY)
                .build();
        return uri.toString();
    }

    private List<WeatherItem> getWeatherDataFromJson(String response) throws JSONException {
        final List<WeatherItem> weatherItems = new ArrayList<>();

        final String JSON_LIST = "list";
        final String JSON_ICON = "icon";
        final String JSON_DT = "dt";
        final String JSON_MAX = "temp_max";
        final String JSON_MIN = "temp_min";
        final String JSON_WEATHER = "weather";
        final String JSON_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(response);
        JSONArray weatherArray = forecastJson.getJSONArray(JSON_LIST);

        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject dayForecast = weatherArray.getJSONObject(i);
            long timeStamp = dayForecast.getLong(JSON_DT);
            JSONObject weather = dayForecast.getJSONArray(JSON_WEATHER).getJSONObject(0);
            String description = weather.getString(JSON_DESCRIPTION);
            String icon = weather.getString(JSON_ICON);
            JSONObject temperature = dayForecast.getJSONObject(JSON_DESCRIPTION);
            double high = temperature.getDouble(JSON_MAX);
            double low = temperature.getDouble(JSON_MIN);

            WeatherItem weatherItem = new WeatherItem(cityName, icon, timeStamp, description, high, low);

            if (i % 8 == 0) {
                weatherItems.add(weatherItem);
            }
        }
        return weatherItems;
    }

}

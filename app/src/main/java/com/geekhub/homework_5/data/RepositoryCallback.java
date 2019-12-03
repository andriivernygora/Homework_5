package com.geekhub.homework_5.data;

import java.util.List;

public interface RepositoryCallback {

    void onDataLoaded(List<WeatherItem> weatherItems);

    void onDataFailed();

}

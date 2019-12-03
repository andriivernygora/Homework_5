package com.geekhub.homework_5.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.geekhub.homework_5.R;
import com.geekhub.homework_5.data.Repository;
import com.geekhub.homework_5.data.WeatherItem;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View{

    Handler mHandler;
    WeatherPresenter mWeatherPresenter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0f);
        mHandler = new Handler();

        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.errorMessage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Repository repository = new Repository();
        mWeatherPresenter = new WeatherPresenter(this, repository);
        mWeatherPresenter.init();
    }

    @Override
    public void initView() {
        message.setVisibility(View.GONE);
    }

    @Override
    public void displayData(final List<WeatherItem> weatherItems) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // remove progress bar and error message
                message.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                // display data
                WeatherAdapter weatherAdapter = new WeatherAdapter(weatherItems);
                recyclerView.setAdapter(weatherAdapter);
            }
        });
    }

    @Override
    public void displayError() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
            }
        });
    }
}

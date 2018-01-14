package com.example.root.mpolispager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.mpolispager.model.AfishaInfo;
import com.example.root.mpolispager.retrofit.App;
import com.example.root.mpolispager.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {


    String href;
    RelativeLayout progress;
    TextView tv_Date;
    TextView tv_Desc;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.darkenStatusBar(this, R.color.status_bar);
        try {
            setContentView(R.layout.activity_news);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
//            upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            progress = (RelativeLayout) findViewById(R.id.news_act_progress);
            tv_Date = (TextView) findViewById(R.id.news_act_date);
            tv_Desc = (TextView) findViewById(R.id.news_act_desc);

            Intent intent = getIntent();
            href = intent.getStringExtra("href");
            String title = intent.getStringExtra("name");
            getSupportActionBar().setTitle(title);

            getNews();
            //toolbar.setSubtitle("Пинск");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNews(){

        progress.setVisibility(View.VISIBLE);

        App.getApi().getNewsId(href).enqueue(new Callback<List<AfishaInfo>>() {
            @Override
            public void onResponse(Call<List<AfishaInfo>> call, Response<List<AfishaInfo>> response) {
                AfishaInfo afishaInfo = response.body().get(0);

                tv_Date.setText(afishaInfo.getDate());
                tv_Desc.setText(afishaInfo.getDesc());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<AfishaInfo>> call, Throwable t) {
               showSnack();
            }
        });


    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(progress, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getNews();
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

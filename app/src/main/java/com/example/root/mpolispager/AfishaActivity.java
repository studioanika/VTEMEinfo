package com.example.root.mpolispager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.model.AfishaInfo;
import com.example.root.mpolispager.model.News;
import com.example.root.mpolispager.retrofit.App;
import com.example.root.mpolispager.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfishaActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    RelativeLayout rel_progress;
    TextView tv_date;
    TextView tv_desc;
    ImageView img;
    String href = "";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.darkenStatusBar(this, R.color.colorPrimaryDark);
        setContentView(R.layout.activity_afisha_info);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        href = intent.getStringExtra("href");

        rel_progress = (RelativeLayout) findViewById(R.id.afisha_progress);
        tv_date = (TextView) findViewById(R.id.afisha_date);
        tv_desc = (TextView) findViewById(R.id.afisha_desc);
        img = (ImageView) findViewById(R.id.main_backdrop);

        getArticle(href);
    }

    private void getArticle(String href){
        rel_progress.setVisibility(View.VISIBLE);
        App.getApi().getAfishaId(href).enqueue(new Callback<List<AfishaInfo>>() {
            @Override
            public void onResponse(Call<List<AfishaInfo>> call, Response<List<AfishaInfo>> response) {

                AfishaInfo news = response.body().get(0);

                collapsingToolbar.setTitle(news.getTitle());
                tv_date.setText(news.getDate());
                tv_desc.setText(news.getDesc());
                Glide.with(img.getContext()).load(news.getImg()).into(img);
                rel_progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<AfishaInfo>> call, Throwable t) {
                showSnack();
                rel_progress.setVisibility(View.VISIBLE);
            }
        });

    }
    private void showSnack(){

        Snackbar snackbar = Snackbar.make(img, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getArticle(href);
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }
}

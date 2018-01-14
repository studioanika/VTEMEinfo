package com.example.root.mpolispager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.mpolispager.adapter.AdapterActionShops;
import com.example.root.mpolispager.model.ExampeInfoShop;
import com.example.root.mpolispager.model.Info;
import com.example.root.mpolispager.model.InfoActionShop;
import com.example.root.mpolispager.retrofit.App;
import com.example.root.mpolispager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoShopsActivity extends AppCompatActivity {

    ArrayList<InfoActionShop> arrayList = new ArrayList<>();
    ListView listView;
    ProgressBar progressBar;
    TextView tvEmpty;
    String href = null;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.darkenStatusBar(this, R.color.status_bar);
        try {
            setContentView(R.layout.activity_info_shops);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
//            upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            progressBar = (ProgressBar) findViewById(R.id.progressBarInfoShops);
            listView = (ListView) findViewById(R.id.listViewInfoShops);
            tvEmpty = (TextView) findViewById(R.id.textView36);

            Intent intent = getIntent();
            href = intent.getStringExtra("href");
            String title = intent.getStringExtra("name");
            getSupportActionBar().setTitle("Акции, " + title);
            //toolbar.setSubtitle("Пинск");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getIngoShop(href);
        //new MyTaskGetShops(InfoShopsActivity.this, href).execute();
    }

    private void getIngoShop(String url) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            App.getApi().getInfoAction(url).enqueue(new Callback<List<ExampeInfoShop>>() {
                @Override
                public void onResponse(Call<List<ExampeInfoShop>> call, Response<List<ExampeInfoShop>> response) {
                    List<ExampeInfoShop> list = response.body();
                    if (list.get(0).getInfo() != null) {
                        List<Info> infos = list.get(0).getInfo();
                        arrayList.clear();

                        for (Info info : infos) {
                            arrayList.add(new InfoActionShop(info.getTitle(), info.getPrice(), info.getSale(),
                                    info.getTime(), info.getImage()));
                        }
                    }
                    if (arrayList.size() != 0)
                        listView.setAdapter(new AdapterActionShops(InfoShopsActivity.this, arrayList));
                    else tvEmpty.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<ExampeInfoShop>> call, Throwable t) {
                    showSnack();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(listView, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getIngoShop(href);
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
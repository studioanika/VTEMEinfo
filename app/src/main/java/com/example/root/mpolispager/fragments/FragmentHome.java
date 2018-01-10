package com.example.root.mpolispager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.root.mpolispager.MainActivity;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.adapter.CategoryAdapter;
import com.example.root.mpolispager.adapter.NewsAdapter;
import com.example.root.mpolispager.model.Category;
import com.example.root.mpolispager.model.News;
import com.example.root.mpolispager.retrofit.App;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 1.1.18.
 */

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment {


    View v;

    private static final String TAG = "main";

    MainActivity activity;
    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public FragmentHome(Context _context) {
        this.context = _context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list, container, false);
        return v;
    }

    private void initv(){
        progressBar = (ProgressBar) v.findViewById(R.id.list_progress);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onResume() {
        initv();
        getNews();
        super.onResume();
    }

    private void getNews(){
        progressBar.setVisibility(View.VISIBLE);
        App.getApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                adapterSetup(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showSnack();
            }
        });

    }

    public void adapterSetup(List<News> newsList){
        if(newsList!=null && newsList.size()!=0){
            NewsAdapter adapter = new NewsAdapter(newsList, v.getContext(),0);

            recyclerView.setAdapter(adapter);
        }
    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(recyclerView, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getNews();
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }

}

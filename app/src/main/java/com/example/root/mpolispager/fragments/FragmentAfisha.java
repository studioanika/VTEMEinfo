package com.example.root.mpolispager.fragments;

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

import com.example.root.mpolispager.R;
import com.example.root.mpolispager.adapter.NewsAdapter;
import com.example.root.mpolispager.model.News;
import com.example.root.mpolispager.retrofit.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 4.1.18.
 */

public class FragmentAfisha extends Fragment {

    View v;
    RecyclerView recyclerView;
    FragmentAfisha fragmentAfisha;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list, container, false);
        fragmentAfisha = this;

        return v;
    }

    @Override
    public void onResume() {
        initV();
        getAfisha();
        super.onResume();
    }

    private void initV(){
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        progressBar = (ProgressBar) v.findViewById(R.id.list_progress);
    }

    public void adapterSetup(List<News> newsList){
        if(newsList!=null && newsList.size()!=0){
            NewsAdapter adapter = new NewsAdapter(newsList, getActivity(),1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    private void getAfisha(){
        progressBar.setVisibility(View.VISIBLE);
        App.getApi().getAfisha().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                adapterSetup(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("afisha_err", t.toString());
                progressBar.setVisibility(View.GONE);
                showSnack();
            }
        });
    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(recyclerView, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAfisha();
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }


}

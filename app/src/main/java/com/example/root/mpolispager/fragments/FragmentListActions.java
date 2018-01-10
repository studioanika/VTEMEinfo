package com.example.root.mpolispager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.root.mpolispager.InfoShopsActivity;
import com.example.root.mpolispager.MainActivity;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.adapter.ShopsAdapter;
import com.example.root.mpolispager.model.Datum;
import com.example.root.mpolispager.model.Example;
import com.example.root.mpolispager.model.Shop;
import com.example.root.mpolispager.retrofit.App;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 2.1.18.
 */

@SuppressLint("ValidFragment")
public class FragmentListActions extends Fragment {

    Context context;
    MainActivity activity;
    View v;

    ArrayList<Shop> arrayListShop = new ArrayList<>();
    ListView listView;
    ProgressBar progressBar;

    public FragmentListActions(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list_action, container, false);


        context = v.getContext();

        return v;
    }

    @Override
    public void onResume() {
        listView = (ListView) v.findViewById(R.id.list);
        progressBar = (ProgressBar) v.findViewById(R.id.action_progress);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InfoShopsActivity.class);
                intent.putExtra("href", arrayListShop.get(i).getHref());
                intent.putExtra("name", arrayListShop.get(i).getName());
                startActivity(intent);
            }
        });
        getList("pinsk");
        super.onResume();
    }

    private void getList(String city){
        progressBar.setVisibility(View.VISIBLE);

        App.getApi().getCityAction(city).enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                List<Example> list = response.body();
                List<Datum> datum = list.get(0).getData();
                arrayListShop.clear();
                for(Datum datum1 : datum ){
                    arrayListShop.add(new Shop(datum1.getName(), datum1.getImg(), datum1.getHref()));
                }
                progressBar.setVisibility(View.GONE);
                if(arrayListShop.size()==0) showSnack();
                listView.setAdapter(new ShopsAdapter(context, arrayListShop));

                String s = "";
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showSnack();
            }
        });
    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(listView, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getList("pinsk");
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }
}

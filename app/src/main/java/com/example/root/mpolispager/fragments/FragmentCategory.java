package com.example.root.mpolispager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.root.mpolispager.R;
import com.example.root.mpolispager.adapter.CategoryAdapter;
import com.example.root.mpolispager.model.Category;
import com.example.root.mpolispager.model.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2.1.18.
 */

public class FragmentCategory extends Fragment {

    View v;
    private static final String TAG = "main";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CategoryAdapter adapter;

    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list, container, false);

        return v;
    }

    private void init(){
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        progressBar = (ProgressBar) v.findViewById(R.id.list_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        init();
        getCategory();
        super.onResume();
    }

    private void getCategory(){
        progressBar.setVisibility(View.VISIBLE);
        Log.e(TAG,"getCategory");
        final List<Category> categories = new ArrayList<>();
        db.collection("megapolis/pinsk/category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc : task.getResult()){
                    Category e = new Category();
                    e.setId(doc.getString("id"));
                    e.setName(doc.getString("name"));
                    e.setTitle(doc.getString("img"));
                    Log.e("i",e.getName());

                    categories.add(e);
                }

                adapterSetup(categories);
                progressBar.setVisibility(View.GONE);
            }
        });

        //getCompany();

    }

    private void getCompany(){
        progressBar.setVisibility(View.VISIBLE);
        Log.e(TAG,"getCategory");
        final List<Company> categories = new ArrayList<>();
        db.collection("megapolis/pinsk/category/1/1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc : task.getResult()){
                    Company e = doc.toObject(Company.class);

                    categories.add(e);
                }


                progressBar.setVisibility(View.GONE);
            }
        });

    }

    protected void adapterSetup(final List<Category> list) {
        if(list!=null && list.size()!=0){
            adapter = new CategoryAdapter(list, recyclerView.getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }else Toast.makeText(this.getContext(), "Список пуст...",Toast.LENGTH_SHORT).show();

    }
}

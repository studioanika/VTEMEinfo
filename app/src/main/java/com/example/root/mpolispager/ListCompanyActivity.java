package com.example.root.mpolispager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.root.mpolispager.adapter.CategoryAdapter;
import com.example.root.mpolispager.adapter.CompanyAdapter;
import com.example.root.mpolispager.model.Category;
import com.example.root.mpolispager.model.Company;
import com.example.root.mpolispager.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListCompanyActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RelativeLayout progressBar;
    RecyclerView recyclerView;

    CompanyAdapter adapter;

    private static final String TAG = "list_company";

    private String id = "";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.darkenStatusBar(this, R.color.status_bar);
        setContentView(R.layout.activity_list_company);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);

        initV();
        getCompany();
    }

    private void initV() {

        progressBar = (RelativeLayout) findViewById(R.id.list_company_progress);
        recyclerView = (RecyclerView) findViewById(R.id.list_company_recycler);

    }

    private void getCompany(){

        progressBar.setVisibility(View.VISIBLE);
        Log.e(TAG,"getCategory");
        final List<Company> categories = new ArrayList<>();
        db.collection("megapolis/pinsk/category/"+id+"/1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc : task.getResult()){
                    Company e = doc.toObject(Company.class);

                    categories.add(e);
                }

                adapterSetup(categories);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    protected void adapterSetup(final List<Company> list) {
        if(list!=null && list.size()!=0){
            adapter = new CompanyAdapter(list, recyclerView.getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }else showSnack();

    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(progressBar, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCompany();
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }

    public void startInfo(Company company){

        Intent intent = new Intent(ListCompanyActivity.this, InfoCompanyActivity.class);
        intent.putExtra("obj", company);
        startActivity(intent);
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

    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

}

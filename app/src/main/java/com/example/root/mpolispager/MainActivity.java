package com.example.root.mpolispager;

/**
 * Created by root on 11.1.18.
 */
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.adapter.CategoryAdapter;
import com.example.root.mpolispager.fragments.FragmentAfisha;
import com.example.root.mpolispager.fragments.FragmentCategory;
import com.example.root.mpolispager.fragments.FragmentConverter;
import com.example.root.mpolispager.fragments.FragmentDev;
import com.example.root.mpolispager.fragments.FragmentHome;
import com.example.root.mpolispager.fragments.FragmentListActions;
import com.example.root.mpolispager.model.Category;
import com.example.root.mpolispager.model.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ///////////////////////////-----------------   VIEW  -----

    private static Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    RecyclerView recyclerView;

    ///////////////////////////////////////////////////////////////////////
    private static final String TAG = "main";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CategoryAdapter adapter;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setupToolbar();
        //getCategory();

        fragment = new FragmentHome(this);
        transactionFragment();
    }

    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCity();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = "";

        if(id == R.id.nav_home){
            fragment = new FragmentHome(this);
            title = "Главная";
        }else if(id == R.id.nav_sale){
            fragment = new FragmentListActions(this);
            title = "Акции";
        }else if(id == R.id.nav_film){
            fragment = new FragmentAfisha();
            title = "Анонс";
        }else if(id == R.id.nav_catalog){
            fragment = new FragmentCategory();
            title = "Каталог";
        }else if(id == R.id.nav_cash){
            fragment = new FragmentConverter();
            title = "Конвертер валют";
        }else if(id == R.id.nav_dev){
            fragment = new FragmentDev();
            title = "Связаться с нами";
        }


        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        transactionFragment();

        return true;
    }

    private void getCategory(){
        Log.e(TAG,"getCategory");
        final List<Category> categories = new ArrayList<>();
        db.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
            }
        });
    }

    private void getCity(){
        Log.e(TAG,"getCity");

        View v = navigationView.getHeaderView(0);
        ImageView header_img = v.findViewById(R.id.header_img);
        Glide.with(header_img.getContext()).load(R.drawable.info_3).into(header_img);
        RelativeLayout rel_select_city = (RelativeLayout) v.findViewById(R.id.rel_select_city);

//
    }

    private void showAlert(List<City> cityes){
        final Dialog dialogEdit = new Dialog(navigationView.getContext());
        //dialogEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_select_city);
        String[] names = new String[cityes.size()];
        if(cityes!=null && cityes.size()!=0){


            for(int i =0; i< cityes.size(); i++){

                names[i] = cityes.get(i).getTitle();
            }
        }

        ListView lv = (ListView) dialogEdit.findViewById(R.id.lvCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        lv.setAdapter(adapter);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogEdit.show();
        dialogEdit.getWindow().setAttributes(lp);

    }

    protected void adapterSetup(final List<Category> list) {
        if(list!=null && list.size()!=0){

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            adapter = new CategoryAdapter(list, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }else Toast.makeText(this, "Список пуст...",Toast.LENGTH_SHORT).show();

    }
    private void transactionFragment(){

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void startAfisha(String href, String title){

        Intent intent = new Intent(this, AfishaActivity.class);
        intent.putExtra("href", href);
        intent.putExtra("name", title);
        startActivity(intent);

    }

    public void startNews(String href, String title){
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("href", href);
        intent.putExtra("name", title);
        startActivity(intent);
    }

    public void startListCompany(String id, String title){
        Intent intent = new Intent(MainActivity.this, ListCompanyActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}

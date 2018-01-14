package com.example.root.mpolispager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.model.Company;
import com.example.root.mpolispager.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class InfoCompanyActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    RelativeLayout rel_progress;
    ImageView img;
    Company company;
    TextView tvEmail, tvPhone, tvUNP, tvDesc;
    MenuItem mapsItem;
    boolean isMaps = true;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.darkenStatusBar(this, R.color.status_bar);
        setContentView(R.layout.activity_info_company);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        company =  (Company) intent.getSerializableExtra("obj");

        initV();

    }

    private void initV() {

        img = (ImageView) findViewById(R.id.main_backdrop);
        tvEmail = (TextView) findViewById(R.id.info_company_email);
        tvPhone = (TextView) findViewById(R.id.info_company_phone);
        tvUNP = (TextView) findViewById(R.id.info_company_ip);
        tvDesc = (TextView) findViewById(R.id.info_company_descr);

        collapsingToolbar.setTitle(company.getTitle());
        if(!company.getBanner().isEmpty())Glide.with(img.getContext()).load(company.getBanner()).into(img);
        else Glide.with(img.getContext()).load(R.drawable.info_1).into(img);
        if(!company.getIp().isEmpty()) {
            tvUNP.setVisibility(View.VISIBLE);
            tvUNP.setText(company.getIp()+" УНП:" +company.getUnp());
        }
        else {
            tvUNP.setVisibility(View.VISIBLE);
            tvUNP.setText("УНП:" +company.getUnp());
        }
        if(!company.getDescription().isEmpty()) {
            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText(company.getDescription());
        }
        if(company.getMaps().isEmpty()) isMaps = false;

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!company.getEmail().isEmpty()) send(company.getEmail());
                else Toast.makeText(InfoCompanyActivity.this, "Не указан E-mail...",Toast.LENGTH_SHORT).show();
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!company.getPhone().isEmpty()) call(company.getPhone());
                else Toast.makeText(InfoCompanyActivity.this, "Не указан номер телефона...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info_company, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_maps) {
            maps(company.getMaps());
        }else if(id == R.id.home) {
            onBackPressed();
            return true;
        }
        else if(id == R.id.action_share) {
            share();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        mapsItem = menu.findItem(R.id.action_maps);
        mapsItem.setVisible(isMaps);
        return true;
    }

    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    private void send(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] TO = {email};
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void maps(String maps){
        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+maps));
        i.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        startActivity(i);
    }

    private void share(){
        Intent shareIntent;
        String descr = "";
        if(!company.getDescription().isEmpty()) descr = company.getDescription();
        if(!company.getPhone().isEmpty()) descr = descr +"\n"+"Телефон: "+company.getPhone();
        if(!company.getEmail().isEmpty()) descr = descr +"\n"+"E-mail: "+company.getEmail();

        String url = "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();

        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT,company.getTitle()+ "\n\n"+descr+"\n\n"+url);
        shareIntent.setType("image/png");
        startActivity(Intent.createChooser(shareIntent,"Share with"));

    }
}

package com.example.root.mpolispager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.R;
import com.example.root.mpolispager.model.Valuta;
import com.example.root.mpolispager.retrofit.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 6.1.18.
 */

public class FragmentConverter extends Fragment {

    View v;

    double UAH = 0;
    double USD = 0;
    double EUR = 0;
    double RUB = 0;

    TextView tvUSD, tvUAH, tvRUB, tvEUR;
    RelativeLayout progress;
    EditText etBYN, etUSD, etUAH, etRUB, etEUR;

    ImageView imgByn, imgUsd, imgUah, imgRub, imgEur, banner;

    boolean isBYN = false;
    boolean isUSD = false;
    boolean isUAH = false;
    boolean isRUB = false;
    boolean isEUR = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_converter, container, false);

        return v;
    }

    @Override
    public void onResume() {
        init();
        getValuta();
        super.onResume();
    }

    private void init(){

        imgByn = (ImageView) v.findViewById(R.id.conv_img_byn);
        Glide.with(imgByn.getContext()).load(R.drawable.by).into(imgByn);

        imgUsd = (ImageView) v.findViewById(R.id.conv_img_usd);
        Glide.with(imgUsd.getContext()).load(R.drawable.us).into(imgUsd);

        imgUah = (ImageView) v.findViewById(R.id.conv_img_uah);
        Glide.with(imgUah.getContext()).load(R.drawable.ua).into(imgUah);

        imgRub = (ImageView) v.findViewById(R.id.conv_img_rub);
        Glide.with(imgRub.getContext()).load(R.drawable.ru).into(imgRub);

        imgEur = (ImageView) v.findViewById(R.id.conv_img_eur);
        Glide.with(imgEur.getContext()).load(R.drawable.europeanunion).into(imgEur);

        banner = (ImageView) v.findViewById(R.id.conv_img_banner);
        Glide.with(banner.getContext()).load(R.drawable.banner_bank).into(banner);

        tvUSD = (TextView) v.findViewById(R.id.converter_usdTV);
        tvUAH = (TextView) v.findViewById(R.id.converter_uahTV);
        tvRUB = (TextView) v.findViewById(R.id.converter_rubTV);
        tvEUR = (TextView) v.findViewById(R.id.converter_eurTV);

        progress = (RelativeLayout) v.findViewById(R.id.converter_progress);

        etBYN = (EditText) v.findViewById(R.id.etBYN);
        etUSD = (EditText) v.findViewById(R.id.etUSD);
        etUAH = (EditText) v.findViewById(R.id.etUAH);
        etRUB = (EditText) v.findViewById(R.id.etRUB);
        etEUR = (EditText) v.findViewById(R.id.etEUR);

        etBYN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etBYN.setText("");
            }
        });

        etUSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUSD.setText("1");
            }
        });

        etUAH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUAH.setText("");
            }
        });

        etRUB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etRUB.setText("");
            }
        });

        etEUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEUR.setText("");
            }
        });


        etBYN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()>0)setValue(0, editable.toString());
            }
        });

        etUSD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()>0)setValue(1, editable.toString());
            }
        });

        etUAH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0)setValue(2, editable.toString());
            }
        });

        etRUB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0)setValue(3, editable.toString());
            }
        });

        etEUR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0)setValue(4, editable.toString());
            }
        });

    }



    private void setValue(int type, String s){
        double vBYN = 0;
        double vUSD = 0;
        double vUAH = 0;
        double vRUB = 0;
        double vEUR = 0;
        switch (type){

            case 0:
            {
                try {

                    vBYN =  Double.parseDouble(s);
                    vUSD =  vBYN / USD;
                    vEUR =  vBYN / EUR;
                    vUAH =  vBYN / UAH *100;
                    vRUB =  vBYN / RUB * 100;

                    String formatUSD = String.format("%.2f", vUSD);
                    String formatEUR = String.format("%.2f", vEUR);
                    String formatUAH = String.format("%.2f", vUAH);
                    String formatRUB = String.format("%.2f", vRUB);

                    etUSD.setText(formatUSD);
                    etEUR.setText(formatEUR);
                    etUAH.setText(formatUAH);
                    etRUB.setText(formatRUB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1:
            {
                try {
                    vUSD =  Double.parseDouble(s);
                    vBYN =  vUSD * USD;
                    vEUR =  vBYN / EUR;
                    vUAH =  vBYN / UAH *100;
                    vRUB =  vBYN / RUB * 100;

                    String formatBYN = String.format("%.2f", vBYN);
                    String formatEUR = String.format("%.2f", vEUR);
                    String formatUAH = String.format("%.2f", vUAH);
                    String formatRUB = String.format("%.2f", vRUB);

                    //etUSD.setText(formatUSD);
                    etBYN.setText(formatBYN);
                    etEUR.setText(formatEUR);
                    etUAH.setText(formatUAH);
                    etRUB.setText(formatRUB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2:
            {
                try {
                    vUAH =  Double.parseDouble(s);
                    vBYN =  vUAH * UAH / 100;
                    vEUR =  vBYN / EUR;
                    vUSD =  vBYN / USD;
                    vRUB =  vBYN / RUB * 100;

                    String formatBYN = String.format("%.2f", vBYN);
                    String formatEUR = String.format("%.2f", vEUR);
                    String formatUSD = String.format("%.2f", vUSD);
                    String formatRUB = String.format("%.2f", vRUB);


                    etBYN.setText(formatBYN);
                    etEUR.setText(formatEUR);
                    etUSD.setText(formatUSD);
                    etRUB.setText(formatRUB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3:
            {
                try {
                    vRUB =  Double.parseDouble(s);
                    vBYN =  vRUB * RUB / 100;
                    vEUR =  vBYN / EUR;
                    vUSD =  vBYN / USD;
                    vUAH =  vBYN / UAH * 100;

                    String formatBYN = String.format("%.2f", vBYN);
                    String formatEUR = String.format("%.2f", vEUR);
                    String formatUSD = String.format("%.2f", vUSD);
                    String formatUAH = String.format("%.2f", vUAH);


                    etBYN.setText(formatBYN);
                    etEUR.setText(formatEUR);
                    etUSD.setText(formatUSD);
                    etUAH.setText(formatUAH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4:
            {
                try {
                    vEUR =  Double.parseDouble(s);
                    vBYN =  vEUR * EUR;
                    vUSD =  vBYN / USD;
                    vUAH =  vBYN / UAH *100;
                    vRUB =  vBYN / RUB * 100;

                    String formatBYN = String.format("%.2f", vBYN);
                    String formatUSD = String.format("%.2f", vUSD);
                    String formatUAH = String.format("%.2f", vUAH);
                    String formatRUB = String.format("%.2f", vRUB);

                    //etUSD.setText(formatUSD);
                    etBYN.setText(formatBYN);
                    etUSD.setText(formatUSD);
                    etUAH.setText(formatUAH);
                    etRUB.setText(formatRUB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }

        //etBYN.setText(s);

        //
       // etEUR.setText(String.valueOf(vEUR));
        //etEUR.setText(String.valueOf(vEUR));

    }

    private void getValuta(){
        progress.setVisibility(View.VISIBLE);
        App.getApiNbrb().getValuta("0").enqueue(new Callback<List<Valuta>>() {
            @Override
            public void onResponse(Call<List<Valuta>> call, Response<List<Valuta>> response) {

                for (Valuta value:response.body()
                     ) {

                    if(value.getCurID() == 290){
                        UAH = value.getCurOfficialRate();
                        tvUAH.setText("UAH "+String.valueOf(UAH));
                    }else if(value.getCurID() == 145){
                        USD = value.getCurOfficialRate();
                        tvUSD.setText("USD "+String.valueOf(USD));
                    }else if(value.getCurID() == 292){
                        EUR = value.getCurOfficialRate();
                        tvEUR.setText("EUR "+String.valueOf(EUR));
                    }else if(value.getCurID() == 298){
                        RUB = value.getCurOfficialRate();
                        tvRUB.setText("RUB "+String.valueOf(RUB));
                    }
                    Log.e("valuta_"+value.getCurName(),"="+value.getCurOfficialRate());

                }
                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<Valuta>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("valuta", t.toString());
                showSnack();
            }
        });

    }

    private void showSnack(){

        Snackbar snackbar = Snackbar.make(progress, "Отсутствует подключение к интернету...", Snackbar.LENGTH_LONG)
                .setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getValuta();
                    }
                });
        snackbar.setDuration(8000); // 8 секунд
        snackbar.show();
    }
}

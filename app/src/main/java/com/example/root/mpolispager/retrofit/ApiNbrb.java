package com.example.root.mpolispager.retrofit;

import com.example.root.mpolispager.model.AfishaInfo;
import com.example.root.mpolispager.model.Valuta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 9.1.18.
 */

public interface ApiNbrb {

    @GET("/API/ExRates/Rates?")
    Call<List<Valuta>> getValuta(@Query("Periodicity") String date);

}

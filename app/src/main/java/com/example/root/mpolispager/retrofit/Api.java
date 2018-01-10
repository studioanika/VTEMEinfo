package com.example.root.mpolispager.retrofit;

import com.example.root.mpolispager.model.AfishaInfo;
import com.example.root.mpolispager.model.ExampeInfoShop;
import com.example.root.mpolispager.model.Example;
import com.example.root.mpolispager.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 2.1.18.
 */

public interface Api {


    @GET("bonus/parser/parser_select_city.php")
    Call<List<Example>> getCityAction(@Query("town") String town);

    @GET("bonus/parser/parser_tovars.php")
    Call<List<ExampeInfoShop>> getInfoAction(@Query("url") String url);

    @GET("bonus/parser/get_afisha.php")
    Call<List<News>> getAfisha();

    @GET("bonus/parser/get_news.php")
    Call<List<News>> getNews();

    @GET("bonus/parser/get_afisha_id.php")
    Call<List<AfishaInfo>> getAfishaId(@Query("url") String url);

    @GET("bonus/parser/get_news_desc.php")
    Call<List<AfishaInfo>> getNewsId(@Query("url") String url);

}
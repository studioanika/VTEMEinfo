package com.example.root.mpolispager.retrofit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 2.1.18.
 */

public class App extends Application {

    private static Api umoriliApi;
    private static ApiNbrb umoriliApiNbrb;
    private Retrofit retrofit, retrofit2;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://anika-cs.by/server/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        umoriliApi = retrofit.create(Api.class);
        retrofit2 = new Retrofit.Builder()
                .baseUrl("http://www.nbrb.by/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        umoriliApiNbrb = retrofit2.create(ApiNbrb.class);
        //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static Api getApi() {
        return umoriliApi;
    }

    public static ApiNbrb getApiNbrb() {
        return umoriliApiNbrb;
    }
}
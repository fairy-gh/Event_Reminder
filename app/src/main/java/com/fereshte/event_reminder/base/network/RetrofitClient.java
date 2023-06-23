package com.fereshte.event_reminder.base.network;

import android.content.Context;
import com.fereshte.event_reminder.util.datetime.interseptor.HeaderInterceptor;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance = null;

    private static OkHttpClient getOkHttpClient(Context context){
        return new OkHttpClient().newBuilder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }

    public static Retrofit getInstance(String baseUrl, Context context){
        if(instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .client(getOkHttpClient(context))
                    .build();
        return instance;
    }
}

package com.schoolshieldchild_ui.presenter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceConnection {

    public static final String baseUrl = "http://112.196.23.162:8026/schoolbully/";
    private static WebServiceHolder holder;

    public WebServiceConnection() {
        init();
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        holder = retrofit.create(WebServiceHolder.class);
    }

    public static WebServiceHolder getInstance() {
        return holder;
    }


}

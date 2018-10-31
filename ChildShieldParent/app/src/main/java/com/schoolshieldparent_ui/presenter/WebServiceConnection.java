package com.schoolshieldparent_ui.presenter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceConnection {

    public static final String baseUrl = "http://live.csdevhub.com/cas/schoolbully/index.php/";
    private static WebServiceHolder holder;
    public static String APPLICATION_ICON_URLS = "http://live.csdevhub.com/cas/schoolbully/uploads/AppImages/";
    public static String GALLERY_IMAGES_URLS = "http://live.csdevhub.com/cas/schoolbully/uploads/GalleryImages/";

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

package com.lollipop.uprint.utils;

public class UtilsApi {
    public static final String BASE_URL_API = "http://uprint.aplikasiulun.com/android/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

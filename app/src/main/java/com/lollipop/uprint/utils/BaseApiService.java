package com.lollipop.uprint.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BaseApiService {
    @Headers("Content-Type: application/json")
    @POST("api.php")
    Call<ResponseBody> data(@Body String body);
}

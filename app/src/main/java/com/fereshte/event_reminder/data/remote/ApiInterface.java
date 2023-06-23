package com.fereshte.event_reminder.data.remote;

import com.fereshte.event_reminder.data.model.RequestModel;
import com.fereshte.event_reminder.data.model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("event/share")
    Call<ResponseModel> shareEvent(@Body RequestModel requestModel);


    @POST("event/get/{token}")
    Call<RequestModel> getEvent(@Path("token") String token);
}

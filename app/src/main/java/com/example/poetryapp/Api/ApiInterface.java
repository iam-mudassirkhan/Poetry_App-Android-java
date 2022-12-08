package com.example.poetryapp.Api;

import com.example.poetryapp.Response.DeletePoetryResponse;
import com.example.poetryapp.Response.readPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("readpoetry.php")
    Call<readPoetryResponse> getPoetry();
    @FormUrlEncoded
    @POST("deletepoetry.php")
    Call<DeletePoetryResponse> deletePoetry(@Field("id_Poetry") String id_Poetry );

    @FormUrlEncoded
    @POST("addpoetry.php")
    Call<DeletePoetryResponse> addPoetry(@Field("Poetry_dt") String poetryData, @Field("poetName") String poet_Name);

    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeletePoetryResponse> updatePoetry(@Field("Poetrydt") String updatePoetrydata, @Field("id") String poetry_Id);
}

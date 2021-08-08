package com.example.mrdr.retrofitutil;

import android.graphics.Bitmap;

import com.example.mrdr.models.ApiResponse;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
   @POST("registe.php")
    Call<ApiResponse> performUserSignIn(@Field("user_name") String userName, @Field("password") String password, @Field("name") String name,@Field("age") String age,@Field("place") String place);

    @FormUrlEncoded
    @POST("logi.php")
    Call<ApiResponse> performUserLogin(@Field("user_name") String userName,@Field("password") String password);

    @FormUrlEncoded
    @POST("qrcode1.php")
    Call<ApiResponse> performQrcodeUpload(@Field("username1") String username, @Field("date") String date, @Field("time") String time, @Field("age") String age, @Field("place") String place);
//1, @Field("code") Bitmap code

    @FormUrlEncoded
    @POST("feedback.php")
    Call<ApiResponse> performFeedBack(@Field("user_name") String user_name,@Field("feedback") String feedback);


    @FormUrlEncoded
    @POST("profileimage.php")
    Call<ApiResponse> performImageUpload(@Field("user_name") String user_name,@Field("image") String image);

    @FormUrlEncoded
    @POST("imageaccess.php")
    Call<ApiResponse> performImageAccess(@Field("user_name") String user_name);

   @FormUrlEncoded
   @POST("uploadnotpass.php")
   Call<ApiResponse> performUpdateNotPassword(@Field("user_name") String user_name, @Field("name") String name, @Field("age") String age, @Field("place") String place);
   //1, @Field("code") Bitmap code

   @FormUrlEncoded
   @POST("uploadpass.php")
   Call<ApiResponse> performUpdatePassword(@Field("user_name") String user_name, @Field("name") String name, @Field("age") String age, @Field("place") String place, @Field("password") String password);
   //1, @Field("code") Bitmap code




}

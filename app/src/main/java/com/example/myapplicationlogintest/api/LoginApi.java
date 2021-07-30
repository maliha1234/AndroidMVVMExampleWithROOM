package com.example.myapplicationlogintest.api;

import com.example.myapplicationlogintest.data.LoginUser;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("users")

    //on below line we are creating a method to post our data.
    Call<LoginUser> createPost(@Body LoginUser loginUser);
}
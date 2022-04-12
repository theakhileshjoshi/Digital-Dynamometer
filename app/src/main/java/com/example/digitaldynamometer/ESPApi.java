package com.example.digitaldynamometer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ESPApi {
    @GET("posts")
    Call<List<Post>> getPosts();
}

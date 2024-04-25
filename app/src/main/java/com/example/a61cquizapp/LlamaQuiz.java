package com.example.a61cquizapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LlamaQuiz {
    @GET("getQuiz?topic=cats")
    Call<Quiz> getQuiz();
}

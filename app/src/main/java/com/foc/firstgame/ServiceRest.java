package com.foc.firstgame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceRest {

    @GET("192.168.0.21:8080/personas/listar")
    Call<List<Users>> getUsersGet();

    @GET("192.168.0.21:8080/personas/buscar/{id}")
    public Call<Users> find(@Path("id") Integer id);

    @POST("192.168.0.21:8080/personas/guardar")
    Call<List<Users>> getUsersPost();
}

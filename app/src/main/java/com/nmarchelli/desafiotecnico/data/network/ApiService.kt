package com.nmarchelli.desafiotecnico.data.network

import com.nmarchelli.desafiotecnico.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api")
    suspend fun getRandomUser():Response<UserModel>

    @GET("api")
    suspend fun getTenUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
        @Query("seed") seed: String = "challenge"
    ): Response<UserModel>
}
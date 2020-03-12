package com.tadiuzzz.baseapp.data.source.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.tadiuzzz.baseapp.data.entity.EntityExample

interface BaseApi {

    @GET("api/example")
    suspend fun getData(
        @Query("id") id: String,
        @Query("amount") amount: String
    ): Response<EntityExample>

}
package com.example.subscription.remote

import com.example.subscription.model.SubscriptionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionApi {

    @GET("payment/plan/get/?")
    suspend fun getData(
        @Query("uid") uid: String,
        @Query("con") country: String,
        @Query("date") date: String,
    ): SubscriptionResponse

}
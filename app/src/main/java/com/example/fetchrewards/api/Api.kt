package com.example.fetchrewards.api

import com.example.fetchrewards.model.Item
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("hiring.json")
    fun getItems(): Call<List<Item>>
}
package com.example.fetchrewards

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("hiring.json")
    fun getItems(): Call<List<Item>>
}
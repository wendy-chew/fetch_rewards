package com.example.fetchrewards.api

import com.example.fetchrewards.model.Item
import retrofit2.Call

class ApiService(private val api: Api) {
    fun getItems(): Call<List<Item>> {
        return api.getItems()
    }
}
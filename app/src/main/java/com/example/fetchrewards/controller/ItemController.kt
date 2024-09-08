package com.example.fetchrewards.controller

import android.util.Log
import com.example.fetchrewards.api.ApiService
import com.example.fetchrewards.model.Item
import retrofit2.Call
import retrofit2.Response
class ItemController(private val apiService: ApiService) {
    fun fetchItems(callback: (List<Item>) -> Unit) {
        apiService.getItems().enqueue(object : retrofit2.Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // Handle API call failure
                Log.e("ItemController", "Error fetching items: $t")
                callback(emptyList())
            }

            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        callback(filterItems(items))
                    } else {
                        callback(emptyList())
                    }
                } else {
                    // Handle API call error
                    Log.e("ItemController", "Error fetching items: ${response.code()}")
                    callback(emptyList())
                }
            }
        })
    }
    // Filter out any items where "name" is null or blank
    // & sort the results first by "listId" then by "name"
    private fun filterItems(items: List<Item>): List<Item> {
        val filteredItems = items.filter { it.name != null && it.name.isNotBlank() }
        return filteredItems.sortedWith(compareBy({ it.listId }, { extractNumber(it.name) }))
    }
    // Extracts the number from the name of the item
    private fun extractNumber(name: String?): Int {
        return name?.substringAfter("Item")?.trim()?.toIntOrNull() ?: 0
    }
}
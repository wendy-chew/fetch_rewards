package com.example.fetchrewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Response
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an API instance
        val api = retrofit.create(Api::class.java)

        // Fetch data
        api.getItems().enqueue(object : retrofit2.Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // Handle error
                Log.e("MainActivity", "Error fetching data", t)
            }

            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val items = response.body()

                    // Check if items is null
                    if (items == null) {
                        Log.e("MainActivity", "Items is null")
                        return
                    }

                    // Process the data
                    processItems(items)
                } else {
                    Log.e("MainActivity", "Error fetching data: ${response.code()}")
                }
            }
        })
    }

    private fun processItems(items: List<Item>) {
        // Filter out items with blank or null names
        val filteredItems = items.filter { it.name != null && it.name.isNotBlank() }

        // Pass the filtered items to the adapter, sorting is handled in ItemAdapter
        adapter = ItemAdapter(filteredItems)

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter
    }

}
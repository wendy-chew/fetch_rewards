package com.example.fetchrewards.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.api.Api
import com.example.fetchrewards.api.ApiService
import com.example.fetchrewards.controller.ItemController
import com.example.fetchrewards.view.adapter.ItemAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var itemController: ItemController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemController = ItemController(
            ApiService(Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java))
        )

        fetchItems()
    }
    private fun fetchItems() {
        itemController.fetchItems { items ->
            adapter = ItemAdapter(itemController)
            recyclerView.adapter = adapter
        }
    }

}
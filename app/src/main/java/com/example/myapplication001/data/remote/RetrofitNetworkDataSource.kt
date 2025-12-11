package com.example.myapplication001.data.remote

import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MuseumApi {
    @GET("museums")
    suspend fun getMuseums(): List<MuseumEntity>

    @GET("events")
    suspend fun getEvents(): List<EventEntity>
}

class RetrofitNetworkDataSource(baseUrl: String) : NetworkDataSource {
    private val api: MuseumApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(MuseumApi::class.java)
    }

    override suspend fun getMuseums(): List<MuseumEntity> {
        return api.getMuseums()
    }

    override suspend fun getEvents(): List<EventEntity> {
        return api.getEvents()
    }
}

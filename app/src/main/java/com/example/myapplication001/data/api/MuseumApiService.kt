package com.example.myapplication001.data.api

import com.example.myapplication001.domain.model.Museum
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaz del servicio API
interface MuseumApiService {
    @GET("museums")
    suspend fun getMuseums(
        @Query("location") location: String = "Arequipa"
    ): Response<List<Museum>>
}

// Objeto singleton para Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://www.google.com/" // Cambia esto por tu API real

    val api: MuseumApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MuseumApiService::class.java)
    }
}

// Repositorio para manejar datos de museos
class MuseumRepository {
    private val api = RetrofitInstance.api

    suspend fun getMuseums(): Result<List<Museum>> {
        return try {
            val response = api.getMuseums()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                // Si falla la API, usar datos de muestra
                Result.success(Museum.sampleData)
            }
        } catch (e: Exception) {
            // En caso de error, usar datos de muestra como fallback
            Result.success(Museum.sampleData)
        }
    }
}
package com.example.theavengers_mad5254_project.model.api

import android.content.Context
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.Weather
import com.example.theavengers_mad5254_project.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiService: ApiService
    private lateinit var weatherApiService: ApiService

    // val BASE_URL: String = "https://snowapp.lcmaze.com/"
     val BASE_URL: String = "http://192.168.2.15:8100/"

    fun getApiService(context: Context): ApiService {

        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    fun getWeatherApiService(context: Context): ApiService {

        // Initialize ApiService if not initialized yet
        if (!::weatherApiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            weatherApiService = retrofit.create(ApiService::class.java)
        }

        return weatherApiService
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

}

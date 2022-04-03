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
    private lateinit var googlePlaceApiService: ApiService

    val BASE_URL: String = AppConstants.BASE_URL

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

    fun getGooglePlaceApiService(context: Context): ApiService {

        // Initialize ApiService if not initialized yet
        if (!::googlePlaceApiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.GOOGLE_PLACE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            googlePlaceApiService = retrofit.create(ApiService::class.java)
        }

        return googlePlaceApiService
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

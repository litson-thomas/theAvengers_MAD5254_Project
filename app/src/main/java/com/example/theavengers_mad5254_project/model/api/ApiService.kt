package com.example.theavengers_mad5254_project.model.api

import com.example.theavengers_mad5254_project.model.data.User
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.*
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ForecastResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.WeatherForecastResponse
import com.example.theavengers_mad5254_project.utils.AppPreference
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {
    companion object{
        const val BASE_URL: String = "https://snowapp.lcmaze.com/"
        const val OPEN_WEATHER_MAP_URL: String = "https://api.openweathermap.org/"
        val TOKEN: String = AppPreference.userToken;

        private var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!

        }

        private var apiWeatherService: ApiService? = null
        fun getWeatherInstance(): ApiService {
            if (apiWeatherService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(OPEN_WEATHER_MAP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiWeatherService = retrofit.create(ApiService::class.java)
            }
            return apiWeatherService!!

        }
    }

    @GET("placeholder/user/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): User

    @GET("data/2.5/weather?")
    suspend fun getWeatherDetails( @Query("q") address: String,@Query("APPID") apiKey: String): Response<WeatherForecastResponse>

    @GET("data/2.5/forecast?")
    suspend fun getWeatherForecastDetails( @Query("q") address: String,@Query("APPID") apiKey: String): Response<ForecastResponse>

    @POST("api/user")
    suspend fun registerUser(@Body requestBody: CreateUserRequest): Response<CreateUserResponse>

    @GET("api/shovler?q=&order=id&order_type=ASC")
    suspend fun getShovlers(@Header("token") token: String): Response<ShovlersResponse>

    @GET("api/shovler")
    suspend fun getShovlerById(@Header("token") token: String, @Query("id") id: Int): Response<ShovlersResponse>

    @GET("api/user")
    suspend fun getUser(@Header("token") token: String, @Query("uid") uid: String): Response<UserResponse>

}

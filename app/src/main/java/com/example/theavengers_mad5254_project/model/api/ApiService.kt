package com.example.theavengers_mad5254_project.model.api

import com.example.theavengers_mad5254_project.model.data.*
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.*
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ForecastResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.WeatherForecastResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.UserResponse
import com.example.theavengers_mad5254_project.utils.AppPreference
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {
    /*
    companion object{
<<<<<<< HEAD
        const val BASE_URL: String = "https://snowapp.lcmaze.com/"
        const val OPEN_WEATHER_MAP_URL: String = "https://api.openweathermap.org/"
=======
         const val BASE_URL: String = "https://snowapp.lcmaze.com/"
        // const val BASE_URL: String = "http://192.168.2.15:8100/"
>>>>>>> 61cd10684e6a763c266c0c36d37548239cc4eb0d
        val TOKEN: String = AppPreference.userToken;

        private var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClient(context))
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!

        }
<<<<<<< HEAD

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
=======
>>>>>>> 61cd10684e6a763c266c0c36d37548239cc4eb0d

        private fun okhttpClient(context: Any): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
        }
    }
    */

    @POST("api/shovler")
    suspend fun addShovler(@Body requestBody: Shovler): Response<APIResponse>


    @PUT("api/shovler/{id}")
    suspend fun updateShovler(@Path(value = "id") id: Int?,@Body requestBody: Shovler): Response<APIResponse>

    @GET("data/2.5/weather?")
    suspend fun getWeatherDetails( @Query("q") address: String,@Query("APPID") apiKey: String): Response<WeatherForecastResponse>

    @GET("data/2.5/forecast?")
    suspend fun getWeatherForecastDetails( @Query("q") address: String,@Query("APPID") apiKey: String): Response<ForecastResponse>

    @POST("api/user")
    suspend fun registerUser(@Body requestBody: CreateUserRequest): Response<CreateUserResponse>

    @GET("api/bookings")
    suspend fun getBookings(@Query("shovlerId") shovlerId: Int): Response<BookingResponse>

    @GET("api/bookings")
    suspend fun getBookings(): Response<BookingResponse>

    @PUT("api/bookings/{id}")
    suspend fun updateBooking(@Path(value = "id") id: Int?,@Body requestBody: Booking): Response<APIResponse>

    @GET("api/reviews")
    suspend fun getReviews(@Query("shovlerId") shovlerId: Int, @Query("bookingId") bookingId: Int): Response<ReviewResponse>

    @GET("api/shovler")
    suspend fun getShovlerListings(@Query("userUid") userUid: String): Response<ShovlerResponse>

    @Multipart
    @POST("api/shovler/images")
    suspend fun uploadShowlerImage(@Part file: MultipartBody.Part?): Response<APIResponse>

    @GET("api/address")
    suspend fun getAddress(@Query("userUid") userUid: String): Response<AddressResponse>

    @DELETE("api/shovler/images/{id}")
    suspend fun deleteShovlerImage(@Path(value = "id") id: Int?): Response<APIResponse>

    @GET("api/shovler?q=&order=id&order_type=ASC")
    suspend fun getShovlers(@Header("token") token: String): Response<ShovlersResponse>

    @GET("api/shovler")
    suspend fun getShovlerById(@Header("token") token: String, @Query("id") id: Int): Response<ShovlersResponse>

    @GET("api/user?q=&order=uid&order_type=ASC")
    suspend fun getUser(@Header("token") token: String, @Query("uid") uid: String): Response<UserResponse>

}


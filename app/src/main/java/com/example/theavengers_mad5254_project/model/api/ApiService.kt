package com.example.theavengers_mad5254_project.model.api

import com.example.theavengers_mad5254_project.model.data.*
import com.example.theavengers_mad5254_project.model.data.requestModel.*
import com.example.theavengers_mad5254_project.model.data.responseModel.*
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ForecastResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.WeatherForecastResponse
import okhttp3.MultipartBody
import retrofit2.Response
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.UserResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.GeocodeResponseItem
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
   // lat=43.7764&lon=79.2318

    @POST("api/shovler")
    suspend fun addShovler(@Body requestBody: Shovler): Response<APIResponse>


    @PUT("api/shovler/{id}")
    suspend fun updateShovler(@Path(value = "id") id: Int?,@Body requestBody: Shovler): Response<APIResponse>

    @GET("data/2.5/weather?")
    suspend fun getWeatherDetails( @Query("lat") lat: Double,@Query("lon") lon: Double,@Query("APPID") apiKey: String): Response<WeatherForecastResponse>

    @GET("data/2.5/forecast?")
    suspend fun getWeatherForecastDetails(@Query("lat") lat: Double,@Query("lon") lon: Double,@Query("APPID") apiKey: String): Response<ForecastResponse>

    @GET("geo/1.0/reverse?")
    suspend fun getGeocoderDetails(@Query("lat") lat: Double,@Query("lon") lon: Double,@Query("APPID") apiKey: String): Response<GeocodeResponseItem>

    @POST("api/user")
    suspend fun registerUser(@Body requestBody: CreateUserRequest): Response<CreateUserResponse>

    @PUT("api/user/{userUid}")
    suspend fun updateUser(@Header("token") token: String,@Path(value = "userUid") userUid: String?,@Body requestBody: UpdateUserRequest): Response<CreateUserResponse>

    @GET("api/bookings")
    suspend fun getBookings(@Query("shovlerId") shovlerId: Int): Response<BookingResponse>

    @GET("api/bookings")
    suspend fun getBookings(): Response<BookingResponse>

    @PUT("api/bookings/{id}")
    suspend fun updateBooking(@Path(value = "id") id: Int?,@Body requestBody: Booking): Response<APIResponse>

    @GET("api/reviews")
    suspend fun getReviews(@Query("shovlerId") shovlerId: Int, @Query("bookingId") bookingId: Int): Response<ReviewResponse>

    @POST("api/reviews")
    suspend fun addReview(@Body requestBody: Review): Response<APIResponse>

    @GET("api/shovler")
    suspend fun getShovlerListings(@Query("userUid") userUid: String): Response<ShovlerResponse>

    @GET("api/shovler")
    suspend fun getShovlerListing(@Query("id") id: Int): Response<ShovlerResponse>

    @Multipart
    @POST("api/shovler/images")
    suspend fun uploadShowlerImage(@Part file: MultipartBody.Part?): Response<APIResponse>

    @GET("api/address")
    suspend fun getAddress(@Query("userUid") userUid: String): Response<AddressResponse>

    @DELETE("api/address/{id}")
    suspend fun deleteAddress(@Path(value = "id") id: Int?): Response<DeleteAddressResponse>

    @GET("api/address")
    suspend fun getAddress(): Response<AddressResponse>

    @DELETE("api/shovler/images/{id}")
    suspend fun deleteShovlerImage(@Path(value = "id") id: Int?): Response<APIResponse>

    @GET("api/shovler?q=&order=id&order_type=ASC")
    suspend fun getShovlers(@Header("token") token: String): Response<ShovlersResponse>

    @GET("api/shovler")
    suspend fun getShovlerById(@Header("token") token: String, @Query("id") id: Int): Response<ShovlersResponse>

    @GET("api/user?q=&order=uid&order_type=ASC")
    suspend fun getUser(@Header("token") token: String, @Query("uid") uid: String): Response<UserResponse>

    // booking
    @POST("api/user/prepare-booking")
    suspend fun prepareBooking(@Body requestBody: PrepareBookingRequest): Response<PrepareBookingResponse>

    // chat messages
    @GET("api/messages?order=createdAt&order_type=asc")
    suspend fun getChats(@Query("room") room: String): Response<ChatResponse>
    // chat messages
    @GET("api/messages?order=createdAt&order_type=asc")
    suspend fun getMessages(@Query("shovlerId") shovlerId: Int): Response<ChatResponse>

    @GET("api/shovler?order=id&order_type=ASC")
    suspend fun getShovlerBySearch( @Header("token") token: String,@Query("q") q: String): Response<ShovlersResponse>
    // chat messages
    @GET("api/messages?order=createdAt&order_type=asc")
    suspend fun getMessages(): Response<ChatResponse>

    //Google place API
    @GET
    suspend fun getGooglePlacesNew(@Url url: String?): Response<GooglePlaceResponse>

    //Google Geocode API
    @GET
    suspend fun getGoogleGeocode(@Url url: String?): Response<GoogleGeocodeResponse>

    @GET("api/location/city")
    suspend fun getCity(): Response<LocationResponse>

    @POST("api/address")
    suspend fun addNewAddress(@Header("token") token: String,@Body addNewAddressRequest: AddNewAddressRequest): Response<AddNewAddressResponse>

    @PUT("api/address/{id}")
    suspend fun updateAddress(@Path(value = "id") id: Int?,@Body updateAddressRequest: UpdateAddressRequest): Response<ApiResponse>

}


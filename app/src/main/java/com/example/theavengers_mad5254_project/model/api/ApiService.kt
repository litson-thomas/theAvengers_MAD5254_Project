package com.example.theavengers_mad5254_project.model.api

import com.example.theavengers_mad5254_project.model.data.User
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.UserResponse
import com.example.theavengers_mad5254_project.utils.AppPreference
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {
    companion object{
        // const val BASE_URL: String = "https://snowapp.lcmaze.com/"
        const val BASE_URL: String = "http://192.168.2.15:8100/"
        val TOKEN: String = AppPreference.userToken;

        var apiService: ApiService? = null
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
    }

    @GET("placeholder/user/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): User

    @POST("api/user")
    suspend fun registerUser(@Body requestBody: CreateUserRequest): Response<CreateUserResponse>

    @GET("api/shovler?q=&order=id&order_type=ASC")
    suspend fun getShovlers(@Header("token") token: String): Response<ShovlersResponse>

    @GET("api/shovler")
    suspend fun getShovlerById(@Header("token") token: String, @Query("id") id: Int): Response<ShovlersResponse>

    @GET("api/user?q=&order=uid&order_type=ASC")
    suspend fun getUser(@Header("token") token: String, @Query("uid") uid: String): Response<UserResponse>

}

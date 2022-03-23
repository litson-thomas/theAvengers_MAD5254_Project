package com.example.theavengers_mad5254_project.model.api

import com.example.theavengers_mad5254_project.model.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): User

}
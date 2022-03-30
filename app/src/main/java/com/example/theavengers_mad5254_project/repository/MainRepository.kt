package com.example.theavengers_mad5254_project.repository

import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.utils.AppPreference

class MainRepository constructor(private val apiService: ApiService) {

    suspend fun createUser( createUserRequest: CreateUserRequest) = apiService.registerUser(createUserRequest)

    suspend fun loadShovlers() = apiService.getShovlers(token = AppPreference.userToken)

    suspend fun loadShovlerById( id: Int) = apiService.getShovlerById(token = AppPreference.userToken, id = id)

    suspend fun getUser( uid: String) = apiService.getUser(token = AppPreference.userToken, uid = uid)
}

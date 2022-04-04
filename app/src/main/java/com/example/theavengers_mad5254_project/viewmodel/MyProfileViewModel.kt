package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.requestModel.AddNewAddressRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.UpdateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.*
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import kotlinx.coroutines.*

class MyProfileViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _updateProfile = MutableLiveData<CreateUserResponse>()
    var updateProfile: LiveData<CreateUserResponse> = _updateProfile

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _searchPlace = MutableLiveData<List<Prediction>>()
    var searchPlace: LiveData< List<Prediction>> = _searchPlace

    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun updateProfile(name:String,phone:String,uid:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val updateUserRequest = UpdateUserRequest(name,phone,uid)
            val response = repository.updateProfile(updateUserRequest)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _updateProfile.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getPlaces:  ${response.message()}")
                }
            }
        }
    }
    fun getUser(){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUser(AppPreference.userID)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.e(ContentValues.TAG, "load user:  ${response.message()}")
                }
            }
        }
    }

    fun getGooglePlaces(url: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.searchGooglePlaces(url)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _searchPlace.postValue(response.body()?.predictions)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getPlaces:  ${response.message()}")
                }
            }
        }
    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.postValue(false)
    }
    fun fetchLoading():LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
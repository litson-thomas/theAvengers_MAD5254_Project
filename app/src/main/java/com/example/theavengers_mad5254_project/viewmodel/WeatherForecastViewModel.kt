package com.example.theavengers_mad5254_project.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ForecastResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.WeatherForecastResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*

class WeatherForecastViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {

    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _weatherStatus = MutableLiveData<WeatherForecastResponse>()
    var weatherStatus: LiveData<WeatherForecastResponse> = _weatherStatus


    private val _weatherForecastStatus = MutableLiveData<ForecastResponse>()
    var weatherForecastStatus: LiveData<ForecastResponse> = _weatherForecastStatus

    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun getWeatherDetails(lat: Double,lng:Double,apiKey:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getWeatherDetails(lat,lng,apiKey)
           withContext((Dispatchers.Main)) {
            if (response.isSuccessful) {
                _weatherStatus.postValue(response.body())
                loading.postValue(false)
            } else {
                onError("Error : ${response.message()}")
                Log.d(TAG, "createUser:  ${response.raw().message}")
            }
              }
        }
    }

    fun getWeatherForecastDetails(lat: Double,lng:Double,apiKey:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getWeatherForecastDetails(lat,lng,apiKey)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _weatherForecastStatus.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(TAG, "createUser:  ${response.raw().message}")
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


package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.Review
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.LocationResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.Prediction
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class AddNewAddressViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    val _addNewAddress = MutableLiveData<List<Prediction>>()
    var addNewAddress: LiveData< List<Prediction>> = _addNewAddress

    val _searchLocation = MutableLiveData<LocationResponse>()
    var searchLocation: LiveData<LocationResponse> = _searchLocation

    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun getGooglePlaces(place: String, apiKey: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.searchGooglePlaces(place,apiKey)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _addNewAddress.postValue(response.body()?.predictions)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getPlaces:  ${response.message()}")
                }
            }
        }
    }

    fun getCity(){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.searchCity()
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _searchLocation.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getCity:  ${response.message()}")
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
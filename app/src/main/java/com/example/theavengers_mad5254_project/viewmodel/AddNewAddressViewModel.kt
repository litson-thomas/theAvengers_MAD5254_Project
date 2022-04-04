package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.requestModel.AddNewAddressRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.*
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import kotlinx.coroutines.*

class AddNewAddressViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _searchPlace = MutableLiveData<List<Prediction>>()
    var searchPlace: LiveData< List<Prediction>> = _searchPlace

    private val _searchLocation = MutableLiveData<List<Row>>()
    var searchLocation: LiveData<List<Row>> = _searchLocation

    private val _addNewAddress = MutableLiveData<AddNewAddressResponse>()
    var addNewAddress: LiveData<AddNewAddressResponse> = _addNewAddress

    private val _geocode = MutableLiveData<GoogleGeocodeResponse>()
    var geocode: LiveData<GoogleGeocodeResponse> = _geocode

    var job: Job? = null

    init {
        loading.postValue(false)
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

    fun getGoogleGeocode(url: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getGoogleGeocode(url)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _geocode.postValue(response.body())
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
                    _searchLocation.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getCity:  ${response.message()}")
                }
            }
        }
    }
    fun getNewAddress(address_one:String,address_two:String,city:String,city_id:Int,
                      latitude:String,longitude:String,postal_code:String,state:String,userUid:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val addNewAddressRequest = AddNewAddressRequest(address_one,address_two,city,
                city_id,latitude,longitude,postal_code,state,userUid)
            val response = repository.addNewAddress(addNewAddressRequest)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _addNewAddress.postValue(response.body())
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
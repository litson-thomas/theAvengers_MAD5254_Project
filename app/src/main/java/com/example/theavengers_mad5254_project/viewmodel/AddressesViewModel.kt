package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.UpdateAddressRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.DeleteAddressResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.GoogleGeocodeResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import kotlinx.coroutines.*

class AddressesViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()


    val addressList = MutableLiveData<ArrayList<Address>>()

    private val _deleteAddress = MutableLiveData<DeleteAddressResponse>()
    var deleteAddress: LiveData<DeleteAddressResponse> = _deleteAddress

    private val _updateAddress = MutableLiveData<ApiResponse>()
    var updateAddress: LiveData<ApiResponse> = _updateAddress

    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun getAddress(userUid: String) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAddress(userUid)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {

                    val Add: List<Address> = response.body()?.rows!!
                    var userAddresses = listOf<Address>()
                    for (a in Add) {
                        if (a.userUid == userUid) {
                            userAddresses += a
                        }
                    }
                    addressList.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getAddress:  ${response.message()}")
                }
            }
        }

    }
    fun deleteAddress(id: Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.deleteAddress(id)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                   // _deleteAddress.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getAddress:  ${response.message()}")
                }
            }
        }

    }


    fun updateAddress(
        id: Int?, address_one: String?, address_two: String?, CityId: String?,
        StateId: String?, postalCode: String?, city: String?, state: String?,
        latitude: Number?, longitude: Number?, userUid: String?
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val updateAddressRequest = UpdateAddressRequest( id,
                address_one, address_two, CityId, StateId, postalCode, city,
                state, latitude, longitude, userUid)
            val response = id?.let { repository.updateAddress(it,updateAddressRequest) }

            withContext((Dispatchers.Main)) {
                if (response!!.isSuccessful) {
                    _updateAddress.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "updateAddress:  ${response.message()}")
                }
            }

        }

    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.postValue(false)
    }

    fun fetchLoading(): LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}


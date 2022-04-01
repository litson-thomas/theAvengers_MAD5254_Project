package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class AddressesViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()


    val addressList = MutableLiveData<List<Address>>()
    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun getAddress(userUid: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAddress(userUid)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
//                    val Add: List<Address> = response.body()?.rows!!
//                    var userAddresses = listOf<Address>()
//                    for(a in Add) {
//                        if (a.userUid == userUid) {
//                            userAddresses += a
//                        }
//                    }
                    addressList.postValue(response.body()?.rows!!)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getAddress:  ${response.message()}")
                }
            }
        }

    }


  /*  fun updateAddress(
        id: Int?,
        address_one: String?,
        address_two: String?,
        latitude: Number?,
        longitude: Number?,
        createdAt: String?,
        updatedAt: String?,
        userUid: String?
    ){
        loading.postValue(true)
        address = CoroutineScope(Dispatchers.IO).launch {
            val newAddress = Address(id,address_one,address_two,latitude, longitude, createdAt, updatedAt, userUid)
            val response = repository.updateBooking(newAddress)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "updateAddress:  ${response.message()}")
                }
            }
        }

   */
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


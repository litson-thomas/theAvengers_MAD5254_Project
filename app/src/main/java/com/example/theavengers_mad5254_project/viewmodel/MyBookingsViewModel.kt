package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.APIResponse
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class MyBookingsViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _markCompletedStatus = MutableLiveData<APIResponse>()
    var markCompletedStatus: LiveData<APIResponse> = _markCompletedStatus

    val bookingList = MutableLiveData<List<Booking>>()


    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun getBookings(userUid: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getBookings()
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    val bookings: List<Booking> = response.body()?.rows!!
                    var userBookings = listOf<Booking>()
                    for(booking in bookings) {
                        if (booking.shovler != null && booking.shovler!!.userUid == userUid) {
                            userBookings += booking
                        }
                    }
                    bookingList.postValue(userBookings)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getReviews:  ${response.message()}")
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
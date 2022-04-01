package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.APIResponse
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.BookingResponse
import com.example.theavengers_mad5254_project.model.data.Review
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class BookingViewModel(private val repository: MainRepository)
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
    fun updateBooking(id: Int?, shovlerId:Int?, addressId:String?, instructions:String?,
                      date:String?, price:Number?, hours_required:Int?, is_completed:Boolean?
                      ){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val booking = Booking(id,shovlerId,addressId,instructions,
                date,price,hours_required,is_completed)
            val response = repository.updateBooking(booking)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _markCompletedStatus.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "updateBooking:  ${response.message()}")
                }
            }
        }
    }
    fun getBookings(userUid: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getBookings()
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    val bookings: List<Booking> = response.body()?.rows!!
                    var shovlerBookings = listOf<Booking>()
                    for(booking in bookings) {
                        if (booking.shovler != null && booking.shovler!!.userUid == userUid) {
                            shovlerBookings += booking
                        }
                    }
                    bookingList.postValue(shovlerBookings)
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
    fun fetchLoading():LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
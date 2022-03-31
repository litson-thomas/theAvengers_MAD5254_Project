package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.Review
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class ReviewViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    val reviewList = MutableLiveData<List<Review>>()

    var job: Job? = null

    init {
        loading.postValue(false)
    }
    fun getReviews(shovlerId: Int, bookingId: Int){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getReviews(shovlerId,bookingId)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    reviewList.postValue(response.body()?.rows)
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
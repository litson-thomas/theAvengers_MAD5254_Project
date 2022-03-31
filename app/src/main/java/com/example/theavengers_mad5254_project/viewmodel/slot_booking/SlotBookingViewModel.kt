package com.example.theavengers_mad5254_project.viewmodel.slot_booking

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.model.data.ShovlerUser
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.UserResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import kotlinx.coroutines.*

class SlotBookingViewModel(private val repository: MainRepository) : ViewModel(), LifecycleObserver {
  val errorMessage = MutableLiveData<String>()
  var loading: MutableLiveData<Boolean> = MutableLiveData()

  private val _user = MutableLiveData<UserResponse>()
  val user: LiveData<UserResponse> = _user
  var job: Job? = null

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
          Log.e(TAG, "load user:  ${response.message()}")
        }
      }
    }
  }

  private fun onError(message: String) {
    errorMessage.value = message
    loading.postValue(false)
  }

  fun fetchLoading():LiveData<Boolean> = loading

  fun fetchUser(): ShovlerUser? = user.value?.rows?.get(0)

  override fun onCleared() {
    super.onCleared()
    job?.cancel()
  }
}

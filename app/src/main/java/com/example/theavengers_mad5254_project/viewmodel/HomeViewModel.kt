package com.example.theavengers_mad5254_project.viewmodel

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ProgressBar
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.repository.Repository
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class HomeViewModel(private val repository: MainRepository) : ViewModel(), LifecycleObserver {
  val errorMessage = MutableLiveData<String>()
  var loading: MutableLiveData<Boolean> = MutableLiveData()

  private val _loadShovlerStatus = MutableLiveData<ApiResponse>()
  private val _shovlers = MutableLiveData<ShovlersResponse>()
  var loadShovlerStatus: LiveData<ApiResponse> = _loadShovlerStatus
  val shovlers: LiveData<ShovlersResponse> = _shovlers
  var job: Job? = null

  fun loadShovlers(){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.loadShovlers()
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          _shovlers.postValue(response.body())
          loading.postValue(false)
        } else {
          onError("Error : ${response.message()}")
          Log.e(TAG, "load Shovlers:  ${response.message()}")
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

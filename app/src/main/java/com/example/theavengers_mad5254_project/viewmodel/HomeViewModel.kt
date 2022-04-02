package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*

class HomeViewModel(private val repository: MainRepository) : ViewModel(), LifecycleObserver {
  val errorMessage = MutableLiveData<String>()
  var loading: MutableLiveData<Boolean> = MutableLiveData()

  private val _loadShovlerStatus = MutableLiveData<ApiResponse>()
  private val _shovlers = MutableLiveData<ShovlersResponse>()
  var loadShovlerStatus: LiveData<ApiResponse> = _loadShovlerStatus
  val shovlers: LiveData<ShovlersResponse> = _shovlers
  val selectedShovler = MutableLiveData<Shoveler>()


  private val _shovlerSearchStatus = MutableLiveData<ShovlersResponse>()
  var shovlerSearchStatus: LiveData<ShovlersResponse> = _shovlerSearchStatus

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

  fun loadShovlerById(id: Int){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.loadShovlerById(id)
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          _shovlers.postValue(response.body())
          selectedShovler.postValue(response.body()?.rows?.get(0))
          loading.postValue(false)
        } else {
          onError("Error : ${response.message()}")
          Log.e(TAG, "load Shovlers by id:  ${response.message()}")
        }
      }
    }
  }
  fun loadShovlerBySearching(searchKey: String){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.searchShovlers(searchKey)
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          _shovlerSearchStatus.postValue(response.body())
          loading.postValue(false)
        } else {
          onError("Error : ${response.message()}")
          Log.e(TAG, "load Shovlers by Search:  ${response.message()}")
        }
      }
    }
  }

  fun getSelectedShoveler(): Shoveler? {
    return selectedShovler.value
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

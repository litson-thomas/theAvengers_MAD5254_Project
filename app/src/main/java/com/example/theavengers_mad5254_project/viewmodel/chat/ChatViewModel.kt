package com.example.theavengers_mad5254_project.viewmodel.chat

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.model.data.ShovlerUser

import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.PrepareBookingRequest

import com.example.theavengers_mad5254_project.model.data.responseModel.ApiResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.PrepareBookingResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.ShovlersResponse
import com.example.theavengers_mad5254_project.model.data.responseModel.UserResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import kotlinx.coroutines.*

class ChatViewModel(private val repository: MainRepository) : ViewModel(), LifecycleObserver {
  val errorMessage = MutableLiveData<String>()
  var loading: MutableLiveData<Boolean> = MutableLiveData()

  private val _chats = MutableLiveData<MutableList<ChatMessage>>()
  val chats: LiveData<MutableList<ChatMessage>> = _chats
  var job: Job? = null

  fun getChats(room: String){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.getChats(room)
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          _chats.postValue(response.body()?.rows!!)
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

  override fun onCleared() {
    super.onCleared()
    job?.cancel()
  }
}

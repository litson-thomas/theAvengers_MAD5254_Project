package com.example.theavengers_mad5254_project.viewmodel

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

class MessageViewModel(private val repository: MainRepository) : ViewModel(), LifecycleObserver {
  val errorMessage = MutableLiveData<String>()
  var loading: MutableLiveData<Boolean> = MutableLiveData()

  private val _messages = MutableLiveData<MutableList<ChatMessage>>()
  val messages: LiveData<MutableList<ChatMessage>> = _messages
  var job: Job? = null

  fun getMessages(shovlerId: Int){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.getMessages(shovlerId)
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          var messages = response.body()?.rows!!
          var filteredMessages = listOf<ChatMessage>()
          var users = listOf<String>()
          if (messages.count() > 0 ) {
            for (item in messages) {
              if (!users.contains(item.userUid)
                && AppPreference.userID!=item.userUid) {
                filteredMessages+=item
                users+=item.userUid!!
              }
            }
          }
          _messages.postValue(filteredMessages.toMutableList())
          loading.postValue(false)
        } else {
          onError("Error : ${response.message()}")
          Log.e(TAG, "load user:  ${response.message()}")
        }
      }
    }
  }

  fun getMessages(shovlerId: Int, userUid:String, userUid2 :String){
    loading.postValue(true)
    job = CoroutineScope(Dispatchers.IO).launch {
      val response = repository.getMessages(shovlerId)
      withContext((Dispatchers.Main)) {
        if (response.isSuccessful) {
          var messages = response.body()?.rows!!
          var filteredMessages = listOf<ChatMessage>()
          if (messages.count() > 0 ) {
            for (item in messages) {
              if (item.userUid == userUid || item.userUid == userUid2) {
                filteredMessages+=item
              }
            }
          }
          _messages.postValue(filteredMessages.toMutableList())
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

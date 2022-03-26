package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.repository.Repository
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class RegisterViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {

    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _createUserStatus = MutableLiveData<CreateUserResponse>()
    var createUserStatus: LiveData<CreateUserResponse> = _createUserStatus
    var job: Job? = null

    init {
        loading.postValue(false)
    }

    fun createUser(email: String,isShovler:Boolean,name:String,password:String,phone:String,uid:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val createUserRequest = CreateUserRequest(email,isShovler,name,password,phone,uid)
            val response = repository.createUser(createUserRequest)
           withContext((Dispatchers.Main)) {
            if (response.isSuccessful) {
                _createUserStatus.postValue(response.body())
                loading.postValue(false)
            } else {
                onError("Error : ${response.message()}")
                Log.d(TAG, "createUser:  ${response.message()}")
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


package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import com.example.theavengers_mad5254_project.repository.MainRepository

import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.coroutines.*

class RegisterViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {

    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var auth: FirebaseAuth? = null
    private val _createUserStatus = MutableLiveData<CreateUserResponse>()
    var createUserStatus: LiveData<CreateUserResponse> = _createUserStatus

    private val _signInStatus = MutableLiveData<ResultOf<String>>()
    val signInStatus: LiveData<ResultOf<String>> = _signInStatus

    var job: Job? = null

    init {
        auth = FirebaseAuth.getInstance()
        loading.postValue(false)
    }

    fun createUser(email: String,isShovler:Boolean,name:String,password:String,phone:String,uid:String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val createUserRequest = CreateUserRequest(email,isShovler,name,password,phone,uid)
            val response = repository.createUser(createUserRequest)
           withContext((Dispatchers.Main)) {
               if (!response.isSuccessful){
                   onError("Error : ${response.body()?.err?.message}")
                   Log.d(TAG, "createUser:  ${response.body()?.err?.message}")
               }else{
                       _createUserStatus.postValue(response.body())
                       loading.postValue(false)
               }

              }
        }
    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.postValue(false)
    }

    //Firebase Login Method
    fun signIn(email: String,password: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch  {
            val errorCode = -1
            try {
                auth?.let { login ->
                    login.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if(!task.isSuccessful){
                                println("Login Failed with ${task.exception}")
                                _signInStatus.postValue(ResultOf.Success("Login Failed with ${task.exception}"))
                            }else{
                                getUserToken(task)
                                _signInStatus.postValue(ResultOf.Success("Login Successful"))

                            }
                            loading.postValue(false)
                        }
                }
            }catch (e: Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _signInStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _signInStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }
    }

    fun fetchLoading():LiveData<Boolean> = loading

    //getUserToken Method
    private fun getUserToken(task: Task<AuthResult>){
        val firebaseUser: FirebaseUser? = task.result.user
        firebaseUser?.getIdToken(true)?.addOnSuccessListener{result ->
            AppPreference.userToken = result.token.toString()

            Log.d(TAG, "signIn: ${AppPreference.userToken}")
        }
        AppPreference.userID = firebaseUser?.uid.toString()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}


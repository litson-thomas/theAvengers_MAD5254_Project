package com.example.theavengers_mad5254_project.viewmodel

import androidx.lifecycle.*
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FirebaseViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel(),LifecycleObserver {
    private var auth: FirebaseAuth? = null
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        auth = FirebaseAuth.getInstance()
        loading.postValue(false)
    }
    private val _registrationStatus = MutableLiveData<ResultOf<String>>()
    val registrationStatus: LiveData<ResultOf<String>> = _registrationStatus

    //Firebase RegisterMethod
    fun signUp(email:String,password: String){
        loading.postValue(true)
        viewModelScope.launch(dispatcher) {
            var errorCode = -1
            try {
                auth?.let { authentication ->
                    authentication.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if (!task.isSuccessful){
                                println("Registration Failed with ${task.exception}")
                                _registrationStatus.postValue(ResultOf.Success("Registration Failed with ${task.exception}"))
                            }else{
                                _registrationStatus.postValue(ResultOf.Success("UserCreated"))

                            }
                            loading.postValue(false)
                        }
                }
            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                if (errorCode != -1){
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }
    }
    

    private val _signInStatus = MutableLiveData<ResultOf<String>>()
    val signInStatus: LiveData<ResultOf<String>> = _signInStatus

    //Firebase Login Method
    fun signIn(email: String,password: String){
        loading.postValue(true)
        viewModelScope.launch(dispatcher) {
            var errorCode = -1
            try {
                auth?.let { login ->
                    login.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if(!task.isSuccessful){
                                println("Login Failed with ${task.exception}")
                                _signInStatus.postValue(ResultOf.Success("Login Failed with ${task.exception}"))
                            }else{
                                _signInStatus.postValue(ResultOf.Success("Login Successful"))

                            }
                            loading.postValue(false)
                        }
                }
            }catch (e: Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }
    }

    private val _signOutStatus = MutableLiveData<ResultOf<String>>()
    val signOutStatus: LiveData<ResultOf<String>> = _signOutStatus

    //Firebase SignOut Method
    fun signOut(){
        loading.postValue(true)
        viewModelScope.launch(dispatcher) {
            var errorCode = -1
            try {
                auth?.let { authentication ->
                    authentication.signOut()
                    _signOutStatus.postValue(ResultOf.Success("Signout Successful"))
                    loading.postValue(false)
                }
            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _signOutStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _signOutStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }
    }

    fun fetchLoading():LiveData<Boolean> = loading
}
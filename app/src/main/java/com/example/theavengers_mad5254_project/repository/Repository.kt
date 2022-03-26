package com.example.theavengers_mad5254_project.repository

import androidx.lifecycle.LiveData
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.api.MyRetrofitBuilder
import com.example.theavengers_mad5254_project.model.data.User
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.responseModel.CreateUserResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Retrofit

class Repository() {

    var job: CompletableJob? = null

    fun getUser(userId: String): LiveData<User>{
        job = Job()
        return object: LiveData<User>(){
            override fun onActive() {
                super.onActive()
                job?.let{ theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val user = MyRetrofitBuilder.apiService.getUser(userId)
                        withContext(Main){
                            value = user
                            theJob.complete()
                        }
                    }

                }

            }
        }
    }


    fun cancelJobs(){
        job?.cancel()
    }

}

package com.example.theavengers_mad5254_project.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theavengers_mad5254_project.model.data.*
import com.example.theavengers_mad5254_project.repository.MainRepository
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import org.apache.commons.io.FilenameUtils
import java.io.File

class ShovlerViewModel(private val repository: MainRepository)
    : ViewModel(), LifecycleObserver {
    val errorMessage = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _becomeShovlerStatus = MutableLiveData<APIResponse>()
    var becomeShovlerStatus: LiveData<APIResponse> = _becomeShovlerStatus
    var job: Job? = null

    private val _uploadShovlerImageStatus = MutableLiveData<Boolean>()
    var uploadShovlerImageStatus: LiveData<Boolean> = _uploadShovlerImageStatus

    private val _deleteShovlerImageStatus = MutableLiveData<Boolean>()
    var deleteShovlerImageStatus: LiveData<Boolean> = _deleteShovlerImageStatus



    val shovlerList = MutableLiveData<List<Shovler>>()
    val addressList = MutableLiveData<List<Address>>()


    init {
        loading.postValue(false)
    }
    fun uploadShowlerImage(list : ArrayList<String>) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            //val file = list[0]
            val cnt = list.count()
            var i = 0
            var success = true
            for(item in list) {
                i++
                val file = File(item)
                val filename = FilenameUtils.getName(file.name)
                val part = MultipartBody.Part.createFormData(
                    "files",
                    filename,
                    RequestBody.create("image/*".toMediaTypeOrNull(), file)
                )
                val response = repository.uploadShowlerImage(part)
                withContext((Dispatchers.Main)) {
                    if (response.isSuccessful) {
                       // _uploadShovlerImageStatus.postValue(response.body())
                       // loading.postValue(false)
                    } else {
                        success = false
                        onError("Error : ${response.message()}")
                        Log.d(ContentValues.TAG, "uploadShowlerImage:  ${response.message()}")
                    }
                }
            }
            _uploadShovlerImageStatus.postValue(success)
            loading.postValue(false)
        }
    }
    fun deleteShovlerImage(list : ArrayList<Int>) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            //val file = list[0]
            val cnt = list.count()
            var i = 0
            var success = true
            for(item in list) {
                val response = repository.deleteShovlerImage(item)
                withContext((Dispatchers.Main)) {
                    if (response.isSuccessful) {
                        // _uploadShovlerImageStatus.postValue(response.body())
                        // loading.postValue(false)
                    } else {
                        success = false
                        onError("Error : ${response.message()}")
                        Log.d(ContentValues.TAG, "deleteShovlerImage:  ${response.message()}")
                    }
                }
            }
            _deleteShovlerImageStatus.postValue(success)
            loading.postValue(false)
        }
    }

    fun addShovler(userUid: String, title:String, description:String, radius_limit:Int,
                      one_four_price:Int, five_eight_price:Int, nine_twelve_price:Int, city_side_price:Int,
                      transit_number:Int,institution_number:Int,account_number:Int, addressId: Int,shovler_images:List<ShovlerImage>){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val shovler = Shovler(null,userUid,title,description,radius_limit,
                one_four_price,five_eight_price,nine_twelve_price,city_side_price,
                transit_number,institution_number,account_number, addressId, shovler_images)
            val response = repository.addShovler(shovler)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _becomeShovlerStatus.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "addShovler:  ${response.message()}")
                }
            }
        }
    }
    fun updateShovler(shovlerId: Int, userUid: String, title:String, description:String, radius_limit:Int,
                   one_four_price:Int, five_eight_price:Int, nine_twelve_price:Int, city_side_price:Int,
                   transit_number:Int,institution_number:Int,account_number:Int, addressId: Int,shovler_images:List<ShovlerImage>){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val shovler = Shovler(shovlerId,userUid,title,description,radius_limit,
                one_four_price,five_eight_price,nine_twelve_price,city_side_price,
                transit_number,institution_number,account_number, addressId, shovler_images)
            val response = repository.updateShovler(shovler)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    _becomeShovlerStatus.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "updateShovler:  ${response.message()}")
                }
            }
        }
    }
    fun getShovlerListings(userUid: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getShovlerListings(userUid)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    shovlerList.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getShovlerListings:  ${response.message()}")
                }
            }
        }
    }
    fun getShovlerListing(id: Int){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getShovlerListing(id)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    shovlerList.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getShovlerListing:  ${response.message()}")
                }
            }
        }
    }
    fun getAddress(userUid: String){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAddress(userUid)
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    addressList.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getAddress:  ${response.message()}")
                }
            }
        }
    }
    fun getAddress(){
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAddress()
            withContext((Dispatchers.Main)) {
                if (response.isSuccessful) {
                    addressList.postValue(response.body()?.rows)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()}")
                    Log.d(ContentValues.TAG, "getAddress:  ${response.message()}")
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
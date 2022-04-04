package com.example.theavengers_mad5254_project.views.my_account

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyProfileBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.responseModel.Prediction
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.MyProfileViewModel
import com.example.theavengers_mad5254_project.viewmodel.MyProfileViewModelFactory

class MyProfile : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var viewModel: MyProfileViewModel
    private lateinit var viewModelFactory: MyProfileViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_profile)

        //ApiService.getInstance()
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = MyProfileViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MyProfileViewModel::class.java]

        observerLoadingProgress()
        getUser()

        binding.btnUpdateProfile.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.myprofileEditname.text.toString()) -> {
                    binding.myprofileEditname.error = "Name Field is empty"
                    binding.myprofileEditname.requestFocus()

                }
                TextUtils.isEmpty(binding.myprofileEditphone.text.toString()) -> {
                    binding.myprofileEditphone.error = "Phone Field is empty"
                    binding.myprofileEditphone.requestFocus()
                }

                else -> {
                    updateProfile(binding.myprofileEditname.text.toString(),
                        binding.myprofileEditphone.text.toString(),AppPreference.userID)
                }
            }
        }
    }

    //Update Profile
    private fun updateProfile(name:String, phone:String, uid:String) {
        viewModel.updateProfile(name,phone,uid)
        viewModel.updateProfile.observe(this, Observer {
            if (it.status) {
                AppPreference.userName = binding.myprofileEditname.text.toString()
                onBackPressed()
                CommonMethods.toastMessage(applicationContext,"Profile Updated")
            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

    private fun getUser(){
        viewModel.getUser()
        viewModel.user.observe(this, Observer {
            if (it.rows.isNotEmpty()){
                binding.myprofileEditname.setText(it.rows.first().name)
                binding.myprofileEditphone.setText(it.rows.first().phone)
            }
        })
    }
    //method for progress bar
    private fun observerLoadingProgress(){
        viewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.loginProgress.visibility = View.GONE
            }else{
                binding.loginProgress.visibility = View.VISIBLE
            }

        })


    }
}
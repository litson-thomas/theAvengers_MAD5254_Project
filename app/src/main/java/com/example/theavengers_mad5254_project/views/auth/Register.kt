package com.example.theavengers_mad5254_project.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityRegisterBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.example.theavengers_mad5254_project.viewmodel.FireBaseViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.FirebaseViewModel
import com.example.theavengers_mad5254_project.views.home.Home

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: FirebaseViewModel
    private lateinit var viewModelFactory: FireBaseViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        viewModelFactory = FireBaseViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory)[FirebaseViewModel::class.java]
        binding.viewModel = viewModel

        binding.registerButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.registerEmail.text.toString())
                || TextUtils.isEmpty(binding.registerName.text.toString())
                || TextUtils.isEmpty(binding.registerPhone.text.toString())
                || TextUtils.isEmpty(binding.registerPassword.text.toString())
                || TextUtils.isEmpty(binding.regsiterPlace.text.toString())) {
                    CommonMethods.toastMessage(applicationContext,"Input Fields cannot be Empty")
            } else{
                doRegistration()
            }
        }
        observeRegistration()
    }

    private fun doRegistration(){
        viewModel.signUp(binding.registerEmail.text.toString(),binding.registerPassword.text.toString())
    }

    private fun observeRegistration(){
        viewModel.registrationStatus.observe(this, Observer { result->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("UserCreated",ignoreCase = true)){
                            val intent =  Intent(this, Login::class.java)
                            startActivity(intent)
                            finish()
                            CommonMethods.toastMessage(applicationContext,"Registration Successful User created")
                        }else{
                            CommonMethods.toastMessage(applicationContext,"Registration failed with ${it.value}")
                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        CommonMethods.toastMessage(applicationContext,"Registration failed with $failedMessage")
                    }
                }
            }
        })
    }
}

package com.example.theavengers_mad5254_project.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityLoginBinding
import com.example.theavengers_mad5254_project.databinding.ActivityRegisterBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.example.theavengers_mad5254_project.viewmodel.FireBaseViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.FirebaseViewModel
import com.example.theavengers_mad5254_project.views.home.Home

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: FirebaseViewModel
    private lateinit var viewModelFactory: FireBaseViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModelFactory = FireBaseViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory)[FirebaseViewModel::class.java]
        binding.viewModel = viewModel

        binding.loginButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.loginEmail.text.toString()) || TextUtils.isEmpty(binding.loginPassword.text.toString())){
                CommonMethods.toastMessage(applicationContext,"Login fields can't be empty")
            }else{
                signIn(binding.loginEmail.text.toString(),binding.loginPassword.text.toString())
            }
        }
        binding.loginCreateAccount.setOnClickListener {
            val intent =  Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signIn(loginEmail: String, loginPassword: String) {
        viewModel.signIn(loginEmail,loginPassword)
        observeSignIn()
    }

    private fun observeSignIn() {
        viewModel.signInStatus.observe(this, Observer { result->
            result?.let{
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("Login Successful",ignoreCase = true)){
                            CommonMethods.toastMessage(applicationContext,"Login Successful")
                            val intent =  Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        }else if(it.value.equals("Reset",ignoreCase = true)){

                        }
                        else{
                            CommonMethods.toastMessage(applicationContext,"Login failed with ${it.value}")
                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        CommonMethods.toastMessage(applicationContext,"Login failed with $failedMessage")
                    }
                }
            }
        })
    }
}

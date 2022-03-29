package com.example.theavengers_mad5254_project.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityLoginBinding
import com.example.theavengers_mad5254_project.databinding.ActivityResetBinding
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.example.theavengers_mad5254_project.viewmodel.FireBaseViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.FirebaseViewModel

class Reset : AppCompatActivity() {
    private lateinit var binding: ActivityResetBinding
    private lateinit var viewModel: FirebaseViewModel
    private lateinit var viewModelFactory: FireBaseViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reset)
        viewModelFactory = FireBaseViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory)[FirebaseViewModel::class.java]
        binding.viewModel = viewModel
       // AppPreference.init(this)
        observerLoadingProgress()

        binding.resetButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.resetEmail.text.toString())){
                CommonMethods.toastMessage(applicationContext,"Input Field cannot be Empty")
                }else{
                    resetPassword(binding.resetEmail.text.toString())
            }
        }

    }

    private fun resetPassword(email: String) {
        viewModel.resetPassword(email)
        observeResetPassword()
    }

    private fun observeResetPassword() {
        viewModel.resetPasswordStatus.observe(this, Observer { result ->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        when {
                            it.value.equals("Password Reset Email was Sent", ignoreCase = true) -> {
                               onBackPressed()
                                CommonMethods.toastMessage(
                                    applicationContext,
                                    "Check email to reset your password!"
                                )

                            }  else -> {
                            CommonMethods.toastMessage(applicationContext,"Failed with ${it.value}")
                        }
                        }
                    }
                    is ResultOf.Failure ->{
                        val failedMessage =  it.message ?: "Unknown Error"
                        CommonMethods.toastMessage(applicationContext,"Failed with $failedMessage")

                    }
                }
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

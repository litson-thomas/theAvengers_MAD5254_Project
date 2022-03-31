package com.example.theavengers_mad5254_project.views.auth


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityRegisterBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.RegisterViewModel
import com.example.theavengers_mad5254_project.viewmodel.RegisterViewModelFactory
import com.example.theavengers_mad5254_project.views.home.Home

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
//    private lateinit var viewModel: FirebaseViewModel
//    private lateinit var viewModelFactory: FireBaseViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerViewModelFactory: RegisterViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
//        viewModelFactory = FireBaseViewModelFactory()
//        viewModel = ViewModelProvider(this, viewModelFactory)[FirebaseViewModel::class.java]
//        binding.viewModel = viewModel
        val retrofitService =  ApiClient().getApiService(this) //ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        registerViewModelFactory = RegisterViewModelFactory(mainRepository)
        registerViewModel = ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]
        binding.viewModelRegister = registerViewModel

        observerLoadingProgress()

        binding.registerButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.registerEmail.text.toString())
                || TextUtils.isEmpty(binding.registerName.text.toString())
                || TextUtils.isEmpty(binding.registerPhone.text.toString())
                || TextUtils.isEmpty(binding.registerPassword.text.toString())
                || TextUtils.isEmpty(binding.regsiterPlace.text.toString())) {
                    CommonMethods.toastMessage(applicationContext,"Input Fields cannot be Empty")
            } else  if (!CommonMethods.isValidMobile(binding.registerPhone.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Enter a valid phone number")

            } else{
                doRegisterUser()

            }
        }


        }

    //method for progress bar
    private fun observerLoadingProgress(){
        registerViewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.loginProgress.visibility = View.GONE
            }else{
                binding.loginProgress.visibility = View.VISIBLE
            }

        })


    }

    private fun doRegisterUser(){
        val id: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        registerViewModel.createUser(binding.registerEmail.text.toString(),false,binding.registerName.text.toString(),binding.registerPassword.text.toString(),binding.registerPhone.text.toString(), id )
        createUser()
    }

    //validating create user method
    private fun createUser(){
       registerViewModel.createUserStatus.observe(this, Observer {
           if (it.status) {
               signIn(binding.registerEmail.text.toString(),binding.registerPassword.text.toString())
           } else {
               CommonMethods.toastMessage(applicationContext, "FAILURE ${it.err.message}")
           }
       })
    }

    private fun signIn(loginEmail: String, loginPassword: String) {
        registerViewModel.signIn(loginEmail,loginPassword)
        observeSignIn()
    }

    private fun observeSignIn() {
        registerViewModel.signInStatus.observe(this, Observer { result->
            result?.let{
                when(it){
                    is ResultOf.Success ->{
                        when {
                            it.value.equals("Login Successful",ignoreCase = true) -> {
                                AppPreference.isLogin = true
                                val intent = Intent(this, Home::class.java)
                                startActivity(intent)
                                finish()
                                Log.d(TAG, "signIn: ${AppPreference.userToken}")
                                CommonMethods.toastMessage(applicationContext,"Registration Successful User created")
                            }

                            else -> {
                                CommonMethods.toastMessage(applicationContext,"Registration failed with ${it.value}")
                            }
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

    //    private fun doRegistration(){
//        viewModel.signUp(binding.registerEmail.text.toString(),binding.registerPassword.text.toString())
//
//        observeRegistration()
//    }
//
//    private fun observeRegistration(){
//        viewModel.registrationStatus.observe(this, Observer { result->
//            result?.let {
//                when(it){
//                    is ResultOf.Success ->{
//                        if(it.value.equals("UserCreated",ignoreCase = true)){
//                            AppPreference.isLogin = true
//
//
//                        }else{
//                            CommonMethods.toastMessage(applicationContext,"Registration failed with ${it.value}")
//                        }
//                    }
//                    is ResultOf.Failure -> {
//                        val failedMessage =  it.message ?: "Unknown Error"
//                        CommonMethods.toastMessage(applicationContext,"Registration failed with $failedMessage")
//                    }
//                }
//            }
//        })
//    }
}

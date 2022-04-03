package com.example.theavengers_mad5254_project.views.auth

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityLoginBinding
import com.example.theavengers_mad5254_project.utils.AppPreference
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

        observerLoadingProgress()
        selfLocationPermissionGranted()
        onClickEvents()



    }

    private fun onClickEvents() {
        binding.loginButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.loginEmail.text.toString()) || TextUtils.isEmpty(binding.loginPassword.text.toString())){
                CommonMethods.toastMessage(applicationContext,"Login fields can't be empty")
            }else{
                signIn(binding.loginEmail.text.toString(),binding.loginPassword.text.toString())
            }
        }
        binding.loginCreateAccount.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginForgotPassword.setOnClickListener {
            val intent = Intent(this, Reset::class.java)
            startActivity(intent)
        }

    }

    private fun selfLocationPermissionGranted() {

        if (ContextCompat.checkSelfPermission(this@Login,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@Login,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@Login,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@Login,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
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
                        when {
                            it.value.equals("Login Successful",ignoreCase = true) -> {
                                AppPreference.isLogin = true
                                Log.d("TOKENNN", "signIn: ${AppPreference.userToken}")
                                CommonMethods.toastMessage(applicationContext,"Login Successful")
                                val intent = Intent(this, Home::class.java)
                                startActivity(intent)
                                finish()
                            }

                            else -> {
                                CommonMethods.toastMessage(applicationContext,"Login failed with ${it.value}")
                            }
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@Login,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


}

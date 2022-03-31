package com.example.theavengers_mad5254_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.databinding.ActivitySplashBinding
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.viewmodel.SplashState
import com.example.theavengers_mad5254_project.viewmodel.SplashViewModel
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.becomeShovler.BecomeShovler
import com.example.theavengers_mad5254_project.views.home.Home
import com.example.theavengers_mad5254_project.views.shovlerDashboard.MapsActivity
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ShovlerDashboard
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewDirections

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        AppPreference.init(this)
        viewModel.liveData.observe(this, Observer {
            when(it){
                is SplashState.Home ->{
                   continueHomePage()
                }
            }
        })

    }
    private fun continueHomePage() {
        if (AppPreference.isLogin) {
           val intent = Intent(this, Home::class.java)
           // val intent = Intent(this, ShovlerDashboard::class.java)
            //val intent = Intent(this, BecomeShovler::class.java)
            //val intent = Intent(this, ViewDirections::class.java)

            startActivity(intent)
        } else {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        finish()
    }
}
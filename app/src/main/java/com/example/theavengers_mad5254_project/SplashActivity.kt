package com.example.theavengers_mad5254_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewOrder
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        auth = FirebaseAuth.getInstance()

        AppPreference.init(this)
        if(auth?.currentUser != null){
          AppPreference.userName = auth?.currentUser!!.displayName.toString()
        }

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
            startActivity(intent)
        } else {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        finish()
    }
}

package com.example.theavengers_mad5254_project.views.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityRegisterBinding
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.RegisterViewModel
import com.example.theavengers_mad5254_project.viewmodel.RegisterViewModelFactory


class Home : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val retrofitService = ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = HomeViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        loadShovlers()
    }

    private fun loadShovlers(){
      viewModel.loadShovlers()
      viewModel.shovlers.observe(this, Observer {
        Log.e("Shovlers are", ""+it);
      })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

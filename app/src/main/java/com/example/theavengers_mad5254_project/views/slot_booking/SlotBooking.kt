package com.example.theavengers_mad5254_project.views.slot_booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivitySlotBookingBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModelFactory

class SlotBooking : AppCompatActivity() {

    private lateinit var binding: ActivitySlotBookingBinding
    private lateinit var viewModel: SlotBookingViewModel
    private lateinit var viewModelFactory: SlotBookingViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_slot_booking)
        // setting up view model
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = SlotBookingViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SlotBookingViewModel::class.java]

        getUser()
    }

    private fun getUser(){
      viewModel.user.observe(this) { user ->
        Log.e("USER IS => ", ""+user);
      }
    }
}

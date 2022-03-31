package com.example.theavengers_mad5254_project.views.slot_booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityConfirmSlotBookingBinding
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.ShovelerAddress
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModelFactory

class ConfirmSlotBooking : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmSlotBookingBinding
    private lateinit var viewModel: SlotBookingViewModel
    private lateinit var viewModelFactory: SlotBookingViewModelFactory
    private lateinit var userAddress: ShovelerAddress
    private var addressId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_slot_booking)
        val bundle :Bundle ? = intent.extras
        if (bundle!=null){
          binding.slotConfirmDate.text = bundle.getString("date")
          addressId = bundle.getInt("addressId")
        }
        // set up the view model
        val retrofitService = ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = SlotBookingViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SlotBookingViewModel::class.java]
        getUser()
    }

    private fun getUser(){
      viewModel.getUser()
      viewModel.user.observe(this, Observer {
        for (address in it.rows.get(0).addresses){
          if(address.id == addressId){
            userAddress = address
            getBundleValues()
          }
        }
      })
    }

    private fun getBundleValues(){
      // get the details
      val bundle :Bundle ? = intent.extras
      if (bundle!=null){
        binding.slotConfirmAddressOne.text = userAddress.addressOne
        binding.slotConfirmAddressTwo.text = userAddress.addressTwo
        binding.slotConfirmHoursValue.text = "$" + bundle.getString("hours_required")
        var side_walk = "No"
        if(bundle.getBoolean("side_walk") == true) side_walk = "Yes"
        binding.slotConfirmSidewalkValue.text = side_walk
        binding.slotConfirmAmountValue.text = "$" + bundle.getDouble("amount").toString()
        var tax = 0.0
        var total = 0.0
        tax = bundle.getDouble("amount") * 0.18
        total = bundle.getDouble("amount") + tax
        binding.slotConfirmTaxValue.text = "$" + tax.toString()
        binding.slotConfirmGrandtotalValue.text = "$" + total.toString()
      }
    }
}

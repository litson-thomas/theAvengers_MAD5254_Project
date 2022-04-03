package com.example.theavengers_mad5254_project.views.slot_booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityConfirmSlotBookingBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.ShovelerAddress
import com.example.theavengers_mad5254_project.model.data.requestModel.PrepareBookingRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.ShovelerBookingRequest
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModelFactory
import com.example.theavengers_mad5254_project.views.my_account.Bookings.MyBookings
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.text.SimpleDateFormat

class ConfirmSlotBooking : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmSlotBookingBinding
    private lateinit var viewModel: SlotBookingViewModel
    private lateinit var viewModelFactory: SlotBookingViewModelFactory
    private lateinit var userAddress: ShovelerAddress
    private var addressId = 0
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_slot_booking)
        val bundle :Bundle ? = intent.extras
        // stripe payment sheet init
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        // set up the view model
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = SlotBookingViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SlotBookingViewModel::class.java]
        if (bundle!=null){
          binding.slotConfirmDate.text = bundle.getString("date")
          addressId = bundle.getInt("addressId")
          var tax = 0.0
          var total = 0.0
          tax = bundle.getDouble("amount") * 0.18
          total = bundle.getDouble("amount") + tax
          val bookingRequest = PrepareBookingRequest(
            instructions = bundle.getString("instructions"),
            date = bundle.getString("date"),
            price = total,
            hours_required = getHoursByPosition(bundle.getInt("hours_position")),
            shovelerId = bundle.getInt("shovelerId"),
            addressId = bundle.getInt("addressId"),
            amount = total
          )
          viewModel.prepareBooking(bookingRequest)
        }
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
        binding.slotConfirmBtn.setOnClickListener {
          checkOut()
        }
      }
    }

    private fun checkOut(){
      viewModel.prepareBooking.observe(this, Observer {
        paymentIntentClientSecret = it.paymentIntent
        customerConfig = PaymentSheet.CustomerConfiguration(
          it.customer,
          it.ephemeralKey
        )
        val publishableKey = it.publishableKey
        PaymentConfiguration.init(this, publishableKey)
        presentPaymentSheet()
      })
    }

    fun presentPaymentSheet() {
      paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
          merchantDisplayName = "GoSnow Shoveler Booking",
          customer = customerConfig,
          allowsDelayedPaymentMethods = true
        )
      )
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
      when(paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
          Log.e("PAYMENT STATUS => ", "PAYMENT CANCELLED");
        }
        is PaymentSheetResult.Failed -> {
          Log.e("PAYMENT ERROR => ", "Error: ${paymentSheetResult.error}");
        }
        is PaymentSheetResult.Completed -> {
          CommonMethods.toastMessage(this, "Payment Successful!")
          val intent = Intent(applicationContext, BookingSuccess::class.java)
          finishAffinity();
          startActivity(intent)
        }
      }
    }

    fun getHoursByPosition(position: Int): Int{
      var value = 4
      if(position == 1) value = 4
      if(position == 2) value = 8
      if(position == 3) value = 12
      return value
    }

}

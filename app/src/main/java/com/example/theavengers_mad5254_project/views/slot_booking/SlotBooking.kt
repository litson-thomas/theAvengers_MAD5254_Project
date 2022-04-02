package com.example.theavengers_mad5254_project.views.slot_booking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.SlotBookingAddressAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivitySlotBookingBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient

import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovelerAddress
import com.example.theavengers_mad5254_project.model.data.ShovlerUser

import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.SlotBookingViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class SlotBooking : AppCompatActivity() {

    private lateinit var binding: ActivitySlotBookingBinding
    private lateinit var viewModel: SlotBookingViewModel
    private lateinit var viewModelFactory: SlotBookingViewModelFactory
    private lateinit var shovlerViewModel: HomeViewModel
    private lateinit var shovlerViewModelFactory: HomeViewModelFactory
    private lateinit var shovelerUser: ShovlerUser
    private lateinit var addressAdapter: SlotBookingAddressAdaptor
    private var shovelerId: Int? = null
    private var shovelerDetails: Shoveler? = null
    var hoursItems = arrayOf("1 - 4 Hours", "5 - 8 Hours", "9 - 12 Hours")
    private var hoursRate: Double? = null
    private var hoursPosition: Int = 1
    private var selectedAddress: ShovelerAddress? = null
    private var selectedDate: String? = null
    private var selectedDateValue: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_slot_booking)
        // setting up view model
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = SlotBookingViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SlotBookingViewModel::class.java]
        shovlerViewModelFactory = HomeViewModelFactory(mainRepository)
        shovlerViewModel = ViewModelProvider(this, shovlerViewModelFactory)[HomeViewModel::class.java]
        getShoveler()
        getUser()
    }


    fun getShoveler(){
      // get the id
      val bundle :Bundle ?=intent.extras
      if (bundle!=null){
        shovelerId = bundle.getInt("id")
      }
      if(shovelerId != null){
        shovlerViewModel.loadShovlerById(shovelerId!!)
        shovlerViewModel.selectedShovler.observe(this, androidx.lifecycle.Observer {
          shovelerDetails = it
        })
      }
    }

    private fun getUser(){
      viewModel.getUser()
      viewModel.user.observe(this) { user ->
        shovelerUser = user.rows.get(0)
        prepareValues()
      }
    }

    private fun prepareValues(){
      addressAdapter = SlotBookingAddressAdaptor(
        this,
        R.layout.custom_spinner,
        shovelerUser.addresses
      )
      binding.slotBookingAddress.adapter = addressAdapter
      binding.slotBookingAddress.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          selectedAddress = shovelerUser.addresses.get(position)
        }
      }
      // set the hours spinner
      val hoursAdaptor = ArrayAdapter(this, R.layout.custom_spinner, hoursItems)
      // Set layout to use when the list of choices appear
      hoursAdaptor.setDropDownViewResource(R.layout.custom_spinner)
      // Set Adapter to Spinner
      binding.slotBookingHours.setAdapter(hoursAdaptor)
      binding.slotBookingHours.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          binding.slotBookingHoursResult.text = hoursItems[position] + " selected: $" + getHourPriceFromUser(position)
          hoursRate = getHourPriceFromUser(position)
          calculateTotal()
        }
      }
      // set the date picker
      val c = Calendar.getInstance()
      val year = c.get(Calendar.YEAR)
      val month = c.get(Calendar.MONTH)
      val day = c.get(Calendar.DAY_OF_MONTH)
      val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        binding.slotBookingDate.setText("" + dayOfMonth + " " + AppPreference.MONTHS[monthOfYear] + ", " + year)
        selectedDate = "" + dayOfMonth + " " + AppPreference.MONTHS[monthOfYear] + ", " + year
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: String = "" + year + "-" + (month+1) + "-" + day
        selectedDateValue = format.parse(date)
      }, year, month, day)
      datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
      // datepicker select button click
      binding.slotBookingDate.setOnClickListener {
        datePicker.show()
      }
      // side walk btn click listener
      binding.slotBookingSidewalkBtn.setOnClickListener {
        var tag = 0
        if(binding.slotBookingSidewalkIcon.tag == 0) tag = 1
        if(binding.slotBookingSidewalkIcon.tag == 1) tag = 0
        binding.slotBookingSidewalkIcon.tag = tag
        changeSideWalkBtn(tag)
      }
      // confirm details
      binding.slotBookingContinue.setOnClickListener{
        confirmDetails()
      }
    }

    private fun confirmDetails(){
      if(selectedAddress == null) {
        CommonMethods.toastMessage(this, "Please select Address")
        return
      }
      if(selectedDate == null) {
        CommonMethods.toastMessage(this, "Please select Date")
        return
      }
      if(hoursRate == null) {
        CommonMethods.toastMessage(this, "Please select hours")
        return
      }
      var total = 0.0
      total += hoursRate!!
      if(binding.slotBookingSidewalkIcon.tag == 1) total += shovelerDetails?.citySidePrice!!
      binding.slotBookingTotalCost.text = "$" + total
      var citySideWalk = false
      if(binding.slotBookingSidewalkIcon.tag == 1) citySideWalk = true
      val intent = Intent(this, ConfirmSlotBooking::class.java)
      intent.putExtra("amount", total)
      intent.putExtra("instructions", binding.slotBookingInstructions.text.toString())
      intent.putExtra("date", selectedDate)
      intent.putExtra("addressId", selectedAddress!!.id)
      intent.putExtra("side_walk",  citySideWalk)
      intent.putExtra("hours_required",  hoursRate.toString())
      intent.putExtra("hours_position", hoursPosition)
      intent.putExtra("shovelerId", shovelerId)
      intent.putExtra("date_value", selectedDateValue.toString())
      startActivity(intent)
    }

    private fun getHourPriceFromUser(position: Int): Double {
      var price = 0.0
      hoursPosition = position + 1
      if(position == 0 && shovelerDetails?.oneFourPrice != null) price = shovelerDetails?.oneFourPrice!!
      if(position == 1 && shovelerDetails?.fiveEightPrice != null) price = shovelerDetails?.fiveEightPrice!!
      if(position == 2 && shovelerDetails?.nineTwelvePrice != null) price = shovelerDetails?.nineTwelvePrice!!
      return price
    }

    private fun calculateTotal(){
      var total = 0.0
      total += hoursRate!!
      if(binding.slotBookingSidewalkIcon.tag == 1) total += shovelerDetails?.citySidePrice!!
      binding.slotBookingTotalCost.text = "$" + total
    }

    private fun changeSideWalkBtn(tag: Int){
      if(tag == 0) binding.slotBookingSidewalkIcon.setImageResource(R.drawable.ic_round_check_circle_24)
      else binding.slotBookingSidewalkIcon.setImageResource(R.drawable.ic_checkmark_selected)
      calculateTotal()
    }
}

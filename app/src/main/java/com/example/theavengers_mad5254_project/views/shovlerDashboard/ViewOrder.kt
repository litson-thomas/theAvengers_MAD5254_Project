package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityViewOrderBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.BookingResponse
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat

class ViewOrder : AppCompatActivity() {
    private lateinit var binding: ActivityViewOrderBinding
    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var bookingViewModelFactory: BookingViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_order)
        var booking = intent.getSerializableExtra("booking") as Booking
        binding.orderAddress.text = booking.addressId
        binding.orderHours.text = "${booking.hours_required} hrs"
        binding.orderTotalAmount.text = "$${booking.price}"
        binding.orderTax.text = "$0"
        binding.orderGrandTotal.text = "$${booking.price}"
        binding.orderAddress.text = "${booking.address?.address_one}"
        if (booking.address?.address_two != null) {
            binding.orderAddress.text ="${booking.address?.address_one} ${booking.address?.address_two}"
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(booking.date)
        val stringFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy")
        binding.orderDate.text = stringFormat.format(date);

        binding.feedbackBtn.setOnClickListener {
            var booking = intent.getSerializableExtra("booking") as Booking
            val feedBackIntent = Intent(this, ViewFeedBack::class.java)
            feedBackIntent.putExtra("bookingId",booking.id)
            feedBackIntent.putExtra("shovlerId", booking.shovlerId)
            startActivity(feedBackIntent)
        }

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        bookingViewModelFactory = BookingViewModelFactory(mainRepository)
        bookingViewModel = ViewModelProvider(this,bookingViewModelFactory)[BookingViewModel::class.java]

        //observerLoadingProgress()
        binding.markCompletedBtn.setOnClickListener {
            markCompleted()
        }
    }

    private fun markCompleted() {
        var booking = intent.getSerializableExtra("booking") as Booking
        bookingViewModel.updateBooking(
            booking.id, booking.shovlerId, booking.addressId, booking.instructions,
        booking.date, booking.price, booking.hours_required, true)
        bookingViewModel.markCompletedStatus.observe(this, Observer {
            if (it.status) {
                //binding.markCompletedBtn.text = "Completed"
                CommonMethods.toastMessage(applicationContext,"Job Completed Successful")
                val intent = Intent(this, MyJobs::class.java)
                startActivity(intent)
            } else {
                CommonMethods.toastMessage(applicationContext, "FAILURE ${it.err.message}")
            }
        })
    }

    private fun observerLoadingProgress() {
        bookingViewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.markCompletedProgress.visibility = View.GONE
            }else{
                binding.markCompletedProgress.visibility = View.VISIBLE
            }

        })
    }

}
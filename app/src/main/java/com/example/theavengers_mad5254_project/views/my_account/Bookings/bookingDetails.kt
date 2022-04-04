package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.DataBindingTriggerClass
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityBookingDetailsBinding
import com.example.theavengers_mad5254_project.fragments.common.DirectionHeader
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import java.text.DateFormat
import java.text.SimpleDateFormat

class bookingDetails : AppCompatActivity() {
    private lateinit var binding: ActivityBookingDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_booking_details)

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
        FragmentUtil.setHeader("Order #${booking.id}","Order Details", false,supportFragmentManager)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(booking.date)
        val stringFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy")
        binding.orderDate.text = stringFormat.format(date);

    }

    fun btnGiveRating(view: View){
        var booking = intent.getSerializableExtra("booking") as Booking
        val giveFeedbackIntent = Intent(this, giveFeedback::class.java)
        giveFeedbackIntent.putExtra("bookingId",booking.id)
        giveFeedbackIntent.putExtra("shovlerId", booking.shovlerId)
        startActivity(giveFeedbackIntent)
    }
}
package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.DataBindingTriggerClass
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityBookingDetailsBinding
import com.example.theavengers_mad5254_project.model.data.Booking
import java.text.DateFormat
import java.text.SimpleDateFormat

class bookingDetails : AppCompatActivity() {
    private lateinit var binding: ActivityBookingDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_booking_details)

        var booking = intent.getSerializableExtra("booking") as Booking
        binding.txtAddress.text = booking.addressId
        binding.hours.text = "${booking.hours_required} hrs"
        binding.totalamount.text = "$${booking.price}"
        binding.taxes.text = "$0"
        binding.grandtotal.text = "$${booking.price}"
        binding.txtAddress.text = "${booking.address?.address_one}"
        if (booking.address?.address_two != null) {
            binding.txtAddress.text ="${booking.address?.address_one} ${booking.address?.address_two}"
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(booking.date)
        val stringFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy")
        binding.txtBookingDate.text = stringFormat.format(date);

    }

    fun btnGiveRating(view: View){
        val intent = Intent(this, giveFeedback::class.java)
        startActivity(intent)
    }
}
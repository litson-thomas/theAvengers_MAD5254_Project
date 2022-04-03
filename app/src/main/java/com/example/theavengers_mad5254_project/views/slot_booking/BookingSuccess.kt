package com.example.theavengers_mad5254_project.views.slot_booking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityBookingSuccessBinding
import com.example.theavengers_mad5254_project.views.home.Home


class BookingSuccess : AppCompatActivity() {

    private lateinit var binding: ActivityBookingSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_success)

        binding.filtersApplyBtn.setOnClickListener {
          val intent = Intent(applicationContext, Home::class.java)
          // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
          finishAffinity();
          startActivity(intent)
        }
    }
}

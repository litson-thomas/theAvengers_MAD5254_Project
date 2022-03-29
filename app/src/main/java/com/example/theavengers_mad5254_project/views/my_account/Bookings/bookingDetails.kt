package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.theavengers_mad5254_project.R

class bookingDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
    }

    fun btnGiveRating(view: View){
        val intent = Intent(this, giveFeedback::class.java)
        startActivity(intent)
    }
}
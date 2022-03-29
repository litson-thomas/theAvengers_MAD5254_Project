package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.becomeShovler.BecomeShovler
import com.example.theavengers_mad5254_project.views.my_account.Bookings.MyBookings

class MyAccountHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myaccount_home)
    }

    fun myProfile(view:View)
    {
        val intent = Intent(this, MyProfile::class.java)
        startActivity(intent)
    }
    fun becomeShov(view:View)
    {
        val intent = Intent(this, BecomeShovler::class.java)
        startActivity(intent)
    }
    fun logoutBtn(view:View)
    {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
    fun mybookings(view:View)
    {
        val intent = Intent(this, MyBookings::class.java)
        startActivity(intent)
    }
}
package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.becomeShovler.BecomeShovler
import com.example.theavengers_mad5254_project.views.my_account.Bookings.MyBookings
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ShovlerDashboard

class MyAccountHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myaccount_home)
        FragmentUtil.setHeader("My Account","Manage your profile",
        false,supportFragmentManager)

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

    fun mybookings(view:View)
    {
        val intent = Intent(this, MyBookings::class.java)
        startActivity(intent)
    }
    fun btn_dashboard(view:View)
    {
        val intent = Intent(this,ShovlerDashboard::class.java)
        startActivity(intent)
    }
    fun btn_myaddresses(view:View)
    {
        val intent = Intent(this,MyAddresses::class.java)
        startActivity(intent)
    }
    fun logoutBtn(view:View)
    {
        CommonMethods.showAlertDialogLogout(this,"Do you want to Log out?","Yes","No")

    }
    fun myMessages(view:View)
    {
        val intent = Intent(this, UserChatRoom::class.java)
        startActivity(intent)
    }
}

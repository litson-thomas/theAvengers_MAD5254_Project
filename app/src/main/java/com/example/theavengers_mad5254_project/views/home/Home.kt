package com.example.theavengers_mad5254_project.views.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.auth.Register
import com.example.theavengers_mad5254_project.views.my_account.MyAccountHome
import com.example.theavengers_mad5254_project.views.my_account.MyProfile

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


            }
    fun nav_myAccount(view: View){
        val intent = Intent(this, MyAccountHome::class.java)
        startActivity(intent)
    }

    }


package com.example.theavengers_mad5254_project.views.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.auth.Register
import com.example.theavengers_mad5254_project.views.my_account.MyAccountHome

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


            }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
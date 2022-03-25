package com.example.theavengers_mad5254_project.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.theavengers_mad5254_project.R

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

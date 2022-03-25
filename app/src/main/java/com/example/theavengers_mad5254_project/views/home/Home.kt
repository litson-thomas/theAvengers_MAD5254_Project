package com.example.theavengers_mad5254_project.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
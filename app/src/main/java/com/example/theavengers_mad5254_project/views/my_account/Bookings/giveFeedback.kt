package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityGiveFeedbackBinding
import com.example.theavengers_mad5254_project.views.my_account.MyAccountHome
import com.example.theavengers_mad5254_project.views.my_account.MyAddresses

class giveFeedback : AppCompatActivity() {
    private lateinit var binding: ActivityGiveFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_give_feedback)
        binding.seekBarFeedback.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.ratingTitle.setText("Select a rating (" + p1.toString() + ") Stars")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })




    }

    fun submitFeedback(view:View){

        if(TextUtils.isEmpty(binding.feedbackTitle.text.toString())) {
            binding.feedbackTitle.setError("Title Field is empty")
            binding.feedbackTitle.requestFocus()
        } else if(TextUtils.isEmpty(binding.feedbackBody.text.toString())) {
            binding.feedbackBody.setError("Feedback Field is empty")
            binding.feedbackBody.requestFocus()
        } else {
            val newIntent = Intent(this, MyBookings::class.java)
            startActivity(newIntent)
        }

    }
}
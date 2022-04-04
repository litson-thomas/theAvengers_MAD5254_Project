package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityGiveFeedbackBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.ReviewViewModel
import com.example.theavengers_mad5254_project.viewmodel.ReviewViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModel
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModelFactory
import com.example.theavengers_mad5254_project.views.my_account.MyAccountHome
import com.example.theavengers_mad5254_project.views.my_account.MyAddresses

class giveFeedback : AppCompatActivity() {
    private lateinit var binding: ActivityGiveFeedbackBinding
    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var reviewViewModelFactory: ReviewViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookingId = intent.getIntExtra("bookingId",0)

        FragmentUtil.setHeader("Give a feedback","Order #${bookingId}", false,supportFragmentManager)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_give_feedback)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        reviewViewModelFactory = ReviewViewModelFactory(mainRepository)
        reviewViewModel = ViewModelProvider(this,reviewViewModelFactory)[ReviewViewModel::class.java]

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
        reviewViewModel.addReviewStatus.observe(this, Observer {
            if (it.status) {
                finish()
            } else {
                CommonMethods.toastMessage(applicationContext, "FAILURE ${it}")
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
            addReview()
        }
    }
    private fun addReview() {
        val bookingId = intent.getIntExtra("bookingId",0)
        val shovlerId = intent.getIntExtra("shovlerId",0)
        reviewViewModel.addReview(
            AppPreference.userID , shovlerId, bookingId,
            binding.seekBarFeedback.progress, binding.feedbackTitle.text.toString(),
            binding.feedbackBody.text.toString())
    }
}
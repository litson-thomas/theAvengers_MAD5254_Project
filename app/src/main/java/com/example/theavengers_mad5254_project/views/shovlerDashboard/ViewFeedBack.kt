package com.example.theavengers_mad5254_project.views.shovlerDashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityViewFeedBackBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.*

class ViewFeedBack : AppCompatActivity() {
    private lateinit var binding: ActivityViewFeedBackBinding
    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var reviewViewModelFactory: ReviewViewModelFactory
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_feed_back)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        reviewViewModelFactory = ReviewViewModelFactory(mainRepository)
        reviewViewModel = ViewModelProvider(this,reviewViewModelFactory)[ReviewViewModel::class.java]

        reviewAdapter = ReviewAdapter()
        binding.feedbacklist.adapter = reviewAdapter
        reviewViewModel.reviewList.observe(this) {
            reviewAdapter.addReviews(it)
        }

        reviewViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        reviewViewModel.loading.observe(this, Observer {
            if (it) {
                binding.viewFeedbackProgress.visibility = View.VISIBLE
            } else {
                binding.viewFeedbackProgress.visibility = View.GONE
            }
        })
        val bookingId = intent.getIntExtra("bookingId",0)
        val shovlerId = intent.getIntExtra("shovlerId",0)
        reviewViewModel.getReviews(shovlerId,bookingId)
    }
}
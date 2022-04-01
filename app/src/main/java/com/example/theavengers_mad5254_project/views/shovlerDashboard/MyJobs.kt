package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyJobsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.ReviewViewModel
import com.example.theavengers_mad5254_project.viewmodel.ReviewViewModelFactory
import java.io.Serializable

class MyJobs : AppCompatActivity() {
    private lateinit var binding: ActivityMyJobsBinding
    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var bookingViewModelFactory: BookingViewModelFactory
    private lateinit var bookingAdapter: BookingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_jobs)
        FragmentUtil.setHeader("My Jobs","All my jobs so far", false,supportFragmentManager)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        bookingViewModelFactory = BookingViewModelFactory(mainRepository)
        bookingViewModel = ViewModelProvider(this,bookingViewModelFactory)[BookingViewModel::class.java]

        bookingAdapter = BookingAdapter{ position -> onListItemClick(position) }
        binding.jobListRv.adapter = bookingAdapter

        bookingViewModel.bookingList.observe(this) {
            bookingAdapter.addBookings(it)
        }

        bookingViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        bookingViewModel.loading.observe(this, Observer {
            if (it) {
                binding.myJobsProgress.visibility = View.VISIBLE
            } else {
                binding.myJobsProgress.visibility = View.GONE
            }
        })
        bookingViewModel.getBookings(AppPreference.userID)
    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, ViewOrder::class.java)
        intent.putExtra("booking", bookingAdapter.bookings[position] as Serializable)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        //bookingViewModel.getBookings(AppPreference.userID)
       // bookingAdapter.notifyDataSetChanged()
    }
}

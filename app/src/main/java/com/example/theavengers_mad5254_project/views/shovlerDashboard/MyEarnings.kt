package com.example.theavengers_mad5254_project.views.shovlerDashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyEarningsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModelFactory

class MyEarnings : AppCompatActivity() {
    private lateinit var binding: ActivityMyEarningsBinding
    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var bookingViewModelFactory: BookingViewModelFactory
    private lateinit var earninngAdapter: EarninngAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_earnings)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        bookingViewModelFactory = BookingViewModelFactory(mainRepository)
        bookingViewModel =
            ViewModelProvider(this, bookingViewModelFactory)[BookingViewModel::class.java]

        earninngAdapter = EarninngAdapter()
        binding.jobList.adapter = earninngAdapter

        bookingViewModel.bookingList.observe(this) {
            earninngAdapter.addBookings(it)
            var total = 0
            for (book: Booking in it) {
                total += book.price!!
            }
            binding.totalEarnings.text = "$${total}"
        }

        bookingViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        bookingViewModel.loading.observe(this, Observer {
            if (it) {
                binding.myEarningsProgress.visibility = View.VISIBLE
            } else {
                binding.myEarningsProgress.visibility = View.GONE
            }
        })
        bookingViewModel.getBookings(AppPreference.userID)
    }

}
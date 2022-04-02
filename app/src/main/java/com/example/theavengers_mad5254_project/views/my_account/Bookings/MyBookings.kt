package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyBookingsBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyJobsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.MyBookingsViewModel
import com.example.theavengers_mad5254_project.viewmodel.MyBookingsViewModelFactory
import com.example.theavengers_mad5254_project.views.my_account.MyProfile
import com.example.theavengers_mad5254_project.views.shovlerDashboard.BookingAdapter
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewOrder
import java.io.Serializable

class MyBookings : AppCompatActivity() {
    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingViewModel: MyBookingsViewModel
    private lateinit var bookingViewModelFactory: MyBookingsViewModelFactory
    private lateinit var bookingAdapter: bookingsRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_bookings)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        bookingViewModelFactory = MyBookingsViewModelFactory(mainRepository)
        bookingViewModel = ViewModelProvider(this,bookingViewModelFactory)[MyBookingsViewModel::class.java]

        bookingAdapter = bookingsRvAdapter{ position -> onListItemClick(position) }
        binding.RVbookings.adapter = bookingAdapter

        bookingViewModel.bookingList.observe(this) {
            bookingAdapter.addBookings(it)
        }

        bookingViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        bookingViewModel.getBookings(AppPreference.userID)

    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, bookingDetails::class.java)
        intent.putExtra("booking", bookingAdapter.bookings[position] as Serializable)
        startActivity(intent)
    }
}
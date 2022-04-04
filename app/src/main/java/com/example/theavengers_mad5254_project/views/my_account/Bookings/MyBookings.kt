package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyBookingsBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyJobsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.*
import com.example.theavengers_mad5254_project.views.my_account.MyProfile
import com.example.theavengers_mad5254_project.views.shovlerDashboard.BookingAdapter
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewOrder
import java.io.Serializable

class MyBookings : AppCompatActivity() {
    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingViewModel: MyBookingsViewModel
    private lateinit var bookingViewModelFactory: MyBookingsViewModelFactory
    private lateinit var addressViewModel: AddressesViewModel
    private lateinit var addressViewModelFactory: AddressesViewModelFactory
    private lateinit var bookingAdapter: bookingsRvAdapter
    private lateinit var addressList: List<Address>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_bookings)
        FragmentUtil.setHeader("My Bookings","All my bookings so far", false,supportFragmentManager)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        bookingViewModelFactory = MyBookingsViewModelFactory(mainRepository)
        bookingViewModel = ViewModelProvider(this,bookingViewModelFactory)[MyBookingsViewModel::class.java]

        addressViewModelFactory = AddressesViewModelFactory(mainRepository)
        addressViewModel = ViewModelProvider(this,addressViewModelFactory)[AddressesViewModel::class.java]

        addressViewModel.addressList.observe(this) {
            addressList = it
            bookingViewModel.getBookings(AppPreference.userID, addressList)
        }

        bookingViewModel.loading.observe(this, Observer {
            if (it) {
                binding.myJobsProgress.visibility = View.VISIBLE
            } else {
                binding.myJobsProgress.visibility = View.GONE
            }
        })

        bookingAdapter = bookingsRvAdapter{ position -> onListItemClick(position) }
        binding.RVbookings.adapter = bookingAdapter

        bookingViewModel.bookingList.observe(this) {
            bookingAdapter.addBookings(it)
        }

        bookingViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        addressViewModel.getAddress(AppPreference.userID)

    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, bookingDetails::class.java)
        intent.putExtra("booking", bookingAdapter.bookings[position] as Serializable)
        startActivity(intent)
    }
}
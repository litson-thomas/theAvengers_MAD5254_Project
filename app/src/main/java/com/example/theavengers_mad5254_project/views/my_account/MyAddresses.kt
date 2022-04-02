package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyAddressesBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModel
import com.example.theavengers_mad5254_project.viewmodel.BookingViewModelFactory
import com.example.theavengers_mad5254_project.views.my_account.Bookings.bookingDetails
import com.example.theavengers_mad5254_project.views.my_account.Bookings.bookingsRvAdapter
import com.example.theavengers_mad5254_project.views.shovlerDashboard.BookingAdapter
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewOrder
import java.io.Serializable

class MyAddresses : AppCompatActivity() {
    private lateinit var binding: ActivityMyAddressesBinding
    private lateinit var addressViewModel: AddressesViewModel
    private lateinit var addressViewModelFactory: AddressesViewModelFactory
    private lateinit var addressAdapter: addressesRVadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_addresses)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)

        addressViewModelFactory = AddressesViewModelFactory(mainRepository)
        addressViewModel = ViewModelProvider(this,addressViewModelFactory)[AddressesViewModel::class.java]

        addressAdapter= addressesRVadapter { position -> onListItemClick(position) }
        binding.RVaddresses.adapter=addressAdapter

        addressViewModel.addressList.observe(this) {
            addressAdapter.addAddress(it)
        }

        addressViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        addressViewModel.getAddress(AppPreference.userID)

    }
    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, Update_Address::class.java)
        intent.putExtra("address", addressAdapter.addresses[position] as Serializable)
        startActivity(intent)
    }

    fun btn_AddNewAddress(view: View){
        var intent = Intent(this, Add_New_Address::class.java)
        startActivity(intent)
    }
}
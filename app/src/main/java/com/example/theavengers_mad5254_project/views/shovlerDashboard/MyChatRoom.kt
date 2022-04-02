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
import com.example.theavengers_mad5254_project.databinding.ActivityEditListingBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyChatRoomBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyJobsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.*
import java.io.Serializable

class MyChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityMyChatRoomBinding
    private lateinit var shovlerViewModel: ShovlerViewModel
    private lateinit var shovlerViewModelFactory: ShovlerViewModelFactory
    private lateinit var shovlerAdapter: ShovlerAdapter
    private lateinit var addressList :List<Address>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_chat_room)
        FragmentUtil.setHeader("My Listing","Select a listing", false,supportFragmentManager)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        shovlerViewModelFactory = ShovlerViewModelFactory(mainRepository)
        shovlerViewModel = ViewModelProvider(this,shovlerViewModelFactory)[ShovlerViewModel::class.java]

        shovlerAdapter = ShovlerAdapter{ position -> onListItemClick(position) }
        binding.editListingRv.adapter = shovlerAdapter

        shovlerViewModel.shovlerList.observe(this) {
            if (it.count() > 0 ) {
                for (item in shovlerViewModel.shovlerList.value!!) {
                    for( address in addressList) {
                        if (item.addressId == address.id) {
                            item.address = address
                        }
                    }
                }
            }
            shovlerAdapter.addShovlerList(it)
        }
        shovlerViewModel.addressList.observe(this) {
            addressList = it
            val userUid = AppPreference.userID
            shovlerViewModel.getShovlerListings(userUid)
        }

        shovlerViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        shovlerViewModel.loading.observe(this, Observer {
            if (it) {
                binding.editListingProgress.visibility = View.VISIBLE
            } else {
                binding.editListingProgress.visibility = View.GONE
            }
        })
        val userUid = AppPreference.userID
        shovlerViewModel.getAddress(userUid)
    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, MyChatRoomUsers::class.java)
        var item = shovlerAdapter.shoverListings[position]
        intent.putExtra("shovlerId", item.id)
        startActivity(intent)
    }

}

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
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.*
import com.example.theavengers_mad5254_project.views.becomeShovler.BecomeShovler
import java.io.Serializable

class EditListing : AppCompatActivity() {
    private lateinit var binding: ActivityEditListingBinding
    private lateinit var shovlerViewModel: ShovlerViewModel
    private lateinit var shovlerViewModelFactory: ShovlerViewModelFactory
    private lateinit var shovlerAdapter: ShovlerAdapter
    private lateinit var addressList :List<Address>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_listing)
        FragmentUtil.setHeader("Edit My Listing","All my listings",false, supportFragmentManager)

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
            shovlerAdapter.addShovlerList(false,it)
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
        var becomeShovlerIntent =  Intent(this, BecomeShovler::class.java)
        var item = shovlerAdapter.shoverListings[position]
        becomeShovlerIntent.putExtra("shovlerListItem", item as Serializable)
        startActivity(becomeShovlerIntent)
    }

}

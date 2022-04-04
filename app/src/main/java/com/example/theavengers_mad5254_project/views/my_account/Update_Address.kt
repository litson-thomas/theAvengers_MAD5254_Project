package com.example.theavengers_mad5254_project.views.my_account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityUpdateAddressBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModelFactory

class Update_Address : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAddressBinding
    private lateinit var addressViewModel: AddressesViewModel
    private lateinit var addressViewModelFactory: AddressesViewModelFactory
    private lateinit var address: Address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_update_address)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)

        addressViewModelFactory = AddressesViewModelFactory(mainRepository)
        addressViewModel = ViewModelProvider(this,addressViewModelFactory)[AddressesViewModel::class.java]


            val address = intent.getSerializableExtra("address") as Address
            binding.txtUpdateAddress1.setText(address.address_one)
            binding.txtUpdateAddress2.setText(address.address_two)
            binding.txtUpdatePostalCode.setText(address.postalCode)

        binding.btnUpdateAddress.setOnClickListener{
           updateAddress(address.id, binding.txtUpdateAddress1.text.toString(),
               binding.txtUpdateAddress1.text.toString(),address.CityId,address.StateId,
               binding.txtUpdatePostalCode.text.toString(),
               address.city,
               address.state,
               address.latitude,address.longitude,
          address.userUid)
        }
    }
    private fun updateAddress(id: Int?, address_one: String?, address_two: String?, CityId: String?,
                              StateId: String?, postalCode: String?, city: String?, state: String?,
                              latitude: Number?, longitude: Number?, userUid: String?) {
        addressViewModel.updateAddress( id,
            address_one, address_two, CityId, StateId, postalCode, city,
            state, latitude, longitude, userUid)
        addressViewModel.updateAddress.observe(this, Observer {
            if (it.status) {
                onBackPressed()
            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

}
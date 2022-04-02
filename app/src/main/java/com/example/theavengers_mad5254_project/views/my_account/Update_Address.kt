package com.example.theavengers_mad5254_project.views.my_account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityUpdateAddressBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.repository.MainRepository
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

        var address = intent.getSerializableExtra("address") as Address
        binding.txtUpdateAddress1.setText(address.address_one)
        binding.txtUpdateAddress2.setText(address.address_two)
        binding.txtUpdatePostalCode.setText(address.postlCode)
      //  binding.spinnerUpdateCity.selectedItem
      //  binding.spinnerUpdateProvince.selectedItem





        binding.btnUpdateAddress.setOnClickListener{
            //todo update
        }
    }


}
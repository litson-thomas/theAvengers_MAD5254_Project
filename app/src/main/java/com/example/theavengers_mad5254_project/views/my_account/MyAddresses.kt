package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.AddressRVAdapter
import com.example.theavengers_mad5254_project.databinding.ActivityMyAddressesBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.OnClickInterface
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModelFactory
import java.io.Serializable

class MyAddresses : AppCompatActivity() {
    private lateinit var binding: ActivityMyAddressesBinding
    private lateinit var addressViewModel: AddressesViewModel
    private lateinit var addressViewModelFactory: AddressesViewModelFactory
    private lateinit var addressAdapter: AddressRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_addresses)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)

        addressViewModelFactory = AddressesViewModelFactory(mainRepository)
        addressViewModel = ViewModelProvider(this,addressViewModelFactory)[AddressesViewModel::class.java]

        observerLoadingProgress()
        getAddress()


    }
    private fun getAddress(){
        addressAdapter= AddressRVAdapter(mAddressDeleteListener)
        binding.RVaddresses.adapter=addressAdapter

        addressViewModel.addressList.observe(this) {
            addressAdapter.addAddress(it)
        }

        addressViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        addressViewModel.getAddress(AppPreference.userID)
    }

    fun btnAddNewAddress(view: View){
        val intent = Intent(this, AddNewAddress::class.java)
        startActivity(intent)
    }

    private fun deleteAddress(id: Int) {
        addressViewModel.deleteAddress(id)
        addressViewModel.deleteAddress.observe(this, Observer {
            if (it.status) {
               // observerLoadingProgress()
            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        getAddress()
        addressAdapter.notifyDataSetChanged()
    }

    //method for progress bar
    private fun observerLoadingProgress(){
        addressViewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.progressBar.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.VISIBLE
            }

        })


    }
     private val mAddressDeleteListener = object : OnClickInterface{
        override fun onClickDelete(address: Address?, imageButton: ImageButton) {
            address?.id?.let { deleteAddress(it) }
            // just remove the item from list
            address?.let { addressAdapter.removeProduct(it) }
        }

        override fun onClick(address: Address?) {

            val intent =  Intent(applicationContext, UpdateAddress::class.java)
            intent.putExtra("address", address as Serializable)
            startActivity(intent)


        }
    }

}

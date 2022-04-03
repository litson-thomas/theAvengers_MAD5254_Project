package com.example.theavengers_mad5254_project.views.my_account

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddNewAddressBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.responseModel.Prediction
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.AddNewAddressViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddNewAddressViewModelFactory


class AddNewAddress : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewAddressBinding
    private lateinit var placeList: ArrayList<String>
    private lateinit var placesListAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_address)

        binding.btnAddAddress.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.txtAddAddressLine1.text.toString()) -> {
                    binding.txtAddAddressLine1.error = "Address Field is empty"
                    binding.txtAddAddressLine1.requestFocus()
                }
                TextUtils.isEmpty(binding.txtAddPostalCode.text.toString()) -> {
                    binding.txtAddPostalCode.error = "Postal code Field is empty"
                    binding.txtAddPostalCode.requestFocus()
                }
                else -> {
                    searchCity()
                    val newIntent = Intent(this, MyAddresses::class.java)
                    startActivity(newIntent)
                }
            }
        }

        onSearchResult()

    }

    private fun searchPlace(place: String) {

         lateinit var viewModel: AddNewAddressViewModel
         lateinit var viewModelFactory: AddNewAddressViewModelFactory
        //ApiService.getInstance() for googlePlace
        val retrofitService = ApiClient().getGooglePlaceApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = AddNewAddressViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewAddressViewModel::class.java]


        viewModel.getGooglePlaces(place, AppConstants.GOOGLE_API_KEY)
        viewModel.addNewAddress.observe(this, Observer {
            if (it.isNotEmpty()) {
                updatePlaceList(it)
            } else {
                Log.i(TAG, "searchPlace: INVALID REQUEST")
            }
        })
    }

    private fun onSearchResult() {
        binding.txtAddAddressLine1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                placeList = arrayListOf()
                searchPlace(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun updatePlaceList(placeNameList: List<Prediction>) {
        placeList.clear()
        for (i in placeNameList.indices) {
            placeList.add(i, placeNameList[i].structured_formatting.main_text)

        }
        placesListAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,placeList
        )
        binding.txtAddAddressLine1.setAdapter(placesListAdapter)
        placesListAdapter.notifyDataSetChanged()
    }

    private fun searchCity() {

         lateinit var viewModelLocation: AddNewAddressViewModel
         lateinit var viewModelFactoryLocation: AddNewAddressViewModelFactory
        //ApiService.getInstance() for Location
        val retrofitServiceLocation = ApiClient().getApiService(this)
        val mainRepositoryLocation = MainRepository(retrofitServiceLocation)
        viewModelFactoryLocation = AddNewAddressViewModelFactory(mainRepositoryLocation)
        viewModelLocation = ViewModelProvider(this, viewModelFactoryLocation)[AddNewAddressViewModel::class.java]

        viewModelLocation.getCity()
        viewModelLocation.searchLocation.observe(this, Observer {
            if (it.rows.isNotEmpty()) {
                onSearchResult()
                CommonMethods.toastMessage(applicationContext,"SUCCESS")
            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

}


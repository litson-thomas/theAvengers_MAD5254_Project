package com.example.theavengers_mad5254_project.views.my_account

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddNewAddressBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.responseModel.Prediction
import com.example.theavengers_mad5254_project.model.data.responseModel.Row
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.AddNewAddressViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddNewAddressViewModelFactory
import com.example.theavengers_mad5254_project.views.auth.Login


class AddNewAddress : AppCompatActivity(),AdapterView.OnItemSelectedListener{

    private lateinit var binding: ActivityAddNewAddressBinding
    //AutoComplete for Places
    private lateinit var placeList: ArrayList<String>
    private lateinit var placesListAdapter: ArrayAdapter<String>
    //Spinner for city and state
    private lateinit var cityList: ArrayList<String>
    private lateinit var cityListAdapter: ArrayAdapter<String>
    var text: String = ""
    var cityId: Int = 0
    var latitude: String = ""
    var longitude: String = ""
    private lateinit var rowList: ArrayList<Row>

    private lateinit var viewModel: AddNewAddressViewModel
    private lateinit var viewModelFactory: AddNewAddressViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_address)

        //ApiService.getInstance()
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = AddNewAddressViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewAddressViewModel::class.java]
        binding.spinnerAddProvince.onItemSelectedListener = this
        cityList = arrayListOf()
        rowList = arrayListOf()
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
                    addNewAddress(AppPreference.userID,binding.txtAddAddressLine1.text.toString(),binding.txtAddAddressLine2.text.toString(),latitude,
                        longitude,binding.spinnerAddProvince.selectedItemPosition+1)

                }
            }
        }
      onSearchResult()
    }
    //Google Geocode API
    private fun getGeocode(place: String) {
        viewModel.getGoogleGeocode(AppConstants.GOOGLE_GEOCODE_URL+"address="+place+"&key="+AppConstants.GOOGLE_API_KEY)
        viewModel.geocode.observe(this, Observer {
            if (it.results.isNotEmpty()) {
               latitude = it.results.first().geometry.location.lat.toString()
               longitude = it.results.first().geometry.location.lng.toString()
            } else {
                Log.i(TAG, "searchPlace: INVALID REQUEST")
            }
        })
    }
    //Google Place API
    private fun searchPlace(place: String) {
        viewModel.getGooglePlaces(AppConstants.GOOGLE_PLACE_URL+"input="+place+"&types=geocode&sensor=true&key="+AppConstants.GOOGLE_API_KEY)
        viewModel.searchPlace.observe(this, Observer {
            if (it.isNotEmpty()) {
              updatePlaceList(it)
                searchCity()
            } else {
                Log.i(TAG, "searchPlace: INVALID REQUEST")
            }
        })
    }
    //Google Place API on text change
    private fun onSearchResult() {
        binding.txtAddAddressLine1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                placeList = arrayListOf()
                searchPlace(s.toString())
                getGeocode(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    //Google Place API on calling adapter
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


    //City API
    private fun searchCity() {
        viewModel.getCity()
        viewModel.searchLocation.observe(this, Observer {
            if (it.isNotEmpty()) {
                cityList(it)

            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

    //City API adapter
    private fun cityList(cityStateList: List<Row>) {
        cityList.clear()
        for (i in cityStateList.indices) {
            cityList.add(i, cityStateList[i].city_name+", "+cityStateList[i].State.state_name)
        }
        cityListAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, cityList
        )
        // Set layout to use when the list of choices appear
        cityListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set Adapter to Spinner
        binding.spinnerAddProvince.adapter = cityListAdapter
        cityListAdapter.notifyDataSetChanged()


    }


    //City API selecting from spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        text = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        CommonMethods.toastMessage(applicationContext,"Nothing selected")
    }


    //Add new address API call
    private fun addNewAddress(userUid: String,address_one:String,address_two:String,latitude:String,longitude:String,cityId:Int) {
        viewModel.getNewAddress(userUid,address_one,address_two,latitude,longitude,cityId)
        viewModel.addNewAddress.observe(this, Observer {
            if (it.status) {
              onBackPressed()
            } else {
                Log.i(TAG, "searchPlace: INVALID REQUEST")
            }
        })
    }

}


package com.example.theavengers_mad5254_project.views.my_account

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityUpdateAddressBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.responseModel.Prediction
import com.example.theavengers_mad5254_project.model.data.responseModel.Row
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModel
import com.example.theavengers_mad5254_project.viewmodel.AddressesViewModelFactory

class UpdateAddress : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityUpdateAddressBinding
    private lateinit var viewModel: AddressesViewModel
    private lateinit var viewModelFactory: AddressesViewModelFactory
    private lateinit var address: Address

    //Spinner for city
    private lateinit var cityList: ArrayList<String>
    private lateinit var cityListAdapter: ArrayAdapter<String>

    //Spinner for province
    private lateinit var provinceList: ArrayList<String>
    private lateinit var provinceListAdapter: ArrayAdapter<String>

    var text: String = ""
    var city: String = ""
    var cityId: Int = 0
    var state: String = ""
    var stateId: Int = 0
    var latitude: Number = 0.0
    var longitude: Number = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_update_address)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)

        viewModelFactory = AddressesViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this,viewModelFactory)[AddressesViewModel::class.java]

        binding.spinnerUpdateCity.onItemSelectedListener = this
        binding.spinnerUpdateProvince.onItemSelectedListener = this
        cityList = arrayListOf()
        provinceList = arrayListOf()
        observerLoadingProgress()

        address = intent.getSerializableExtra("address") as Address
            binding.txtUpdateAddressLine1.setText(address.address_one)
            binding.txtUpdateAddress2.setText(address.address_two)

        binding.btnUpdateAddress.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.txtUpdateAddressLine1.text.toString()) -> {
                    binding.txtUpdateAddressLine1.error = "Address Field is empty"
                    binding.txtUpdateAddressLine1.requestFocus()
                }
                TextUtils.isEmpty(binding.txtUpdatePostalCode.text.toString()) -> {
                    binding.txtUpdatePostalCode.error = "Postal code Field is empty"
                    binding.txtUpdatePostalCode.requestFocus()
                }

                else -> {
                    getGeocode( binding.txtUpdateAddressLine1.text.toString()+","+ binding.txtUpdateAddress2.text.toString())


                }
            }

        }

        searchCity()



    }
    private fun updateAddress(id: Int?, address_one: String?, address_two: String?, CityId: String?,
                              StateId: String?, postalCode: String?, city: String?, state: String?,
                              latitude: Number?, longitude: Number?, userUid: String?) {
        viewModel.updateAddress( id,
            address_one, address_two, CityId, StateId, postalCode, city,
            state, latitude, longitude, userUid)
        viewModel.updateAddress.observe(this, Observer {
            if (it.status) {
                onBackPressed()
            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

    //City API
    private fun searchCity() {
        viewModel.getCity()
        viewModel.searchLocation.observe(this, Observer {
            if (it.isNotEmpty()) {
                cityList(it)
                provinceList(it)

            } else {
                CommonMethods.toastMessage(applicationContext,"FAILURE")
            }
        })
    }

    //City API adapter
    private fun cityList(cityStateList: List<Row>) {
        cityList.clear()
        for (i in cityStateList.indices) {
            cityList.add(i, cityStateList[i].city_name)
        }

        cityListAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, cityList
        )
        // Set layout to use when the list of choices appear
        cityListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        for (searchCity in cityStateList){
            cityId = searchCity.id
        }
        // Set Adapter to Spinner
        binding.spinnerUpdateCity.adapter = cityListAdapter
        cityListAdapter.notifyDataSetChanged()


    }

    //City API adapter
    private fun provinceList(proviceList: List<Row>) {
        provinceList.clear()
        for (i in proviceList.indices) {
            provinceList.add(i, proviceList[i].State.state_name)
        }

        provinceListAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, provinceList
        )
        // Set layout to use when the list of choices appear
        provinceListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        for (searchCity in proviceList){
            stateId = searchCity.StateId
        }
        // Set Adapter to Spinner
        binding.spinnerUpdateProvince.adapter = provinceListAdapter
        provinceListAdapter.notifyDataSetChanged()


    }


    //City API selecting from spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        text = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        CommonMethods.toastMessage(applicationContext,"Nothing selected")
    }

    //Google Geocode API
    private fun getGeocode(place: String) {
        viewModel.getGoogleGeocode(AppConstants.GOOGLE_GEOCODE_URL+"address="+place+"&radius=50000&key="+ AppConstants.GOOGLE_API_KEY)
        viewModel.geocode.observe(this, Observer {
            if (it.results.isNotEmpty()) {
                latitude = it.results.first().geometry.location.lat
                longitude = it.results.first().geometry.location.lng
                updateAddress(address.id, binding.txtUpdateAddressLine1.text.toString(),
                    binding.txtUpdateAddress2.text.toString(), cityId.toString(), stateId.toString(),
                    binding.txtUpdatePostalCode.text.toString(),
                    text,
                    text,
                    latitude,longitude,
                    address.userUid)
            } else {
                Log.i(ContentValues.TAG, "searchPlace: INVALID REQUEST")
            }
        })
    }
    //method for progress bar
    private fun observerLoadingProgress(){
        viewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.progressBar.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.VISIBLE
            }

        })


    }

}
package com.example.theavengers_mad5254_project.views.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationRequest
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.telecom.TelecomManager.EXTRA_LOCATION
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.WeatherForecastAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityWeatherForecastBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ListItem
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.WeatherForecastViewModel
import com.example.theavengers_mad5254_project.viewmodel.WeatherForecastViewModelFactory
import com.example.theavengers_mad5254_project.views.auth.Login
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import java.io.IOException
import java.net.URI.create
import java.util.*

class WeatherForecastActivity : AppCompatActivity(){
    private lateinit var binding: ActivityWeatherForecastBinding
    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var viewModelFactory: WeatherForecastViewModelFactory
    private var weatherForecastAdaptor: WeatherForecastAdaptor? = null

    //Location Access
    private lateinit var locationManager: LocationManager
    private var mLastLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_forecast)

        val retrofitService = ApiClient().getWeatherApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = WeatherForecastViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherForecastViewModel::class.java]

        observerLoadingProgress()
        //instantiation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
       getCurrentLocation()

        binding.detailsBackBtn.setOnClickListener {
            onBackPressed()
        }
    }



    private fun getLastLocation() {

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { location ->
            if (location.isSuccessful && location.result != null) {

                mLastLocation = location.result
                getLocationAndApiKey(
                    mLastLocation!!.latitude,
                    mLastLocation!!.longitude,
                    AppConstants.WEATHER_API_KEY
                )

            }else{
                Log.w(TAG, "getLastLocation:exception", location.exception)
            }
        }
    }
    private fun getCurrentLocation() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            CommonMethods.showAlertDialogGPSEnable(this,"GPS is disabled in your device. Would you like to enable it?",
                "Settings","No")
        }else{
            getLastLocation()
        }
}
    private fun getLocationAndApiKey(lat: Double,lng:Double, apiKey: String) {
        viewModel.getWeatherDetails(lat, lng,apiKey)
        viewModel.getWeatherForecastDetails(lat, lng,apiKey)
        viewModel.getGeocodeDetails(lat, lng,apiKey)
        observeWeatherDetails()
        observeWeatherForecastDetails()
        observeGeocodeDetails()
    }

    private fun observeWeatherDetails(){
        viewModel.weatherStatus.observe(this, Observer {
          if(it.cod == 200){
              var tempKelvin: Float = it.main.temp.toFloat()
              tempKelvin = (tempKelvin - 273.15F)
              binding.textViewTemperature.text = (tempKelvin).toString().substringBefore(".") + "°"
              binding.textDateTime.text =  CommonMethods.getCurrentDateTime(AppConstants.DATE_FORMAT)
              val iconCode = it.weather.first().icon
              CommonMethods.setGlideImage(
                  binding.imageViewWeatherIcon,
                  AppConstants.WEATHER_API_IMAGE_ENDPOINT + "${iconCode}@4x.png"
              )
          }else{
              CommonMethods.toastMessage(applicationContext,"Something went wrong")
          }

        })
    }

    private fun observeWeatherForecastDetails(){
        viewModel.weatherForecastStatus.observe(this, Observer {
            if(it.cod == "200"){
                val list: ArrayList<ListItem> = ArrayList<ListItem>()
                for (i in it.list){
                    val date = i.dt_txt
                    val parts = date.split(" ").toTypedArray()
                    println("Date: " + parts[0])
                    println("Date: " + parts[1])
                    if (parts[1] == "12:00:00"){
                        list.add(i)
                        println("PRINT  ${list.size}")
                        weatherForecastAdaptor = WeatherForecastAdaptor(this,
                            list
                        )
                        binding.recyclerViewHourOfDay.adapter = weatherForecastAdaptor
                    }
                }

            }else{
                CommonMethods.toastMessage(applicationContext,"Something went wrong")
            }

        })
    }

    private fun observeGeocodeDetails(){
        viewModel.geocodeStatus.observe(this, Observer {
            if(!it.isEmpty()){
               binding.textCityName.text = it.first().name +", " +it.first().country

            }else{
                CommonMethods.toastMessage(applicationContext,"Location Permission Denied")
            }

        })
    }


    //method for progress bar
    private fun observerLoadingProgress(){
        viewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.loginProgress.visibility = View.GONE
            }else{
                binding.loginProgress.visibility = View.VISIBLE
            }

        })


    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
    }
}


package com.example.theavengers_mad5254_project.views.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding
    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var viewModelFactory: WeatherForecastViewModelFactory
    private var weatherForecastAdaptor: WeatherForecastAdaptor? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_forecast)

        val retrofitService = ApiClient().getWeatherApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = WeatherForecastViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherForecastViewModel::class.java]

        //instantiation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        findLocation()

    }


    private fun findLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                getLocationAndApiKey(
                    location.latitude,
                    location.longitude,
                    AppConstants.WEATHER_API_KEY
                )


                Log.d(
                    "LOCATIONNNNN ",
                    "onSucsess: " + location.latitude + " " + location.longitude
                )

            }
        }

    }

    private fun findUserAddress(latitude: Double, longitude: Double) {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName
            binding.textCityName.text= "$city, $country"
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getLocationAndApiKey(lat: Double,lng:Double, apiKey: String) {
        viewModel.getWeatherDetails(lat, lng,apiKey)
        viewModel.getWeatherForecastDetails(lat, lng,apiKey)
        findUserAddress(lat,lng)
        observeWeatherDetails()
        observeWeatherForecastDetails()
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

}
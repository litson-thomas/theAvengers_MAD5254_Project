package com.example.theavengers_mad5254_project.views.weather

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.HomeShovlersAdaptor
import com.example.theavengers_mad5254_project.adaptors.WeatherForecastAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityWeatherForecastBinding
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.responseHelper.ResultOf
import com.example.theavengers_mad5254_project.viewmodel.WeatherForecastViewModel
import com.example.theavengers_mad5254_project.viewmodel.WeatherForecastViewModelFactory
import com.example.theavengers_mad5254_project.views.home.Home

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding
    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var viewModelFactory: WeatherForecastViewModelFactory
    private var weatherForecastAdaptor:WeatherForecastAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weather_forecast)

        val retrofitService = ApiService.getWeatherInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = WeatherForecastViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherForecastViewModel::class.java]

        getLocationAndApiKey("Scarborough,Canada",AppConstants.WEATHER_API_KEY)
    }

    private fun getLocationAndApiKey(address: String, apiKey: String) {
        viewModel.getWeatherDetails(address,apiKey)
        viewModel.getWeatherForecastDetails(address,apiKey)
        observeWeatherDetails()
        //observeWeatherForecastDetails()
    }

    private fun observeWeatherDetails(){
        viewModel.weatherStatus.observe(this, Observer {
          if(it.cod == 200){
              var tempKelvin: Float = it.main.temp.toFloat()
              tempKelvin = (tempKelvin - 273.15F)
              binding.textViewTemperature.text = (tempKelvin).toString().substringBefore(".") + "°"
              binding.textCityName.text = it.name
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
                weatherForecastAdaptor = WeatherForecastAdaptor(this, it.list)
                binding.recyclerViewHourOfDay.adapter = weatherForecastAdaptor
            }else{
                CommonMethods.toastMessage(applicationContext,"Something went wrong")
            }

        })
    }
}
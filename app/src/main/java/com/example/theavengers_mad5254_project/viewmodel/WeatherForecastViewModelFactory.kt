package com.example.theavengers_mad5254_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.repository.Repository

class WeatherForecastViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
            WeatherForecastViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
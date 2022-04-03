package com.example.theavengers_mad5254_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.repository.MainRepository

class AddNewAddressViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddNewAddressViewModel::class.java)) {
            AddNewAddressViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
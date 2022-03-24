package com.example.theavengers_mad5254_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class FireBaseViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirebaseViewModel::class.java)) {
            return FirebaseViewModel(Dispatchers.IO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
package com.example.theavengers_mad5254_project.viewmodel.slot_booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.MessageViewModel

class MessageViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
      MessageViewModel(this.repository) as T
    } else {
      throw IllegalArgumentException("ViewModel Not Found")
    }
  }

}

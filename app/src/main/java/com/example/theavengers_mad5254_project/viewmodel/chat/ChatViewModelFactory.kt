package com.example.theavengers_mad5254_project.viewmodel.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.repository.MainRepository

class ChatViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
      ChatViewModel(this.repository) as T
    } else {
      throw IllegalArgumentException("ViewModel Not Found")
    }
  }

}

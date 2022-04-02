package com.example.theavengers_mad5254_project.model.data.responseModel

import com.example.theavengers_mad5254_project.model.data.ChatMessage

data class ChatResponse(
  val count: Int,
  val rows: MutableList<ChatMessage>
)

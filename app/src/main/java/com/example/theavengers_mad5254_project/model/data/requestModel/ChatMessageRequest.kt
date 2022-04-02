package com.example.theavengers_mad5254_project.model.data.requestModel

data class ChatMessageRequest(
    val message: String,
    val userUid: String,
    val room: String,
)

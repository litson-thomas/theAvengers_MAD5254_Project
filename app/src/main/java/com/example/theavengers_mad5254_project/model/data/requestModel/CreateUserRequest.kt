package com.example.theavengers_mad5254_project.model.data.requestModel

data class CreateUserRequest(
    val email: String,
    val is_shovler: Boolean,
    val name: String,
    val password: String,
    val phone: String,
    val uid: String
)

package com.example.theavengers_mad5254_project.model.data.requestModel

import java.io.Serializable

data class UpdateUserRequest(
    val name: String,
    val phone: String,
    val uid: String
): Serializable

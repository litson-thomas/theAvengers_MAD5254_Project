package com.example.theavengers_mad5254_project.model.data.requestModel

import java.io.Serializable

data class AddNewAddressRequest(
    val userUid: String,
    val address_one: String,
    val address_two: String,
    val latitude: String,
    val longitude: String,
    val cityId: Int
): Serializable
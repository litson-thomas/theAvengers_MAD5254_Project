package com.example.theavengers_mad5254_project.model.data.requestModel

import java.io.Serializable

data class AddNewAddressRequest(
    val address_one: String,
    val address_two: String,
    val city: String,
    val cityId: Int,
    val latitude: String,
    val longitude: String,
    val postal_code: String,
    val state: String,
    val userUid: String
):Serializable
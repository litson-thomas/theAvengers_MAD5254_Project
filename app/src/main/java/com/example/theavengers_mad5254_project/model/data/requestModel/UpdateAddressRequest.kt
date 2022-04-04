package com.example.theavengers_mad5254_project.model.data.requestModel

import java.io.Serializable

class UpdateAddressRequest (
    val id: Int? =null,
    val address_one: String? =null,
    val address_two: String? = null,

    val CityId: String? = null,
    val StateId: String? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val city: String? = null,
    val latitude: Number? = null,
    val longitude: Number? = null,

    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userUid: String? = null
): Serializable
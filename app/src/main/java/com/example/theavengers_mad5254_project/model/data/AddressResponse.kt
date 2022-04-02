package com.example.theavengers_mad5254_project.model.data

import java.io.Serializable

data class AddressResponse  (
    val count: Int,
    val rows: List<Address>
) : Serializable

data class Address (
    val id: Int? =null,
    val address_one: String? =null,
    val address_two: String? = null,

    val CityId: String? = null,
    val StateId: String? = null,
    val postlCode: String? = null,
    val latitude: Number? = null,
    val longitude: Number? = null,

    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userUid: String? = null
):Serializable {
    override fun toString(): String {
        if (address_two != null)
            return "${address_one} ${address_two}"
        return "$address_one"
            /*
        return "Address(id=$id, address_one=$address_one, address_two=$address_two, latitude=$latitude" +
                ", longitude=$longitude,createdAt=$createdAt, updatedAt=$updatedAt" +
                ", userUid=$userUid)"

             */
    }
}

package com.example.theavengers_mad5254_project.model.data

import java.io.Serializable

data class BookingResponse (
    val count: Int,
    val rows: List<Booking>

):Serializable

data class Booking (
    val id: Int? =null,
    val shovlerId: Int? =null,
    val addressId: String? = null,
    val instructions: String? = null,
    val date: String? = null,
    val price: Int? = null,
    val hours_required: Int? = null,
    val is_completed: Boolean? = null,
    val address: Address? = null,
    val shovler: Shovler? = null
):Serializable {
    override fun toString(): String {
        return "Booking(id=$id, shovlerId=$shovlerId, addressId=$addressId, instructions=$instructions" +
                ", date=$date,price=$price, hours_required=$hours_required" +
                ", is_completed=$is_completed)"
    }
}

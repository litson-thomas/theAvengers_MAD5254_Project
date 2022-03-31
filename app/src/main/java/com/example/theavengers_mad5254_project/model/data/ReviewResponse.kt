package com.example.theavengers_mad5254_project.model.data

import java.io.Serializable

data class ReviewResponse (
    val count: Int,
    val rows: List<Review>

):Serializable

data class Review (
    val id: Int? =null,
    val title: String? =null,
    val description: String? = null,
    val rating: Int? = null,
    val shovlerId: Int? = null,
    val userUid: String? = null,
    val bookingId: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
):Serializable {
    override fun toString(): String {
        return "Booking(id=$id, title=$title, description=$description, rating=$rating" +
                ", shovlerId=$shovlerId,userUid=$userUid, bookingId=$bookingId) " +
                ", createdAt=$createdAt, updatedAt=$updatedAt"
    }
}
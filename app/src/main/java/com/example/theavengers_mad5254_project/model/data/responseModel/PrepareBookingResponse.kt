package com.example.theavengers_mad5254_project.model.data.responseModel

data class PrepareBookingResponse(
    val paymentIntent: String,
    val ephemeralKey: String,
    val customer: String,
    val publishableKey: String
)

package com.example.theavengers_mad5254_project.model.data.requestModel

data class PrepareBookingRequest(
  val instructions: String?,
  val date: String?,
  val price: Double,
  val hours_required: Int,
  val shovelerId: Int,
  val addressId: Int,
  val amount: Double
)

package com.example.theavengers_mad5254_project.model.data.responseModel

data class LocationResponse(
    val count: Int,
    val rows: ArrayList<Row>
)

data class Row(
    val State: State,
    val StateId: Int,
    val city_name: String,
    val createdAt: String,
    val id: Int,
    val updatedAt: String
)

data class State(
    val countryId: Int,
    val createdAt: String,
    val id: Int,
    val state_code: String,
    val state_name: String,
    val updatedAt: String
)
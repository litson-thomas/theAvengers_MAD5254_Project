package com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ListItem>,
    val message: Int
)



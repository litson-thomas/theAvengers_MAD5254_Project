package com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel


class GeocodeResponseItem : ArrayList<GeocodeResponseItemItem>()

data class GeocodeResponseItemItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)

data class LocalNames(
    val ar: String,
    val bn: String,
    val ca: String,
    val de: String,
    val el: String,
    val en: String,
    val eo: String,
    val es: String,
    val fa: String,
    val fr: String,
    val gr: String,
    val hi: String,
    val hy: String,
    val ja: String,
    val kn: String,
    val ko: String,
    val mr: String,
    val oc: String,
    val oj: String,
    val pa: String,
    val pl: String,
    val ps: String,
    val pt: String,
    val ru: String,
    val ta: String,
    val ug: String,
    val uk: String,
    val ur: String,
    val zh: String
)
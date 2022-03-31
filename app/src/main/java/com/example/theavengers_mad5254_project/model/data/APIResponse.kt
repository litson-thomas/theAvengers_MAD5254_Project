package com.example.theavengers_mad5254_project.model.data

data class APIResponse(
    val err: Err,
    val message: String,
    val status: Boolean
)

data class Err(
    val code: String,
    val message: String
)
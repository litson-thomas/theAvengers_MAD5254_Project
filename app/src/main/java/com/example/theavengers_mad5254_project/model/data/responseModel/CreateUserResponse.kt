package com.example.theavengers_mad5254_project.model.data.responseModel

import java.io.Serializable

data class CreateUserResponse(
    val err: Err,
    val message: String,
    val status: Boolean
):Serializable

data class Err(
    val code: String,
    val message: String
): Serializable
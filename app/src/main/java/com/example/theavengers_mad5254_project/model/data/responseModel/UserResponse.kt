package com.example.theavengers_mad5254_project.model.data.responseModel

import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.model.data.ShovlerUser

data class UserResponse(
  val count: Number,
  val rows: List<ShovlerUser>
)

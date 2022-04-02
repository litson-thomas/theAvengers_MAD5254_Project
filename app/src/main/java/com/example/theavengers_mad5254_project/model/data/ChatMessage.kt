package com.example.theavengers_mad5254_project.model.data

import com.google.gson.annotations.SerializedName

data class ChatMessage (

  @SerializedName("id"        ) var id        : Int?    = null,
  @SerializedName("message"   ) var message   : String? = null,
  @SerializedName("room"      ) var room      : String? = null,
  @SerializedName("createdAt" ) var createdAt : String? = null,
  @SerializedName("updatedAt" ) var updatedAt : String? = null,
  @SerializedName("userUid"   ) var userUid   : String? = null,
  @SerializedName("shovlerId"   ) var shovlerId   : Int? = null,

  @SerializedName("user"      ) var user      : ChatUser?   = ChatUser()

)

data class ChatUser (

  @SerializedName("uid"  ) var uid  : String? = null,
  @SerializedName("name" ) var name : String? = null

)

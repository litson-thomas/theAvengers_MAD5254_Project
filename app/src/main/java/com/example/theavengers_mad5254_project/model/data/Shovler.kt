package com.example.theavengers_mad5254_project.model.data;

import com.google.gson.annotations.SerializedName

data class Shovler (

  @SerializedName("id"                 ) var id                : Int?                     = null,
  @SerializedName("title"              ) var title             : String?                  = null,
  @SerializedName("description"        ) var description       : String?                  = null,
  @SerializedName("radius_limit"       ) var radiusLimit       : Int?                     = null,
  @SerializedName("one_four_price"     ) var oneFourPrice      : Int?                     = null,
  @SerializedName("five_eight_price"   ) var fiveEightPrice    : Int?                     = null,
  @SerializedName("nine_twelve_price"  ) var nineTwelvePrice   : Int?                     = null,
  @SerializedName("city_side_price"    ) var citySidePrice     : Int?                     = null,
  @SerializedName("transit_number"     ) var transitNumber     : String?                  = null,
  @SerializedName("institution_number" ) var institutionNumber : String?                  = null,
  @SerializedName("account_number"     ) var accountNumber     : String?                  = null,
  @SerializedName("createdAt"          ) var createdAt         : String?                  = null,
  @SerializedName("updatedAt"          ) var updatedAt         : String?                  = null,
  @SerializedName("userUid"            ) var userUid           : String?                  = null,
  @SerializedName("addressId"          ) var addressId         : String?                  = null,
  @SerializedName("user"               ) var user              : User?                    = User(),
  @SerializedName("shovler_images"     ) var shovlerImages     : ArrayList<ShovlerImages> = arrayListOf()

)

data class ShovlerImages (

  @SerializedName("id"        ) var id        : Int?    = null,
  @SerializedName("image"     ) var image     : String? = null,
  @SerializedName("createdAt" ) var createdAt : String? = null,
  @SerializedName("updatedAt" ) var updatedAt : String? = null,
  @SerializedName("shovlerId" ) var shovlerId : Int?    = null

)

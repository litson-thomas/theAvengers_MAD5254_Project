package com.example.theavengers_mad5254_project.model.data

import java.io.Serializable

data class ShovlerResponse  (
    val count: Int,
    val rows: List<Shovler>
) : Serializable

data class Shovler  (
    val id: Int? = null,
    val userUid: String? = null,
    val title: String? = null,
    val description: String? = null,
    val radius_limit: Int? = null,
    val one_four_price: Int? = null,
    val five_eight_price: Int? = null,
    val nine_twelve_price: Int? = null,
    val city_side_price: Int? = null,
    val transit_number: Int? = null,
    val institution_number: Int? = null,
    val account_number: Int? = null,
    val addressId: Int? = null,
    var shovler_images: List<ShovlerImage>? = null

) : Serializable{

    override fun toString(): String {
        return "BecomeSholver( id=$id, userUid=$userUid , title=$title, description=$description, radius_limit=$radius_limit" +
                ", one_four_price=$one_four_price,five_eight_price=$five_eight_price, five_eight_price=$five_eight_price" +
                ", city_side_price=$city_side_price, transit_number=$transit_number, institution_number=$institution_number" +
                ", account_number=$account_number), addressId=$addressId)"
    }
}

data class ShovlerImage  (
    var filename: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val shovlerId: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
): Serializable

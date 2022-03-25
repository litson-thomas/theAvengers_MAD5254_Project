package com.example.theavengers_mad5254_project.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BecomeShovler(

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("description")
    val description: String? = null,

    @Expose
    @SerializedName("radius_limit")
    val radius_limit: Int? = null,

    @Expose
    @SerializedName("one_four_price")
    val one_four_price: Int? = null,

    @Expose
    @SerializedName("five_eight_price")
    val five_eight_price: Int? = null,

    @Expose
    @SerializedName("nine_twelve_price")
    val nine_twelve_price: Int? = null,

    @Expose
    @SerializedName("city_side_price")
    val city_side_price: Int? = null,

    @Expose
    @SerializedName("transit_number")
    val transit_number: Int? = null,

    @Expose
    @SerializedName("institution_number")
    val institution_number: Int? = null,

    @Expose
    @SerializedName("account_number")
    val account_number: Int? = null,

){
    override fun toString(): String {
        return "BecomeSholver(title=$title, description=$description, radius_limit=$radius_limit" +
                ", one_four_price=$one_four_price,five_eight_price=$five_eight_price, five_eight_price=$five_eight_price" +
                ", city_side_price=$city_side_price, transit_number=$transit_number, institution_number=$institution_number" +
                ", account_number=$account_number)"
    }
}
package com.example.theavengers_mad5254_project.repository

import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import com.example.theavengers_mad5254_project.model.data.requestModel.PrepareBookingRequest
import okhttp3.MultipartBody
import com.example.theavengers_mad5254_project.utils.AppPreference


class MainRepository constructor(private val apiService: ApiService) {

    suspend fun getWeatherDetails( lat: Double,lng:Double, apiKey: String) = apiService.getWeatherDetails(lat = lat, lon = lng, apiKey = apiKey)

    suspend fun getWeatherForecastDetails(lat: Double,lng:Double, apiKey: String) = apiService.getWeatherForecastDetails(lat = lat, lon = lng, apiKey = apiKey)

    suspend fun getGeocodeDetails(lat: Double,lng:Double, apiKey: String) = apiService.getGeocoderDetails(lat = lat, lon = lng, apiKey = apiKey)

    suspend fun createUser( createUserRequest: CreateUserRequest) = apiService.registerUser(createUserRequest)

    suspend fun addShovler( shovler: Shovler) = apiService.addShovler(shovler)
    suspend fun updateShovler( shovler: Shovler) = apiService.updateShovler(shovler.id, shovler)
    suspend fun updateBooking( booking: Booking) = apiService.updateBooking(booking.id,booking)
    suspend fun getReviews( shovlerId: Int, bookingId: Int) = apiService.getReviews(shovlerId,bookingId)
    suspend fun getShovlerListings( userUid: String) = apiService.getShovlerListings(userUid)
    suspend fun getShovlerListing( id: Int) = apiService.getShovlerListing(id)

    suspend fun getBookings( shovlerId: Int) = apiService.getBookings(shovlerId)
    suspend fun getBookings() = apiService.getBookings()
    suspend fun uploadShowlerImage(file: MultipartBody.Part) = apiService.uploadShowlerImage(file)
    suspend fun deleteShovlerImage(id: Int) = apiService.deleteShovlerImage(id)
    suspend fun getAddress(userUid: String) = apiService.getAddress(userUid)
    suspend fun getAddress() = apiService.getAddress()

    suspend fun loadShovlers() = apiService.getShovlers(token = AppPreference.userToken)

    suspend fun loadShovlerById( id: Int) = apiService.getShovlerById(token = AppPreference.userToken, id = id)

    suspend fun getUser( uid: String) = apiService.getUser(token = AppPreference.userToken, uid = uid)

    suspend fun prepareBooking( prepareBookingRequest: PrepareBookingRequest) = apiService.prepareBooking(prepareBookingRequest)

    suspend fun getChats( room: String) = apiService.getChats(room)
    suspend fun getMessages( shovlerId: Int) = apiService.getMessages(shovlerId)
    suspend fun getMessages() = apiService.getMessages()

    suspend fun searchShovlers(shovlers: String) = apiService.getShovlerBySearch(token = AppPreference.userToken, shovlers)

}


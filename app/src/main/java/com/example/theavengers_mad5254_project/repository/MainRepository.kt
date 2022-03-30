package com.example.theavengers_mad5254_project.repository

import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.model.data.requestModel.CreateUserRequest
import okhttp3.MultipartBody

class MainRepository constructor(private val apiService: ApiService) {

    suspend fun createUser( createUserRequest: CreateUserRequest) = apiService.registerUser(createUserRequest)
    suspend fun addShovler( shovler: Shovler) = apiService.addShovler(shovler)
    suspend fun updateShovler( shovler: Shovler) = apiService.updateShovler(shovler.id, shovler)
    suspend fun updateBooking( booking: Booking) = apiService.updateBooking(booking.id,booking)
    suspend fun getReviews( shovlerId: Int, bookingId: Int) = apiService.getReviews(shovlerId,bookingId)
    suspend fun getShovlerListings( userUid: String) = apiService.getShovlerListings(userUid)
    suspend fun getBookings( shovlerId: Int) = apiService.getBookings(shovlerId)
    suspend fun getBookings() = apiService.getBookings()
    suspend fun uploadShowlerImage(file: MultipartBody.Part) = apiService.uploadShowlerImage(file)
    suspend fun deleteShovlerImage(id: Int) = apiService.deleteShovlerImage(id)
    suspend fun getAddress(userUid: String) = apiService.getAddress(userUid)

}
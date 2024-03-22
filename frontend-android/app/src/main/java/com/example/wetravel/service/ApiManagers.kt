package com.example.wetravel.service

import com.example.wetravel.models.Trip
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("trips")
    suspend fun createTrip(@Body tripConfiguration: Trip): Response<Unit>
}


class TripRepository (private val apiService: ApiService) {
    suspend fun createTrip(tripConfiguration: Trip): Result<Unit> {
        return try {
            val response = apiService.createTrip(tripConfiguration)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                // Return error
                Result.failure(RuntimeException("Create Trip API call failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // more functions related Trip specific stuff
}





package com.example.wetravel.service

import android.util.Log
import com.example.wetravel.models.Trip
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("trips")
    suspend fun createTrip(@Body tripConfiguration: Trip): Response<Trip>
}


class TripRepository (private val apiService: ApiService) {
    suspend fun createTrip(tripConfiguration: Trip): Result<Trip> {
        return try {
            val response = apiService.createTrip(tripConfiguration)
            Log.d("Response from trips API", response.toString())
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


    // Voting
    // Trip ID, User ID, Place ID
    // Send the request
    // On success: update the destination vote counts
    // On failure: throw an error

//    suspend fun castVote(): Result<Unit> {
//        return Result.success()
//    }



    // TODO: more functions related Trip specific stuff
}

// TODO: Create more API Managers here (e.g. DatabaseAPIManager, GoogleMapsAPIManager, ...)




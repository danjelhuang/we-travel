package com.example.wetravel.service

import android.util.Log
import com.example.wetravel.models.AddDestinationRequest
import com.example.wetravel.models.Trip
import com.example.wetravel.models.User
import com.example.wetravel.models.UserCreationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("trips")
    suspend fun createTrip(@Body tripConfiguration: Trip): Response<Trip>

    @POST("trips/{id}/destinationsList")
    suspend fun addDestination(@Path("id") tripID: String, @Body placeID: AddDestinationRequest) :Response<Trip>

    @POST("users")
    suspend fun createUser(@Body userRequestBody: UserCreationRequest): Response<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userID: String): Response<User>

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


    suspend fun addDestination(tripID: String, placeID: String) : Result<Trip> {
        return try {
            val requestBody = AddDestinationRequest(placeID)
            val response = apiService.addDestination(tripID, requestBody)
            Log.d("Response from trips API", response.toString())
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                // Return error
                Result.failure(RuntimeException("Add Destination API call failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO: more functions related Trip specific stuff
}

class UserRepository (private val apiService: ApiService) {
    suspend fun createUser(userID: String): Result<User> {
        return try {
            val requestBody = UserCreationRequest(userID)
            val response = apiService.createUser(requestBody)
            Log.d("Response from API", response.toString())
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                // Return error
                Result.failure(RuntimeException("Create User API call failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(userID: String): Result<User> {
        return try {
            val response = apiService.getUser(userID)
            Log.d("Response from API", response.toString())
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                // Return error
                Result.failure(RuntimeException("Get User API call failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// TODO: Create more API Managers here (e.g. DatabaseAPIManager, GoogleMapsAPIManager, ...)




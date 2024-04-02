package com.example.wetravel.models

import android.graphics.Bitmap
import java.util.UUID


// This file contains the Data Models used by our Frontend
data class Destination(
    val placeId: String,
    val name: String,
    val address: String,
    val rating: String,
    val reviewCount: Int,
    val imageResId: Int,
    val numOfVotes: Int,
    val voted: Boolean,
    val description: String
)

data class APIDestinationData (
    val placeId: String,
    val name: String,
    val address: String,
    val rating: Double,
    val reviewCount : Int,
    val type : String,
    val imageBitmap : Bitmap?
)

// ENUM for voting phase
// TODO: Decide how to incorporate this into our DB Schema
enum class VotingPhase {
    ADD_DESTINATIONS,
    VOTING,
    RESULTS
}

data class AddDestinationResponse(
    val success: String,
    val message: String
)

data class AddDestinationRequest(
    val placeID: String
)

data class User(
    val userID: String = "",
    val tripIDs: List<String> = emptyList(),
)

data class UserCreationRequest(
    val userId: String
)

data class UserUpdateRequest(
    val tripID: String
)

data class TripUsers(
    val userID: String = "",
    val votes: Int = 0,
)

data class Trip(
    val tripID: String = "",
    val name: String = "",
    val city: String = "",
    val finalDestinationCount: Int = 0,
    val users: List<TripUsers> = emptyList(),
    val adminUserID: String = "",
    val votesPerPerson: Int = 0,
    val phase: String = "",
    val destinationsList: List<Destination> = emptyList(),
)

data class TripUpdateRequest(
    val tripID: String = "",
    val name: String = "",
    val city: String = "",
    val finalDestinationCount: Int = 0,
    val votesPerPerson: Int = 0,
    val phase: String = "Adding"
)


// TODO: Remove this and convert ViewModel properties in the ViewModels.kt class
data class ViewModel(
    val userId: String = "",
    val username: String = "",
    val userEmail: String = "",
    val trips: List<String> = emptyList(), // List of Trip Code's
    val currentTrip: Trip = Trip(), // The current Trip's
)


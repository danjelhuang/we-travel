package com.example.wetravel.models

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

// ENUM for voting phase
// TODO: Decide how to incorporate this into our DB Schema
enum class VotingPhase {
    ADD_DESTINATIONS,
    VOTING,
    RESULTS
}

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
    val destinationsList: List<Destination> = emptyList()
)

// TODO: Remove this and convert ViewModel properties in the ViewModels.kt class
data class ViewModel(
    val userId: String = "",
    val username: String = "",
    val userEmail: String = "",
    val trips: List<String> = emptyList(), // List of Trip Code's
    val currentTrip: Trip = Trip(), // The current Trip's
)


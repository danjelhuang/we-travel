package com.example.wetravel.models



// This file contains the Data Models used by our Frontend
data class Destination(
    val name: String,
    val address: String,
    val rating: String,
    val reviewCount: Int,
    val imageResId: Int,
    val numOfVotes: Int,
    val voted: Boolean
)

enum class VotingPhase {
    ADD_DESTINATIONS,
    VOTING,
    RESULTS
}

data class Trip(
    val code: String = "",
    val name: String = "",
    val city: String = "",
    val finalDestinationCount: String = "",
    val participants: List<String> = emptyList(),
    val votesPerPerson: Int = 0,
    val votingPhase: String = "",
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


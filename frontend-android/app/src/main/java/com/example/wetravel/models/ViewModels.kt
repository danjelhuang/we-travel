package com.example.wetravel.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.R
import com.example.wetravel.service.TripRepository
import com.example.wetravel.service.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID

// Below is a class to help us enforce state of data while we fetch from the backend
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class UserViewModel(
    private val tripRepository: TripRepository, private val userRepository: UserRepository
) : ViewModel() {
    private val _tripCode = MutableLiveData<Resource<String>>()
    val tripCode: LiveData<Resource<String>> = _tripCode

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    private val _allTrips = MutableLiveData<Resource<Map<String, Trip>>>()
    val allTrips: LiveData<Resource<Map<String, Trip>>> = _allTrips

    // Sample trip for frontend voting scaffolding
    private val _sampleTrip = MutableLiveData<Resource<Trip>>(
        Resource.Success<Trip>(
            Trip(
                tripID = "tester",
                name = "Sample Trip",
                city = "Sample City",
                finalDestinationCount = 5,
                votesPerPerson = 5,
                phase = "ADD_DESTINATIONS",
                users = listOf(
                    TripUsers(
                        userID = "local",
                        votes = 5
                    )
                ),
                destinationsList = listOf(
                    // Add your destinations here, for example:
                    Destination(
                        UUID.randomUUID().toString(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
                        R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
                    ),
                    // Add more destinations...
                    Destination(
                        UUID.randomUUID().toString(),"MoMA 2", "11 W 53rd St, New York", "4.6", 50,
                        R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
                    ),
                    Destination(
                        UUID.randomUUID().toString(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
                        R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
                    )
                )
            )
        )
    )

    val sampleTrip: LiveData<Resource<Trip>> = _sampleTrip

    // Cast a vote to the sample trip
    fun castSampleVote(id: String) {
        // Don't do anything if the user doesn't have votes left
        val votesRemaining = (_sampleTrip.value as Resource.Success<Trip>).data.users.find { it.userID == "local" }?.votes
        if ((votesRemaining ?:0) < 1) {
            return
        }
        // copy update and post
        viewModelScope.launch {
            val updatedTrip = (_sampleTrip.value as Resource.Success<Trip>).data.copy(
                destinationsList = (_sampleTrip.value as Resource.Success<Trip>).data.destinationsList.map { d ->
                    if (id == d.placeId) {
                        d.copy(totalVotes = d.totalVotes + 1, userVotes = d.userVotes + 1)
                    } else {
                        d
                    }
                },
                users = (_sampleTrip.value as Resource.Success<Trip>).data.users.map { u ->
                    if (u.userID == "local") {
                        u.copy(votes = u.votes - 1)
                    } else {
                        u
                    }
                }

            )
            Log.d("UPDATED", updatedTrip.toString())
            // Create the updated Trip
            _sampleTrip.postValue(Resource.Success(updatedTrip))
        }
    }

    fun removeSampleVote(id: String) {
        // To be safe, don't allow users to remove votes if they have full votes
        val votesRemaining = (_sampleTrip.value as Resource.Success<Trip>).data.users.find { it.userID == "local" }?.votes
        if ((votesRemaining ?:0) >= (_sampleTrip.value as Resource.Success<Trip>).data.votesPerPerson) {
            return
        }
        // copy update and post
        viewModelScope.launch {
            val updatedTrip = (_sampleTrip.value as Resource.Success<Trip>).data.copy(
                destinationsList = (_sampleTrip.value as Resource.Success<Trip>).data.destinationsList.map { d ->
                    if (id == d.placeId) {
                        d.copy(totalVotes = d.totalVotes - 1, userVotes = d.userVotes - 1)
                    } else {
                        d
                    }
                },
                users = (_sampleTrip.value as Resource.Success<Trip>).data.users.map { u ->
                    if (u.userID == "local") {
                        u.copy(votes = u.votes + 1)
                    } else {
                        u
                    }
                }
            )
            Log.d("UPDATED", updatedTrip.toString())
            // Create the updated Trip
            _sampleTrip.postValue(Resource.Success(updatedTrip))
        }
    }

    // TODO: API call for casting a vote; requires destinations
    fun castVote(tripId: String, placeId: String) {

    }

    // TODO: API call for removing a vote; requires destinations
    fun removeVote(tripId: String, placeId: String) {

    }


    /* TODO: More Fields here for UserViewModel...*/

    fun createTrip(trip: Trip) {
        _tripCode.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("create trip", "Create Trip called")
                val result = tripRepository.createTrip(trip)
                Log.d("Create trip Return Values: ", result.toString())
                if (result.isSuccess) {
                    // Load trip Data into tripData state var
                    _tripCode.postValue(Resource.Success(result.getOrNull()!!.tripID)) // Shouldn't be null
                } else {
                    _tripCode.postValue(Resource.Error("The API call failed with an Error. Check the API Logs"))
                }
            } catch (e: Exception) {
                _tripCode.postValue(Resource.Error("An exception occurred while calling the createTrip API"))
            }
        }
    }

    fun getOrCreateUser(userID: String) {
        _user.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("get user", "Get User called")
                val result = userRepository.getUser(userID)
                Log.d("Get user Return Values: ", result.toString())
                if (result.isSuccess) {
                    val newUser = User(result.getOrNull()!!.userID, result.getOrNull()!!.tripIDs)
                    _user.postValue(Resource.Success(newUser))
                } else {
                    Log.d("create user", "Create User called")
                    val result = userRepository.createUser(userID)
                    Log.d("Create user Return Values: ", result.toString())
                    if (result.isSuccess) {
                        val newUser = User(result.getOrNull()!!.userID, result.getOrNull()!!.tripIDs)
                        _user.postValue(Resource.Success(newUser))
                    } else {
                        _user.postValue(Resource.Error("The Create User API call failed with an Error. Check the API Logs"))
                    }
                }
            } catch (e: Exception) {
                _user.postValue(Resource.Error("An exception occurred while calling the createUser API"))
            }
        }
    }

    fun getAllTrips(userID: String) {
        _allTrips.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("get all trip", "get all Trips called")
                val result = tripRepository.getAllTrips(userID)
                Log.d("get all Trips Return Values: ", result.toString())
                if (result.isSuccess) {
                    // Construct the map here
                    val tripMap: Map<String, Trip> = result.getOrNull()!!.associateBy { it.tripID }
                    _allTrips.postValue(Resource.Success(tripMap))
                } else {
                    _allTrips.postValue(Resource.Error("The Get All Trips API call failed with an Error. Check the API Logs"))
                }
            } catch (e: Exception) {
                _allTrips.postValue(Resource.Error("An exception occurred while calling the Get All Trips API"))
            }
        }
    }

}

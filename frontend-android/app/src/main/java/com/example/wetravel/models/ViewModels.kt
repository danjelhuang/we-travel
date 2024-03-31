package com.example.wetravel.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.R
import com.example.wetravel.service.TripRepository
import kotlinx.coroutines.launch
import java.util.UUID

// Below is a class to help us enforce state of data while we fetch from the backend
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class UserViewModel(private val tripRepository: TripRepository /* TODO: More API Managers here*/) :
    ViewModel() {
    private val _tripCode = MutableLiveData<Resource<String>>()
    val tripCode: LiveData<Resource<String>> = _tripCode

    /* TODO: More Fields here for UserViewModel...*/

    val sampleTrip: Trip = Trip(
        tripID = "a1b2c3",
        name = "Sample Trip",
        city = "London",
        finalDestinationCount = 5,
        users = emptyList(),
        adminUserID = "localUser",
        votesPerPerson = 5,
        phase = "ADD_DESTINATIONS",
        destinationsList = listOf(
            Destination(
                UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
                R.drawable.sample_destination_image, voted = true, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
            ),
            // Add more destinations...
            Destination(
                UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
                R.drawable.sample_destination_image, voted = false, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
            ),
            Destination(
                UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
                R.drawable.sample_destination_image, voted = true, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
            ),
        )
    )

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

    // TODO: Casting a vote
    fun castVote(tripID: String, placeID: String) {
        try {

        } catch (e: Exception) {

        }

    }


    // TODO: Removing a vote
    fun removeVote() {

    }

//    fun loadTrip(tripId: String) {
//
//        viewModelScope.launch {
//            try {
//                Log.d("load trip", "load trip called")
//                val result = tripRepository.loadTrip(tripId)
//                // handle data here
//                // where to load - into the viewmodel?
//            }
//
//            catch (e: Exception) {
//                _createAccountResponse.postValue(Resource.Error("An exception occurred while calling the loadrip API"))
//
//            }
//        }
//
//    }

    

}

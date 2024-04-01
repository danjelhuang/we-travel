package com.example.wetravel.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.service.TripRepository
import com.example.wetravel.service.UserRepository
import kotlinx.coroutines.launch

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

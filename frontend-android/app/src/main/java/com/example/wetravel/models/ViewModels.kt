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

    private val _userID = MutableLiveData<Resource<String>>()
    val userID: LiveData<Resource<String>> = _userID

    private val _tripIDs = MutableLiveData<Resource<List<String>>>()
    val tripIDs: LiveData<Resource<List<String>>> = _tripIDs

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
        _userID.value = Resource.Loading
        _tripIDs.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("get user", "Get User called")
                val result1 = userRepository.getUser(userID)
                Log.d("Get user Return Values: ", result1.toString())
                if (result1.isSuccess) {
                    _userID.postValue(Resource.Success(result1.getOrNull()!!.userID))
                    _tripIDs.postValue(Resource.Success(result1.getOrNull()!!.tripIDs))
                } else {
                    Log.d("create user", "Create User called")
                    val result = userRepository.createUser(userID)
                    Log.d("Create user Return Values: ", result.toString())
                    if (result.isSuccess) {
                        _userID.postValue(Resource.Success(result.getOrNull()!!.userID))
                        _tripIDs.postValue(Resource.Success(result.getOrNull()!!.tripIDs))
                    } else {
                        _userID.postValue(Resource.Error("The Create User API call failed with an Error. Check the API Logs"))
                        _tripIDs.postValue(Resource.Error("The Create User API call failed with an Error. Check the API Logs"))
                    }
                }
            } catch (e: Exception) {
                _tripCode.postValue(Resource.Error("An exception occurred while calling the createUser API"))
            }
        }
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

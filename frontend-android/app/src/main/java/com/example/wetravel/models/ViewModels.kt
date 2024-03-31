package com.example.wetravel.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.service.TripRepository
import kotlinx.coroutines.launch

// Below is a class to help us enforce state of data while we fetch from the backend
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class UserViewModel(private val tripRepository: TripRepository /* TODO: More API Managers here*/) :
    ViewModel() {
    private val _createAccountResponse = MutableLiveData<Resource<Any?>>()
    val createAccountResponse: LiveData<Resource<Any?>> = _createAccountResponse

    /* TODO: More Fields here for UserViewModel...*/

    fun createTrip(trip: Trip) {
        _createAccountResponse.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("create trip", "Create Trip called")
                val result = tripRepository.createTrip(trip)
                if (result.isSuccess) {
                    _createAccountResponse.postValue(Resource.Success(null))
                } else {
                    _createAccountResponse.postValue(Resource.Error("The API call failed with an Error. Check the API Logs"))
                }
            } catch (e: Exception) {
                _createAccountResponse.postValue(Resource.Error("An exception occurred while calling the createTrip API"))
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

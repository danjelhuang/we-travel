package com.example.wetravel.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.service.TripRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

// Below is a class to help us enforce state of data while we fetch from the backend
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class UserViewModel(private val tripRepository: TripRepository,
                    private val db: FirebaseFirestore /* TODO: More API Managers here*/) :
    ViewModel() {
    private val _tripCode = MutableLiveData<Resource<String>>()
    val tripCode: LiveData<Resource<String>> = _tripCode

    // LiveData for trip city updates
    private val _tripCity = MutableLiveData<String>()
    val tripCity: LiveData<String> = _tripCity

    // included so that tripId can be passed as a string for the snapshot listener
    private val _tripId = MutableLiveData<String>()
    val tripId: LiveData<String> = _tripId

    private var tripListener: ListenerRegistration? = null

    init {
        listenToTrip("BYB97") // Replace with dynamic trip ID if necessary
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
                    val newTripId = result.getOrNull()?.tripID ?: return@launch
                    setTripId(newTripId)
                    _tripCode.postValue(Resource.Success(newTripId))

                } else {
                    _tripCode.postValue(Resource.Error("The API call failed with an Error. Check the API Logs"))
                }
            } catch (e: Exception) {
                _tripCode.postValue(Resource.Error("An exception occurred while calling the createTrip API"))
            }
        }
    }

    fun setTripId(newTripId: String) {
        if (_tripId.value != newTripId) {
            _tripId.value = newTripId
            tripListener?.remove() // Remove the old listener
            listenToTrip(newTripId) // Start listening to the new trip ID
        }
    }

    fun listenToTrip(tripId: String) {
        tripListener = db.collection("trips").document(tripId)
            .addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("String","did not work lol")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                _tripCity.postValue(snapshot.getString("city"))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
    }

    

}

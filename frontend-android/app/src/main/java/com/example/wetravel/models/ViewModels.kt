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
import kotlinx.coroutines.tasks.await

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

    
    ////////////////////////////////////////////////////////////////////////////
    
    //snapshot listeners are only concerned when a string value gets modified, which is why "copies" of variables that will be changed are made into strings

    private val _tripCity = MutableLiveData<String>()
    val tripCity: LiveData<String> = _tripCity

    private val _tripId = MutableLiveData<String>()
    val tripId: LiveData<String> = _tripId

    private val _finalDestinationCount = MutableLiveData<Int>()
    val finalDestinationCount: LiveData<Int> = _finalDestinationCount

    private val _tripName = MutableLiveData<String>()
    val tripName: LiveData<String> = _tripName

    private val _votesPerPerson = MutableLiveData<Int>()
    val votesPerPerson: LiveData<Int> = _votesPerPerson

     ////////////////////////////////////////////////////////////////////////////

    private var tripListener: ListenerRegistration? = null



    // init {
    //     listenToTrip(tripId) // Replace with dynamic trip ID if necessary
    // }


    /* TODO: More Fields here for UserViewModel...*/


    fun createTrip(trip: Trip) {
        _tripCode.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("create trip", "Create Trip called")
                val result = tripRepository.createTrip(trip)
                Log.d("Create trip Return Values: ", result.toString())
                if (result.isSuccess) {
                    val newTripId = result.getOrNull()?.tripID ?: return@launch //return@launch
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

    fun joinTripByCode(code: String) {
        viewModelScope.launch {
            try {
                val tripQuerySnapshot = db.collection("trips")
                    .whereEqualTo("joinCode", code) // Assuming there is a field named 'joinCode' in the trips documents
                    .get()
                    .await()

                if (tripQuerySnapshot.documents.isNotEmpty()) {
                    val tripId = tripQuerySnapshot.documents.first().id
                    setTripId(tripId)
                } else {
                    // Handle the case where no trip with the given code exists
                }
            } catch (e: Exception) {
                // Handle any errors, such as network issues
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

    // Document-level listener for a single trip, and can monitor
    /*      city
            finalDestinationCount
            name (trip name)
            votesPerPerson
     */ 
    fun listenToTrip(tripId: String) {

        // Remove any existing listener for this ViewModel
        tripListener?.remove()

        // Start a new listener and assign it to tripListener
        tripListener = db.collection("trips").document(tripId)
            .addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("String","did not work lol")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                _tripCity.postValue(snapshot.getString("city"))
                _finalDestinationCount.postValue(snapshot.getLong("finalDestinationCount")?.toInt())
                _tripName.postValue(snapshot.getString("name"))
                _votesPerPerson.postValue(snapshot.getLong("votesPerPerson")?.toInt())
            }
        }

    }

    // remember to call stopListeningToTrip() to clean up listeners for trips that no longer need to be monitored. 
    // i.e when the user navigates away from a screen displaying trip details
//    fun stopListeningToTrip(tripId: String) {
//        listeners[tripId]?.remove() // Remove the listener
//        listeners.remove(tripId) // Remove the entry from the map
//    }

    


    // important to clear the listeners when the Viewmodel ends 
    override fun onCleared() {
        super.onCleared()
    }

    

}

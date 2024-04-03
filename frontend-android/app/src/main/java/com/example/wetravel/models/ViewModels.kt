package com.example.wetravel.models

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetravel.R
import com.example.wetravel.service.TripRepository
import com.example.wetravel.service.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch
import java.util.UUID

// Below is a class to help us enforce state of data while we fetch from the backend
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class UserViewModel(
    private val tripRepository: TripRepository,
    private val userRepository: UserRepository,
    private val db: FirebaseFirestore

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
                        placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
                        type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
                    ),
                    // Add more destinations...
                    Destination(
                        placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
                        type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
                    ),
                    Destination(
                        placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
                        type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
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
    /////////////////////////////////////////////////
    // listener related vals

    private val _tripId = MutableLiveData<String>()
    val tripId: LiveData<String> = _tripId

    private val _snapshotUser = MutableLiveData<User>()
    val snapshotUser: LiveData<User> = _snapshotUser


    private val _tripCity = MutableLiveData<String>()
    val tripCity: LiveData<String> = _tripCity

    private val _destinationCurrentVotes = MutableLiveData<String>()
    val destinationCurrentVotes: LiveData<String> = _destinationCurrentVotes

    private var tripListener: ListenerRegistration? = null


    ////////////////////////////////////////////

    fun createTrip(trip: Trip) {
        _tripCode.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("create trip", "Create Trip called")
                val result = tripRepository.createTrip(trip)
                if (result.isSuccess) {
                    val newTrip = result.getOrNull()!!
                    _tripCode.postValue(Resource.Success(newTrip.tripID))
                    val currentTrips = _allTrips.value
                    if (currentTrips is Resource.Success) {
                        val updatedTrips = currentTrips.data.toMutableMap()
                        updatedTrips[newTrip.tripID] = newTrip
                        _allTrips.postValue(Resource.Success(updatedTrips))

                        // listener
                        setTripId(newTrip.tripID)

                    } else {
                        _allTrips.postValue(Resource.Error("Failed to add new trip to all trips"))
                    }
                    // call endpoint to update user
                    Log.d("update trip fields", trip.adminUserID)
                    val userResult = userRepository.updateUser(
                        userID = trip.adminUserID,
                        tripID = newTrip.tripID
                    )
                    if (userResult.isSuccess) {
                        _user.postValue(Resource.Success(userResult.getOrNull()!!))
                        Log.d("createTrip", "Trip created successfully")
                    } else {
                        Log.d("error", userResult.toString())
                        _user.postValue(Resource.Error("Failed to join the trip. Check the API logs."))
                    }
                } else {
                    _tripCode.postValue(Resource.Error("The API call failed with an Error. Check the API Logs"))
                }
            } catch (e: Exception) {
                _tripCode.postValue(Resource.Error("An exception occurred while calling the createTrip API"))
            }
        }
    }

    fun updateTrip(trip: TripUpdateRequest) {

        viewModelScope.launch {
            try {
                val result = tripRepository.updateTrip(trip.tripID, trip)
                Log.d("result", result.toString())
                if (result.isSuccess) {
                    Log.d("updateTrip", "Trip Updated successfully")
                } else {
                    Log.d("updateTrip", "Trip Update API call Failed")
                }
            } catch (e: Exception) {
                Log.d("updateTrip", "An exception occurred while calling the updateTrip API: $e")
            }
        }
    }

    fun getOrCreateUser(userID: String) {
        _user.value = Resource.Loading

        viewModelScope.launch {
            try {
                Log.d("get user", "Get User called")
                val result1 = userRepository.getUser(userID)
                Log.d("Get user Return Values: ", result1.toString())
                if (result1.isSuccess) {
                    val newUser = User(result1.getOrNull()!!.userID, result1.getOrNull()!!.tripIDs)
                    _user.postValue(Resource.Success(newUser))
                } else {
                    Log.d("create user", "Create User called")
                    val result = userRepository.createUser(userID)
                    Log.d("Create user Return Values: ", result.toString())
                    if (result.isSuccess) {
                        val newUser =
                            User(result.getOrNull()!!.userID, result.getOrNull()!!.tripIDs)
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


    fun addDestinations(tripID: String, placeID: String, onError: (String) -> Unit, onSuccess: () -> Unit)
    {
        viewModelScope.launch {
            try {
                Log.d("add destination", "Add Destination called")
                val result = tripRepository.addDestination(tripID, placeID)
                Log.d("Add Destination Return Values: ", result.toString())
                if (result.isSuccess) {
                    onSuccess()
                    return@launch
                } else {
                    onError("Error adding destination. Destination may already exist")
                    return@launch

                }
            } catch (e: Exception) {
                onError("Error adding destination. Destination may already exist")
                return@launch
            }
        }
    }
    fun joinTrip(tripID: String, onError: (String) -> Unit, onSuccess: () -> Unit) {

        _tripCode.value = Resource.Loading

        viewModelScope.launch {
            // Check if tripcode exists. If it does, add to local allTrips var
            try {
                val getTripResult = tripRepository.getTrip(tripID = tripID)

                if (getTripResult.isSuccess) {
                    // Trip code found, add to local allTrips var
                    val newTrip = getTripResult.getOrNull()!!
                    val currentTrips = _allTrips.value
                    Log.d("joinTrip", currentTrips.toString())
                    if (currentTrips is Resource.Success) {
                        // listener
                        setTripId(newTrip.tripID)

                        val updatedMap = currentTrips.data.toMutableMap()
                        // Add the new trip to the map
                        updatedMap[tripID] = newTrip
                        // Post the updated map
                        _allTrips.postValue(Resource.Success(updatedMap))

                        Log.d("joinTrip", "Trip Get successful")
                    } else {
                        Log.d(
                            "joinTrip",
                            "Cannot update trips: Current trips state is not Success."
                        )
                    }
                } else {
                    // trip code not found
                    onError("Invalid code")
                    return@launch
                }
            } catch (e: Exception) {
                onError("An exception occurred while trying to get the trip.")
                return@launch
            }

            // Add tripID to user's tripIds array
            val currentUser = _user.value
            when (currentUser) {
                is Resource.Success -> {
                    try {
                        Log.d("joinTrip", "Joining trip")
                        // Assuming updateUser function takes a User object and returns a Result<User>
                        val result = userRepository.updateUser(
                            userID = currentUser.data.userID,
                            tripID = tripID
                        )
                        if (result.isSuccess) {
                            // update the _user LiveData with the new user data
                            _user.postValue(Resource.Success(result.getOrNull()!!))
                            Log.d("joinTrip", "Trip joined successfully")
                        } else {
                            _user.postValue(Resource.Error("Failed to join the trip. Check the API logs."))
                        }
                    } catch (e: Exception) {
                        _user.postValue(Resource.Error("An exception occurred while trying to join the trip."))
                    }
                }

                else -> {
                    _user.postValue(Resource.Error("User data is not loaded yet or failed to load."))
                }
            }

            // Update local tripCode
            _tripCode.postValue(Resource.Success(tripID))

            // Add current participant
            when (currentUser) {
                is Resource.Success -> {
                    try {
                        Log.d("joinTrip", "Adding current participant to trip users")
                        val addParticipantResult =
                            tripRepository.addParticipant(
                                tripID = tripID,
                                userID = currentUser.data.userID
                            )

                        if (!addParticipantResult.isSuccess) {
                            Log.d("joinTrip", "Add Participant Failed, userID not added to trip")
                        }

                    } catch (e: Exception) {
                        Log.d("joinTrip", e.toString())
                    }
                }

                else -> {
                    Log.d("joinTrip", "User is not in Success State")
                }
            }
            onSuccess()
        }

    }

    fun updateTripCode(newTripCode: String) {
        _tripCode.postValue(Resource.Success(newTripCode))
    }

    private fun setTripId(newTripId: String) {
        if (_tripId.value != newTripId) {
            _tripId.value = newTripId
            tripListener?.remove() // Remove the old listener
            listenToTrip(newTripId) // Start listening to the new trip ID
        }
    }

    private fun listenToTrip(tripId: String) {
        tripListener = db.collection("trips").document(tripId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.d("listenToTrip", "did not work lol")
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val currentTrips = _allTrips.value
                    Log.d("listenToTrip", "listen")

                    if (currentTrips is Resource.Success) {
                        Log.d("listenToTrip", "currentTrips Success")
                        // Snapshots
                        val updatedMap = currentTrips.data.toMutableMap()
                        val existingTrip = updatedMap[tripId]
                        val currentUserId = (_user.value as Resource.Success<User>).data.userID

                        // Add the new trip to the map

                        // trip details: name, city, finalDestinationCount, votesPerPerson, phase

                        var newTrip = existingTrip?.copy(
                            name = snapshot.getString("name")!!,
                            city = snapshot.getString("city")!!,
                            finalDestinationCount = (snapshot.getLong("finalDestinationCount")
                                ?: 0L).toInt(),
                            votesPerPerson = (snapshot.getLong("votesPerPerson") ?: 0L).toInt(),
                            phase = snapshot.getString("phase")!!,
                            users = snapshot.get("users")!! as? List<TripUsers> ?: emptyList()
                        )

                        // destinationList details
                        val destinationsMap = snapshot.get("destinationsList") as? Map<String, Map<String, Any>> ?: emptyMap()
                        val destinationsCount = destinationsMap.size
                        val existingDestinationsCount = existingTrip?.destinationsList?.size ?: 0
                        Log.d("listenToTrip", destinationsMap.toString())

                        // if a new destination has been added to destinationsList
                        if (destinationsCount != existingDestinationsCount) {
                            newTrip = updateDestinations(newTrip!!, currentUserId, destinationsMap)
                            Log.d("listenToTrip", newTrip.toString())
                        } else {
                            Log.d("listenToTrip", "Destination count equals existing destination count")
                            newTrip = updateDestinations(newTrip!!, currentUserId, destinationsMap)
                            Log.d("listenToTrip", "Updated trip: $newTrip")
                        }

                        updatedMap[tripId] = newTrip
                        // Post the updated map
                        _allTrips.postValue(Resource.Success(updatedMap))

                        Log.d("listenToTrip", "Trip Get successful")
                    } else {
                        Log.d(
                            "listenToTrip",
                            "Cannot update trips: Current trips state is not Success."
                        )
                    }
                }
            }
    }

    private fun updateDestinations(newTrip: Trip, currentUserId: String, destinationsMap: Map<String, Map<String, Any>>): Trip {
        val updatedDestinationsList = mutableListOf<Destination>()

        newTrip.destinationsList.forEach { existingDestination ->
            val destinationData = destinationsMap[existingDestination.placeId]

            if (destinationData != null) {
                val totalVotes = (destinationData["totalVotes"] as? Number)?.toInt() ?: existingDestination.totalVotes
                val userVotesMap = destinationData["userVotes"] as? Map<String, Any> ?: emptyMap()
                val userVotes = (userVotesMap[currentUserId] as? Number)?.toInt() ?: existingDestination.userVotes

                val updatedDestination = existingDestination.copy(
                    totalVotes = totalVotes,
                    userVotes = userVotes
                )
                updatedDestinationsList.add(updatedDestination)
            } else {
                // If no matching destination is found, add the existing destination without changes
                updatedDestinationsList.add(existingDestination)
            }
        }
        Log.d("updateDestinations", updatedDestinationsList.toString())

        // Call Shannons google function
        return newTrip.copy(destinationsList = updatedDestinationsList)
    }


    override fun onCleared() {
        super.onCleared()
        tripListener?.remove()
    }


}

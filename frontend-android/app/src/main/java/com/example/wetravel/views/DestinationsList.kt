package com.example.wetravel.views

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wetravel.components.DestinationEntry
import com.example.wetravel.components.DestinationsListFooter
import com.example.wetravel.components.DestinationsListHeader
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.User
import com.example.wetravel.models.UserViewModel
import java.util.UUID

@Composable
fun DestinationsColumn(destinations: List<Destination>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        itemsIndexed(destinations) { _, destination ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                DestinationEntry(destination = destination)
            }

        }
    }
}

@Composable
fun DestinationsList(
    onAddDestinationButtonClicked: () -> Unit,
    onStartVotingButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    val sampleTrip by userViewModel.sampleTrip.observeAsState(initial = Resource.Loading)
    // Assuming we have a list of destinations to display
//    val destinations = listOf(
//        // Add your destinations here, for example:
//        Destination(
//            UUID.randomUUID().toString(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//        // Add more destinations...
//        Destination(
//            UUID.randomUUID().toString(),"MoMA 2", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//        Destination(
//            UUID.randomUUID().toString(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//    )

//    val tripCodeResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
//    val allTrips by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
//    val user by userViewModel.user.observeAsState(initial = Resource.Loading)
//
//    if (tripCodeResource is Resource.Success && allTrips is Resource.Success && user is Resource.Success) {
//        Log.d("Frontend state after Join", (tripCodeResource as Resource.Success<String>).data)
//        Log.d("Frontend state after Join", (allTrips as Resource.Success<Map<String, Trip>>).data.toString())
//        Log.d("Frontend state after Join", (user as Resource.Success<User>).data.toString())
//    }

    Scaffold(
        topBar = { DestinationsListHeader(tripName = "Toronto", onSettingsButtonClicked) },
        bottomBar = { DestinationsListFooter(onAddDestinationButtonClicked, onStartVotingButtonClicked) }
    ) { innerPadding ->
        when (sampleTrip) {
            is Resource.Success -> {
                val trip = (sampleTrip as Resource.Success<Trip>).data
                DestinationsColumn(destinations = trip.destinationsList, innerPadding = innerPadding)
            }
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Error -> {
                val errorMessage = (sampleTrip as Resource.Error).message
                // Display the error message
                Text(text = "Error: $errorMessage")
            }
        }

    }
}




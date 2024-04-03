package com.example.wetravel.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wetravel.components.DestinationEntry
import com.example.wetravel.components.DestinationsListFooter
import com.example.wetravel.components.DestinationsListHeader
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.User
import com.example.wetravel.models.UserViewModel

@Composable
fun DestinationsColumn(destinations: List<Destination>,
                       innerPadding: PaddingValues,
                       userViewModel: UserViewModel) {
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
    userViewModel: UserViewModel,
    userName: String? = ""
) {
    val sampleTripResource by userViewModel.sampleTrip.observeAsState(initial = Resource.Loading)
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

    val tripCodeResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
    val tripsResource by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
    val userResource by userViewModel.user.observeAsState(initial = Resource.Loading)

//    Scaffold(
//        topBar = { DestinationsListHeader(tripName = "Toronto", onSettingsButtonClicked) },
//        bottomBar = { DestinationsListFooter(onAddDestinationButtonClicked, onStartVotingButtonClicked) }
//    ) { innerPadding ->
//        when (sampleTrip) {
//            is Resource.Success -> {
//                val trip = (sampleTrip as Resource.Success<Trip>).data
//                DestinationsColumn(destinations = trip.destinationsList, innerPadding = innerPadding)
//            }
//            is Resource.Loading -> {
//                CircularProgressIndicator()
//            }
//            is Resource.Error -> {
//                val errorMessage = (sampleTrip as Resource.Error).message
//                // Display the error message
//                Text(text = "Error: $errorMessage")
//            }
//        }

    when {
        tripCodeResource is Resource.Success<*> && tripsResource is Resource.Success<*> && userResource is Resource.Success<*> -> {
            val tripCode = (tripCodeResource as Resource.Success<String>).data
            val trip = (tripsResource as Resource.Success<Map<String, Trip>>).data[tripCode]
            val user = (userResource as Resource.Success<User>).data

            Scaffold(
                topBar = { DestinationsListHeader(
                    tripName = trip?.name ?: "",
                    userName = userName ?: "User",
                    numParticipants = trip?.users?.size ?: -1,
                    onSettingsButtonClicked = onSettingsButtonClicked) },
                bottomBar = { DestinationsListFooter(onAddDestinationButtonClicked, onStartVotingButtonClicked, userViewModel) }
            ) { innerPadding ->
                val sampleTrip = (sampleTripResource as Resource.Success<Trip>).data
                DestinationsColumn(destinations = sampleTrip.destinationsList, innerPadding = innerPadding, userViewModel = userViewModel)
            }
        }

        tripCodeResource is Resource.Loading || tripsResource is Resource.Loading || userResource is Resource.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }
        tripCodeResource is Resource.Error || tripsResource is Resource.Error || userResource is Resource.Error -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val tripCodeErrorMessage = (tripCodeResource as Resource.Error).message
                val tripsErrorMessage = (tripsResource as Resource.Error).message
                val userErrorMessage = (userResource as Resource.Error).message
                Text(text = "Error: $tripCodeErrorMessage, $tripsErrorMessage, $userErrorMessage", textAlign = TextAlign.Center)
            }
        }
    }
}




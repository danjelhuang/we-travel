package com.example.wetravel.views

import android.util.Log
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
import com.example.wetravel.components.DestinationsVotingListHeader
import com.example.wetravel.components.VotingBottomCard
import com.example.wetravel.components.VotingDestinationEntry
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.TripUsers
import com.example.wetravel.models.User
import com.example.wetravel.models.UserViewModel


// The column of destination entries
@Composable
fun DestinationsVotingColumn(
    destinations: List<Destination>,
    innerPadding: PaddingValues,
    userViewModel: UserViewModel,
    tripID: String,
    userID: String
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        itemsIndexed(destinations) { _, destination ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                VotingDestinationEntry(
                    destination = destination,
                    userViewModel = userViewModel,
                    tripID = tripID,
                    userID = userID
                )
            }

        }
    }
}

// The actual page
@Composable
fun DestinationsVotingList(
    onEndVotingButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    // Trip from userViewModel
    val sampleTrip by userViewModel.sampleTrip.observeAsState()

    val tripCodeResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
    val tripsResource by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
    val userResource by userViewModel.user.observeAsState(initial = Resource.Loading)

    when {
        tripCodeResource is Resource.Success<*> && tripsResource is Resource.Success<*> && userResource is Resource.Success<*> -> {
            val tripCode = (tripCodeResource as Resource.Success<String>).data
            val trip = (tripsResource as Resource.Success<Map<String, Trip>>).data[tripCode]
            val userID = (userResource as Resource.Success<User>).data.userID

            Scaffold(
                topBar = { DestinationsVotingListHeader(
                    tripName = trip?.name ?: "",
                    numParticipants = trip?.users?.size ?: -1,
                    onSettingsButtonClicked = onSettingsButtonClicked,
                )},

                bottomBar = {
                    // TODO: Disable end voting for non-admin users
                    VotingBottomCard(
                        onEndVotingButtonClicked,
                        maxVotes = trip?.votesPerPerson ?: -1,
                        destinations = trip?.destinationsList ?: listOf(),
                        userViewModel = userViewModel
                    )
                }
            ) { innerPadding ->
                DestinationsVotingColumn(
                    destinations = trip?.destinationsList ?: emptyList(),
                    innerPadding = innerPadding,
                    userViewModel = userViewModel,
                    tripID = tripCode,
                    userID = userID)
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




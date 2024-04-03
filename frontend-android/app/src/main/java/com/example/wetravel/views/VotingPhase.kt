package com.example.wetravel.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wetravel.components.DestinationsVotingListHeader
import com.example.wetravel.components.VotingBottomCard
import com.example.wetravel.components.VotingDestinationEntry
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.UserViewModel


// The column of destination entries
@Composable
fun DestinationsVotingColumn(destinations: List<Destination>, innerPadding: PaddingValues, userViewModel: UserViewModel) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        itemsIndexed(destinations) { _, destination ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                VotingDestinationEntry(destination = destination, userViewModel = userViewModel)
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

    Scaffold(
        topBar = { DestinationsVotingListHeader(tripName = (sampleTrip as Resource.Success<Trip>).data.city, onSettingsButtonClicked) },
        bottomBar = {
            VotingBottomCard(
                onEndVotingButtonClicked,
                maxVotes = (sampleTrip as Resource.Success<Trip>).data.votesPerPerson,
                userVotesRemaining = (sampleTrip as Resource.Success<Trip>).data.users.find { it.userID == "local" }?.votes, userViewModel)
        }
    ) { innerPadding ->

        DestinationsVotingColumn(destinations = (sampleTrip as Resource.Success<Trip>).data.destinationsList, innerPadding = innerPadding, userViewModel = userViewModel)

    }
}




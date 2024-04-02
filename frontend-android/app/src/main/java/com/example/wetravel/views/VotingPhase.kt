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
    val sampleTrip by userViewModel.sampleTrip.observeAsState()
    // Assuming we have a list of destinations to display
//    val destinations = listOf(
//        // Add your destinations here, for example:
//        Destination(
//            UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//        // Add more destinations...
//        Destination(
//            UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//        Destination(
//            UUID.randomUUID().toString(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
//            R.drawable.sample_destination_image, totalVotes = 0, userVotes = 0, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
//        ),
//    )

    Scaffold(
        topBar = { DestinationsVotingListHeader(tripName = (sampleTrip as Resource.Success<Trip>).data.city, onSettingsButtonClicked) },
        bottomBar = { VotingBottomCard(onEndVotingButtonClicked) }
    ) { innerPadding ->

        DestinationsVotingColumn(destinations = (sampleTrip as Resource.Success<Trip>).data.destinationsList, innerPadding = innerPadding, userViewModel = userViewModel)

    }
}




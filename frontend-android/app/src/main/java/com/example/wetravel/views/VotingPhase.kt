package com.example.wetravel.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wetravel.R
import com.example.wetravel.components.DestinationsVotingListHeader
import com.example.wetravel.components.VotingBottomCard
import com.example.wetravel.components.VotingDestinationEntry
import com.example.wetravel.models.Destination
import java.util.UUID


// The column of destination entries
@Composable
fun DestinationsVotingColumn(destinations: List<Destination>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        itemsIndexed(destinations) { _, destination ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                VotingDestinationEntry(destination = destination, coins = 2)
            }

        }
    }
}

// The actual page
@Composable
fun DestinationsVotingList(
    onEndVotingButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit
) {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        Destination(
            UUID.randomUUID(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
        ),
        // Add more destinations...
        Destination(
            UUID.randomUUID(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = false, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
        ),
        Destination(
            UUID.randomUUID(), "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true, numOfVotes = 5, description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"
        ),
    )

    Scaffold(
        topBar = { DestinationsVotingListHeader(tripName = "Toronto", onSettingsButtonClicked) },
        bottomBar = { VotingBottomCard(onEndVotingButtonClicked) }
    ) { innerPadding ->
        DestinationsVotingColumn(destinations = destinations, innerPadding = innerPadding)
    }
}




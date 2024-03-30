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
import com.example.wetravel.components.DestinationEntry
import com.example.wetravel.components.DestinationsListFooter
import com.example.wetravel.components.DestinationsListHeader
import com.example.wetravel.models.Destination
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
    onSettingsButtonClicked: () -> Unit
) {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        Destination(
            UUID.randomUUID(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true, numOfVotes = 5
        ),
        // Add more destinations...
        Destination(
            UUID.randomUUID(),"MoMA 2", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = false, numOfVotes = 5
        ),
        Destination(
            UUID.randomUUID(),"MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true, numOfVotes = 5
        ),
    )

    Scaffold(
        topBar = { DestinationsListHeader(tripName = "Toronto", onSettingsButtonClicked) },
        bottomBar = { DestinationsListFooter(onAddDestinationButtonClicked, onStartVotingButtonClicked) }
    ) { innerPadding ->
        DestinationsColumn(destinations = destinations, innerPadding = innerPadding)
    }
}




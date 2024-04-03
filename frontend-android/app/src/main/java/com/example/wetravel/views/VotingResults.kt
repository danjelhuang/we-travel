package com.example.wetravel.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wetravel.R
import com.example.wetravel.components.VotingListTextFooter
import com.example.wetravel.components.VotingResultItineraryListComponent
import com.example.wetravel.components.VotingResultsHeader
import com.example.wetravel.components.Map
import com.example.wetravel.components.VotingResultsFooter
import com.example.wetravel.components.VotingResultsPathList
import com.example.wetravel.models.Destination
import java.util.UUID


// The scrollable that structures the components for this page
@Composable
fun ScrollableContent(destinations: List<Destination>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        item {
            VotingResultsHeader("New York")
        }
        itemsIndexed(destinations) { idx, destination ->
            VotingResultItineraryListComponent(idx + 1, destination)
        }
        item {
            VotingListTextFooter()
        }
        item {
            Map()
        }
        item {
            Spacer(modifier = Modifier.height((20.dp)))
        }
        itemsIndexed(destinations) { idx, destination ->
            VotingResultsPathList(idx, destination, destinations.size)
        }
        item {
            Spacer(modifier = Modifier.height((20.dp)))
        }
        item {
            VotingResultsFooter()
        }
        item {
            Spacer(modifier = Modifier.height((20.dp)))
        }

    }
}

// The actual page
@Preview(showBackground = true)
@Composable
fun VotingResultsMainScreen() {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for exampl
        Destination(
            placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
            type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
        ),
        Destination(
            placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
            type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
        ),
        Destination(
            placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
            type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
        ),
        Destination(
            placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
            type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
        ),
        Destination(
            placeId = UUID.randomUUID().toString(),name = "MoMA", address ="11 W 53rd St, New York", rating =4.6, reviewCount = 50,
            type = "attraction", imageBitmap = null, totalVotes = 0, userVotes = 0, userId = ""
        ),
    )

    Scaffold(
    ) { innerPadding ->
        ScrollableContent(destinations = destinations, innerPadding = innerPadding)
    }
}
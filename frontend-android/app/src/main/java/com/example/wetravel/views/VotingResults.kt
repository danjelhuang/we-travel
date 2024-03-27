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
            "MoMA",
            "11 W 53rd St, New York",
            "4.6",
            50,
            R.drawable.sample_destination_image,
            voted = true,
            numOfVotes = 5,
        ),
        Destination(
            "MoMA",
            "11 W 53rd St, New York",
            "4.6",
            50,
            R.drawable.sample_destination_image,
            voted = true,
            numOfVotes = 5,
        ),
        Destination(
            "MoMA",
            "11 W 53rd St, New York",
            "4.6",
            50,
            R.drawable.sample_destination_image,
            voted = true,
            numOfVotes = 5,
        ),
        Destination(
            "MoMA",
            "11 W 53rd St, New York",
            "4.6",
            50,
            R.drawable.sample_destination_image,
            voted = true,
            numOfVotes = 5,
        ),
        Destination(
            "MoMA",
            "11 W 53rd St, New York",
            "4.6",
            50,
            R.drawable.sample_destination_image,
            voted = true,
            numOfVotes = 5,
        ),
    )

    Scaffold(
    ) { innerPadding ->
        ScrollableContent(destinations = destinations, innerPadding = innerPadding)
    }
}
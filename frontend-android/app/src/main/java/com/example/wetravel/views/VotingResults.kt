package com.example.wetravel.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.UserViewModel
import java.util.UUID


// The scrollable that structures the components for this page
@Composable
fun ScrollableContent(destinations: List<Destination>, innerPadding: PaddingValues, userViewModel: UserViewModel) {
    val allTripsResource by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
    val currentTripIDResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
    val trip = (allTripsResource as Resource.Success<Map<String, Trip>>).data[(currentTripIDResource as Resource.Success<String>).data]
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        item {
            VotingResultsHeader(trip!!.city)
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
@Composable
fun VotingResultsMainScreen(userViewModel: UserViewModel) {
    val allTripsResource by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
    val currentTripIDResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)

    Scaffold(
    ) { innerPadding ->
        when (allTripsResource) {
            is Resource.Success -> {
                val allTripsMap = (allTripsResource as Resource.Success<Map<String, Trip>>).data[(currentTripIDResource as Resource.Success<String>).data]
                val destinations = allTripsMap?.destinationsList
                ScrollableContent(destinations = destinations!!, innerPadding = innerPadding, userViewModel)
            }

            else -> {
                CircularProgressIndicator()
            }
        }

    }
}
package com.example.project

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class VotingResultListItem(
    val destination: Destination,
    val voteCount: Int,
)

@Composable
fun VotingResultsHeader(tripName: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "your itinerary for",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = tripName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Voting results",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            )
        }
    }
}

@Composable
fun VotingResultItineraryListComponent(index: Int, votingListItem: VotingResultListItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(2.dp, Color(0xFF1D3557)),
                modifier = Modifier.size(45.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = index.toString(),
                        color = Color(0xFF1D3557),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.voting_coin),
                    contentDescription = "Location pin widget",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${votingListItem.voteCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        DestinationEntry(destination = votingListItem.destination)
    }
}

@Composable
fun ItineraryList(destinations: List<VotingResultListItem>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding),
    ) {
        itemsIndexed(destinations) { idx, destination ->
            VotingResultItineraryListComponent(idx + 1, destination)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VotingResultsMainScreen() {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        VotingResultListItem(
            Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, R.drawable.sample_destination_image),
            voteCount = 5,
        ),
        VotingResultListItem(
            Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, R.drawable.sample_destination_image),
            voteCount = 4,
        ),
        VotingResultListItem(
            Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, R.drawable.sample_destination_image),
            voteCount = 3,
        ),
        VotingResultListItem(
            Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, R.drawable.sample_destination_image),
            voteCount = 2,
        ),
        VotingResultListItem(
            Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, R.drawable.sample_destination_image),
            voteCount = 1,
        )
    )

    Scaffold(
        topBar = { VotingResultsHeader(tripName = "New York") }
    ) { innerPadding ->
        ItineraryList(destinations = destinations, innerPadding = innerPadding)
    }
}




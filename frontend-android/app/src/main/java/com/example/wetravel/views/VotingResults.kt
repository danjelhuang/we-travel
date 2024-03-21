package com.example.wetravel.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.components.Destination
import com.example.wetravel.components.DestinationEntry
import com.example.wetravel.components.DestinationEntrySimple
import com.example.wetravel.R

data class VotingResultListItem(
    val destination: Destination,
    val voteCount: Int,
)

val dmSansFamily = FontFamily(
    Font(
        resId = R.font.dmsans_semibold, FontWeight(600)
    )
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
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = dmSansFamily
                )
            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = tripName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = dmSansFamily
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Voting results",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = dmSansFamily
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
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(45.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = index.toString(),
                        color = Color(0xFF1D3557),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = dmSansFamily
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
                    color = Color.Black,
                    fontFamily = dmSansFamily
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        DestinationEntry(destination = votingListItem.destination)
    }
}

@Composable
fun VotingListTextFooter() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Text(
            text = "Map + Best Travel Path",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = dmSansFamily
            )
        )
    }
}

// TODO: This function will need a lot of changes once we implement the Maps API
@Composable
fun Map() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.map_placeholder),
            contentDescription = "Place-holder map for google maps integration",
            modifier = Modifier
                .fillMaxWidth()
                .size(540.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun VotingResultsPathList(idx: Int, votingListItem: VotingResultListItem, n: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column {
            DestinationEntrySimple(destination = votingListItem.destination)
            if (idx != n - 1) {
                Image(
                    painter = painterResource(id = R.drawable.path_list_connector),
                    contentDescription = "path list connector line",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp)
                        .padding(vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
fun VotingResultsFooter() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.save_icon),
                    contentDescription = "save icon"
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.send_icon),
                    contentDescription = "send icon"
                )
            }
        }
    }
}

@Composable
fun ScrollableContent(destinations: List<VotingResultListItem>, innerPadding: PaddingValues) {
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

@Preview(showBackground = true)
@Composable
fun VotingResultsMainScreen() {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        VotingResultListItem(
            Destination(
                1,
                "MoMA",
                "11 W 53rd St, New York",
                "4.6",
                50,
                R.drawable.sample_destination_image,
                voted = true
            ),
            voteCount = 5,
        ),
        VotingResultListItem(
            Destination(
                1,
                "MoMA",
                "11 W 53rd St, New York",
                "4.6",
                50,
                R.drawable.sample_destination_image,
                voted = false
            ),
            voteCount = 4,
        ),
        VotingResultListItem(
            Destination(
                1,
                "MoMA",
                "11 W 53rd St, New York",
                "4.6",
                50,
                R.drawable.sample_destination_image,
                voted = false
            ),
            voteCount = 3,
        ),
        VotingResultListItem(
            Destination(
                1,
                "MoMA",
                "11 W 53rd St, New York",
                "4.6",
                50,
                R.drawable.sample_destination_image,
                voted = false
            ),
            voteCount = 2,
        ),
        VotingResultListItem(
            Destination(
                1,
                "MoMA",
                "11 W 53rd St, New York",
                "4.6",
                50,
                R.drawable.sample_destination_image,
                voted = false
            ),
            voteCount = 1,
        )
    )

    Scaffold(
    ) { innerPadding ->
        ScrollableContent(destinations = destinations, innerPadding = innerPadding)
    }
}
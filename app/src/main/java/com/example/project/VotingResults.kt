package com.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Composable
//fun NumberIndicator(number: Int, numVotes: Int) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .size(24.dp)
//            .background(Color.Blue, shape = RoundedCornerShape(12.dp))
//    ) {
//        Text(
//            text = number.toString(),
//            color = Color.White,
//            fontSize = 12.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}


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
fun ItineraryList(destinations: List<Destination>, innerPadding: PaddingValues) {
    LazyColumn (
        modifier = Modifier
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        itemsIndexed(destinations) { _, destination ->
            DestinationEntry(destination = destination)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VotingResultsMainScreen() {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
        // Add more destinations...
        Destination(2, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
        Destination(3, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
    )

    Scaffold(
        topBar = { VotingResultsHeader(tripName = "New York") }
    ) { innerPadding ->
            ItineraryList(destinations = destinations, innerPadding = innerPadding)
    }
}




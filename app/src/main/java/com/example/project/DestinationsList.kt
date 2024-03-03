package com.example.project

import android.text.style.ClickableSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold

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
fun DestinationsListHeader(tripName: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "User1's Trip to",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Trip title
                Text(
                    text = tripName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )

                // Button Section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    // User count and button

                    Row(
                        Modifier.clickable {  }, // TODO: Non functional
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile2users),
                            contentDescription = "User icon",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "4", // TODO: ADD STATE FOR USER COUNT
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 28.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    // Settings button

                    Row(
                        Modifier.clickable { } // TODO: Non functional
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "User icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }




                }
            }

        }
    }
}

@Composable
fun DestinationsColumn(destinations: List<Destination>, innerPadding: PaddingValues) {
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
fun DestinationsList() {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        Destination(1, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
        // Add more destinations...
        Destination(2, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
        Destination(3, "MoMA", "11 W 53rd St, New York", "4.6", 50, 5),
    )

    Scaffold(
        topBar = { DestinationsListHeader(tripName = "Toronto") }
    ) { innerPadding ->
        DestinationsColumn(destinations = destinations, innerPadding = innerPadding)
    }
}




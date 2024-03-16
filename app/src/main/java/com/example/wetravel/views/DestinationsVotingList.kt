package com.example.wetravel.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.components.Destination
import com.example.wetravel.components.VotingDestinationEntry


@Composable
// TODO: This is repeated code from DestinationsList lol
fun DestinationsVotingListHeader(tripName: String, onSettingsButtonClicked: () -> Unit) {
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
                text = "destination voting",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
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
                ) {
                    // User count and button

                    Row(
                        Modifier.clickable { }, // TODO: Non functional
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
                        Modifier.clickable { onSettingsButtonClicked() } // TODO: Non functional
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


@Composable
fun VotingBottomCard(onEndVotingButtonClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFA8DADC))
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 16.dp)
        ) {

            // Voting countdown
            Column {
                Text(
                    text = "Voting begins in...",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF)
                    )
                )
                Text(
                    text = "19:04:32",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFE63946)
                    )
                )

                FilledTonalButton(
                    onClick = { onEndVotingButtonClicked() },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE63946),
                    ),
                    contentPadding = PaddingValues(horizontal = 30.dp)
                ) {
                    Text(
                        text = "End Voting",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            //
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Remaining travelCoins
                Row(

                    modifier = Modifier.fillMaxWidth()

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.coin_bunch),
                        contentDescription = "User icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy((-5).dp)
                    ) {
                        Text(
                            text = "3/5",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFFFE500)
                            )
                        )
                        Text(
                            text = "travelCoins\nremaining",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                // Finished voters
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile2users),
                        contentDescription = "User icon",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy((-5).dp)
                    ) {
                        Text(
                            text = "2/5",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF1D3557)
                            )
                        )
                        Text(
                            text = "finished voting",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DestinationsVotingList(
    onEndVotingButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit
) {
    // Assuming we have a list of destinations to display
    val destinations = listOf(
        // Add your destinations here, for example:
        Destination(
            1, "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true
        ),
        // Add more destinations...
        Destination(
            2, "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = false
        ),
        Destination(
            3, "MoMA", "11 W 53rd St, New York", "4.6", 50,
            R.drawable.sample_destination_image, voted = true
        ),
    )

    Scaffold(
        topBar = { DestinationsVotingListHeader(tripName = "Toronto", onSettingsButtonClicked) },
        bottomBar = { VotingBottomCard(onEndVotingButtonClicked) }
    ) { innerPadding ->
        DestinationsVotingColumn(destinations = destinations, innerPadding = innerPadding)
    }
}




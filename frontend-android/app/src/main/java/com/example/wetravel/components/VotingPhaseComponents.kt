package com.example.wetravel.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.models.Destination

// The header of the page
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

// The special Voting Destination Entry
@Composable
fun VotingDestinationEntry(destination: Destination, coins: Int) {
    // Could use Card instead if we don't want the elevation
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1FAEE),
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = destination.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rating_star),
                        contentDescription = "Location pin widget",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${destination.rating} "
                    )
                    Text(
                        text = "(${destination.reviewCount})",
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "TravelCoin",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " $coins",
                        fontSize = 14.sp
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            if (destination.voted) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy((-8).dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "TravelCoin",
                        modifier = Modifier.size(40.dp)
                    )
                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE63946),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 3.dp),
                    ) {
                        Text(
                            text = "Remove",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            )
                        )
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vote_icon),
                        contentDescription = "Vote with TravelCoin",
                        modifier = Modifier.size(40.dp)
                    )
                    Row {
                        Text(
                            text = "Vote",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            )
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }

                }
            }

            Spacer(modifier = Modifier.width(10.dp))


            Image(
                painter = painterResource(id = destination.imageResId),
                contentDescription = "${destination.name} image",
                modifier = Modifier
                    .size(65.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

// Footer of the VotingPhase
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

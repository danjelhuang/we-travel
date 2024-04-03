package com.example.wetravel.components

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.TripUpdateRequest
import com.example.wetravel.models.UserViewModel

// The header of the page
@Composable
// TODO: This is repeated code from DestinationsList lol
fun DestinationsVotingListHeader(tripName: String, numParticipants: Int, onSettingsButtonClicked: () -> Unit) {
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
                            text = numParticipants.toString(),
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
fun VotingDestinationEntry(
    destination: Destination,
    userViewModel: UserViewModel,
    tripID: String,
    userID: String
) {
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
            .defaultMinSize(minHeight = 110.dp)

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            // Info column
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
                        text = " ${destination.totalVotes}",
                        fontSize = 14.sp
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            // Button Columns

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    // TravelCoin and Count
                    if (destination.userVotes > 0) {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "TravelCoin",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        userViewModel.addVote(tripID = tripID, userID = userID, placeID = destination.placeId)
                                    }
                            )
                            Text(
                                text = destination.userVotes.toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        // Decrease vote button
                        FilledTonalButton(
                            onClick = {
                                userViewModel.removeVote(tripID = tripID, userID = userID, placeID = destination.placeId)
                            },
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
                                text = "Decrease",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                )
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .clickable {
                                    userViewModel.addVote(tripID = tripID, userID = userID, placeID = destination.placeId)
                                }

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vote_icon),
                                contentDescription = "Vote with TravelCoin",
                                modifier = Modifier.size(50.dp)
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

                }




            Spacer(modifier = Modifier.width(10.dp))

            // TODO: LOAD BITMAP IMAGE
//            Image(
//                painter = painterResource(id = destination.imageResId),
//                contentDescription = "${destination.name} image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(65.dp)
//                    .clip(RoundedCornerShape(8.dp))
//
//            )
        }
    }
}

// Footer of the VotingPhase
@Composable
fun VotingBottomCard(onEndVotingButtonClicked: () -> Unit, maxVotes: Int, destinations: List<Destination>, userViewModel: UserViewModel) {
    val formattedUserVotes: Int = maxVotes - destinations.sumOf { it.userVotes }
    val currentTripIDResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
    val allTripsResource by userViewModel.allTrips.observeAsState(initial = Resource.Loading)
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
                    text = "Cast your votes!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                FilledTonalButton(
                    onClick =
                    {
                        handleEndVotingButtonClick(currentTripIDResource, allTripsResource, userViewModel)
                        onEndVotingButtonClicked()
                    },
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

            Spacer(modifier = Modifier.width(50.dp))
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

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy((-5).dp)
                    ) {
                        Text(
                            text = "${formattedUserVotes}/${maxVotes}",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFFFE500)
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
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
            }
        }
    }
}

private fun handleEndVotingButtonClick(currentTripIDResource: Resource<String>, allTripsResource: Resource<Map<String, Trip>>, userViewModel: UserViewModel) {
    var updatedTrip = TripUpdateRequest()
    if (currentTripIDResource is Resource.Success && allTripsResource is Resource.Success) {
        val currentTripID = currentTripIDResource.data
        val currentTrip = allTripsResource.data[currentTripID]
        updatedTrip = TripUpdateRequest(
            tripID = currentTrip!!.tripID,
            name = currentTrip.name,
            city = currentTrip.city,
            finalDestinationCount = currentTrip.finalDestinationCount,
            votesPerPerson = currentTrip.votesPerPerson,
            phase = "Ended"
        )
    }
    userViewModel.updateTrip(updatedTrip)
    userViewModel.updateFinalDestinations((currentTripIDResource as Resource.Success<String>).data)
}

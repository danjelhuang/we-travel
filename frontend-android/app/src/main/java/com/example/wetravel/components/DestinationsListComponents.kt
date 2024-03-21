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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
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


// Header of the Destinations List page
@Composable
fun DestinationsListHeader(tripName: String, onSettingsButtonClicked: () -> Unit) {
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

// Footer of DestinationsList Page
@Composable
fun DestinationsListFooter(onAddDestinationButtonClicked: () -> Unit, onStartVotingButtonClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFA8DADC))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
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
                    text = "1d 14hrs",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFE63946)
                    )
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val mainButtonColor = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE63946),
                    contentColor = Color.White
                )

                // Add destination button
                FilledTonalButton(
                    onClick = { onAddDestinationButtonClicked() },
                    shape = RoundedCornerShape(20),
                    colors = mainButtonColor,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_square),
                        contentDescription = "User icon",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Destination",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                FilledTonalButton(
                    onClick = { onStartVotingButtonClicked() },
                    shape = RoundedCornerShape(20),
                    colors = mainButtonColor,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Start Voting",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    )
                }
            }
            // TODO: Move this out
        }
    }
}


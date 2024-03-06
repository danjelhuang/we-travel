package com.example.project.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R


data class Destination(
    val id: Int,
    val name: String,
    val address: String,
    val rating: String,
    val reviewCount: Int,
    val imageResId: Int,
    val voted: Boolean
)

@Composable
fun DestinationEntrySimple(destination: Destination) {
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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Column {
                Text(
                    text = destination.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    RatingBar(rating = destination.rating.toFloat()) // Assuming you have a custom RatingBar composable
                    Image(
                        painter = painterResource(id = R.drawable.destination_pin),
                        contentDescription = "Location pin widget",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${destination.address}",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DestinationEntry(destination: Destination) {
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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Column {
                Text(
                    text = destination.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    RatingBar(rating = destination.rating.toFloat()) // Assuming you have a custom RatingBar composable
                    Image(
                        painter = painterResource(id = R.drawable.destination_pin),
                        contentDescription = "Location pin widget",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${destination.address}",
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    RatingBar(rating = destination.rating.toFloat()) // Assuming you have a custom RatingBar composable
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
            }


            Spacer(modifier = Modifier.weight(1f))

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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ){
            Column {
                Text(
                    text = destination.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    RatingBar(rating = destination.rating.toFloat()) // Assuming you have a custom RatingBar composable
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
//                    RatingBar(rating = destination.rating.toFloat()) // Assuming you have a custom RatingBar composable
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
                    Row{
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


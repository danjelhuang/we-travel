package com.example.project.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
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


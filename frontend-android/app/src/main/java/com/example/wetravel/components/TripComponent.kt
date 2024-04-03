package com.example.wetravel.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.models.Trip

@Composable
fun TripComponent(trip: Trip, isAdmin: Boolean, phase: String, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation( defaultElevation = 2.dp ),
        colors = CardDefaults.cardColors( containerColor = Color(0xFFF1FAEE) ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = trip.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.destination_pin),
                            contentDescription = "Location pin Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = " ${trip.city}", fontSize = 12.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.profile2users),
                            contentDescription = "Participants Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = " ${trip.users.size}", fontSize = 12.sp
                        )
                    }
                    if (isAdmin) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.crown_icon),
                                contentDescription = "Admin Icon",
                                modifier = Modifier.size(16.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )

                            Text(
                                text = "Admin", fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .width(120.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
                Arrangement.Center,
                Alignment.CenterVertically
            ) {
                Text(text = phase, textAlign = TextAlign.Center)
            }
        }
    }
}
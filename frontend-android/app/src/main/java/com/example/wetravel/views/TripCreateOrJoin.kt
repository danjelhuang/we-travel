package com.example.wetravel.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wetravel.R
import com.example.wetravel.components.LogoTopAppBar
import com.example.wetravel.components.Trip
import com.example.wetravel.components.TripComponent
import com.example.wetravel.models.Resource
import com.example.wetravel.models.UserViewModel
import com.example.wetravel.presentation.sign_in.UserData

// TODO: link this with data model
val trips = listOf(
    Trip(
        1,
        "besties takeover ny fjwaieofjiwaoejfioawefjiowjviowejviowejviowahviowehiovawjiova",
        "New York",
        listOf(0, 1, 2, 3, 4),
        0,
        R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1,
        "weekend trip",
        "Toronto",
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
        0,
        R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
    Trip(
        1, "end of term trip", "Tokyo", listOf(0, 1, 2), 0, R.drawable.sample_destination_image
    ),
)
//val trips: List<Trip> = listOf()

@Composable
fun LandingPage(
    userData: UserData?,
    onSignOut: () -> Unit,
    onCreateTripButtonClicked: () -> Unit,
    onJoinTripButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {

    Scaffold(topBar = {
        LogoTopAppBar()
    }) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            ProfileSection(userData, onSignOut)
            TripList(trips, Modifier.weight(1f))
            CreateOrJoinTripBottomBar(onCreateTripButtonClicked, onJoinTripButtonClicked)
        }
    }
}

@Composable
fun TripList(
    trips: List<Trip>, modifier: Modifier
) {
    if (trips.isNotEmpty()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = modifier.padding(10.dp)
        ) {
            itemsIndexed(trips) { _, trip ->
                TripComponent(trip = trip)
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Create or Join a trip")
        }
    }
    
}

@Composable
fun ProfileSection(
    userData: UserData?,
    onSignOut: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.sample_destination_image),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            modifier = Modifier.fillMaxHeight()
        ) {
            if (userData?.username != null) {
                Text(
                    text = "Hello ${userData.username}!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    text = "Hello user!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = { onSignOut() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .height(30.dp)
                    .width(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "sign out", fontFamily = dmSansFamily, fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun CreateOrJoinTripBottomBar(
    onCreateTripButtonClicked: () -> Unit, onJoinTripButtonClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onCreateTripButtonClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(60.dp)
                .width(150.dp)
                .padding(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "create trip", fontFamily = dmSansFamily, fontSize = 16.sp
            )
        }
        Button(
            onClick = { onJoinTripButtonClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(60.dp)
                .width(150.dp)
                .padding(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "join trip", fontFamily = dmSansFamily, fontSize = 16.sp
            )
        }
    }
}

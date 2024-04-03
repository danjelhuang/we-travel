import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.TripUpdateRequest
import com.example.wetravel.models.User
import com.example.wetravel.models.UserViewModel

@Composable
fun TripCreateForm(
    onButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    val currentUserResource by userViewModel.user.observeAsState(initial = Resource.Loading)
    // TODO: Convert all of these fields to the values from ViewModel and observe them for state changes
    val tripName = remember { mutableStateOf(TextFieldValue()) }
    val destinationCity = remember { mutableStateOf(TextFieldValue()) }
    val finalDestinationCount = remember { mutableStateOf(TextFieldValue()) }
    val buttonText = "create"
    val dmSansFamily = FontFamily(
        Font(
            resId = R.font.dmsans_semibold, FontWeight(600)
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        LogoTopBar()

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "create trip",
            fontSize = 48.sp,
            fontFamily = dmSansFamily,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(50.dp))

        InputField(
            label = "trip name",
            value = tripName.value,
            onValueChange = { tripName.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            label = "destination city",
            value = destinationCity.value,
            onValueChange = { destinationCity.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))
        IntInputField(
            label = "final destination count",
            value = finalDestinationCount.value,
            onValueChange = { finalDestinationCount.value = it },
            dmSansFamily
        )

        Spacer(modifier = Modifier.height(56.dp))

        when (currentUserResource) {
            is Resource.Success -> {
                val currentUser = (currentUserResource as Resource.Success<User>).data.userID
                Button(
                    onClick = {
                        handleCreateButtonClick(
                            tripName.value.text,
                            destinationCity.value.text,
                            finalDestinationCount.value.text,
                            userViewModel = userViewModel,
                            user = currentUser
                        )
                        onButtonClicked()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(buttonText, color = Color.White, fontFamily = dmSansFamily)
                }
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                val errorMessage = (currentUserResource as Resource.Error).message
                // Display the error message
                Text(text = "Error: $errorMessage")
            }
        }
    }
}

@Composable
fun TripEditForm(
    onButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    val currentUserResource by userViewModel.user.observeAsState(initial = Resource.Loading)
    val currentTripID by userViewModel.tripCode.observeAsState(initial = Resource.Loading)

    val allTrips by userViewModel.allTrips.observeAsState(initial = Resource.Loading)

    val currentTrip =
        (allTrips as Resource.Success<Map<String, Trip>>).data[(currentTripID as Resource.Success<String>).data]
    val tripName = remember { mutableStateOf(TextFieldValue(currentTrip?.name ?: "")) }
    val destinationCity = remember { mutableStateOf(TextFieldValue(currentTrip?.city ?: "")) }
    val finalDestinationCount = remember {
        mutableStateOf(
            TextFieldValue(
                currentTrip?.finalDestinationCount.toString()
            )
        )
    }
    val buttonText = "save changes"
    val dmSansFamily = FontFamily(
        Font(
            resId = R.font.dmsans_semibold, FontWeight(600)
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        LogoTopBar()

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "edit trip",
            fontSize = 48.sp,
            fontFamily = dmSansFamily,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(50.dp))

        InputField(
            label = "trip name",
            value = tripName.value,
            onValueChange = { tripName.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            label = "destination city",
            value = destinationCity.value,
            onValueChange = { destinationCity.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))
        IntInputField(
            label = "final destination count",
            value = finalDestinationCount.value,
            onValueChange = { finalDestinationCount.value = it },
            dmSansFamily
        )

        when (currentTripID) {
            is Resource.Success -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "trip code: ${(currentTripID as Resource.Success<String>).data}",
                    fontFamily = dmSansFamily,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            } else -> {
                Log.d("TripEditForm", "Current Trip ID State is not Success")
            }
        }


        Spacer(modifier = Modifier.height(56.dp))

        when (currentUserResource) {

            is Resource.Success -> {
                Button(
                    onClick = {
                        handleEditButtonClick(
                            tripName.value.text,
                            destinationCity.value.text,
                            finalDestinationCount.value.text,
                            userViewModel = userViewModel,
                        )
                        onButtonClicked()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(buttonText, color = Color.White, fontFamily = dmSansFamily)
                }
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                val errorMessage = (currentUserResource as Resource.Error).message
                // Display the error message
                Text(text = "Error: $errorMessage")
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    fontFamily: FontFamily
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = fontFamily) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 48.dp)
    )
}

@Composable
fun IntInputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    fontFamily: FontFamily
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = fontFamily) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 48.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoTopBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_we),
                contentDescription = "WeTravelLogo",
                modifier = Modifier.size(100.dp)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}


// Button On-click functions
private fun handleCreateButtonClick(
    tripName: String,
    destinationCity: String,
    finalDestinationCount: String,
    userViewModel: UserViewModel,
    user: String
) {
    val tripData = Trip(
        name = tripName,
        city = destinationCity,
        finalDestinationCount = finalDestinationCount.toInt(),
        votesPerPerson = finalDestinationCount.toInt(),
        adminUserID = user,
        phase = "Adding"
    )
    userViewModel.createTrip(tripData)
}


private fun handleEditButtonClick(
    tripName: String,
    destinationCity: String,
    finalDestinationCount: String,
    userViewModel: UserViewModel,
) {

    val currentTripID = userViewModel.tripCode.value
    if (currentTripID is Resource.Success) {
        val tripID = currentTripID.data
        val tripData = TripUpdateRequest(
            tripID = tripID,
            name = tripName,
            city = destinationCity,
            finalDestinationCount = finalDestinationCount.toInt(),
            votesPerPerson = finalDestinationCount.toInt()
        )
        userViewModel.updateTrip(tripData)
    }
}

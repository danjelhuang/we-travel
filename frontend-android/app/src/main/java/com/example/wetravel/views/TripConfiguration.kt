import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.models.Trip
import com.example.wetravel.models.UserViewModel

@Composable
fun TripConfigurationForm(
    title: String,
    onButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    // TODO: Convert all of these fields to the values from ViewModel and observe them for state changes
    val tripName = remember { mutableStateOf(TextFieldValue()) }
    val destinationCity = remember { mutableStateOf(TextFieldValue()) }
    val finalDestinationCount = remember { mutableStateOf(TextFieldValue()) }
    val numberOfVotesPerPerson = remember { mutableStateOf(TextFieldValue()) }
    val allowAnonymousVoting = remember { mutableStateOf(false) }
    val buttonText = if (title == "create") "create" else "save changes"
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
            text = "$title trip",
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
        InputField(
            label = "final destination count",
            value = finalDestinationCount.value,
            onValueChange = { finalDestinationCount.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            label = "number of votes/person",
            value = numberOfVotesPerPerson.value,
            onValueChange = { numberOfVotesPerPerson.value = it },
            dmSansFamily
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Switch(
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                ),
                checked = allowAnonymousVoting.value,
                onCheckedChange = { allowAnonymousVoting.value = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "allow anonymous voting",
                color = MaterialTheme.colorScheme.primary,
                fontFamily = dmSansFamily
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                handleButtonClick(
                    tripName.value.text,
                    destinationCity.value.text,
                    numberOfVotesPerPerson.value.text,
                    finalDestinationCount.value.text,
                    userViewModel = userViewModel
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
private fun handleButtonClick(
    tripName: String,
    destinationCity: String,
    finalDestinationCount: String,
    numberOfVotesPerPerson: String,
    userViewModel: UserViewModel
) {
    val tripData = Trip(
        name = tripName,
        city = destinationCity,
        finalDestinationCount = finalDestinationCount.toInt(),
        votesPerPerson = numberOfVotesPerPerson.toInt(),
        votingPhase = "default"
    )
    userViewModel.createTrip(tripData)
}

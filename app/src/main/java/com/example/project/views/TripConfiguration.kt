import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TripConfigurationForm(title: String) {
    val tripName = remember { mutableStateOf(TextFieldValue()) }
    val destinationCity = remember { mutableStateOf(TextFieldValue()) }
    val finalDestinationCount = remember { mutableStateOf(TextFieldValue()) }
    val numberOfVotesPerPerson = remember { mutableStateOf(TextFieldValue()) }
    var allowAnonymousVoting = remember { mutableStateOf(false) }
    val buttonText = if (title == "create") "create" else "save changes"

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight()) {
        Text(
            text = "$title trip",
            fontSize = 48.sp,
            color = Color(0x1D, 0x35, 0x57),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 48.dp)
        )
        
        Spacer(modifier = Modifier.height(40.dp))

        InputField(label = "trip name", value = tripName.value, onValueChange = { tripName.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        InputField(label = "destination city", value = destinationCity.value, onValueChange = { destinationCity.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        InputField(label = "final destination count", value = finalDestinationCount.value, onValueChange = { finalDestinationCount.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        InputField(label = "number of votes/person", value = numberOfVotesPerPerson.value, onValueChange = { numberOfVotesPerPerson.value = it })
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Switch(
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(red = 69, green = 123, blue = 157)
                ),
                checked = allowAnonymousVoting.value,
                onCheckedChange = { allowAnonymousVoting.value = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("allow anonymous voting", color = Color(0x1D, 0x35, 0x57))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  /* TODO */ },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 69, green = 123, blue = 157)
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)){
            Text(buttonText, color = Color.White)
        }
    }
}

@Composable
fun InputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 48.dp)
    )
}
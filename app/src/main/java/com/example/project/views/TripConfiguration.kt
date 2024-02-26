import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

    Column(modifier = Modifier.padding(16.dp).fillMaxHeight()) {
        Text(
            text = "$title trip",
            fontSize = 24.sp,
            color = Color.Blue,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "trip name", value = tripName.value, onValueChange = { tripName.value = it }, modifier = Modifier.weight(1f))
        InputField(label = "destination city", value = destinationCity.value, onValueChange = { destinationCity.value = it }, modifier = Modifier.weight(1f))
        InputField(label = "final destination count", value = finalDestinationCount.value, onValueChange = { finalDestinationCount.value = it }, modifier = Modifier.weight(1f))
        InputField(label = "number of votes/person", value = numberOfVotesPerPerson.value, onValueChange = { numberOfVotesPerPerson.value = it }, modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Switch(
                checked = allowAnonymousVoting.value,
                onCheckedChange = { allowAnonymousVoting.value = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("allow anonymous voting", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {  /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Create")
        }
    }
}

@Composable
fun InputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(text = label, color = Color.Black, fontSize = 16.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp)
        )
    }
}
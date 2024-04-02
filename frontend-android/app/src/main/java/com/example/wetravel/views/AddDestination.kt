import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.wetravel.R
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.launch
import com.example.wetravel.models.UserViewModel

val dmSansFamily = FontFamily(
    Font(
        resId = R.font.dmsans_semibold, FontWeight(600)
    )
)

//TODO: REMOVE THIS tempTripID - just for testing
val tempTripID = "1UM8I";

@Composable
fun AddDestinations(
    onAddDestinationButtonClicked: () -> Unit,
    onLeaveButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    PlacesClientManager.initialize(context)
    val placesClient = PlacesClientManager.getPlacesClient(context)
    val scope = rememberCoroutineScope()

    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    var predictions by remember {
        mutableStateOf<List<AutocompletePrediction>>(emptyList())
    }

    var selectedPredictionId by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .padding(
            horizontal = 20.dp,
            vertical = 10.dp
        )
        .fillMaxWidth()
        .clickable(interactionSource = interactionSource, indication = null, onClick = {
            expanded = false
        })
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_we),
                contentDescription = "WeTravelLogo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(
                modifier = Modifier
                    .size(20.dp)
            )

            Row(
                Modifier.clickable{onLeaveButtonClicked()} ,
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = "User icon",
                    modifier = Modifier.size(32.dp),

                    )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        Text(modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = "Add Destinations",
            fontFamily = dmSansFamily,
            fontSize = 28.sp,
            onTextLayout = {})
        Spacer(modifier = Modifier.height(5.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(heightTextFields)
                    .border(
                        width = 3.dp,
                        color = Color(red = 69, green = 123, blue = 157),
                        shape = RoundedCornerShape(10.dp)
                    )

                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },

                    value = category, onValueChange = {
                        category = it
                        expanded = false
                        if (it.isNotEmpty()) {
                            scope.launch {
                                fetchPredictions(placesClient, it) { predictionsList ->
                                    predictions = predictionsList
                                }
                            }
                        } else {
                            predictions = emptyList()
                        }

                    }, placeholder = {
                        Text(
                            "Enter a Destination", onTextLayout = {}, fontFamily = dmSansFamily
                        )
                    }, textStyle = TextStyle(
                        color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
                    ), keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ), singleLine = true, trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "search",
                                tint = Color.Black
                            )
                        }
                    })
            }

            AnimatedVisibility(visible = expanded) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .width(textFieldSize.width.dp)
                        .border(
                            width = 3.dp,
                            color = Color(red = 69, green = 123, blue = 157),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    items(predictions) { prediction ->
                        Text(text = prediction.getPrimaryText(null)
                            .toString() + ", " + prediction.getSecondaryText(null).toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    category = prediction
                                        .getPrimaryText(null)
                                        .toString() + ", "
                                    prediction
                                        .getSecondaryText(null)
                                        .toString()
                                    selectedPredictionId = prediction.placeId
                                    expanded = false
                                    predictions = emptyList()
                                }
                                .padding(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                // TODO: SEND REQUEST TO BACKEND WITH TRIP ID AND selectedPrediction
                if (selectedPredictionId !== "") {
                    userViewModel.addDestinations(tempTripID, selectedPredictionId)
                    onAddDestinationButtonClicked()
                    selectedPredictionId = ""
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 69, green = 123, blue = 157)
            ),
            enabled = selectedPredictionId !== ""
        ) {
            Text(
                "Add", fontFamily = dmSansFamily
            )
        }
    }
}

fun fetchPredictions(
    placesClient: com.google.android.libraries.places.api.net.PlacesClient,
    query: String,
    onComplete: (List<AutocompletePrediction>) -> Unit
) {
    val token = AutocompleteSessionToken.newInstance()
    // TODO: UPDATE QUERY TO INCLUDE CITY AFTER VIEWMODEL IS DONE
    val request =
        FindAutocompletePredictionsRequest.builder().setQuery(query).setSessionToken(token).build()
    placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
        onComplete(response.autocompletePredictions)
    }.addOnFailureListener { exception ->
        println("failed")
    }
}
